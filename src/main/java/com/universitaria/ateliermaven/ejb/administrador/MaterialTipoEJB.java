/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.ejb.administrador;

import com.universitaria.atelier.web.jpa.AbstractFacade;
import com.universitaria.atelier.web.jpa.Materialtipo;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;

/**
 *
 * @author jeisson.gomez
 */
@Stateless
public class MaterialTipoEJB extends AbstractFacade<Materialtipo>{
 
    public MaterialTipoEJB() {
        super(Materialtipo.class);
    }
 
    public List<Materialtipo> getMaterialTipos(){
        try {
            return (ArrayList<Materialtipo>) em.createNamedQuery("Materialtipo.findAll",Materialtipo.class).getResultList();
        } catch (NullPointerException e){
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }       
        return null;
    }
    
    public List<SelectItem> getSelectItemMaterialTipo(){
        List<SelectItem> lista = new ArrayList<>();
        try {
            for(Materialtipo materialTipo :(ArrayList<Materialtipo>) em.createNamedQuery("Materialtipo.findAll",Materialtipo.class).getResultList()){
                lista.add(new SelectItem(materialTipo.getMaterialTipoId(),materialTipo.getMaterialTipoDescript()));
            }
            return lista;
        } catch (NullPointerException e){
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
        }            
        return lista;
    }
    
    public boolean getExisteMaterialTipo(String materialTipoDesc){
        try {
            return (em.createNamedQuery("Materialtipo.findByMaterialTipoDescript").setParameter("materialTipoDescript", materialTipoDesc).getSingleResult()!=null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean setCrearMaterialTipo(String materialTipoDes){        
        try {
            Materialtipo materialTipo = new Materialtipo();
            materialTipo.setMaterialTipoDescript(materialTipoDes);
            create(materialTipo);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean setModifiDesMaterialTipo(String materialTipoId, String materialTipoDesc){
        try {
           Materialtipo materialTipo = new Materialtipo();
           materialTipo = em.find(Materialtipo.class, Integer.parseInt(materialTipoId));
           materialTipo.setMaterialTipoDescript(materialTipoDesc);
           edit(materialTipo);
           return true;           
        } catch (Exception e) {
        }
        return false;
    }
}
