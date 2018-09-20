/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.ejb.alquilerventas;

import com.universitaria.atelier.web.jpa.AbstractFacade;
import com.universitaria.atelier.web.jpa.Cliente;
import com.universitaria.atelier.web.jpa.Estado;
import com.universitaria.atelier.web.jpa.Prenda;
import com.universitaria.atelier.web.jpa.Reservacion;
import com.universitaria.atelier.web.jpa.Usuario;
import com.universitaria.atelier.web.utils.ReservacionUtil;
import com.universitaria.ateliermaven.ejb.inventario.StockPrendaEJB;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;

/**
 *
 * @author SoulHunter
 */
@Stateless
public class ReservaEJB extends AbstractFacade<Reservacion> {

    @EJB
    private StockPrendaEJB stockPrendaEJB;

    public ReservaEJB() {
        super(Reservacion.class);
    }

    public List<Reservacion> getReservaciones() {
        try {
            return (ArrayList<Reservacion>) em.createNamedQuery("Reservacion.findAll", Reservacion.class).getResultList();
        } catch (NullPointerException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<SelectItem> getSelectItemReservaciones() {
        List<SelectItem> lista = new ArrayList<>();
        try {
            for (Reservacion reservacion : (ArrayList<Reservacion>) em.createNamedQuery("Reservacion.findAll", Reservacion.class).getResultList()) {
                lista.add(new SelectItem(reservacion.getReservacionId(), String.valueOf(reservacion.getClienteId().getClienteIdentificacion()), reservacion.getPrendaId().getPrendaNombre()));
            }
            return lista;
        } catch (NullPointerException e) {
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<SelectItem> getSelectItemReservaciones(int estadoId) {
        List<SelectItem> lista = new ArrayList<>();
        try {
            for (Reservacion reservacion : (ArrayList<Reservacion>) em.createNamedQuery("Reservacion.findByEstadoId", Reservacion.class).setParameter("estadoId", em.find(Estado.class, estadoId)).getResultList()) {
                lista.add(new SelectItem(reservacion.getReservacionId(), String.valueOf(reservacion.getClienteId().getClienteIdentificacion()), reservacion.getPrendaId().getPrendaNombre()));
            }
            return lista;
        } catch (NullPointerException e) {
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean setModificarReservacion(Reservacion reservacion, String estadoId) {
        try {

            edit(reservacion);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

}
