/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.ejb.produccion;

import com.universitaria.atelier.web.jpa.AbstractFacade;
import com.universitaria.atelier.web.jpa.Estado;
import com.universitaria.atelier.web.jpa.Prenda;
import com.universitaria.atelier.web.jpa.Produccion;
import com.universitaria.atelier.web.jpa.Usuario;
import com.universitaria.atelier.web.utils.ProduccionUtil;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;

/**
 *
 * @author SoulHunter
 */
@Stateless
public class ProduccionEJB extends AbstractFacade<Produccion> {

    public ProduccionEJB() {
        super(Produccion.class);
    }

    public List<Produccion> getProduccion() {
        try {
            return (ArrayList<Produccion>) em.createNamedQuery("Produccion.findAll", Produccion.class).getResultList();
        } catch (NullPointerException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<SelectItem> getSelectItemProduccion() {
        List<SelectItem> lista = new ArrayList<>();
        try {
            for (Produccion produccion : (ArrayList<Produccion>) em.createNamedQuery("Produccion.findAll", Produccion.class).getResultList()) {
                lista.add(new SelectItem(produccion.getProduccionId(), produccion.getProduccionDescripcion()));
            }
            return lista;
        } catch (NullPointerException e) {
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean setCrearProduccion(ProduccionUtil produccionUtil) {
        try {
            Produccion produccion = new Produccion();
            produccion.setProduccionFecha(produccionUtil.getProduccionFecha());
            produccion.setProduccionInicioHora(produccionUtil.getProduccionInicioHora());
            produccion.setProduccionDiaEstimated(Float.parseFloat(produccionUtil.getProduccionDiaEstimated()));
            produccion.setPrendaId(em.find(Prenda.class, Integer.parseInt(produccionUtil.getPrendaId())));
            produccion.setEstadoId(em.find(Estado.class, Integer.parseInt(produccionUtil.getEstadoId())));
            Usuario usuario = em.find(Usuario.class, Integer.parseInt(produccionUtil.getUsuarioId()));
            produccion.setUsuarioCreador(usuario);
            produccion.setUsuarioId(usuario);
            produccion.setProduccionDescripcion(produccionUtil.getProduccionDescripcion());

            create(produccion);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean existeProduccion(String produccionDescripcion) {
        try {
            return (em.createNamedQuery("Produccion.findByProduccionDescripcion").setParameter("produccionDescripcion", produccionDescripcion).getSingleResult() != null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean setModificarProduccion(Produccion produccion) {
        try {
            edit(produccion);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
