/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.library.services;

import com.egg.library.entities.Author;
import com.egg.library.exceptions.ExceptionService;
import com.egg.library.repositories.AuthorRepository;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author LENOVO
 */
@Service
public class AuthorService {

    @Autowired
    AuthorRepository ar;

    @Transactional
    public void createAuthor(String name) throws ExceptionService {

        if (name.trim().isEmpty() || name == null) {
            throw new ExceptionService("Error al ingresar el nombre del autor.");
        }
        Author a = ar.searchAuthorByName(name);
        if (a != null) {
            throw new ExceptionService("Ya se encuentra un autor con el nombre ingresado.");
        } else {
            Author author = new Author();
            author.setNombre(name);
            ar.save(author);
        }

    }

    @Transactional
    public void modifyNameAuthor(String id, String name) throws ExceptionService {

        Author author = ar.searchAuthorById(id);
        if (author == null) {//if (!!author) - entra si el objeto no esta vacio
            throw new ExceptionService("No se encontró ningun autor para modificar.");
        }
        //Validar si el nuevo nombre se coloco correctamente por el usuario
        if (name.trim().isEmpty() || name == null) {
            throw new ExceptionService("Error al ingresar el nuevo nombre.");
        }

        //Seteo el nuevo nombre
        author.setNombre(name);
        //Lo guardo en la base de datos
        ar.save(author);
    }

    //Metodo para dar de baja a un autor (no es para eliminarlo sino que cambia el atributo alta)
    @Transactional
    public void dropAuthor(String id) throws ExceptionService {

        Author author = ar.searchAuthorById(id);
        if (author == null) {
            throw new ExceptionService("No se encontró ningun autor para dar de baja.");
        }

        //Modifico el atributo alta por falso y lo modifico en la base de datos llamando al repositorio
        author.setAlta(false);
        ar.save(author);

    }

    @Transactional
    public void highAuthor(String id) throws ExceptionService {

        Author author = ar.searchAuthorById(id);
        if (author == null) {
            throw new ExceptionService("No se encontró ningun autor para dar de baja.");
        }

        //Modifico el atributo alta por falso y lo modifico en la base de datos llamando al repositorio
        author.setAlta(true);
        ar.save(author);

    }
    
    public Author searchById(String id) throws ExceptionService {
        Author author = ar.searchAuthorById(id);
        if (author == null) {
            throw new ExceptionService("No se encontró ningun autor.");
        }

        return author;
    }

    public Author searchByName(String name) throws ExceptionService {
        Author author = ar.searchAuthorByName(name);
        if (author == null) {
            throw new ExceptionService("No se encontro ningun autor.");
        }

        return author;
    }

    public List<Author> searchAuthors() throws ExceptionService {
        List<Author> authors = ar.searchAuthors();
        if (authors.isEmpty()) {
            throw new ExceptionService("No hay autores para mostrar.");
        }

        return authors;
    }

}
