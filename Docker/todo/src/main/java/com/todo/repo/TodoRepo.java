package com.todo.repo;

import com.todo.model.Todo;
//import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//public interface TodoRepo extends MongoRepository<Todo, Long> {
@Repository
public interface TodoRepo extends JpaRepository<Todo, Long> {
}
