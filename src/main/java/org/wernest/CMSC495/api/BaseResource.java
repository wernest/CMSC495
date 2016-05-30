package org.wernest.CMSC495.api;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

/**
 * Created by will on 5/30/16.
 */
public class BaseResource {

    @Context
    SecurityContext securityContext;
    @Context
    HttpServletRequest servletRequest;

    String username;

    protected String setUser(){
        Principal principal = securityContext.getUserPrincipal();
        username = principal.getName();
        return username;
    }
}
