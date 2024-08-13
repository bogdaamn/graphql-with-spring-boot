package com.example.demo.controller;

import com.example.demo.response.StudentResponse;
import com.example.demo.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping("/get/{id}")
    public StudentResponse getStudent(@PathVariable Integer id){
        return clientService.getStudent(id);
    }
}