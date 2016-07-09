package org.wernest.CMSC495.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.wernest.CMSC495.api.SwotResource;
import org.wernest.CMSC495.entities.SwotReport;
import org.wernest.CMSC495.entities.UserEntity;

import java.util.List;

/**
 * Swot Report DAO.
 */
public class SwotReportDAO extends AbstractHibernateDAO<SwotReport> {

    public SwotReportDAO(){
        this.setClazz(SwotReport.class);
    }

    /**
     * Returns all Swot Reports that this user has access to
     * @param user UserEntity
     * @return List of SwotReports for this user
     */
    public List<SwotReport> findAllByUser(UserEntity user){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        List<SwotReport> reportList = session.getNamedQuery("SwotReport.findAllByUser")
                .setString("user_id", user.getID().toString()).list();
        session.getTransaction().commit();
        session.close();
        return reportList;
    }
}
