/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.ejb.compras;

import com.universitaria.atelier.web.jpa.AbstractFacade;
import com.universitaria.atelier.web.jpa.Marca;
import com.universitaria.atelier.web.jpa.Material;
import com.universitaria.atelier.web.jpa.Materialtipo;
import com.universitaria.atelier.web.utils.MaterialRequerimientoUtil;
import com.universitaria.atelier.web.utils.MaterialUtil;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;

/**
 *
 * @author jeisson.gomez
 */
@Stateless
public class MaterialEJB extends AbstractFacade<Material>{

    public MaterialEJB() {
        super(Material.class);
    }   
    
    public List<Material> getMateriales(){
        try {
            return (ArrayList<Material>) em.createNamedQuery("Material.findAll",Material.class).getResultList();
        } catch (NullPointerException e){
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }       
        return null;
    }
    
    public List<SelectItem> getSelectItemMaterial(){
        List<SelectItem> lista = new ArrayList<>();
        try {
            for(Material mate :(ArrayList<Material>) em.createNamedQuery("Material.findAll",Material.class).getResultList()){
                lista.add(new SelectItem(mate.getMaterialId(),mate.getMaterialNombre()));
            }
            return lista;
        } catch (NullPointerException e){
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
        }            
        return lista;
    }
    
    public boolean setCrearMaterial(MaterialUtil material){
        try {
            Material mate = new Material();  
            mate.setMaterialNombre(material.getNombre());
            mate.setMaterialReference(material.getReferencia());
            mate.setMaterialTipoId(em.find(Materialtipo.class, Integer.valueOf(material.getTipoId())));
            mate.setMarcaId(em.find(Marca.class, Integer.valueOf(material.getMarcaId())));
            create(mate);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean setModificarMaterial(MaterialUtil material){
        try {
            Material mate = new Material();
            mate = em.find(Material.class, Integer.parseInt(material.getMaterialId()));
            mate.setMaterialNombre(material.getNombre());
            mate.setMaterialReference(material.getReferencia());
            mate.setMaterialTipoId(em.find(Materialtipo.class, Integer.valueOf(material.getTipoId())));
            mate.setMarcaId(em.find(Marca.class, Integer.valueOf(material.getMarcaId())));
            edit(mate);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public List<MaterialRequerimientoUtil> getMaterialesRequerimiento(){
        List <MaterialRequerimientoUtil> list = new ArrayList<>();
        try {
            for (Material mat : (ArrayList<Material>) em.createNamedQuery("Material.findAll",Material.class).getResultList()) {
                MaterialRequerimientoUtil util = new MaterialRequerimientoUtil();
                util.setMaterialId(mat.getMaterialId().toString());
                util.setNombre(mat.getMaterialNombre());
                util.setMarcaId(mat.getMarcaId().getMarcaNombre());
                util.setReferencia(mat.getMaterialReference());
                util.setTipoId(mat.getMaterialTipoId().getMaterialTipoDescript());
                util.setCantidad("0.0");
                list.add(util);
            }
            return list;
        } catch (NullPointerException e){
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }       
        return null;
    }
}
