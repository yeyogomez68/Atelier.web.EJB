/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.ejb.produccion;

import com.universitaria.atelier.web.jpa.AbstractFacade;
import com.universitaria.atelier.web.jpa.Produccion;
import com.universitaria.atelier.web.jpa.Producciondeta;
import com.universitaria.atelier.web.utils.DetalleProduccionUtil;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;

/**
 *
 * @author SoulHunter
 */
@Stateless
public class DetalleProduccionEJB extends AbstractFacade<Producciondeta> {

    public DetalleProduccionEJB() {
        super(Producciondeta.class);
    }

    public List<Producciondeta> getDetalleProduccion() {
        try {
            return (ArrayList<Producciondeta>) em.createNamedQuery("Producciondeta.findAll", Producciondeta.class).getResultList();
        } catch (NullPointerException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<SelectItem> getSelectItemDetalleProduccion() {
        List<SelectItem> lista = new ArrayList<>();
        try {
            for (Producciondeta produccionDeta : (ArrayList<Producciondeta>) em.createNamedQuery("Producciondeta.findAll", Producciondeta.class).getResultList()) {
                lista.add(new SelectItem(produccionDeta.getProduccionDetaId(), produccionDeta.getProduccionId().getProduccionDescripcion()));
            }
            return lista;
        } catch (NullPointerException e) {
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean setCrearDetalleProduccion(DetalleProduccionUtil detalleProduccionUtil) {
        try {
            Producciondeta produccionDeta = new Producciondeta();

            produccionDeta.setMaterialId(Integer.parseInt(detalleProduccionUtil.getMaterialId()));
            produccionDeta.setProduccionId(em.find(Produccion.class, Integer.parseInt(detalleProduccionUtil.getProduccionId())));
            produccionDeta.setProduccionDetaCant(Float.parseFloat(detalleProduccionUtil.getProduccionDetaCant()));
            produccionDeta.setProduccionDetaFecha(detalleProduccionUtil.getProduccionDetaFecha());

            create(produccionDeta);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean existeDetalleProduccion(String produccionDescripcion) {
        try {
            return (em.createNamedQuery("Produccion.findByProduccionDescripcion").setParameter("produccionDescripcion", produccionDescripcion).getSingleResult() != null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean setModificarDetalleProduccion(Producciondeta produccionDeta) {
        try {
            edit(produccionDeta);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
