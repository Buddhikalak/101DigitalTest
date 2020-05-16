/*
 * Copyright (c) 2020. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.coffeeshop.DAO.Impl;

import com.coffeeshop.DAO.CustomerDAO;
import com.coffeeshop.EntityClasses.CustomerEntity;
import com.coffeeshop.Repository.CustomerRepository;
import com.coffeeshop.Utils.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;

@Service
public class CustomerDAOImpl implements CustomerDAO {

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public CustomerEntity create(CustomerEntity customerEntity) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        customerEntity.setTimestamp(timestamp);
        return customerRepository.save(customerEntity);
    }

    @Override
    public CustomerEntity login(CustomerEntity customerEntity) {
        CustomerEntity byUsername = customerRepository.findByUsername(customerEntity.getUsername());
        if(byUsername.getPassword().equals(customerEntity.getPassword())){
            byUsername.setToken(TokenGenerator.generateNewToken());
        }

        return customerRepository.save(byUsername);
    }
}
