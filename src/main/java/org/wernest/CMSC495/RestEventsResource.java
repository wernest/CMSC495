/*
MIT License

Copyright (c) 2016 William Ernest

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
package org.wernest.CMSC495;

import org.hibernate.Session;
import org.wernest.CMSC495.authentication.Secured;
import org.wernest.CMSC495.dao.HibernateUtil;
import org.wernest.CMSC495.dao.SampleRowDAO;
import org.wernest.CMSC495.dao.UserEntityDAO;
import org.wernest.CMSC495.entities.SampleRow;
import org.wernest.CMSC495.entities.UserEntity;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.security.Principal;
import java.util.*;

/**
 * @author wernest
 */
@Path("/")
public class RestEventsResource{


    @Context
    SecurityContext securityContext;


    @GET
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response createNewSampleObject(@Context SecurityContext securityContext){

        SampleRow sampleRow = new SampleRow();
        SampleRowDAO sampleRowDAO = new SampleRowDAO();
        try {

            sampleRow.name = "Wiru";
            Principal principal = securityContext.getUserPrincipal();
            String username = principal.getName();
            sampleRow.owner = new UserEntityDAO().getByUsername(username);

            sampleRowDAO.save(sampleRow);
            System.out.println("Employee ID=" + sampleRow.id);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return Response.ok(sampleRow).build();
    }

    List<SampleRow> list = null;

    @GET
    @Secured
    @Path("/list")
    @Produces({MediaType.APPLICATION_JSON})
    public List<SampleRow> listSampleObjects(@Context HttpServletRequest servletRequest,
                                             @Context SecurityContext securityContext){

        try{

            Principal principal = securityContext.getUserPrincipal();
            String username = principal.getName();
            UserEntity user = new UserEntityDAO().getByUsername(username);

            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            list = session.createCriteria(SampleRow.class).list();
            //Commit transaction
            session.getTransaction().commit();
            session.flush();
            session.close();


        } catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
