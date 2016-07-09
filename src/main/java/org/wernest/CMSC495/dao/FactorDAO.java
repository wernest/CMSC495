package org.wernest.CMSC495.dao;

import org.hibernate.Session;
import org.wernest.CMSC495.entities.Factors;
import org.wernest.CMSC495.entities.SwotReport;

import java.util.Collections;
import java.util.List;

/**
 * DAO implementation for Factors
 *
 */
public class FactorDAO extends AbstractHibernateDAO<Factors> {

    /**
     * Locates all records based on parent ID
     * @param parentID Integer
     * @return List of factors with a parent of parentID
     */
    public List<Factors> findAllByParent(Integer parentID){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        List<Factors> factorsList = Collections.emptyList();
        session.getTransaction().commit();
        session.close();
        return factorsList;
    }
}
