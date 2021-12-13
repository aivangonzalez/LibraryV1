/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.library.services;

import com.egg.library.entities.Author;
import com.egg.library.entities.Book;
import com.egg.library.entities.PublishingHouse;
import com.egg.library.exceptions.ExceptionService;
import com.egg.library.repositories.AuthorRepository;
import com.egg.library.repositories.BookRepository;
import com.egg.library.repositories.PublishingHouseRepository;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author LENOVO
 */
@Service
public class BookService {
    
    @Autowired
    BookRepository br;
    
    @Autowired
    AuthorRepository ar;
    
    @Autowired
    PublishingHouseRepository phr;
    
    @Transactional
    public void createBook(Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, String nameAuthor, String namePHouse) throws ExceptionService {
        
        validate(isbn, titulo, anio, ejemplares, ejemplaresPrestados, nameAuthor, namePHouse);
        
        Book book = new Book();
        book.setIsbn(isbn);
        book.setTitulo(titulo);
        book.setAnio(anio);
        book.setEjemplares(ejemplares);
        book.setEjemplaresPrestados(ejemplaresPrestados);
        book.setEjemplaresRestantes(ejemplares - ejemplaresPrestados);
        Author a = ar.searchAuthorByName(nameAuthor);
        book.setAuthor(a);
        PublishingHouse ph = phr.searchPubHouseByName(namePHouse);
        book.setP_house(ph);
        
        br.save(book);
        
    }
    
    @Transactional
    public void modifyBook(String id, Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, String nameAuthor, String namePHouse) throws ExceptionService {
        
        Book book = searchById(id);
        validate(isbn, titulo, anio, ejemplares, ejemplaresPrestados, nameAuthor, namePHouse);
        
        book.setIsbn(isbn);
        book.setTitulo(titulo);
        book.setAnio(anio);
        book.setEjemplares(ejemplares);
        book.setEjemplaresPrestados(ejemplaresPrestados);
        book.setEjemplaresRestantes(ejemplares - ejemplaresPrestados);
        Author author = ar.searchAuthorByName(nameAuthor);
        book.setAuthor(author);
        PublishingHouse pHouse = phr.searchPubHouseByName(namePHouse);
        book.setP_house(pHouse);
        
        br.save(book);
    }
    
    @Transactional
    public void dropBook(String id) throws ExceptionService {
        
        Book book = searchById(id);
        
        book.setAlta(false);
        br.save(book);
        
    }
    
    @Transactional
    public void highBook(String id) throws ExceptionService {
        
        Book book = searchById(id);
        
        book.setAlta(true);
        br.save(book);
        
    }
    
    public Book searchById(String id) throws ExceptionService {
        
        Book book = br.searchBookById(id);
        if (book == null) {
            throw new ExceptionService("No se encontró ningun libro.");
        }
        
        return book;
    }
    
    public Book searchByTitle(String title) throws ExceptionService {
        
        Book book = br.searchBookByTitle(title);
        if (book == null) {
            throw new ExceptionService("No se encontro ningun libro.");
        }
        
        return book;
    }
    
    public Book searchByIsbn(Long isbn) throws ExceptionService {
        
        Book book = br.searchBookByIsbn(isbn);
        if (book == null) {
            throw new ExceptionService("No se encontró ningun libro con el isbn.");
        }
        
        return book;
    }
    
    public List<Book> searchBooks() throws ExceptionService {
        
        List<Book> books = br.searchBooks();
        if (books == null || books.isEmpty()) {
            throw new ExceptionService("No se encontraron libros para mostrar.");
        }
        
        return books;
    }
    
    public List<Book> searchBooksByAuthor(String nameAuthor) throws ExceptionService {
        
        List<Book> books = br.searchBooksByAuthor(nameAuthor);
        if (books == null || books.isEmpty()) {
            throw new ExceptionService("No se encontraron libros para mostrar.");
        }
        
        return books;
    }
    
    public List<Book> searchBooksByPHouse(String namePHouse) throws ExceptionService {
        
        List<Book> books = br.searchBooksByPublishingHouse(namePHouse);
        if (books == null || books.isEmpty()) {
            throw new ExceptionService("No se encontraron libros para mostrar.");
        }
        
        return books;
    }
    
    public void validate(Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, String nameAuthor, String namePHouse) throws ExceptionService {
        if (isbn == null) {
            throw new ExceptionService("Error al ingresar el isbn.");
        }
        Book bIsbn = br.searchBookByIsbn(isbn);
        if (bIsbn != null) {
            throw new ExceptionService("Ya se encuentra un libro con el mismo isbn.");
        }
        if (titulo.trim().isEmpty() || titulo == null) {
            throw new ExceptionService("Error al ingresar el titulo.");
        }
        if (anio == null || anio < 1000 || anio > 2021) {
            throw new ExceptionService("Error al ingresar el año.");
        }
        if (ejemplares == null || ejemplares < 0) {
            throw new ExceptionService("Error al ingresar la cantidad de ejemplares.");
        }
        if (ejemplaresPrestados == null || ejemplaresPrestados > ejemplares) {
            throw new ExceptionService("La cantidad de ejemplares prestados no debe ser mayor a la cantidad total libros.");
        }
        
        Author a = ar.searchAuthorByName(nameAuthor);
        if (a == null) {
            throw new ExceptionService("No se encontró el autor en la base de datos. Antes de ingresar un libro o modificarlo debe ingresar el autor.");
        }
        
        PublishingHouse ph = phr.searchPubHouseByName(namePHouse);
        if (ph == null) {
            throw new ExceptionService("No se encontró la editorial en la base de datos. Antes de ingresar un libro o modificarlo debe ingresar la editorial.");
        }
    }
}
