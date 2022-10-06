/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcm.tcmapp.dao.base;

import com.tcm.tcmapp.audit.AuditFieldsInterceptor;
import com.tcm.tcmapp.entity.base.BaseEntity;

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
import java.util.List;

/**
 * @param <T>
 * @author MFIGUEROAG
 */
@Local
public abstract class BaseDAO<T extends BaseEntity> {

    private Class<T> entityClass;

    @PersistenceContext(unitName = "tcm-PU")
    private EntityManager em;

    public BaseDAO() {
    }

    public void setEntityClass(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
    @AuditFieldsInterceptor
    public void save(T entity) {
        getEntityManager().persist(entity);
    }

    @AuditFieldsInterceptor
    public void update(T entity) {
        getEntityManager().merge(entity);
    }

    public void delete(Object id) {
        T entity = findById(id);
        getEntityManager().remove(entity);
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public T findById(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<T> findAll() {
        CriteriaQuery<T> criteriaQuery = getEntityManager().getCriteriaBuilder().createQuery(entityClass);
        criteriaQuery.select(criteriaQuery.from(entityClass));
        return getEntityManager().createQuery(criteriaQuery).getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<T> findAllActive() {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        Root<T> root = criteriaQuery.from(entityClass);
        criteriaQuery.where(criteriaBuilder.equal(root.get("activo"), true));
        criteriaQuery.select(root);
        return getEntityManager().createQuery(criteriaQuery).getResultList();
    }

    public List<T> findActiveByField(String fieldName, Object value) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        Root<T> root = criteriaQuery.from(entityClass);
        criteriaQuery.where(criteriaBuilder.equal(root.get(fieldName), value),
                criteriaBuilder.equal(root.get("activo"), true));
        criteriaQuery.select(root);
        return getEntityManager().createQuery(criteriaQuery).getResultList();
    }

    public List<T> findByField(String fieldName, Object value) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        Root<T> root = criteriaQuery.from(entityClass);
        criteriaQuery.where(criteriaBuilder.equal(root.get(fieldName), value));
        criteriaQuery.select(root);
        return getEntityManager().createQuery(criteriaQuery).getResultList();
    }

    public T findFirstActiveByField(String fieldName, Object value) {
        List<T> resultList = findActiveByField(fieldName, value);
        if (resultList.isEmpty())
            return null;
        return resultList.get(0);
    }

    public int deleteAll() {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaDelete<T> query = criteriaBuilder.createCriteriaDelete(entityClass);
        return getEntityManager().createQuery(query).executeUpdate();
    }

    protected EntityManager getEntityManager() {
        return em;
    }

    public void executeNativeQuery(String sql) {
        getEntityManager().createNativeQuery("BEGIN " + sql + " END;").executeUpdate();
    }

    public void flush() {
        em.flush();
    }

    public int executeJpqlUpdate(String jpql) {
        return em.createQuery(jpql).executeUpdate();
    }

    public T findFirst() {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
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