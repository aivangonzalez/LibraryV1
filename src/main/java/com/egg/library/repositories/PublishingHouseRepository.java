/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.library.repositories;

import com.egg.library.entities.PublishingHouse;
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
public interface PublishingHouseRepository extends JpaRepository<PublishingHouse, String>{
    
    @Query("SELECT p FROM PublishingHouse p WHERE p.id= :id")
    public PublishingHouse searchPubHouseById(@Param("id") String id);
    
    @Query("SELECT p FROM PublishingHouse p WHERE p.nombre= :nombre")
    public PublishingHouse searchPubHouseByName(@Param("nombre") String nombre);
    
    @Query("SELECT p FROM PublishingHouse p")
    public List<PublishingHouse> searchPHouses();
}
