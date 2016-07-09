package org.wernest.CMSC495.authentication;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.wernest.CMSC495.api.BaseResource;
import org.wernest.CMSC495.dao.UserEntityDAO;
import org.wernest.CMSC495.dao.UserTokenDAO;
import org.wernest.CMSC495.entities.UserCredentials;
import org.wernest.CMSC495.entities.UserEntity;
import org.wernest.CMSC495.entities.UserToken;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Used to log a user in.
 *
 * The user will supply their credentials and if that
 * user exists, and the password matches the user will be logged in.
 * Logging in will take place by placing a "Cookie" on the response as well as
 * providing the user with a token in the response.
 */
@Path("/login")
public class LoginResource extends BaseResource{

    /**
     * REST Endpoint for a user to supply credentials to
     * in order to be logged in
     * @param userCredentials object with username/password
     * @return Response with token/cookie or error
     */
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
            NewCookie newCookie = new NewCookie("CMSC495", token, "", "localhost", "", 60*60*24, false);
            return Response.ok(token).cookie(newCookie).build();

        } catch (Exception e) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("errors", "Invalid username or password. Please try again.");
            Gson gson = new Gson();
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(gson.toJson(jsonObject))
                    .type(MediaType.APPLICATION_JSON_TYPE)
                    .build();
        }
    }

    /**
     * Used to return a token for a user that is already logged in via
     * a cookie.
     * @param securityContext Contains the username after the filter is run
     * @return Response with token or HTTP 401 if not
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Secured
    public Response getToken(@Context SecurityContext securityContext){
        String user = this.setUser();
        UserEntity userEntity = new UserEntityDAO().getByUsername(user);
        UserToken userToken = new UserTokenDAO().getByUser(userEntity.getID());
        if(!userToken.isValid()) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        return Response.ok(userToken.getToken()).build();
    }


    /**
     * Utility method to validate if the user exists and password is valid
     * @param username username
     * @param password password
     * @return UserEntity object if valid
     * @throws Exception if the password is invalid, or if that username doesn't exist
     */
    private UserEntity authenticate(String username, String password) throws Exception {
        UserEntityDAO userEntityDAO = new UserEntityDAO();
        UserEntity userEntity = userEntityDAO.getByUsername(username);
        if(userEntity != null) {
            if (!password.equals(userEntity.getPassword())) {
                throw new Exception("Bad password");
            }
        }else { // UserEntity is null
            throw new Exception("That user doesn't exist");
        }
        return userEntity;
    }

    /**
     * Utility method to get the token from the userentity object.
     * @param userEntity UserEntity object
     * @return String of the token for that userEntity object
     */
    private String issueToken(UserEntity userEntity) {
        UserTokenDAO userTokenDAO = new UserTokenDAO();
        UserToken oldToken = userTokenDAO.getByUser(userEntity.getID());
        if(oldToken != null) {
            userTokenDAO.delete(oldToken);
        }
        SessionIdentifierGenerator sessionIdentifierGenerator = new SessionIdentifierGenerator();
        String token = sessionIdentifierGenerator.nextSessionId();

        UserToken userToken = new UserToken(userEntity, token, System.currentTimeMillis());
        userTokenDAO.save(userToken);
        return token;
    }


}