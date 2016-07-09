package org.wernest.CMSC495.api;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

/**
 * Base class for setting the user object based on the received
 * security context from the grizzly container.
 *
 * The user object is set in the filter as the incoming request
 * is being processed.
 *
 * @see org.wernest.CMSC495.authentication.AuthenticationFilter
 *
 */
public class BaseResource {

    @Context
    SecurityContext securityContext;
    @Context
    HttpServletRequest servletRequest;

    String username;

    /**
     * Method to set and return the username
     * @return username
     */
    protected String setUser(){
        Principal principal = securityContext.getUserPrincipal();
        username = principal.getName();
        return username;
    }
}
