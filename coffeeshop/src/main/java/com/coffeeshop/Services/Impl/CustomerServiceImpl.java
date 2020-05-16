/*
 * Copyright (c) 2020. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.coffeeshop.Services.Impl;

import com.coffeeshop.DAO.CustomerDAO;
import com.coffeeshop.EntityClasses.CustomerEntity;
import com.coffeeshop.Services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerDAO customerDAO;

    @Override
    public CustomerEntity create(CustomerEntity customerEntity) {

        return customerDAO.create(customerEntity);
    }

    @Override
    public CustomerEntity login(CustomerEntity customerEntity) {
        return customerDAO.login(customerEntity);
    }
}
