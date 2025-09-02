package com.example.app.todo;

import com.example.app.todo.dto.CreateTodoDto;
import com.example.app.todo.dto.TodoDto;
import com.example.app.todo.dto.UpdateTodoDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {
    private final TodoService service;

    @GetMapping
    public List<TodoDto> findAll() { return service.findAll(); }

    @PostMapping
    public ResponseEntity<TodoDto> create(@Valid @RequestBody CreateTodoDto in) {
        TodoDto out = service.create(in);
        return ResponseEntity.status(HttpStatus.CREATED).body(out);
    }

    @PutMapping("/{id}")
    public TodoDto update(@PathVariable Map<String, String> pathVars, @Valid @RequestBody UpdateTodoDto in) {
        Long id = Long.valueOf(pathVars.get("id"));
        return service.update(id, in);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Map<String, String> pathVars) {
        Long id = Long.valueOf(pathVars.get("id"));
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
} 