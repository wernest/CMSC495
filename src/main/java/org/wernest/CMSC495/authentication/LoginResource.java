package org.wernest.CMSC495.authentication;


import org.wernest.CMSC495.dao.UserEntityDAO;
import org.wernest.CMSC495.dao.UserTokenDAO;
import org.wernest.CMSC495.entities.UserCredentials;
import org.wernest.CMSC495.entities.UserEntity;
import org.wernest.CMSC495.entities.UserToken;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by will on 5/22/16.
 */
@Path("/login")
public class LoginResource {

    @POST
    @Produces(MediaType.APPLICATION_FORM_URLENCODED)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticateUser(UserCredentials userCredentials) {

        try {

            // Authenticate the user using the credentials provided
            UserEntity userEntity = authenticate(userCredentials.getUsername(), userCredentials.getPassword());

            // Issue a token for the user
            String token = issueToken(userEntity);

            // Return the token on the response
            return Response.ok(token).build();

        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    private UserEntity authenticate(String username, String password) throws Exception {
        UserEntityDAO userEntityDAO = new UserEntityDAO();
        UserEntity userEntity = userEntityDAO.getByUsername(username);
        if(userEntity != null) {
            if (!password.equals(userEntity.getPassword())) {
                throw new Exception("Bad password");
            }
        }
        return userEntity;
    }

    private String issueToken(UserEntity userEntity) {
        // Issue a token (can be a random String persisted to a database or a JWT token)
        // The issued token must be associated to a user
        // Return the issued token
        UserTokenDAO userTokenDAO = new UserTokenDAO();
        UserToken oldToken = userTokenDAO.getByUser(userEntity.getID());
        SessionIdentifierGenerator sessionIdentifierGenerator = new SessionIdentifierGenerator();
        String token = sessionIdentifierGenerator.nextSessionId();

        UserToken userToken = new UserToken(userEntity, token, System.currentTimeMillis());
        userTokenDAO.save(userToken);
        return token;
    }
}