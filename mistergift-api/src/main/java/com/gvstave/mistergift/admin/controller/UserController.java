package com.gvstave.mistergift.admin.controller;

import com.gvstave.mistergift.data.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Secured("ROLE_USER")
    @RequestMapping(method = { RequestMethod.GET }, produces = { "application/json" })
    public ResponseEntity<User> getCurrentUser() {
        return new ResponseEntity<>(new User(), HttpStatus.OK);
    }

}
