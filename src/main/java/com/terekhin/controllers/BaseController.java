package com.terekhin.controllers;


import com.terekhin.database.DBContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public abstract class BaseController {

    @Autowired
    protected DBContext _dbCtx;
}
