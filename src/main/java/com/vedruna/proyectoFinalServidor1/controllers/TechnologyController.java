package com.vedruna.proyectoFinalServidor1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vedruna.proyectoFinalServidor1.dto.ResponseDTO;
import com.vedruna.proyectoFinalServidor1.persistance.model.Technology;
import com.vedruna.proyectoFinalServidor1.services.TechnologyServiceI;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class TechnologyController {

    @Autowired
    private TechnologyServiceI technologyService;

    /**
     * Saves a technology with the projects associated
     * @param technology the technology to be saved
     * @return a ResponseEntity with the HTTP status 201 (Created) and a ResponseDTO containing a success message
     */
    @PostMapping("/technologies")
    public ResponseEntity<String> createTechnology(@RequestBody Technology technology) {
        try {
            technologyService.saveTechnology(technology);
            return ResponseEntity.status(HttpStatus.CREATED).body("Technology created successfully");
        } catch (IllegalArgumentException e) {
            // Si el ID ya está en uso o algún otro error, devuelve un 400
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    /**
     * Deletes a technology by its ID.
     * 
     * @param id the ID of the technology to be deleted
     * @return a ResponseEntity with the HTTP status 204 (No Content) and a ResponseDTO containing a success message, or a 404 if there isn't a technology with the given ID
     */
    @DeleteMapping("/technologies/{id}")
    public ResponseEntity<ResponseDTO<String>> deleteTechnology(@PathVariable Integer id) {
        boolean technologyDeleted = technologyService.deleteTechnology(id);
        if (!technologyDeleted) {
            throw new IllegalArgumentException("There isn't a technology with the ID: " + id);
        }
        ResponseDTO<String> response = new ResponseDTO<>("Technology successfully removed", null);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }
    
    
}
