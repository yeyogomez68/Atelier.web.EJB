/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.ejb.administrador;

import com.universitaria.atelier.web.jpa.AbstractFacade;
import com.universitaria.atelier.web.jpa.Prendatipo;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author jeisson.gomez
 */
@Stateless
public class PrendaTipoEJB extends AbstractFacade<Prendatipo>{

    public PrendaTipoEJB() {
        super(Prendatipo.class);
    }
    
    
    public List<Prendatipo> getPrendatipos(){
        try {
            return (ArrayList<Prendatipo>) em.createNamedQuery("Prendatipo.findAll",Prendatipo.class).getResultList();
        } catch (NullPointerException e){
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }       
        return null;
    }
    
}
