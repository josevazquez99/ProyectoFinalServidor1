package com.vedruna.proyectoFinalServidor1.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.vedruna.proyectoFinalServidor1.dto.ProjectDTO;
import com.vedruna.proyectoFinalServidor1.dto.ResponseDTO;
import com.vedruna.proyectoFinalServidor1.persistance.model.Project;
import com.vedruna.proyectoFinalServidor1.services.ProjectServiceI;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class ProjectController {

    @Autowired
    private ProjectServiceI projectService;

    @Operation(summary = "Retrieve all projects", description = "Fetches a paginated list of all projects.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Projects retrieved successfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Page.class))})
    })
    @GetMapping("/projects")
    public Page<ProjectDTO> getAllProjects(@RequestParam("page") int page, @RequestParam("size") int size) {
        return projectService.showAllProjects(page, size);
    }

    @Operation(summary = "Search projects by name", description = "Fetches a paginated list of projects filtered by name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Projects retrieved successfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Page.class))}),
            @ApiResponse(responseCode = "404", description = "No projects found with the provided name",
                    content = @Content)
    })
    @GetMapping("/projects/{name}")
    public ResponseEntity<Page<ProjectDTO>> getProjectsByName(
            @PathVariable String name,
            @RequestParam("page") int page,
            @RequestParam("size") int size) {
        Page<ProjectDTO> projects = projectService.showProjectsByName(name, page, size);
        return ResponseEntity.ok(projects);
    }

    @Operation(summary = "Create a new project", description = "Adds a new project to the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Project created successfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Validation error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class))})
    })
    @PostMapping("/projects")
    public ResponseEntity<ResponseDTO<Object>> postProject(@Valid @RequestBody Project project, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessages = new StringBuilder();
            bindingResult.getFieldErrors().forEach(error ->
                    errorMessages.append(error.getField())
                            .append(": ")
                            .append(error.getDefaultMessage())
                            .append("\n")
            );
            ResponseDTO<Object> response = new ResponseDTO<>("Validation Error", errorMessages.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        LocalDate today = LocalDate.now();
        if (project.getStart_date().toLocalDate().isBefore(today)) {
            ResponseDTO<Object> response = new ResponseDTO<>("Validation Error", "The start date cannot be before today.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        projectService.saveProject(project);
        ResponseDTO<Object> response = new ResponseDTO<>("Project created successfully", null);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Delete a project", description = "Deletes a project by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project deleted successfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Project not found",
                    content = @Content)
    })

    @DeleteMapping("/projects/{id}")
    public ResponseEntity<ResponseDTO<String>> deleteProject(@PathVariable Integer id) {
        boolean projectDeleted = projectService.deleteProject(id);
        if (!projectDeleted) {
            throw new IllegalArgumentException("There isn't any project with the ID: " + id);
        }
        ResponseDTO<String> response = new ResponseDTO<>("Project deleted successfully", "Project with ID " + id + " deleted.");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Update a project", description = "Updates the details of an existing project.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project updated successfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Validation error",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Project not found",
                    content = @Content)
    })

    @PutMapping("/projects/{id}")
    public ResponseEntity<ResponseDTO<Object>> updateProject(@PathVariable Integer id, @Valid @RequestBody Project project, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessages = new StringBuilder();
            bindingResult.getFieldErrors().forEach(error ->
                    errorMessages.append(error.getField())
                            .append(": ")
                            .append(error.getDefaultMessage())
                            .append("\n")
            );

            ResponseDTO<Object> response = new ResponseDTO<>("Validation Error", errorMessages.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        boolean projectUpdated = projectService.updateProject(id, project);
        if (!projectUpdated) {
            ResponseDTO<Object> response = new ResponseDTO<>("Error", "There isn't any project with the ID: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        ResponseDTO<Object> response = new ResponseDTO<>("Project updated successfully", project);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Update a project state", description = "Updates the state of an existing project.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project updated successfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Validation error",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Project not found",
                    content = @Content)
    })
    @PatchMapping("/projects/totesting/{id}")
    public ResponseEntity<String> moveProjectToTesting(@PathVariable Integer id) {
        try {
            boolean result = projectService.moveProjectToTesting(id);
            if (result) {
                return ResponseEntity.ok("Projects moved to testing successfully");
            } else if(projectService.findById(id)==null){ 
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Project not found");
            }else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No projects were moved to testing");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while moving projects to testing: " + e.getMessage());
        }
    }
    
    @Operation(summary = "Update a project state", description = "Updates the state of an existing project.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project updated successfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Validation error",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Project not found",
                    content = @Content)
    })
    @PatchMapping("/projects/toprod/{id}")
    public ResponseEntity<String> moveProjectToProduction(@PathVariable Integer id) {
        try {
            boolean result = projectService.moveProjectToProduction(id);
            if (result) {
                return ResponseEntity.ok("Projects moved to production successfully");
            } else if(projectService.findById(id)==null){ 
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Project not found");
            }else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No projects were moved to testing");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while moving projects to production: " + e.getMessage());
        }
    }

    @Operation(summary = "Get projects by technology", description = "Fetches a list of projects filtered by technology.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project retrieved successfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Validation error",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Project not found",
                    content = @Content)
    })
    @GetMapping("/projects/tec/{tech}")
    public ResponseEntity<ResponseDTO<List<ProjectDTO>>> getProjectsByTechnology(@PathVariable String tech) {
        List<ProjectDTO> projects = projectService.getProjectsByTechnology(tech);
    
        if (projects.isEmpty()) {
            ResponseDTO<List<ProjectDTO>> response = new ResponseDTO<>("No projects found with this technology", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    
        ResponseDTO<List<ProjectDTO>> response = new ResponseDTO<>("Projects found successfully", projects);
        return ResponseEntity.ok(response);
    }
    
    
    

    
}
