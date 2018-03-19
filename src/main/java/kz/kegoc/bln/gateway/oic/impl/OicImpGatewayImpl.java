package kz.kegoc.bln.gateway.oic.impl;

import kz.kegoc.bln.gateway.oic.OicImpGateway;
import kz.kegoc.bln.gateway.oic.TelemetryRaw;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OicImpGatewayImpl implements OicImpGateway {
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    @Override
    public OicImpGateway config(OicConfigImpl config) {
        this.config = config;
        return this;
    }

    @Override
    public OicImpGateway points(List<Long> points) {
        this.points = points;
        return this;
    }

    @Override
    public List<TelemetryRaw> request(LocalDateTime requestedTime) throws Exception {
        String requestedTimeStr = requestedTime.format(timeFormatter);

        String pointsStr = points.stream()
            .map(t -> t.toString())
            .collect(Collectors.joining(","));

        List<TelemetryRaw> telemetry = new ArrayList<>();
        try (Connection con = new OicConnectionImpl(config).getConnection()) {
            try (PreparedStatement pst = con.prepareStatement("exec master..xp_gettidata2 1, '" + requestedTimeStr + "', " + pointsStr)) {
                try (ResultSet rs = pst.executeQuery()) {
                    while (rs.next()) {
                        Long logti = rs.getLong(1);
                        Double val = rs.getDouble(2);
                        telemetry.add(new TelemetryRaw(logti, val));
                    }
                }
            }
        }

        return telemetry;
    }

    private OicConfigImpl config;
    private List<Long> points;
}