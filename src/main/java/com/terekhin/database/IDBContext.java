package com.terekhin.database;

import com.terekhin.database.repository.IUsers;

public interface IDBContext {
    IUsers getUsers();
}
