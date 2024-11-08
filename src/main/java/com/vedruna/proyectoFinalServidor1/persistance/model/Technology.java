package com.vedruna.proyectoFinalServidor1.persistance.model;

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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name="technologies")
public class Technology {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="tech_id")
    private int id;

    @Column(name="tech_name")
    private String name;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name="technologies_used_in_projects", joinColumns={@JoinColumn(name="players_idplayer")}, inverseJoinColumns={@JoinColumn(name="trophies_idtrophie")})
    private List<Project> projectsTechnologies;




    
}
