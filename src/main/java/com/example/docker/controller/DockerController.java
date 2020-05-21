/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.docker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author kobe
 */
@RestController
@RequestMapping("docker/v1")
public class DockerController {
    
    @GetMapping
    public String hello(){
        return "Hello Docker Geek";
    }
}
