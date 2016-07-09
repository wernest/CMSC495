package org.wernest.CMSC495.api;

import org.hibernate.HibernateException;
import org.wernest.CMSC495.authentication.Secured;
import org.wernest.CMSC495.dao.StratDAO;
import org.wernest.CMSC495.dao.UserEntityDAO;
import org.wernest.CMSC495.entities.Strats;
import org.wernest.CMSC495.entities.UserEntity;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * The Strat Resource is the REST endpoints for the Strats objects
 */
@Path("/swot")
@Secured
public class StratResource extends BaseResource{

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listStratReports(){
        UserEntity user = new UserEntityDAO().getByUsername(this.setUser());
        List<Strats> reportList = new StratDAO().findAll();
        return Response.ok(reportList).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStrat(@PathParam("id") Integer id){
        UserEntity user = new UserEntityDAO().getByUsername(this.setUser());
        try {
            Strats strat = new StratDAO().findOne(id);
            if (strat == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            } else {
                return Response.ok(strat).build();
            }
        }catch(HibernateException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response newStrat(Strats strat){
        UserEntity user = new UserEntityDAO().getByUsername(this.setUser());
        try {
            //TODO: Validate this user has access to SWOT Report adding STRAT to
            new StratDAO().save(strat);
            return Response.ok(strat).build();
        }catch(HibernateException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveStrat(@PathParam("id") Integer id, Strats strats) {
        UserEntity user = new UserEntityDAO().getByUsername(this.setUser());
        StratDAO stratDao = new StratDAO();
        try {
            //TODO: Validate this user has access to SWOT Report adding STRAT to
            Strats strat = stratDao.findOne(id);
            if (strats == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            } else {
                stratDao.save(strats);
                return Response.ok(strats).build();
            }
        } catch (HibernateException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteStrat(@PathParam("id") Integer id) {
        UserEntity user = new UserEntityDAO().getByUsername(this.setUser());
        StratDAO stratDao = new StratDAO();
        try {
            //TODO: Validate this user has access to SWOT Report adding STRAT to
            Strats strat = stratDao.findOne(id);
            if (strat == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            stratDao.deleteById(id);
            return Response.ok().build();
        } catch (HibernateException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
