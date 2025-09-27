package com.todo.service;

import com.todo.model.Todo;
import com.todo.repo.TodoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    @Autowired
    private TodoRepo todoRepo;

    public Todo create(Todo todo){
        return todoRepo.save(todo);
    }

    public Todo update(Todo todo){
        Todo resTodo = todoRepo.findById(todo.getTodoId()).get();
        if(resTodo!=null){
            return todoRepo.save(todo);
        }
        return null;
    }

    public String delete(Long id){
        Todo resTodo = todoRepo.findById(id).get();
        todoRepo.delete(resTodo);
        return "Deleted";
    }

    public Todo get(Long id){
        return todoRepo.findById(id).get();
    }

    public List<Todo> getAll(){
        return todoRepo.findAll();
    }
}
