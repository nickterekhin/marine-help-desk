package com.terekhin.database;


import com.terekhin.domain.IBaseModel;
import com.terekhin.exceptions.DBException;

import java.util.List;

public interface IDAOService<T extends IBaseModel> {
    void create(T obj) throws DBException;
    T getById(Long id) throws DBException;
    void delete(T obj) throws DBException;
    void delete(Long id) throws DBException;
    void update(T obj) throws DBException;
    List<T> getAll() throws DBException;
    T activate(Long id,boolean status) throws DBException;
}
