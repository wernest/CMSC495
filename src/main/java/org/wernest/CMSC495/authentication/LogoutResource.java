package org.wernest.CMSC495.authentication;

import org.wernest.CMSC495.dao.UserEntityDAO;
import org.wernest.CMSC495.dao.UserTokenDAO;
import org.wernest.CMSC495.entities.UserEntity;
import org.wernest.CMSC495.entities.UserToken;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

/**
 * Separate resource for handling logout.
 */
@Path("logout")
public class LogoutResource {


    /**
     * Log out method.
     *
     * Secured because a user must be logged in to log out
     * @param securityContext context with the username to log out
     * @return Response with an new cookie clearing the token previously used.
     */
    @GET
    @Secured
    public Response logout(@Context SecurityContext securityContext){
        UserTokenDAO userTokenDAO = new UserTokenDAO();
        UserEntityDAO userEntityDAO = new UserEntityDAO();

        Principal principal = securityContext.getUserPrincipal();
        UserEntity userEntity = userEntityDAO.getByUsername(principal.getName());

        UserToken oldToken = userTokenDAO.getByUser(userEntity.getID());
        userTokenDAO.delete(oldToken);
        NewCookie newCookie = new NewCookie("CMSC495", "", "", "localhost", "", 60*60*24, false);
        return Response.ok().cookie(newCookie).build();
    }
}
