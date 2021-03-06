/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.ejb.administrador;

import com.universitaria.atelier.web.jpa.AbstractFacade;
import com.universitaria.atelier.web.jpa.Estado;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;

/**
 *
 * @author jeisson.gomez
 */
@Stateless
public class EstadoEJB extends AbstractFacade<Estado>{

    public EstadoEJB() {
        super(Estado.class);
    }
    
    
    public List<Estado> getEstados(){
        try {
            return (ArrayList<Estado>) em.createNamedQuery("Estado.findAll",Estado.class).getResultList();
        } catch (NullPointerException e){
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }       
        return null;
    }
    
    public List<SelectItem> getSelectItemEstados(){
        List<SelectItem> lista = new ArrayList<>();
        try {
            for(Estado esta :(ArrayList<Estado>) em.createNamedQuery("Estado.findAll",Estado.class).getResultList()){
                lista.add(new SelectItem(esta.getEstadoId(),esta.getEstadoDescrip()));
            }
            return lista;
        } catch (NullPointerException e){
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
        }            
        return lista;
    }
    
    
    public boolean crearEstado (String nombreEstado) {
        try {
            Estado estado = new Estado();
            estado.setEstadoDescrip(nombreEstado);           
            create(estado);
            return true;            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean getExisteEstado(String estadoDescripcion) {
        try {
            return (em.createNamedQuery("Estado.findByCargoDesc").setParameter("cargoDesc", estadoDescripcion).getSingleResult() != null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
   public boolean setModificarEstado(String id, String desc) {
        try {
            Estado estad = new Estado();
            estad = em.find(Estado.class, Integer.parseInt(id));
            estad.setEstadoDescrip(desc);
            edit(estad);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
}
