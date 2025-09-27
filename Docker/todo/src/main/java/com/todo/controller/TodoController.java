package com.todo.controller;

import com.todo.model.Todo;
import com.todo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todo")
public class TodoController {

    @Autowired
    TodoService todoService;

    @PostMapping("/create")
    public Todo CreateTodo(@RequestBody Todo todo){
        return todoService.create(todo);
    }

    @PutMapping("/update")
    public Todo updateTodo(@RequestBody Todo todo){
        return todoService.update(todo);
    }

    @DeleteMapping("/delete/{todoid}")
    public String deleteTodo(@PathVariable Long todoid){
        return todoService.delete(todoid);
    }

    @GetMapping("/get")
    public Todo getTodo(@RequestParam("todoid") Long todoid){
        return todoService.get(todoid);
    }

    @GetMapping("/getAll")
    public List<Todo> getAllTodo(){
        return todoService.getAll();
    }
}
