package org.keycloak.protocol.cas;

import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.keycloak.events.EventBuilder;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.protocol.cas.endpoints.AuthorizationEndpoint;
import org.keycloak.protocol.cas.endpoints.LogoutEndpoint;
import org.keycloak.protocol.cas.endpoints.SamlValidateEndpoint;
import org.keycloak.protocol.cas.endpoints.ServiceValidateEndpoint;
import org.keycloak.protocol.cas.endpoints.ValidateEndpoint;
import org.keycloak.services.resources.RealmsResource;

public class CASLoginProtocolService {
    private RealmModel realm;
    private EventBuilder event;

    @Context
    private KeycloakSession session;

    @Context
    private HttpHeaders headers;

    @Context
    private HttpRequest request;

    public CASLoginProtocolService(RealmModel realm, EventBuilder event) {
        this.realm = realm;
        this.event = event;
    }

    public static UriBuilder serviceBaseUrl(UriBuilder baseUriBuilder) {
        return baseUriBuilder.path(RealmsResource.class).path("{realm}/protocol/" + CASLoginProtocol.LOGIN_PROTOCOL);
    }

    @Path("login")
    public Object login() {
        AuthorizationEndpoint endpoint = new AuthorizationEndpoint(realm, event);
        ResteasyProviderFactory.getInstance().injectProperties(endpoint);
        return endpoint;
    }

    @Path("logout")
    public Object logout() {
        LogoutEndpoint endpoint = new LogoutEndpoint(realm, event);
        ResteasyProviderFactory.getInstance().injectProperties(endpoint);
        return endpoint;
    }

    @Path("validate")
    public Object validate() {
        ValidateEndpoint endpoint = new ValidateEndpoint(realm, event);
        ResteasyProviderFactory.getInstance().injectProperties(endpoint);
        return endpoint;
    }

    @Path("samlValidate")
    public Object validateSaml11() {
        SamlValidateEndpoint endpoint = new SamlValidateEndpoint(realm, event);
        ResteasyProviderFactory.getInstance().injectProperties(endpoint);
        return endpoint;
    }

    @Path("serviceValidate")
    public Object serviceValidate() {
        ServiceValidateEndpoint endpoint = new ServiceValidateEndpoint(realm, event);
        ResteasyProviderFactory.getInstance().injectProperties(endpoint);
        return endpoint;
    }

    @Path("proxyValidate")
    public Object proxyValidate() {
        //TODO implement
        return serviceValidate();
    }

    @Path("proxy")
    public Object proxy() {
        return Response.serverError().entity("Not implemented").build();
    }

    @Path("p3/serviceValidate")
    public Object p3ServiceValidate() {
        return serviceValidate();
    }

    @Path("p3/proxyValidate")
    public Object p3ProxyValidate() {
        return proxyValidate();
    }
}
