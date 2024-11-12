package com.vedruna.proyectoFinalServidor1.persistance.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import com.vedruna.proyectoFinalServidor1.validation.ValidUrl;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name="projects")
public class Project implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="project_id")
    private int id;

    @Column(name="project_name")
    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be empty")
    private String name;

    @Column(name="description")
    @Size(min = 2, max = 50, message = "Description must be between 2 and 50 characters")
    private String description;
    
    @Column(name="start_date")
    @FutureOrPresent(message = "The start date cannot be before today")
    private Date start_date;

    @Column(name="end_date")
    private Date end_date;

    @Column(name="repository_url")
    @ValidUrl(message = "Invalid URL format")
    private String repository_url;

    @Column(name="demo_url")
    @ValidUrl(message = "Invalid URL format")
    private String demo_url;

    @Column(name="picture")
    private String picture;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="status_status_id", referencedColumnName = "status_id")
    private State stateProject;

    @ManyToMany(cascade = {CascadeType.ALL}, mappedBy="projectsTechnologies")
    private List<Technology> technologies;

    @ManyToMany(cascade = {CascadeType.ALL}, mappedBy="projectsDevelopers")
    private List<Developer> developers;

}
