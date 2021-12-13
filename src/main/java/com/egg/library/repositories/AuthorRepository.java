/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.library.repositories;

import com.egg.library.entities.Author;
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
public interface AuthorRepository extends JpaRepository<Author, String> {

    @Query("SELECT a FROM Author a WHERE a.id= :id")
    public Author searchAuthorById(@Param("id") String id);
    
    @Query("SELECT a FROM Author a WHERE a.nombre= :nombre")
    public Author searchAuthorByName(@Param("nombre") String nombre);
    
    @Query("SELECT a FROM Author a")
    public List<Author> searchAuthors();
    
}
