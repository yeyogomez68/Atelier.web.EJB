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
import com.universitaria.atelier.web.jpa.Renta;
import com.universitaria.atelier.web.jpa.Reservacion;
import com.universitaria.atelier.web.jpa.Usuario;
import com.universitaria.atelier.web.utils.ReservacionUtil;
import com.universitaria.ateliermaven.ejb.constantes.EstadoEnum;
import com.universitaria.ateliermaven.ejb.inventario.StockPrendaEJB;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    @EJB
    private RentaEJB rentaEJB;

    public ReservaEJB() {
        super(Reservacion.class);
    }

    public List<Reservacion> getReservacionActivas() {
        try {
            return (ArrayList<Reservacion>) em.createNamedQuery("Reservacion.findByEstadoId", Reservacion.class).setParameter("estadoId", em.find(Estado.class, EstadoEnum.RESERVADO.getId())).getResultList();
        } catch (NullPointerException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Reservacion> getReservacionClienteActivas(Cliente cliente) {
        try {
            return (ArrayList<Reservacion>) em.createNamedQuery("Reservacion.findByEstadoIdClienteId", Reservacion.class).setParameter("estadoId", em.find(Estado.class, EstadoEnum.RESERVADO.getId())).setParameter("clienteId", cliente).getResultList();
        } catch (NullPointerException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean entregarReservacionRenTa(Reservacion reservacion, Usuario usuario, Integer valor) {
        try {
            Renta renta = new Renta();
            renta.setClienteId(reservacion.getClienteId());
            renta.setDiaRenta(reservacion.getReservacionLimit());
            renta.setEstadoId(em.find(Estado.class, EstadoEnum.ALQUILADO.getId()));
            Calendar cal = Calendar.getInstance();
            renta.setRentaIdFecha(cal);
            cal.add(Calendar.DATE, reservacion.getReservacionLimit());
            renta.setRentaReinEstadomentFecha(cal);
            renta.setRentaTot(valor);
            renta.setUsuarioId(usuario);
            if (rentaEJB.setCrearRentaReservacion(renta, reservacion)) {
                reservacion.setEstadoId(renta.getEstadoId());
                edit(reservacion);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean entregarReservacionVenta(Reservacion reservacion, Usuario usuario, Integer valor) {
        try {
            Renta renta = new Renta();
            renta.setClienteId(reservacion.getClienteId());

            renta.setEstadoId(em.find(Estado.class, EstadoEnum.VENDIDO.getId()));
            Calendar cal = Calendar.getInstance();
            renta.setRentaIdFecha(cal);

            renta.setRentaTot(valor);
            renta.setUsuarioId(usuario);
            if (rentaEJB.setCrearRentaReservacion(renta, reservacion)) {
                reservacion.setEstadoId(renta.getEstadoId());
                edit(reservacion);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean liberarReservacion(Reservacion reservacion) {
        try {
            reservacion.setEstadoId(em.find(Estado.class, EstadoEnum.INACTIVO.getId()));
            edit(reservacion);
            stockPrendaEJB.setModificarStockPrenda(reservacion.getPrendaId(), 1);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
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
