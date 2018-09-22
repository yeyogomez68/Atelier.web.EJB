/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.ejb.administrador;

import com.universitaria.atelier.web.jpa.AbstractFacade;
import com.universitaria.atelier.web.jpa.Ocasion;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;

/**
 *
 * @author jeisson.gomez
 */
@Stateless
public class OcasionEJB extends AbstractFacade<Ocasion>{

    public OcasionEJB() {
        super(Ocasion.class);
    }
    
    
    public List<Ocasion> getOcasiones(){
        try {
            return (ArrayList<Ocasion>) em.createNamedQuery("Ocasion.findAll",Ocasion.class).getResultList();
        } catch (NullPointerException e){
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }       
        return null;
    }
    
    public boolean setCrearOcasion(String nombreOcasion){
    
    try {
            Ocasion ocasion = new Ocasion();
            ocasion.setOcasionDescrip(nombreOcasion);
            create(ocasion);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    
     public boolean getexisteOcasion(String ocasionDescripcion) {
        try {
            return (em.createNamedQuery("Ocasion.findByOcasionDescrip").setParameter("ocasionDescrip", ocasionDescripcion).getSingleResult() != null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean setModificarOcasion(String ocasionId, String nuevaDescripcion) {
        try {
            Ocasion ocasion = em.find(Ocasion.class, Integer.parseInt(ocasionId));
            if (!nuevaDescripcion.equalsIgnoreCase(ocasion.getOcasionDescrip())) {
                ocasion.setOcasionDescrip(nuevaDescripcion);
                edit(ocasion);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    
       public List<SelectItem> getSelectItemOcasiones(){
        List<SelectItem> lista = new ArrayList<>();
        try {
            for(Ocasion oca :(ArrayList<Ocasion>) em.createNamedQuery("Ocasion.findAll",Ocasion.class).getResultList()){
                lista.add(new SelectItem(oca.getOcasionId(),oca.getOcasionDescrip()));
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
