package com.vedruna.proyectoFinalServidor1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vedruna.proyectoFinalServidor1.dto.ResponseDTO;
import com.vedruna.proyectoFinalServidor1.persistance.model.Technology;
import com.vedruna.proyectoFinalServidor1.services.TechnologyServiceI;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class TechnologyController {

    @Autowired
    private TechnologyServiceI technologyService;

    @Operation(summary = "Create a new technology", description = "Saves a new technology and associates it with projects if provided.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Technology created successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Invalid input or technology ID already exists",
                    content = @Content)
    })
    @PostMapping("/technologies")
    public ResponseEntity<String> createTechnology(@RequestBody Technology technology) {
        try {
            technologyService.saveTechnology(technology);
            return ResponseEntity.status(HttpStatus.CREATED).body("Technology created successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Delete a technology", description = "Deletes a technology by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Technology successfully removed",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Technology not found",
                    content = @Content)
    })
    @DeleteMapping("/technologies/{id}")
    public ResponseEntity<ResponseDTO<String>> deleteTechnology(@PathVariable Integer id) {
        boolean technologyDeleted = technologyService.deleteTechnology(id);
        if (!technologyDeleted) {
            throw new IllegalArgumentException("There isn't a technology with the ID: " + id);
        }
        ResponseDTO<String> response = new ResponseDTO<>("Technology successfully removed", null);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Associate a technology with a project", description = "Links a technology to a specific project by their IDs.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Technology associated with project successfully",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input or IDs not found",
                    content = @Content)
    })
    @PostMapping("/technologies/used/{projectId}/{technologyId}")
    public ResponseEntity<String> associateTechnologyWithProject(@PathVariable int projectId, @PathVariable int technologyId) {
        try {
            technologyService.associateTechnologyWithProject(projectId, technologyId);
            return ResponseEntity.status(HttpStatus.OK).body("Technology associated with project successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
