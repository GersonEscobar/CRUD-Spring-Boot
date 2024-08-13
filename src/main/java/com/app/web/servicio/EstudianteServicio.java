package com.app.web.servicio;

import com.app.web.entidad.Estudiante;
import com.app.web.repositorio.EstudianteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

@Service
public class EstudianteServicio {

    @Autowired
    private EstudianteRepositorio estudianteRepositorio;

    private static final String ARCHIVO_OPERACIONES = "logs/operaciones.txt";

    public List<Estudiante> listarEstudiantes() {
        return estudianteRepositorio.findAll();
    }

    public Estudiante guardarEstudiante(Estudiante estudiante) {
        Estudiante estudianteGuardado = estudianteRepositorio.save(estudiante);
        escribirEnArchivo("Estudiante guardado: " + estudianteGuardado);
        return estudianteGuardado;
    }

    public Estudiante obtenerEstudiantePorId(Long id) {
        Optional<Estudiante> optionalEstudiante = estudianteRepositorio.findById(id);
        if (optionalEstudiante.isPresent()) {
            return optionalEstudiante.get();
        } else {
            throw new RuntimeException("Estudiante con ID " + id + " no encontrado.");
        }
    }

    public Estudiante actualizarEstudiante(Estudiante estudiante) {
        if (estudiante.getId() != null && estudianteRepositorio.existsById(estudiante.getId())) {
            Estudiante estudianteActualizado = estudianteRepositorio.save(estudiante);
            escribirEnArchivo("Estudiante actualizado: " + estudianteActualizado);
            return estudianteActualizado;
        } else {
            throw new RuntimeException("Estudiante con ID " + estudiante.getId() + " no encontrado para actualización.");
        }
    }

    public void eliminarEstudiante(Long id) {
        if (estudianteRepositorio.existsById(id)) {
            estudianteRepositorio.deleteById(id);
            escribirEnArchivo("Estudiante eliminado con ID: " + id);
        } else {
            throw new RuntimeException("Estudiante con ID " + id + " no encontrado para eliminación.");
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
