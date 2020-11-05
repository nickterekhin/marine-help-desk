package com.terekhin.database.repository.impl;

import com.terekhin.database.DAOService;
import com.terekhin.database.repository.IUsers;
import com.terekhin.domain.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional("jpa_tx")
public class Users extends DAOService<User> implements IUsers {
}
