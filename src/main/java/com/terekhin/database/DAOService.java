package com.terekhin.database;


import com.terekhin.domain.IBaseModel;
import com.terekhin.exceptions.DBException;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class DAOService<T extends IBaseModel> extends JPAService<T> implements IDAOService<T>{
    @Override
    public void create(IBaseModel obj) throws DBException {

        entityManager.persist(obj);
    }

    public DAOService() {
        this.persistentClass = (Class<T>)((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public T getById(Long id) throws DBException {
        return entityManager.find(persistentClass,id);
    }

    @Override
    public void delete(T obj) throws DBException {
        entityManager.remove(obj);
    }

    @Override
    public void delete(Long id) throws DBException {
            delete(getById(id));
    }

    @Override
    public void update(T obj) throws DBException {
            entityManager.merge(obj);
    }

    @Override
    public List<T> getAll() throws DBException {
        CriteriaQuery<T> _criteria = this.getCriteria();
        return  entityManager.createQuery(_criteria).getResultList();
    }

    @Override
    public T activate(Long id, boolean status) throws DBException {
        return null;
    }

    protected CriteriaQuery<T> getCriteria()
    {
        CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();
        return cBuilder.createQuery(persistentClass);
    }
}
