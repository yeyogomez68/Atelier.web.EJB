/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.ejb.administrador;

import com.universitaria.atelier.web.jpa.AbstractFacade;
import com.universitaria.atelier.web.jpa.Marca;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;

/**
 *
 * @author jeisson.gomez
 */
@Stateless
public class MarcaEJB extends AbstractFacade<Marca>{
    
    public MarcaEJB() {
        super(Marca.class);
    }
    public List<Marca> getMarcas(){
        try {
            return (ArrayList<Marca>) em.createNamedQuery("Marca.findAll",Marca.class).getResultList();
        } catch (NullPointerException e){
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }       
        return null;
    }
    
    public List<SelectItem> getSelectItemMarcas(){
        List<SelectItem> lista = new ArrayList<>();
        try {
            for(Marca marca :(ArrayList<Marca>) em.createNamedQuery("Marca.findAll",Marca.class).getResultList()){
                lista.add(new SelectItem(marca.getMarcaId(),marca.getMarcaNombre()));
            }
            return lista;
        } catch (NullPointerException e){
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
        }            
        return lista;
    }
    
    public boolean getExisteMarca(String marcaDesc){
        try {
            return (em.createNamedQuery("Marca.findByMarcaNombre").setParameter("marcaNombre", marcaDesc).getSingleResult()!=null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean setCrearMarca(String marcaDesc){        
        try {
            Marca marca = new Marca();
            marca.setMarcaNombre(marcaDesc);
            create(marca);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean setModifiMarca(String marcaId, String marcaDesc){
        try {
           Marca marca = new Marca();
           marca = em.find(Marca.class, Integer.parseInt(marcaId));
           marca.setMarcaNombre(marcaDesc);
           edit(marca);
           return true;           
        } catch (Exception e) {
        }
        return false;
    }        
}
