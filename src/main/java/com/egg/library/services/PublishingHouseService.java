/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.library.services;

import com.egg.library.entities.PublishingHouse;
import com.egg.library.exceptions.ExceptionService;
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
public class PublishingHouseService {

    @Autowired
    PublishingHouseRepository ph;

    @Transactional
    public void createPHouse(String name) throws ExceptionService {

        PublishingHouse pHouse = ph.searchPubHouseByName(name);
        if (pHouse != null) {
            throw new ExceptionService("Ya se encuentra una editorial con el nombre ingresado.");
        }

        if (name.trim().isEmpty() || name == null) {
            throw new ExceptionService("Error al ingresar el nombre.");
        }

        PublishingHouse pubHouse = new PublishingHouse();
        pubHouse.setNombre(name);
        ph.save(pubHouse);
    }

    @Transactional
    public void modifyPHouse(String id, String name) throws ExceptionService {

        PublishingHouse pHouse = ph.searchPubHouseById(id);
        if (pHouse == null) {
            throw new ExceptionService("No se encuentra ninguna editorial con dicho id para modificar.");
        }

        if (name.trim().isEmpty() || name == null) {
            throw new ExceptionService("Error al ingresar el nombre.");
        }

        pHouse.setNombre(name);
        ph.save(pHouse);
    }

    @Transactional
    public void dropHouse(String id) throws ExceptionService {

        PublishingHouse pHouse = ph.searchPubHouseById(id);
        if (pHouse == null) {
            throw new ExceptionService("No se encuentra ninguna editorial para dar de baja.");
        }

        pHouse.setAlta(false);
        ph.save(pHouse);
    }

    @Transactional
    public void highHouse(String id) throws ExceptionService {

        PublishingHouse pHouse = ph.searchPubHouseById(id);
        if (pHouse == null) {
            throw new ExceptionService("No se encuentra ninguna editorial para dar de baja.");
        }

        pHouse.setAlta(true);
        ph.save(pHouse);
    }
    
    public PublishingHouse searchById(String id) throws ExceptionService {

        PublishingHouse pHouse = ph.searchPubHouseById(id);
        if (pHouse == null) {
            throw new ExceptionService("No se encontró ninguna editorial.");
        }

        return pHouse;
    }

    public PublishingHouse searchByName(String name) throws ExceptionService {

        PublishingHouse pHouse = ph.searchPubHouseByName(name);
        if (pHouse == null) {
            throw new ExceptionService("No se encontró ninguna editorial.");
        }

        return pHouse;
    }

    public List<PublishingHouse> searchPHouses() throws ExceptionService {

        List<PublishingHouse> pHouses = ph.searchPHouses();
        if (pHouses.isEmpty()) {
            throw new ExceptionService("No hay ninguna editorial para mostrar.");
        }

        return pHouses;
    }

}
