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
import java.util.Date;
import java.util.List;

/**
 * The SWOT Resource consists of the REST endpoints for the
 * SWOT Report object.
 */
@Path("/swot")
public class SwotResource extends BaseResource{

    /**
     * Returns a lit os SWOT Reports that the current logged in user
     * owns
     * @return Response containing a List of SWOT reports
     */
    @GET
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response listSwotReports(){
        UserEntity user = new UserEntityDAO().getByUsername(this.setUser());
        List<SwotReport> reportList = new SwotReportDAO().findAllByUser(user);
        return Response.ok(reportList).build();
    }

    /**
     * Used to get a specific swot report by ID
     * @param id Integer
     * @return Swot Report or Error response code
     */
    @GET
    @Path("/{id}")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSwotReport(@PathParam("id") Integer id){
        UserEntity user = new UserEntityDAO().getByUsername(this.setUser());
        try {
            SwotReport swotReport = new SwotReportDAO().findOne(id);
            if (swotReport == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            } else if (!swotReport.getUserEntity().getID().equals(user.getID())) {
                return Response.status(Response.Status.FORBIDDEN).build();
            } else {
                return Response.ok(swotReport).build();
            }
        }catch(HibernateException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Creating a new SWOT Report
     * @param swotReport SwotReport to be saved
     * @return Response with the saved SWOT Report
     */
    @POST
    @Secured
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response newSwotReport(SwotReport swotReport){
        UserEntity user = new UserEntityDAO().getByUsername(this.setUser());
        try {
            swotReport.setUserEntity(user);
            swotReport.setCreationDate(new Date());
            new SwotReportDAO().save(swotReport);
            return Response.ok(swotReport).build();
        }catch(HibernateException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Update a swot REPORT
     * @param id Integer ID Value of the SWOT Report to save
     * @param swotReport Swot Report to save
     * @return Response with the saved swot report.
     */
    @POST
    @Secured
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveSwotReport(@PathParam("id") Integer id, SwotReport swotReport) {
        UserEntity user = new UserEntityDAO().getByUsername(this.setUser());
        SwotReportDAO swotReportDAO = new SwotReportDAO();
        try {
            SwotReport oldSwotReport = swotReportDAO.findOne(id);
            if (oldSwotReport == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            } else if (!oldSwotReport.getUserEntity().getID().equals(user.getID())) {
                return Response.status(Response.Status.FORBIDDEN).build();
            } else {
                swotReport.setUserEntity(user);
                swotReportDAO.save(swotReport);
                return Response.ok(swotReport).build();
            }
        } catch (HibernateException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Delete Swot Report
     * @param id Integer ID Value of report to delete
     * @return HTTP Response
     */
    @DELETE
    @Secured
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteSwotReport(@PathParam("id") Integer id) {
        UserEntity user = new UserEntityDAO().getByUsername(this.setUser());
        SwotReportDAO swotReportDAO = new SwotReportDAO();
        try {
            SwotReport oldSwotReport = swotReportDAO.findOne(id);
            if (oldSwotReport == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            } else if (!oldSwotReport.getUserEntity().getID().equals(user.getID())) {
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
