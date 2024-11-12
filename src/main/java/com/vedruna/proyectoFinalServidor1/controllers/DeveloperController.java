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
    /**
     * Creates a new developer.
     *
     * @param developer the developer to be created, given in the request body
     * @return a ResponseEntity with the HTTP status 201 (Created) and a ResponseDTO containing a success message
     */
    @PostMapping("/developers")
    public ResponseEntity<ResponseDTO<String>> postDeveloper(@RequestBody Developer developer) {
        developerService.saveDeveloper(developer);
        ResponseDTO<String> response = new ResponseDTO<>("Developer created successfully", null);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
    /**
     * Deletes a developer by their ID.
     *
     * @param id the ID of the developer to be deleted
     * @return a ResponseEntity with the HTTP status 204 (No Content) and a ResponseDTO containing a success message
     * @throws IllegalArgumentException if no developer exists with the given ID
     */
    @DeleteMapping("/developers/{id}")
    public ResponseEntity<ResponseDTO<String>> deleteDeveloper(@PathVariable Integer id) {
        boolean developerDeleted = developerService.deleteDeveloper(id);
        if (!developerDeleted) {
            throw new IllegalArgumentException("There isn't a developer with the ID: " + id);
        }
        ResponseDTO<String> response = new ResponseDTO<>("Developer successfully removed", null);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }
    
    
}
