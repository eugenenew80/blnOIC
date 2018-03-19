package kz.kegoc.bln.gateway.oic;

import lombok.Data;

@Data
public class TelemetryRaw {
    public TelemetryRaw(Long logti, Double val) {
        this.logti = logti;
        this.val = val;
    }

    private final Long logti;
    private final Double val;
}
