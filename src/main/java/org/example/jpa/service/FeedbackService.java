package org.example.jpa.service;

import org.example.jpa.model.Feedback;
import org.example.jpa.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.SocketHandler;

@Service
public class FeedbackService implements FeedbackServiceInterface {

    @Autowired
    private FeedbackRepository feedbackRepository;


    @Override
    public Feedback save(Feedback feedback){
        return feedbackRepository.save(feedback);
    }

    @Override
    public List<Feedback> findAll() {
        return feedbackRepository.findAll();
    }

    @Override
    public Optional<Feedback> findById(Long id) {
        return feedbackRepository.findById(id);
    }

    @Override
    public Feedback update(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    @Override
    public void deleteById(Long id) {               // void returns nothing
        feedbackRepository.deleteById(id);
    }

    @Override
    public long count() {
        return feedbackRepository.count();      // Long is a number in 8 bytes
    }

}
