/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.ejb.administrador;

import com.universitaria.atelier.web.jpa.AbstractFacade;
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
}
