/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.ejb.inventario;

import com.universitaria.atelier.web.jpa.AbstractFacade;
import com.universitaria.atelier.web.jpa.Material;
import com.universitaria.atelier.web.jpa.Stockmateriales;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;

/**
 *
 * @author SoulHunter
 */
@Stateless
public class StockMaterialesEJB extends AbstractFacade<Stockmateriales> {

    public StockMaterialesEJB() {
        super(Stockmateriales.class);
    }

    public List<Stockmateriales> getStockMaterial() {
        try {
            return (ArrayList<Stockmateriales>) em.createNamedQuery("Stockmateriales.findAll", Stockmateriales.class).getResultList();
        } catch (NullPointerException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean setCrearStockMaterial(Stockmateriales stockmaterial) {
        try {
            if (!existeStockMaterial(stockmaterial.getMaterialId())) {
                
                create(stockmaterial);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean existeStockMaterial(Material material) {
        try {
            return (em.createNamedQuery("Stockmateriales.findByMaterialId").setParameter("materialId", material).getSingleResult() != null);
        } catch (NoResultException nre) {
            System.out.println("com.universitaria.ateliermaven.ejb.inventario.StockMaterialesEJB.existeStockMaterial()");
            System.err.println(nre.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean setModificarStockMaterial(Material material, Double cantidad) {
        try {
            Stockmateriales sm = em.createNamedQuery("Stockmateriales.findByMaterialId", Stockmateriales.class).setParameter("materialId", material).getSingleResult();
            sm.setCantidad(sm.getCantidad() + cantidad);
            edit(sm);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Stockmateriales getStockMaterial(Material material) {
        try {
            return em.createNamedQuery("Stockmateriales.findByMaterialId", Stockmateriales.class).setParameter("materialId", material).getSingleResult();
        } catch (NoResultException nre) {
            System.out.println("com.universitaria.ateliermaven.ejb.inventario.StockMaterialesEJB.getStockMaterial()");
            System.err.println(nre.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
