/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.ejb.produccion;

import com.universitaria.atelier.web.jpa.AbstractFacade;
import com.universitaria.atelier.web.jpa.Estado;
import com.universitaria.atelier.web.jpa.Produccion;
import com.universitaria.atelier.web.jpa.Usuario;
import com.universitaria.atelier.web.utils.ProduccionUtil;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;
import javax.persistence.NoResultException;

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
            Calendar cal = Calendar.getInstance();
            cal.setTime(produccionUtil.getProduccionFechaDate());
            produccion.setProduccionFecha(cal);
            produccion.setProduccionDiaEstimated(Float.parseFloat(produccionUtil.getProduccionDiaEstimated()));
            produccion.setEstadoId(em.find(Estado.class, Integer.parseInt(produccionUtil.getEstadoId())));
            produccion.setAvance(Integer.parseInt(produccionUtil.getAvance()));
            Usuario usuario = em.find(Usuario.class, Integer.parseInt(produccionUtil.getUsuarioCreador()));
            produccion.setUsuarioCreador(usuario);
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
        } catch (NoResultException nre) {
            System.out.println("com.universitaria.ateliermaven.ejb.produccion.ProduccionEJB.existeProduccion()");
            System.err.println(nre.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Produccion traerProduccionDes(String produccionDescripcion) {
        try {
            return (Produccion) em.createNamedQuery("Produccion.findByProduccionDescripcion").setParameter("produccionDescripcion", produccionDescripcion).getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean setModificarProduccion(Produccion produccion, String estadoId) {
        try {
            Estado estado = em.find(Estado.class, Integer.parseInt(estadoId));
            produccion.setEstadoId(estado);
            edit(produccion);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
