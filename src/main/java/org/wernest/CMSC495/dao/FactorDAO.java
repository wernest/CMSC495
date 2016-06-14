package org.wernest.CMSC495.dao;

import org.hibernate.Session;
import org.wernest.CMSC495.entities.Factors;
import org.wernest.CMSC495.entities.SwotReport;

import java.util.Collections;
import java.util.List;

/**
 * Created by will on 5/30/16.
 */
public class FactorDAO extends AbstractHibernateDAO<Factors> {

    public List<Factors> finAllByParent(Long parentID){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        List<Factors> factorsList = Collections.emptyList();
        session.getTransaction().commit();
        session.close();
        return factorsList;
    }
}
