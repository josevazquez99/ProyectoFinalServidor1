package com.vedruna.proyectoFinalServidor1.persistance.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name="technologies")
public class Technology implements Serializable {

    @Id
    @Column(name="tech_id")
    @NotNull(message = "Id cannot be null")
    private int id;

    @Column(name="tech_name")
    private String name;

    @ManyToMany(cascade = {CascadeType.PERSIST})
    @JoinTable(name="technologies_used_in_projects", joinColumns={@JoinColumn(name="technologies_tech_id")}, inverseJoinColumns={@JoinColumn(name="projects_project_id")})
    private List<Project> projectsTechnologies = new ArrayList<>();




    
}
