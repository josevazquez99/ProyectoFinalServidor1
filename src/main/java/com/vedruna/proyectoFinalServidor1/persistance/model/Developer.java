package com.vedruna.proyectoFinalServidor1.persistance.model;

import java.io.Serializable;
import java.util.List;

import com.vedruna.proyectoFinalServidor1.validation.ValidUrl;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name="developers")
public class Developer implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="dev_id")
    private int id;

    @Column(name="dev_name")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @Column(name="dev_surname")
    @Size(min = 2, max = 50, message = "Surname must be between 2 and 50 characters")
    private String surname;

    @Column(name="email")
    @Email(message = "Email should be valid")
    private String email;


    @Column(name="linkedin_url")
    @ValidUrl(message = "Invalid URL format")
    private String linkedin_url;

    @Column(name="github_url")
    @ValidUrl(message = "Invalid URL format")
    private String github_url;

    
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name="developers_worked_on_projects", joinColumns={@JoinColumn(name="developers_dev_id")}, inverseJoinColumns={@JoinColumn(name="projects_project_id")})
    private List<Project> projectsDevelopers;


}
