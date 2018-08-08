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
    
}
