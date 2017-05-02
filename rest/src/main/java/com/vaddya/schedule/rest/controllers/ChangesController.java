package com.vaddya.schedule.rest.controllers;

import com.google.gson.JsonSyntaxException;
import com.vaddya.schedule.core.changes.Change;
import com.vaddya.schedule.core.exceptions.DuplicateIdException;
import com.vaddya.schedule.core.exceptions.NoSuchChangeException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

/**
 * Контроллер для изменений
 *
 * @author vaddya
 */
@RestController
@RequestMapping(Paths.CHANGES)
public class ChangesController extends Controller {

    @GetMapping(produces = JSON)
    public ResponseEntity<String> getAllChanges(@RequestParam(required = false) String date) {
        List<Change> changes;
        if (date != null) {
            try {
                LocalDate localDate = LocalDate.from(DATE_FORMAT.parse(date));
                changes = schedule.getChanges().findAll(localDate);
                return getBodyResponse(OK, gson.toJson(changes));
            } catch (DateTimeParseException e) {
                return getMessageResponse(BAD_REQUEST, "Date format is invalid");
            }
        }
        changes = schedule.getChanges().findAll();
        return getBodyResponse(OK, gson.toJson(changes));
    }

    @PostMapping(consumes = JSON, produces = JSON)
    public ResponseEntity<String> createChange(@RequestBody String body) {
        try {
            Change change = gson.fromJson(body, Change.class);
            schedule.getChanges().addChange(change);
            return getResponseCreated(CREATED, "Change created", Paths.CHANGES, change.getId());
        } catch (JsonSyntaxException e) {
            return getMessageResponse(BAD_REQUEST, "Change syntax is invalid");
        } catch (DuplicateIdException e) {
            return getMessageResponse(CONFLICT, "Change with specified ID already exists");
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAllChanges() {
        schedule.getChanges().removeAllChanges();
        return getResponse(NO_CONTENT);
    }

    @GetMapping(value = "/{id}", produces = JSON)
    public ResponseEntity<String> getChange(@PathVariable String id) {
        try {
            UUID uuid = UUID.fromString(id);
            Change change = schedule.getChanges().findChange(uuid);
            return getBodyResponse(OK, gson.toJson(change));
        } catch (IllegalArgumentException e) {
            return getMessageResponse(BAD_REQUEST, "Change ID format is invalid");
        } catch (NoSuchChangeException e) {
            return getMessageResponse(NOT_FOUND, "Change does not exist");
        }
    }

    @PutMapping(value = "/{id}", consumes = JSON, produces = JSON)
    public ResponseEntity<String> updateChange(@PathVariable String id,
                                               @RequestBody String body) {
        try {
            Change change = gson.fromJson(body, Change.class);
            UUID pathId = UUID.fromString(id);
            if (!change.getId().equals(pathId)) {
                return getMessageResponse(BAD_REQUEST, "ID in path and in body do not match");
            }
            schedule.getChanges().updateChange(change);
            return getResponse(NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return getMessageResponse(BAD_REQUEST, "Change ID format is invalid");
        } catch (JsonSyntaxException e) {
            return getMessageResponse(BAD_REQUEST, "Change syntax is invalid");
        } catch (NoSuchChangeException e) {
            return getMessageResponse(NOT_FOUND, "Change does not exist");
        }
    }

    @DeleteMapping(value = "/{id}", produces = JSON)
    public ResponseEntity<String> deleteChange(@PathVariable String id) {
        try {
            UUID uuid = UUID.fromString(id);
            Change change = schedule.getChanges().findChange(uuid);
            schedule.getChanges().removeChange(change);
            return getResponse(NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return getMessageResponse(BAD_REQUEST, "Change ID format is invalid");
        } catch (NoSuchChangeException e) {
            return getMessageResponse(NOT_FOUND, "Change does not exist");
        }
    }

}