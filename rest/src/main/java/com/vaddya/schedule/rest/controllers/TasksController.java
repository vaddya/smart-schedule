package com.vaddya.schedule.rest.controllers;

import com.google.gson.JsonSyntaxException;
import com.vaddya.schedule.core.exceptions.DuplicateIdException;
import com.vaddya.schedule.core.exceptions.NoSuchTaskException;
import com.vaddya.schedule.core.tasks.Task;
import com.vaddya.schedule.rest.Paths;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.UUID;

import static java.time.LocalDate.from;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;

//TODO старый путь
/**
 * com.vaddya.schedule.rest at smart-schedule
 *
 * @author vaddya
 * @since April 05, 2017
 */
@RestController
@RequestMapping(Paths.TASKS)
public class TasksController extends Controller {
    //TODO есть более новые аннотации GetMapping, PostMapping, PutMapping...,
    //TODO которые позволяют не прописывать параметр method. Получается короче
    @RequestMapping(method = GET, produces = JSON)
    public ResponseEntity<String> getAllTasks(@RequestParam(defaultValue = "all") String filter,
                                              @RequestParam(required = false) String subject,
                                              @RequestParam(required = false) String deadline) {
        //TODO понятно, что происходит. Длина метода на читаемости и понимаемости не отразилась.
        //TODO но мне кажется, стоит разбить на несколько.
        
        //TODO может ли в случае, если есть все необходимые таски есть в БД, ответ быть с каким-то статусом, кроме ОК?
        //TODO вроде нет. Другой статус может быть только, если что-то пойдет не так: не будет тасок, неверные параметры...
        //TODO автор spring in action советует в таких случаях указывать аннотацией ResponseStatus, какой будет статус ответа.
        //TODO а для остальных случаев просто бросать исключения. А ловить их и обрабатывть методами, которые аннотированы ExceptionHandler
        //TODO если так сделать, то ResponseEntity окажется ненужным. Можно будет просто возвращать json. А статус в аннотации.
        //
        //TODO еще плюс такого подхода в том, что один метод - ExceptionHandler обрабатывает 1 исключение.
        //TODO И не важно откуда оно будет выбрасываться. Не придется каждый JsonSyntaxException обрабатывать отдельно и одинаково
        //
        //TODO UPD: посмотрел другие контроллеры. там то же, что и здесь. 
        //TODO Если использовать ExceptionHandler'ы, то будет в 1.5 раза меньше кода
        List<Task> tasks;
        switch (filter.toLowerCase()) {
            case "all":
                tasks = schedule.getTasks().getAllTasks();
                break;
            case "active":
                tasks = schedule.getTasks().getActiveTasks();
                break;
            case "completed":
                tasks = schedule.getTasks().getCompletedTasks();
                break;
            case "overdue":
                tasks = schedule.getTasks().getOverdueTasks();
                break;
            default:
                return getMessageResponse(BAD_REQUEST, "Filter is invalid");
        }
        if (subject != null) {
            tasks = tasks.stream()
                    .filter(task -> task.getSubject().equals(subject))
                    .collect(toList());
        }
        if (deadline != null) {
            try {
                LocalDate date = from(DATE_FORMAT.parse(deadline));
                tasks = tasks.stream()
                        .filter(task -> task.getDeadline().isBefore(date))
                        .collect(toList());
            } catch (DateTimeParseException e) {
                return getMessageResponse(BAD_REQUEST, "Deadline format is invalid");
            }
        }
        if (tasks.isEmpty()) {
            return getResponse(NO_CONTENT);
        }
        return getBodyResponse(OK, gson.toJson(tasks));
    }
    
    //TODO есть более новые аннотации GetMapping, PostMapping, PutMapping...,
    //TODO которые позволяют не прописывать параметр method. Получается короче
    @RequestMapping(method = POST, consumes = JSON, produces = JSON)
    public ResponseEntity<String> createTask(@RequestBody String body) {
        //TODO см выше
        try {
            Task task = gson.fromJson(body, Task.class);
            schedule.getTasks().addTask(task);
            return getResponseCreated(CREATED, "Task created", Paths.TASKS, task.getId());
        } catch (JsonSyntaxException e) {
            return getMessageResponse(BAD_REQUEST, "Task syntax is invalid");
        } catch (DuplicateIdException e) {
            return getMessageResponse(CONFLICT, "Task with specified ID already exists");
        }
    }
    
    //TODO есть более новые аннотации GetMapping, PostMapping, PutMapping...,
    //TODO которые позволяют не прописывать параметр method. Получается короче
    @RequestMapping(method = DELETE)
    public ResponseEntity<String> deleteAllTasks() {
        schedule.getTasks().removeAllTasks();
        return getResponse(NO_CONTENT);
    }
    
    //TODO есть более новые аннотации GetMapping, PostMapping, PutMapping...,
    //TODO которые позволяют не прописывать параметр method. Получается короче
    @RequestMapping(value = "/{id}", method = GET, produces = JSON)
    public ResponseEntity<String> getTask(@PathVariable String id) {
        try {
            UUID uuid = UUID.fromString(id);
            Task task = schedule.getTasks().findTask(uuid);
            return getBodyResponse(OK, gson.toJson(task));
        } catch (IllegalArgumentException e) {
            return getMessageResponse(BAD_REQUEST, "Task ID format is invalid");
        } catch (NoSuchTaskException e) {
            return getMessageResponse(NOT_FOUND, "Task does not exist");
        }
    }
    
    //TODO есть более новые аннотации GetMapping, PostMapping, PutMapping...,
    //TODO которые позволяют не прописывать параметр method. Получается короче
    @RequestMapping(value = "/{id}", method = PUT, consumes = JSON, produces = JSON)
    public ResponseEntity<String> updateTask(@PathVariable String id,
                                             @RequestBody String body) {
        try {
            Task task = gson.fromJson(body, Task.class);
            UUID pathId = UUID.fromString(id);
            if (!task.getId().equals(pathId)) {
                return getMessageResponse(BAD_REQUEST, "ID in path and in body do not match");
            }
            schedule.getTasks().updateTask(task);
            return getResponse(NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return getMessageResponse(BAD_REQUEST, "Task ID format is invalid");
        } catch (JsonSyntaxException e) {
            return getMessageResponse(BAD_REQUEST, "Task syntax is invalid");
        } catch (NoSuchTaskException e) {
            return getMessageResponse(NOT_FOUND, "Task does not exist");
        }
    }
    
    //TODO есть более новые аннотации GetMapping, PostMapping, PutMapping...,
    //TODO которые позволяют не прописывать параметр method. Получается короче
    @RequestMapping(value = "/{id}", method = DELETE, produces = JSON)
    public ResponseEntity<String> deleteTask(@PathVariable String id) {
        try {
            UUID uuid = UUID.fromString(id);
            Task task = schedule.getTasks().findTask(uuid);
            schedule.getTasks().removeTask(task);
            return getResponse(NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return getMessageResponse(BAD_REQUEST, "Task ID format is invalid");
        } catch (NoSuchTaskException e) {
            return getMessageResponse(NOT_FOUND, "Task does not exist");
        }
    }

}
