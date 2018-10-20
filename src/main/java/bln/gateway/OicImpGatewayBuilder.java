package bln.gateway;

import bln.entity.ArcType;
import java.util.List;

public interface OicImpGatewayBuilder {
    OicImpGatewayBuilder config(OicConfig config);
    OicImpGatewayBuilder points(List<Long> points);
    OicImpGatewayBuilder arcType(ArcType arcType);
    OicImpGateway build() throws Exception;
}