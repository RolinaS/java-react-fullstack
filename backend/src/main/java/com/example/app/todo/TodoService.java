package com.example.app.todo;

import com.example.app.todo.dto.CreateTodoDto;
import com.example.app.todo.dto.TodoDto;
import com.example.app.todo.dto.UpdateTodoDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TodoService {
    private final TodoRepository repository;

    public TodoService(TodoRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<TodoDto> findAll() {
        return repository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public TodoDto create(CreateTodoDto in) {
        Todo todo = new Todo();
        todo.setTitle(in.getTitle());
        todo.setCompleted(false);
        Todo saved = repository.save(todo);
        return toDto(saved);
    }

    public TodoDto update(Long id, UpdateTodoDto in) {
        Todo todo = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Todo not found"));
        todo.setTitle(in.getTitle());
        todo.setCompleted(in.isCompleted());
        return toDto(todo);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Todo not found");
        }
        repository.deleteById(id);
    }

    private TodoDto toDto(Todo t) {
        TodoDto dto = new TodoDto();
        dto.setId(t.getId());
        dto.setTitle(t.getTitle());
        dto.setCompleted(t.isCompleted());
        dto.setCreatedAt(t.getCreatedAt());
        return dto;
    }
} 