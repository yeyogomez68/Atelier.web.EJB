/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.ejb.menu;

import com.universitaria.atelier.web.jpa.AbstractFacade;
import com.universitaria.atelier.web.jpa.Opcion;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

/**
 *
 * @author Jeisson Gomez
 */
@Stateless
public class OpcionEJB extends AbstractFacade<Opcion>{

    public OpcionEJB() {
        super(Opcion.class);
    }
    
    public List<Opcion> getOpcionesByPerm(int type, String userId){
        List<Opcion> list = new ArrayList<>();
        try {
            if(type==1){
                 return (ArrayList<Opcion>) em.createNamedQuery("Permiso.findPermByUserMod",Opcion.class).getResultList();
            }else if(type==2){
                 return (ArrayList<Opcion>) em.createNamedQuery("Permiso.findPermByUserItem",Opcion.class).getResultList();
            }           
        } catch (NullPointerException e){
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }       
        return null;
    }
}
