package com.terekhin.database;

import com.terekhin.domain.IBaseModel;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class JPAService<T extends IBaseModel> {

    @PersistenceContext(name="jpa_tx")
    protected EntityManager entityManager;

    protected Class<T> persistentClass;
}
