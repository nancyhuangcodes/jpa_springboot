package org.example.jpa.repository;

import org.example.jpa.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    // Data Persistence: CrudRepository (Basic), JpaRepository (Advanced, extends from CrudRepository)
    // save()           -- save() method is also equivalent to performing an update
    // findOne()
    // findById()
    // findByEmail()
    // findAll()
    // count()
    // delete()
    // deleteById()
}
