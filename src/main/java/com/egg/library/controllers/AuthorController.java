/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.library.controllers;

import com.egg.library.entities.Author;
import com.egg.library.exceptions.ExceptionService;
import com.egg.library.services.AuthorService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author LENOVO
 */
@Controller
@RequestMapping("/autor")
public class AuthorController {

    @Autowired
    private AuthorService aService;

    @GetMapping("/registro")
    public String form() {

        return "form-author";
    }

    @PostMapping("/registro")
    public String save(ModelMap model, @RequestParam(required = true) String nombre) {
        try {
            aService.createAuthor(nombre);

            model.put("exito", "Autor creado exitosamente!!");

            return "form-author";
        } catch (Exception e) {
            model.put("error", e.getMessage());
            return "form-author";
        }
    }

    @GetMapping("/lista")
    public String list(ModelMap model) {
        try {

            List<Author> autoresLista = aService.searchAuthors();
            model.addAttribute("autores", autoresLista);
            return "list-author";
        } catch (Exception e) {
            model.put("error", e.getMessage());
            return "list-author";
        }
    }

    //en ninguno de los dos metodos de los botones para dar de baja en el controlador, le coloque en los parametros el ModelMap
    //sin embargo funciono bien ya que no lo estoy implementando. al mismo tiempo en los metodos drop y hight del servicio de
    //autor tampoco retorno nada a diferencia de los metodos que el profesor fiordelisi hizo, y de igual manera funciona bien
    //ya que el controlador no requiere que el servicio devuelva nada
    @GetMapping("/baja/{id}")
    public String baja(@PathVariable String id) {
        try {
            aService.dropAuthor(id);
            return "redirect:/autor/lista";
        } catch (Exception e) {
            return "redirect:/";
        }
    }

    @GetMapping("/alta/{id}")
    public String alta(@PathVariable String id) {
        try {
            aService.highAuthor(id);
            return "redirect:/autor/lista";
        } catch (Exception e) {
            return "redirect:/";
        }
    }

    @GetMapping("/modificar/{id}")
    public String modify(@PathVariable String id, ModelMap model) {
        try {
            model.put("autor", aService.searchById(id));
            return "modify-author";
        } catch (Exception e) {
            return "modify-author";
        }

    }
    
    @PostMapping("/modificar/{id}")
    public String modify(@PathVariable String id, ModelMap model, @RequestParam(required=false) String nombre) {
        try {
            aService.modifyNameAuthor(id, nombre);
            model.put("exito", "El autor se modificó exitosamente!!");
            return "redirect:/autor/lista";
        } catch (ExceptionService e) {
            try {
                model.put("autor", aService.searchById(id));
            } catch (ExceptionService ex) {
                return "redirect:/autor/lista";
            }
            model.put("error", e.getMessage());
            return "modify-author";
        }

    }
}
