package org.example.jpa.service;

import org.example.jpa.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerServiceInterface {

    public abstract Customer save(Customer customer);               // save()
    public abstract List<Customer> findAll();                       // findAll()
    public abstract Optional<Customer> findById(Long id);           // findById()
    public abstract Customer update(Customer customer);             // update()
    public abstract void deleteById(Long id);                       //  deleteById()
    public abstract long count();                                   // count()
}

// Note that abstract is a Java term