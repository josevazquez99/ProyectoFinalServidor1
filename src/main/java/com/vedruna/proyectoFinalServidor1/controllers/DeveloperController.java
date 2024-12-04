package com.vedruna.proyectoFinalServidor1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vedruna.proyectoFinalServidor1.dto.ResponseDTO;
import com.vedruna.proyectoFinalServidor1.persistance.model.Developer;
import com.vedruna.proyectoFinalServidor1.persistance.model.Project;
import com.vedruna.proyectoFinalServidor1.services.DeveloperServiceI;
import com.vedruna.proyectoFinalServidor1.services.ProjectServiceI;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class DeveloperController {

    @Autowired
    private DeveloperServiceI developerService;

    @Autowired
    private ProjectServiceI projectService;

    @Operation(summary = "Create a new developer", description = "Adds a new developer to the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Developer created successfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Validation error or bad request",
                    content = @Content)
    })
    @PostMapping("/developers")
    public ResponseEntity<ResponseDTO<String>> postDeveloper(@RequestBody Developer developer) {
        developerService.saveDeveloper(developer);
        ResponseDTO<String> response = new ResponseDTO<>("Developer created successfully", null);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @Operation(summary = "Delete a developer", description = "Removes a developer by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Developer successfully removed",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Developer not found",
                    content = @Content)
    })
    @DeleteMapping("/developers/{id}")
    public ResponseEntity<ResponseDTO<String>> deleteDeveloper(@PathVariable Integer id) {
        boolean developerDeleted = developerService.deleteDeveloper(id);
        if (!developerDeleted) {
            throw new IllegalArgumentException("There isn't a developer with the ID: " + id);
        }
        ResponseDTO<String> response = new ResponseDTO<>("Developer successfully removed", null);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Assign a developer to a project", description = "Links a developer to a project by their IDs.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Developer successfully added to project",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Developer or project not found",
                    content = @Content)
    })
    @PostMapping("/developers/worked/{developerId}/{projectId}")
    public ResponseEntity<?> addDeveloperToProject(@PathVariable int developerId, @PathVariable int projectId) {
        Developer developer = developerService.findById(developerId);
        Project project = projectService.findById(projectId);

        if (developer == null) {
            return ResponseEntity.badRequest().body("Developer not found");
        }

        if (project == null) {
            return ResponseEntity.badRequest().body("Project not found");
        }

        if (!project.getDevelopers().contains(developer)) {
            project.getDevelopers().add(developer);
            developer.getProjectsDevelopers().add(project);
            projectService.saveProject(project);
            developerService.saveDeveloper(developer);
        }

        return ResponseEntity.ok("Developer added to project");
    }
}
