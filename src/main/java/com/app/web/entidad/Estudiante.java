package com.app.web.entidad;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="estudiante")
public class Estudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="nombre", nullable = false, length = 30)
    private String nombre;

    @Column(name="apellido", nullable = false, length = 30)
    private String apellido;

    @Column(name="email", nullable = false, length = 40, unique = true)
    private String email;
}
