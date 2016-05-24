package org.wernest.CMSC495.authentication;

import org.hibernate.HibernateException;
import org.wernest.CMSC495.dao.UserEntityDAO;
import org.wernest.CMSC495.entities.UserEntity;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by will on 5/22/16.
 */
@Path("/register")
public class RegisterResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createAccount(UserEntity userEntity){
        UserEntityDAO userEntityDAO = new UserEntityDAO();

        try {
            userEntityDAO.save(userEntity);
            return Response.ok().build();
        }catch(HibernateException e){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }
}
