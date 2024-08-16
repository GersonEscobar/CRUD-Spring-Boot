package com.app.web.entidad;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="estudiante")
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="nombre", nullable = false, length = 30)
    private String nombre;

    @Column(name="apellido", nullable = false, length = 30)
    private String apellido;

    @Column(name="email", nullable = false, length = 40, unique = true)
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
}
