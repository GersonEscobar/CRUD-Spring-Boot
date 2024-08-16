package com.app.web.servicio;

import com.app.web.entidad.Persona;
import com.app.web.repositorio.PersonaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

@Service
public class PersonaServicio {

    @Autowired
    private PersonaRepositorio personaRepositorio;

    private static final String ARCHIVO_OPERACIONES = "logs/operaciones.txt";

    public List<Persona> listarPersonas() {
        return personaRepositorio.findAll();
    }

    public Persona guardarPersona(Persona persona) {
        Persona personaGuardada = personaRepositorio.save(persona);
        escribirEnArchivo("Persona guardada: " + personaGuardada);
        return personaGuardada;
    }

    public Persona obtenerPersonaPorId(Long id) {
        Optional<Persona> optionalPersona = personaRepositorio.findById(id);
        if (optionalPersona.isPresent()) {
            return optionalPersona.get();
        } else {
            throw new RuntimeException("Persona con ID " + id + " no encontrado.");
        }
    }

    public Persona actualizarPersona(Persona persona) {
        if (persona.getId() != null && personaRepositorio.existsById(persona.getId())) {
            Persona estudianteActualizado = personaRepositorio.save(persona);
            escribirEnArchivo("Persona actualizado: " + estudianteActualizado);
            return estudianteActualizado;
        } else {
            throw new RuntimeException("Persona con ID " + persona.getId() + " no encontrado para actualización.");
        }
    }

    public void eliminarPersona(Long id) {
        if (personaRepositorio.existsById(id)) {
            personaRepositorio.deleteById(id);
            escribirEnArchivo("Persona eliminado con ID: " + id);
        } else {
            throw new RuntimeException("Persona con ID " + id + " no encontrado para eliminación.");
        }
    }

    private void escribirEnArchivo(String mensaje) {
        try (FileWriter fileWriter = new FileWriter(ARCHIVO_OPERACIONES, true);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {
            printWriter.println(mensaje);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
