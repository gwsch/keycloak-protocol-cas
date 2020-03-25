package org.keycloak.protocol.cas.mappers;

import java.util.Map;

import org.keycloak.Config;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.models.ProtocolMapperModel;
import org.keycloak.protocol.ProtocolMapper;
import org.keycloak.protocol.cas.CASLoginProtocol;
import org.keycloak.protocol.oidc.mappers.OIDCAttributeMapperHelper;

public abstract class AbstractCASProtocolMapper implements ProtocolMapper, CASAttributeMapper {
    public static final String TOKEN_MAPPER_CATEGORY = "Token mapper";

    @Override
    public String getProtocol() {
        return CASLoginProtocol.LOGIN_PROTOCOL;
    }

    @Override
    public void close() {
    }

    @Override
    public final ProtocolMapper create(KeycloakSession session) {
        throw new RuntimeException("UNSUPPORTED METHOD");
    }

    @Override
    public void init(Config.Scope config) {
    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {
    }

    @Override
    public String getDisplayCategory() {
        return TOKEN_MAPPER_CATEGORY;
    }

    protected void setMappedAttribute(Map<String, Object> attributes, ProtocolMapperModel mappingModel, Object attributeValue) {
        setPlainAttribute(attributes, mappingModel, OIDCAttributeMapperHelper.mapAttributeValue(mappingModel, attributeValue));
    }

    protected void setPlainAttribute(Map<String, Object> attributes, ProtocolMapperModel mappingModel, Object attributeValue) {
        String protocolClaim = mappingModel.getConfig().get(OIDCAttributeMapperHelper.TOKEN_CLAIM_NAME);
        if (protocolClaim == null || attributeValue == null) {
            return;
        }
        attributes.put(protocolClaim, attributeValue);
    }
}
