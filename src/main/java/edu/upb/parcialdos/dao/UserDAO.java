/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.upb.parcialdos.dao;

import edu.upb.parcialdos.model.Users;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

/**
 *
 * @author GLEISSON
 */
@Stateless
public class UserDAO {

    @PersistenceContext(unitName = "Parcial_Dos_PU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public UserDAO() {
    }
    
     public void create(Users entity) {
        getEntityManager().persist(entity);
    }

    public void edit(Users entity) {
        getEntityManager().merge(entity);
    }

    public void remove(Users entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public Users find(Object id) {
        return getEntityManager().find(Users.class, id);
        
    }

    public List<Users> findAll() {
        CriteriaQuery cq;
        cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(Users.class));
        return getEntityManager().createQuery(cq).getResultList();
    }
    
    public Users findAuthLogin(Users actual) {

        Query query = getEntityManager().createQuery("Select u from Users u where u.username = :username and u.userpassword = :userpassword");
        query.setParameter("username", actual.getUsername());
        query.setParameter("userpassword",actual.getUserpassword());
        try {
            return (Users) query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
    
    
}
