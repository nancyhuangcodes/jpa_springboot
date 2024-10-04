package org.example.jpa.service;

import org.example.jpa.model.Feedback;

import java.util.List;
import java.util.Optional;

public interface FeedbackServiceInterface {

    public abstract Feedback save(Feedback feedback);       // save()
    public abstract List<Feedback> findAll();               // findAll()
    public abstract Optional<Feedback> findById(Long id);   // findById()
    public abstract Feedback update(Feedback feedback);     // update()
    public abstract void deleteById(Long id);               // deleteById()
    public abstract long count();                           // count()

}
