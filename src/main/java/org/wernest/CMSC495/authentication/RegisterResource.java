package org.wernest.CMSC495.authentication;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.util.JSONPObject;
import org.hibernate.HibernateException;
import org.json.JSONArray;
import org.wernest.CMSC495.dao.UserEntityDAO;
import org.wernest.CMSC495.entities.UserEntity;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Resource to handle user registration and creating an account.
 *
 * Also determines the errors (cumulatively) and returns an error array to the client
 */
@Path("/register")
public class RegisterResource {

    /**
     * Method to create an account
     * @param userEntity accont to create
     * @return Errors if invalid request, or HTTP 200 if ok.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createAccount(UserEntity userEntity){
        UserEntityDAO userEntityDAO = new UserEntityDAO();

        try {
            UserEntity tempUser = userEntityDAO.getByEmail(userEntity.getEmail());
            JsonArray jsonArray = new JsonArray();

            if(tempUser != null) {
                jsonArray.add("The email address you entered is already being used.");
            }

            tempUser = userEntityDAO.getByUsername(userEntity.getUsername());
            if(tempUser != null) {
                jsonArray.add("The username you entered is already being used.");
            }

            if(jsonArray.size() > 0) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.add("errors", jsonArray);
                Gson gson = new Gson();
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(gson.toJson(jsonObject))
                        .type(MediaType.APPLICATION_JSON_TYPE)
                        .build();
            }

            userEntityDAO.save(userEntity);
            return Response.ok().build();
        }catch(HibernateException e){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }
}
