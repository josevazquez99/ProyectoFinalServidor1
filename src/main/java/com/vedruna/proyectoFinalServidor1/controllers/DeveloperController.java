package com.vedruna.proyectoFinalServidor1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vedruna.proyectoFinalServidor1.dto.ResponseDTO;
import com.vedruna.proyectoFinalServidor1.persistance.model.Developer;
import com.vedruna.proyectoFinalServidor1.services.DeveloperServiceI;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class DeveloperController {

    @Autowired
    private DeveloperServiceI developerService;

    @PostMapping("/developers")
    public ResponseEntity<ResponseDTO<Developer>> postDeveloper(@RequestBody Developer developer) {
        developerService.saveDeveloper(developer);
        ResponseDTO<Developer> response = new ResponseDTO<>("Developer successfully saved", developer);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @DeleteMapping("/developers/{id}")
    public ResponseEntity<ResponseDTO<String>> deleteDeveloper(@PathVariable Integer id) {
        boolean developerDeleted = developerService.deleteDeveloper(id);
        if (!developerDeleted) {
            throw new IllegalArgumentException("There isn't a developer with the ID: " + id);  // Lanzamos una excepción estándar.
        }
        ResponseDTO<String> response = new ResponseDTO<>("Developer successfully removed", "Developer with ID " + id + " has been removed.");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }
}
