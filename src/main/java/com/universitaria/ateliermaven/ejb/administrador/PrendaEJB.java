/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.ejb.administrador;

import com.universitaria.atelier.web.jpa.AbstractFacade;
import com.universitaria.atelier.web.jpa.Prenda;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;


/**
 *
 * @author Jeisson Gomez
 */
@Stateless
public class PrendaEJB extends AbstractFacade<Prenda> {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    public PrendaEJB() {
        super(Prenda.class);
    }

    public List<Prenda> getPrendas(){
        try {
            return (ArrayList<Prenda>) em.createNamedQuery("Prenda.findAll",Prenda.class).getResultList();
        } catch (NullPointerException e){
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }       
        return null;
    }
}
