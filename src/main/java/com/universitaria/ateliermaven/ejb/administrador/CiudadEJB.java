/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.ejb.administrador;

import com.universitaria.atelier.web.jpa.AbstractFacade;
import com.universitaria.atelier.web.jpa.Ciudad;
import com.universitaria.atelier.web.jpa.Color;
import com.universitaria.atelier.web.jpa.Estado;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;

/**
 *
 * @author jeisson.gomez
 */
@Stateless
public class CiudadEJB extends AbstractFacade<Ciudad>{

    public CiudadEJB() {
        super(Ciudad.class);
    }
    
    
    public List<Ciudad> getCiudades(){
        try {
            return (ArrayList<Ciudad>) em.createNamedQuery("Ciudad.findAll",Ciudad.class).getResultList();
        } catch (NullPointerException e){
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }       
        return null;
    }    
    
    public List<SelectItem> getSelectItemCiudad(){
        List<SelectItem> lista = new ArrayList<>();
        try {
            for(Ciudad ciudad :(ArrayList<Ciudad>) em.createNamedQuery("Ciudad.findAll",Ciudad.class).getResultList()){
                lista.add(new SelectItem(ciudad.getCiudadId(),ciudad.getCiudadNombre()));
            }
            return lista;
        } catch (NullPointerException e){
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
        }            
        return lista;
    }
}

