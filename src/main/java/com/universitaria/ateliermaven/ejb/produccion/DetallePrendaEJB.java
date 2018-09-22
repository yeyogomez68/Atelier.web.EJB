/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.ejb.produccion;

import com.universitaria.atelier.web.jpa.AbstractFacade;
import com.universitaria.atelier.web.jpa.Material;
import com.universitaria.atelier.web.jpa.Prenda;
import com.universitaria.atelier.web.jpa.Prendamaterial;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;

/**
 *
 * @author SoulHunter
 */
@Stateless
public class DetallePrendaEJB extends AbstractFacade<Prendamaterial> {

    public DetallePrendaEJB() {
        super(Prendamaterial.class);
    }

    public List<Prendamaterial> getDetallePrenda() {
        try {
            return (ArrayList<Prendamaterial>) em.createNamedQuery("Prendamaterial.findAll", Prendamaterial.class).getResultList();
        } catch (NullPointerException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean setCrearDetallePrenda(String idMaterial, String idPrenda) {
        try {
            if (!existeDetallePrenda(idMaterial, idPrenda)) {
                Prendamaterial pm = new Prendamaterial();
                pm.setMaterialId(em.find(Material.class, Integer.parseInt(idMaterial)));
                pm.setPrendaId(em.find(Prenda.class, Integer.parseInt(idPrenda)));
                create(pm);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean setBorrarDetallePrenda(String idMaterial, String idPrenda) {
        try {
            Prendamaterial pm = em.createNamedQuery("Prendamaterial.findByPrendaIdMaterialId", Prendamaterial.class)
                    .setParameter("prendaId", em.find(Prenda.class, Integer.parseInt(idPrenda)))
                    .setParameter("materialId", em.find(Material.class, Integer.parseInt(idMaterial))).getSingleResult();
            remove(pm);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean existeDetallePrenda(String idMaterial, String idPrenda) {
        try {
            return em.createNamedQuery("Prendamaterial.findByPrendaIdMaterialId", Prendamaterial.class)
                    .setParameter("prendaId", em.find(Prenda.class, Integer.parseInt(idPrenda)))
                    .setParameter("materialId", em.find(Material.class, Integer.parseInt(idMaterial))).getSingleResult() != null;

        } catch (NoResultException nre) {
            System.out.println("com.universitaria.ateliermaven.ejb.produccion.DetallePrendaEJB.existeDetallePrenda()");
            nre.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean setBorrarDetallePrenda(Prendamaterial pm) {
        try {

            remove(pm);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
