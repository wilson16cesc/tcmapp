/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcm.tcmapp.dao;

import java.util.List;
import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;

/**
 *
 * @author MFIGUEROAG
 * @param <T>
 */
@Local
public class BaseDAO<T>{
    
    private Class<T> entityClass;

    @PersistenceContext(unitName = "tcm-PU")
    private EntityManager em;

    public BaseDAO() {
    }
    
    public void setEntityClass(Class<T> entityClass){
        this.entityClass = entityClass;
    }
    
    public void save(T entity) {
        getEntityManager().persist(entity);
    }
    
    public void update(T entity) {
        getEntityManager().merge(entity);
    }

    public void delete(Object id){
        T entity = find(id);
        getEntityManager().remove(entity);
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List findAll() {
        CriteriaQuery<Object> cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public int deleteAll(){
        CriteriaBuilder cb  = getEntityManager().getCriteriaBuilder();
        CriteriaDelete<T> query = cb.createCriteriaDelete(entityClass);
        //Root<T> root = query.from(entityClass);
        //query.where(root.get("id").in(listWithIds));
        return getEntityManager().createQuery(query).executeUpdate();
    }

    protected EntityManager getEntityManager() {
        return em;
    }

    public void executeNativeQuery(String sql){
        getEntityManager().createNativeQuery("BEGIN " + sql + " END;").executeUpdate();
    }
    
    public void flush(){
        em.flush();
    }
}