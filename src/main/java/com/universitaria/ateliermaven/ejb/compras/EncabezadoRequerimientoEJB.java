/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.ejb.compras;

import com.universitaria.atelier.web.jpa.AbstractFacade;
import com.universitaria.atelier.web.jpa.Encabezadorequerimiento;
import com.universitaria.atelier.web.jpa.Estado;
import com.universitaria.atelier.web.jpa.Usuario;
import com.universitaria.ateliermaven.ejb.constantes.EstadoEnum;
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
    
    public List<Encabezadorequerimiento> getRequerimientosByUser(Usuario us){
        try {
            return (ArrayList<Encabezadorequerimiento>) em.createNamedQuery("Encabezadorequerimiento.findByUsuarioCreador",Encabezadorequerimiento.class).setParameter("usuarioCreador", us.getUsuarioId()).getResultList();
        } catch (NullPointerException e){
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }       
        return null;
    }
    
    public Integer setCrearRequerimiento(String desc,Usuario user){        
        try {            
            Encabezadorequerimiento requerimiento = new Encabezadorequerimiento();
            requerimiento.setEncabezadoRequerimientoDeta(desc);
            requerimiento.setUsuarioId(user);
            requerimiento.setUsuarioCreador(user.getUsuarioId());
            requerimiento.setEstadoId(em.find(Estado.class, EstadoEnum.PENDIENTE.getId()));
            create(requerimiento);
            return requerimiento.getEncabezadoRequerimientoId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
    
    public void setEditarRq(Encabezadorequerimiento rq){
        try {
            edit(rq);
        } catch (Exception e) {
        }
    }
    
    public void actualizarRechazado(){
    
    }
    
    public void actualizarAprobado(){
    
    }
    
    public void actualizarEnAprobacion(){
    
    }
}
