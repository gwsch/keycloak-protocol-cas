package org.keycloak.protocol.cas.mappers;

import java.util.Map;

import org.keycloak.models.ClientSessionContext;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.ProtocolMapperModel;
import org.keycloak.models.UserSessionModel;

public interface CASAttributeMapper {
    void setAttribute(Map<String, Object> attributes, ProtocolMapperModel mappingModel, UserSessionModel userSession,
                      KeycloakSession session, ClientSessionContext clientSessionCtx);
}
