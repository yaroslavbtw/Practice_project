package com.practice.practiceproj.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.practice.practiceproj.core.dtos.user.UserLoginDTO;
import com.practice.practiceproj.core.dtos.user.UserRegistrationDTO;
import com.practice.practiceproj.service.IService.IPersonalAccountService;
import jakarta.validation.Valid;
import netscape.javascript.JSObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController()
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/account")
public class PersonalAccountController {

    private final IPersonalAccountService service;

    public PersonalAccountController(IPersonalAccountService service) {
        this.service = service;
    }


    @PostMapping(path = "/registration")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegistrationDTO user) {
        service.register(user);
        return ResponseEntity.status(201).build();
    }

    @GetMapping(path = "/verification")
    public ResponseEntity<?> verified(@RequestParam(value = "code") String code,
                                      @RequestParam(value = "mail") String mail) {
        service.verified(code, mail);
        return ResponseEntity.status(200).build();
    }

    @PostMapping(path = "/login")
    public ResponseEntity<String> login(@Valid @RequestBody UserLoginDTO user) {
        JsonObject responseJson = new JsonObject();
        responseJson.add("token", new JsonPrimitive(service.login(user)));
        return ResponseEntity.ok(responseJson.toString());
    }

    @GetMapping(path = "/me")
    public ResponseEntity<Object> get() {
        return ResponseEntity.status(200).body(service.getMe());
    }
}
