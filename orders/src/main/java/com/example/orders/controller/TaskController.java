package com.example.orders.controller;

import lombok.RequiredArgsConstructor;
import com.example.orders.model.TaskMessage;
import com.example.orders.service.TaskService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {
  private final TaskService service;

  @PostMapping
  public TaskMessage create(@RequestBody final TaskMessage message, final HttpServletRequest request) {
    return service.create(message);
  }
}
