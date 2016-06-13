package org.wernest.CMSC495.api;

import org.hibernate.HibernateException;
import org.wernest.CMSC495.authentication.Secured;
import org.wernest.CMSC495.dao.SwotReportDAO;
import org.wernest.CMSC495.dao.UserEntityDAO;
import org.wernest.CMSC495.entities.SwotReport;
import org.wernest.CMSC495.entities.UserEntity;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/swot")
public class SwotResource extends BaseResource{

    @GET
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response listSwotReports(){
        UserEntity user = new UserEntityDAO().getByUsername(this.setUser());
        List<SwotReport> reportList = new SwotReportDAO().findAllByUser(user);
        return Response.ok(reportList).build();
    }

    @GET
    @Path("/{id}")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSwotReport(@PathParam("id") Long id){
        UserEntity user = new UserEntityDAO().getByUsername(this.setUser());
        try {
            SwotReport swotReport = new SwotReportDAO().findOne(id);
            if (swotReport == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            } else if (swotReport.getUserEntity() != user) {
                return Response.status(Response.Status.FORBIDDEN).build();
            } else {
                return Response.ok(swotReport).build();
            }
        }catch(HibernateException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PUT
    @Secured
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response newSwotReport(SwotReport swotReport){
        UserEntity user = new UserEntityDAO().getByUsername(this.setUser());
        try {
            swotReport.setUserEntity(user);
            new SwotReportDAO().save(swotReport);
            return Response.ok(swotReport).build();
        }catch(HibernateException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    @Secured
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveSwotReport(@PathParam("id") Long id, SwotReport swotReport) {
        UserEntity user = new UserEntityDAO().getByUsername(this.setUser());
        SwotReportDAO swotReportDAO = new SwotReportDAO();
        try {
            SwotReport oldSwotReport = swotReportDAO.findOne(id);
            if (oldSwotReport == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            } else if (oldSwotReport.getUserEntity() != user) {
                return Response.status(Response.Status.FORBIDDEN).build();
            } else {
                swotReportDAO.save(swotReport);
                return Response.ok(swotReport).build();
            }
        } catch (HibernateException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DELETE
    @Secured
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteSwotReport(@PathParam("id") Long id) {
        UserEntity user = new UserEntityDAO().getByUsername(this.setUser());
        SwotReportDAO swotReportDAO = new SwotReportDAO();
        try {
            SwotReport oldSwotReport = swotReportDAO.findOne(id);
            if (oldSwotReport == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            } else if (oldSwotReport.getUserEntity() != user) {
                return Response.status(Response.Status.FORBIDDEN).build();
            } else {
                swotReportDAO.deleteById(id);
                return Response.ok().build();
            }
        } catch (HibernateException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
