/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.library.controllers;

import com.egg.library.entities.PublishingHouse;
import com.egg.library.exceptions.ExceptionService;
import com.egg.library.services.PublishingHouseService;
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
@RequestMapping("/editorial")
public class PHouseController {

    @Autowired
    private PublishingHouseService phService;

    @GetMapping("/registro")
    public String form() {

        return "form-phouse";
    }

    @PostMapping("/registro")
    public String save(ModelMap model, @RequestParam(required = true) String nombre) {
        try {
            phService.createPHouse(nombre);

            model.put("exito", "Editorial creada exitosamente!!");
            return "form-phouse";
        } catch (Exception e) {
            model.put("error", e.getMessage());
            return "form-phouse";
        }
    }

    @GetMapping("/lista")
    public String list(ModelMap model) {
        try {
            List<PublishingHouse> editorialesLista = phService.searchPHouses();

            model.put("editoriales", editorialesLista);
            return "list-phouse";
        } catch (Exception e) {

            model.put("error", e.getMessage());
            return "list-phouse";
        }
    }

    @GetMapping("/baja/{id}")
    public String baja(@PathVariable String id) {
        try {
            phService.dropHouse(id);
            return "redirect:/editorial/lista";
        } catch (Exception e) {
            return "redirect:/";
        }
    }

    @GetMapping("/alta/{id}")
    public String alta(@PathVariable String id) {
        try {
            phService.highHouse(id);
            return "redirect:/editorial/lista";
        } catch (Exception e) {
            return "redirect:/";
        }
    }

    @GetMapping("/modificar/{id}")
    public String modify(@PathVariable String id, ModelMap model) {
        try {
            model.put("editorial", phService.searchById(id));
            return "modify-phouse";
        } catch (Exception e) {
            return "modify-phouse";
        }
    }

    @PostMapping("/modificar/{id}")
    public String modify(@PathVariable String id, ModelMap model, @RequestParam String nombre) {
        try {
            phService.modifyPHouse(id, nombre);
            model.put("exito", "Editorial modificada exitosamente!!");
            return "redirect:/editorial/lista";
        } catch (Exception e) {
            //Aca modelo nuevamente la editorial para poder mostrar el error
            try {
                model.put("editorial", phService.searchById(id));
            } catch (ExceptionService ex) {
                return "redirect:/editorial/registro";
            }
            model.put("error", e.getMessage());
            return "modify-phouse";
        }
    }
}
