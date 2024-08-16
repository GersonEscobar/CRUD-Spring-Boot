package com.app.web.controlador;


import com.app.web.entidad.Persona;
import com.app.web.servicio.PersonaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PersonaControlador {

    @Autowired
    private PersonaServicio personaServicio;

    @GetMapping({"/personas", "/"})
    public String listarPersonas(Model modelo){
        modelo.addAttribute("personas", personaServicio.listarPersonas() );
        return "personas";
    }

    @GetMapping({"/personas/nuevo"})
    public String formularioPersonas(Model modelo){
        Persona persona= new Persona();
        modelo.addAttribute("persona", persona);
        return "crear_persona";
    }

    @PostMapping ("/personas")
    public String guardarPersona(@ModelAttribute("persona") Persona persona){
        personaServicio.guardarPersona(persona);
        return "redirect:personas";
    }

    @GetMapping("/personas/editar/{id}")
    public String formularioEditar(@PathVariable Long id, Model modelo){
        modelo.addAttribute("persona", personaServicio.obtenerPersonaPorId(id));
        return "editar_persona";
    }

    @PostMapping("/personas/{id}")
    public String actualizarEstudiante(@PathVariable Long id, @ModelAttribute("persona") Persona persona, Model modelo) {
        try {
            Persona personaExistente = personaServicio.obtenerPersonaPorId(id);
            personaExistente.setId(id);
            personaExistente.setNombre(persona.getNombre());
            personaExistente.setApellido(persona.getApellido());
            personaExistente.setEmail(persona.getEmail());
            personaServicio.actualizarPersona(personaExistente);
            return "redirect:/personas";
        } catch (RuntimeException e) {
            modelo.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/personas/{id}")
    public String eliminarEstudiante(@PathVariable Long id){
        personaServicio.eliminarPersona(id);
        return "redirect:/personas";
    }
}
