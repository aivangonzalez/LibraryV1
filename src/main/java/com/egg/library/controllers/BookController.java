/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.library.controllers;

import com.egg.library.entities.Book;
import com.egg.library.exceptions.ExceptionService;
import com.egg.library.services.BookService;
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
@RequestMapping("/libro")
public class BookController {

    @Autowired
    private BookService bService;

    @GetMapping("/registro")
    public String form() {

        return "form-books";
    }

    @PostMapping("/registro")
    public String formBooks(ModelMap model, /*@RequestParam */ Long isbn, @RequestParam String titulo, /*@RequestParam*/ Integer anio,
            /*@RequestParam*/ Integer ejemplares, /*@RequestParam*/ Integer ejemplaresPrestados, @RequestParam String autor, @RequestParam String editorial) {
        try {
            bService.createBook(isbn, titulo, anio, ejemplares, ejemplaresPrestados, autor, editorial);

            model.put("exito", "Libro creado exitosamente!!");
            return "form-books";
        } catch (Exception e) {
            model.put("error", e.getMessage());
            return "form-books";
        }
    }

    @GetMapping("/lista")
    public String listBooks(ModelMap model) {
        try {
            List<Book> librosLista = bService.searchBooks();

            model.addAttribute("libros", librosLista);

            return "list-books";
        } catch (Exception e) {
            model.put("error", e.getMessage());
            return "list-books";
        }
    }

    @GetMapping("/baja/{id}")
    public String baja(ModelMap model, @PathVariable String id) {

        try {
            bService.dropBook(id);
            return "redirect:/libro/lista";
        } catch (Exception e) {
            return "redirect:/";
        }

    }

    @GetMapping("/alta/{id}")
    public String alta(ModelMap modelo, @PathVariable String id) {

        try {
            bService.highBook(id);
            return "redirect:/libro/lista";
            //me redirecciona al metodo get de este controlador que se encarga de volver a cargar la lista con el atributo
            //alta modificado
        } catch (Exception e) {
            return "redirect:/";
        }

    }

    @GetMapping("/modificar/{id}")
    public String modify(@PathVariable String id, ModelMap model) {
        try {
            model.put("libro", bService.searchById(id));
            return "modify-book";
        } catch (Exception e) {
            return "modify-book";
        }
    }

    @PostMapping("/modificar/{id}")
    public String modify(@PathVariable String id, ModelMap model, /*@RequestParam*/ Long isbn, @RequestParam String titulo, /*@RequestParam*/ Integer anio,
            /*@RequestParam*/ Integer ejemplares, /*@RequestParam*/ Integer ejemplaresPrestados, @RequestParam String autor, @RequestParam String editorial) {
        try {
            bService.modifyBook(id, isbn, titulo, anio, ejemplares, ejemplaresPrestados, autor, editorial);

            return "redirect:/libro/lista";
        } catch (Exception e) {
            try {
                model.put("libro", bService.searchById(id));
            } catch (ExceptionService ex) {
                return "redirect:/libro/lista";
            }
            model.put("error", e.getMessage());
            return "modify-book";
        }
    }

}
