package com.vedruna.proyectoFinalServidor1.persistance.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
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
    private String name;

    @Column(name="dev_surname")
    private String surname;
    
    @Column(name="email")
    private String email;

    @Column(name="linkedin_url")
    private String linkedin_url;

    @Column(name="github_url")
    private String github_url;

    
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name="developers_worked_on_projects", joinColumns={@JoinColumn(name="developers_dev_id")}, inverseJoinColumns={@JoinColumn(name="projects_project_id")})
    private List<Project> projectsDevelopers;


}
