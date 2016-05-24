package org.wernest.CMSC495.authentication;

import org.wernest.CMSC495.dao.UserTokenDAO;
import org.wernest.CMSC495.entities.UserToken;

import javax.annotation.Priority;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.Principal;
import java.util.Date;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    /**
     * Main method for the filter to validate each
     * api request. Will attempt to retrieve the token
     * from the request, updates the last accessed field
     * and then places the user in the security context for
     * the api to use.
     */
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        // Get the HTTP Authorization header from the request

        String authorizationHeader =
                requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        // Check if the HTTP Authorization header is present and formatted correctly 
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            authorizationHeader = "Bearer " + requestContext.getCookies().get("CMSC495").getValue();
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                throw new NotAuthorizedException("Authorization header must be provided");
            }
        }

        // Extract the token from the HTTP Authorization header
        String token = authorizationHeader.substring("Bearer".length()).trim();

        try {

            // Validate the token
            validateToken(token);

        } catch (Exception e) {
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED).build());
        }

        final String username = getUser(token);

        if("".equals(username) || username == null) {
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED).build());
        }

        requestContext.setSecurityContext(new SecurityContext() {

            @Override
            public Principal getUserPrincipal() {

                return new Principal() {

                    @Override
                    public String getName() {
                        return username;
                    }
                };
            }

            @Override
            public boolean isUserInRole(String role) {
                return true;
            }

            @Override
            public boolean isSecure() {
                return false;
            }

            @Override
            public String getAuthenticationScheme() {
                return null;
            }
        });
    }

    /**
     * Token validator. Gets the token object out of the
     * database and makes sure it is valid.
     * @param token token string
     * @throws Exception
     */
    private void validateToken(String token) throws Exception {
        UserTokenDAO userTokenDAO = new UserTokenDAO();
        UserToken userToken = userTokenDAO.getByToken(token);
        if(userToken!=null){
            if(userToken.isValid()) {
                userToken.setDate(new Date(System.currentTimeMillis()));
                userTokenDAO.update(userToken);
                return;
            }else { //Expired token, delete
                userTokenDAO.delete(userToken);
            }
        }
        throw new Exception("Token not found or expired");
    }

    /**
     * Uses the token to get the User it is assigned to so
     * we can place that user on our security context so that
     * the subsequent endpoints know the user object.
     * @param token token string
     * @return user name this token belongs to
     */
    private String getUser(String token){
        UserTokenDAO userTokenDAO = new UserTokenDAO();
        UserToken userToken = userTokenDAO.getByToken(token);
        if(userToken!=null){
            if(userToken.isValid()) {
                return userToken.getUser().getUsername();
            }
        }
        return null;
    }
}