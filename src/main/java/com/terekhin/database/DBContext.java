package com.terekhin.database;

import com.terekhin.database.repository.IUsers;
import com.terekhin.database.repository.impl.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DBContext implements IDBContext {

    @Autowired
    private IUsers _users;

    @Override
    public IUsers getUsers() {
        return _users;
    }
}
