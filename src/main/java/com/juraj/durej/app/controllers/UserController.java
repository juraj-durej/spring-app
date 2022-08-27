package com.juraj.durej.app.controllers;

import com.juraj.durej.app.entities.User;
import com.juraj.durej.app.repositories.UserRepository;
import com.juraj.durej.app.services.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping(path = "${api}/users")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private UserRepository userRepository;


    @GetMapping("")
    ResponseEntity<List<User>> allUsers(
            @RequestParam(required = false, name = "filter", defaultValue = "{}") String filterStr,
            @RequestParam(required = false, name = "range", defaultValue = "[0,9]") String rangeStr,
            @RequestParam(required = false, name = "sort", defaultValue = "[\"id\",\"ASC\"]") String sortStr
    ) {

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Range", "users " + rangeStr.substring( 1, rangeStr.length() - 1 ).replace(",", "-") + "/" + userRepository.findAll().size());
        responseHeaders.set("Access-Control-Expose-Headers", "Content-Range");

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(service.getAllUsers(filterStr, rangeStr, sortStr).getContent());
    }

    @PostMapping("")
    ResponseEntity<User> createUser(@RequestBody User newUser) {
        return ResponseEntity.ok()
                .body(service.createUser(newUser));
    }

    @GetMapping("/{userId}")
    ResponseEntity<User> getUser(@PathVariable Long userId) {
        return ResponseEntity.ok()
                .body(service.getUserById(userId));
    }
    @ApiOperation("Updates item with the given ID")
    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Long userId, @RequestBody User user) {
        return ResponseEntity.ok()
                .body(service.updateUser(userId,user));
    }

    @ApiOperation("Deletes item with the given ID")
    @DeleteMapping("/{userId}")
    void deleteUser(@PathVariable Long userId) {
        service.deleteUser(userId);
    }
}
