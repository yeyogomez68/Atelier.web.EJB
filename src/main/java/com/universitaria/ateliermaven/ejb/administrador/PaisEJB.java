/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.ejb.administrador;

import com.universitaria.atelier.web.jpa.AbstractFacade;
import com.universitaria.atelier.web.jpa.Pais;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;

/**
 *
 * @author SoulHunter
 */
@Stateless
public class PaisEJB extends AbstractFacade<Pais> {

    public PaisEJB() {
        super(Pais.class);
    }

    public List<Pais> getPaises() {
        try {
            return (ArrayList<Pais>) em.createNamedQuery("Pais.findAll", Pais.class).getResultList();
        } catch (NullPointerException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

     public boolean setCrearPais(String nombrePais){        
        try {
            Pais pais = new Pais();
            pais.setPaisNombre(nombrePais);
            create(pais);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
     
       public boolean getexistePais(String paisDescripcion) {
        try {
            return (em.createNamedQuery("Pais.findByPaisNombre").setParameter("paisNombre", paisDescripcion).getSingleResult() != null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean setModificarPais(String paisId, String nuevaDescripcion) {
        try {
            Pais pais = em.find(Pais.class, Integer.parseInt(paisId));
            if (!nuevaDescripcion.equalsIgnoreCase(pais.getPaisNombre())) {
                pais.setPaisNombre(nuevaDescripcion);
                edit(pais);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public List<SelectItem> getSelectItemPaises(){
        List<SelectItem> lista = new ArrayList<>();
        try {
            for(Pais pai :(ArrayList<Pais>) em.createNamedQuery("Pais.findAll",Pais.class).getResultList()){
                lista.add(new SelectItem(pai.getPaisId(),pai.getPaisNombre()));
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
