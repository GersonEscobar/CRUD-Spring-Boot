package com.app.web.controlador;


import com.app.web.entidad.Estudiante;
import com.app.web.servicio.EstudianteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class EstudianteControlador {

    @Autowired
    private EstudianteServicio estudianteServicio;

    @GetMapping({"/estudiantes", "/"})
    public String listarEstudiantes(Model modelo){
        modelo.addAttribute("estudiantes",estudianteServicio.listarEstudiantes() );
        return "estudiantes";
    }

    @GetMapping({"/estudiantes/nuevo"})
    public String formularioEstudiante(Model modelo){
        Estudiante estudiante= new Estudiante();
        modelo.addAttribute("estudiante", estudiante);
        return "crear_estudiante";
    }

    @PostMapping ("/estudiantes")
    public String guardarEstudiante(@ModelAttribute("estudiante") Estudiante estudiante){
        estudianteServicio.guardarEstudiante(estudiante);
        return "redirect:estudiantes";
    }

    @GetMapping("/estudiantes/editar/{id}")
    public String formularioEditar(@PathVariable Long id, Model modelo){
        modelo.addAttribute("estudiante", estudianteServicio.obtenerEstudiantePorId(id));
        return "editar_estudiante";
    }

    @PostMapping("/estudiantes/{id}")
    public String actualizarEstudiante(@PathVariable Long id, @ModelAttribute("estudiante") Estudiante estudiante, Model modelo) {
        try {
            Estudiante estudianteExistente = estudianteServicio.obtenerEstudiantePorId(id);
            estudianteExistente.setId(id);
            estudianteExistente.setNombre(estudiante.getNombre());
            estudianteExistente.setApellido(estudiante.getApellido());
            estudianteExistente.setEmail(estudiante.getEmail());
            estudianteServicio.actualizarEstudiante(estudianteExistente);
            return "redirect:/estudiantes";
        } catch (RuntimeException e) {
            modelo.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/estudiantes/{id}")
    public String eliminarEstudiante(@PathVariable Long id){
        estudianteServicio.eliminarEstudiante(id);
        return "redirect:/estudiantes";
    }
}
