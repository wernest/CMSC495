package org.wernest.CMSC495.api;

import org.hibernate.HibernateException;
import org.wernest.CMSC495.authentication.Secured;
import org.wernest.CMSC495.dao.FactorDAO;
import org.wernest.CMSC495.dao.UserEntityDAO;
import org.wernest.CMSC495.entities.Factors;
import org.wernest.CMSC495.entities.UserEntity;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/factors")
@Secured
public class FactorResource extends BaseResource{
    
    UserEntityDAO userEntityDAO = new UserEntityDAO();
    FactorDAO factorDAO = new FactorDAO(); 
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listFactors(){
        UserEntity user = userEntityDAO.getByUsername(this.setUser());
        List<Factors> reportList = factorDAO.findAll();
        return Response.ok(reportList).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFactor(@PathParam("id") Long id){
        UserEntity user = userEntityDAO.getByUsername(this.setUser());
        try {
            //TODO Validate this user owns the SWOT REPORT

            Factors factor = factorDAO.findOne(id);
            if (factor == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            } else {
                return Response.ok(factor).build();
            }
        }catch(HibernateException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response newFactor(Factors factor){
        UserEntity user = userEntityDAO.getByUsername(this.setUser());
        try {
            //TODO Validate this user owns the SWOT REPORT
            factorDAO.save(factor);
            return Response.ok(factor).build();
        }catch(HibernateException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveFactor(@PathParam("id") Long id, Factors swotReport) {
        UserEntity user = userEntityDAO.getByUsername(this.setUser());
        try {
            //TODO Validate this user owns the SWOT REPORT

            Factors oldFactor = factorDAO.findOne(id);
            if (oldFactor == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }else {
                factorDAO.save(swotReport);
                return Response.ok(swotReport).build();
            }
        } catch (HibernateException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteFactor(@PathParam("id") Long id) {
        UserEntity user = userEntityDAO.getByUsername(this.setUser());
        try {
            //TODO Validate this user owns the SWOT REPORT

            Factors oldFactor = factorDAO.findOne(id);
            if (oldFactor == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }else {
                factorDAO.deleteById(id);
                return Response.ok().build();
            }
        } catch (HibernateException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
