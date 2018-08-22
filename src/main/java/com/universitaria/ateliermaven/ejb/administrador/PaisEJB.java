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
    
}
