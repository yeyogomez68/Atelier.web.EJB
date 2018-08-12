/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.ejb.administrador;

import com.universitaria.atelier.web.jpa.AbstractFacade;
import com.universitaria.atelier.web.jpa.Talla;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;

/**
 *
 * @author JorgeWilson
 */
@Stateless
public class TallaEJB extends AbstractFacade<Talla>{

    public TallaEJB() {
        super(Talla.class);
    }
    
    public List<Talla> getTallas(){
        try {
            return (ArrayList<Talla>) em.createNamedQuery("Talla.findAll",Talla.class).getResultList();
        } catch (NullPointerException e){
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }       
        return null;
    }
    
    public List<SelectItem> getSelectItemTallas(){
        List<SelectItem> lista = new ArrayList<>();
        try {
            for(Talla tallas :(ArrayList<Talla>) em.createNamedQuery("Talla.findAll",Talla.class).getResultList()){
                lista.add(new SelectItem(tallas.getTallaId(),tallas.getTallaDescrip()));
            }
            return lista;
        } catch (NullPointerException e){
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
        }            
        return lista;
    }
    
    public boolean getExisteTalla(String talla){
        try {
            return (em.createNamedQuery("Talla.findByTallaDescrip").setParameter("tallaDescrip", talla).getSingleResult()!=null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean setCrearTalla(String tallaDes){        
        try {
            Talla talla = new Talla();
            talla.setTallaDescrip(tallaDes);
            create(talla);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean setModifiTalla(String tallaId, String tallaDesc){
        try {
           Talla talla = new Talla();
           talla = em.find(Talla.class, Integer.parseInt(tallaId));
           talla.setTallaDescrip(tallaDesc);
           edit(talla);
           return true;           
        } catch (Exception e) {
        }
        return false;
    }
}
