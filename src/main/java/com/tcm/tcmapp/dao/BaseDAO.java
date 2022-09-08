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
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

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
        T entity = findById(id);
        getEntityManager().remove(entity);
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public T findById(Object id) {
        return getEntityManager().find(entityClass, id);
    }
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<T> findAll() {
        CriteriaQuery<T> cq = getEntityManager().getCriteriaBuilder().createQuery(entityClass);
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<T> findAllActive() {
        CriteriaQuery<T> cq = getEntityManager().getCriteriaBuilder().createQuery(entityClass);
        Root<T> root = cq.from(entityClass);
        cq.where(root.get("activo").in(true));
        cq.select(root);
        return getEntityManager().createQuery(cq).getResultList();
    }

    public int deleteAll(){
        CriteriaBuilder cb  = getEntityManager().getCriteriaBuilder();
        CriteriaDelete<T> query = cb.createCriteriaDelete(entityClass);
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

    public int executeJpqlUpdate(String jpql){
        return em.createQuery(jpql).executeUpdate();
    }

    public T findFirst(){
        CriteriaBuilder criteriaBuilder  = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        Root<T> entityRoot = criteriaQuery.from(entityClass);
        criteriaQuery.select(entityRoot);
        criteriaQuery.orderBy(criteriaBuilder.asc(entityRoot.get("id")));
        try {
            return getEntityManager().createQuery(criteriaQuery).setMaxResults(1).getSingleResult();
        } catch (NonUniqueResultException | NoResultException e) {
            return null;
        }
    }
}