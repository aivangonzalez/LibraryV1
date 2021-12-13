/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.library.repositories;

import com.egg.library.entities.Book;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author LENOVO
 */
@Repository
public interface BookRepository extends JpaRepository<Book, String>{
    
    //Buscar por id
    @Query("SELECT b FROM Book b WHERE b.id= :id")
    public Book searchBookById(@Param("id") String id);
    
    //Buscar por isbn
    @Query("SELECT b FROM Book b WHERE b.isbn= :isbn")
    public Book searchBookByIsbn(@Param("isbn") Long isbn);
    
    //Buscar por nombre
    @Query("SELECT b FROM Book b WHERE b.titulo= :titulo")
    public Book searchBookByTitle(@Param("titulo") String titulo);
    
    @Query("SELECT b FROM Book b")
    public List<Book> searchBooks();
    
    //Buscar libros por autor, ver si esto funcionaria teniendo en cuenta la anotacion onetoone
    @Query("SELECT b FROM Book b WHERE b.author.nombre= :nombre")
    public List<Book> searchBooksByAuthor(@Param("nombre") String nombre);
    
    //Buscar libros por editorial, ver si esto funcionaria teniendo en cuenta la anotacion onetoone
    @Query("SELECT b FROM Book b WHERE b.p_house.nombre= :nombre")
    public List<Book> searchBooksByPublishingHouse(@Param("nombre") String nombre);
}
