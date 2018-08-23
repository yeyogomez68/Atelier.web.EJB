/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.ejb.compras;

import com.universitaria.atelier.web.jpa.AbstractFacade;
import com.universitaria.atelier.web.jpa.Encabezadorequerimiento;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;


/**
 *
 * @author jeisson.gomez
 */
@Stateless
@LocalBean
public class EncabezadoRequerimientoEJB extends AbstractFacade<Encabezadorequerimiento>{

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    public EncabezadoRequerimientoEJB() {
        super(Encabezadorequerimiento.class);
    }

    public List<Encabezadorequerimiento> getRequerimientos(){
        try {
            return (ArrayList<Encabezadorequerimiento>) em.createNamedQuery("Encabezadorequerimiento.findAll",Encabezadorequerimiento.class).getResultList();
        } catch (NullPointerException e){
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }       
        return null;
    }
    
    public boolean setCrearRequerimiento(){        
        try {
            Encabezadorequerimiento requerimiento = new Encabezadorequerimiento();
           
            create(requerimiento);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean setModifiRequerimiento(String idRq,String deta){
        try {
           Encabezadorequerimiento requerimiento = new Encabezadorequerimiento();
           
           edit(requerimiento);
           return true;           
        } catch (Exception e) {
        }
        return false;
    }
}
