/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.ejb.menu;

import com.universitaria.atelier.web.jpa.AbstractFacade;
import com.universitaria.atelier.web.jpa.Permiso;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

/**
 *
 * @author Jeisson Gomez
 */
@Stateless
@LocalBean
public class PermisoEJB extends AbstractFacade<Permiso>{

    public PermisoEJB() {
        super(Permiso.class);
    }
    
    public List<Permiso> getPermisosByUser(){
        try {
            return (ArrayList<Permiso>) em.createNamedQuery("Permiso.findPermByUser",Permiso.class).getResultList();
        } catch (NullPointerException e){
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }       
        return null;
    }
}
