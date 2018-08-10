/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.ejb.administrador;

import com.universitaria.atelier.web.jpa.AbstractFacade;
import com.universitaria.atelier.web.jpa.Estado;
import com.universitaria.atelier.web.jpa.Roll;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;


/**
 *
 * @author jeisson.gomez
 */
@Stateless
public class RollEJB  extends AbstractFacade<Roll>{

    public RollEJB() {
        super(Roll.class);
    }
    
    public List<Roll> getRoles(){
        try {
            return (ArrayList<Roll>) em.createNamedQuery("Roll.findAll",Roll.class).getResultList();
        } catch (NullPointerException e){
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }       
        return null;
    }
    
    public List<SelectItem> getSelectItemRoles(){
        List<SelectItem> lista = new ArrayList<>();
        try {
            for(Roll roles :(ArrayList<Roll>) em.createNamedQuery("Roll.findAll",Roll.class).getResultList()){
                lista.add(new SelectItem(roles.getRollId(),roles.getRollDesc()));
            }
            return lista;
        } catch (NullPointerException e){
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
        }            
        return lista;
    }
    
    public boolean setCrearRoll(String rollDesc){
        try {
            Roll roll = new Roll();            
            roll.setRollDesc(rollDesc);
            roll.setEstadoId(em.find(Estado.class, 1));
            create(roll);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean setModificarRoll(String rollId, String rollDescrip, String estadoId){
        try {
            Roll roll = new Roll();
            roll = em.find(Roll.class, Integer.parseInt(rollId));
            roll.setRollDesc(rollDescrip);
            roll.setEstadoId(em.find(Estado.class, Integer.parseInt(estadoId)));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
