/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.ejb.alquilerventas;

import com.universitaria.atelier.web.jpa.AbstractFacade;
import com.universitaria.atelier.web.jpa.Estado;
import com.universitaria.atelier.web.jpa.Prenda;
import com.universitaria.atelier.web.jpa.Renta;
import com.universitaria.atelier.web.jpa.Rentadeta;
import com.universitaria.atelier.web.jpa.Reservacion;
import com.universitaria.atelier.web.jpa.Usuario;
import com.universitaria.atelier.web.utils.AlquilarUtil;
import com.universitaria.atelier.web.utils.ClienteEJB;
import com.universitaria.atelier.web.utils.PrendaUtil;
import com.universitaria.ateliermaven.ejb.constantes.EstadoEnum;
import com.universitaria.ateliermaven.ejb.inventario.StockPrendaEJB;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author SoulHunter
 */
@Stateless
public class RentaEJB extends AbstractFacade<Renta> {

    @EJB
    private DetalleRentaEJB detalleRentaEJB;
    @EJB
    private ClienteEJB clienteEJB;

    public RentaEJB() {
        super(Renta.class);
    }

    public List<Renta> getRentas() {
        try {
            return (ArrayList<Renta>) em.createNamedQuery("Renta.findAll", Renta.class).getResultList();
        } catch (NullPointerException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Renta> getRentasActivas() {
        try {
            return (ArrayList<Renta>) em.createNamedQuery("Renta.findByRentaEstadoId", Renta.class).setParameter("estadoId", em.find(Estado.class, EstadoEnum.ALQUILADO.getId())).getResultList();
        } catch (NullPointerException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean reintegrarRenta(Renta renta) {
        try {
            renta.setEstadoId(em.find(Estado.class, EstadoEnum.RETORNADO.getId()));
            edit(renta);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean setCrearRentaReservacion(Renta renta, Reservacion reservacion) {
        try {
            create(renta);
            if (detalleRentaEJB.setCrearDetalleRentaReservacion(renta, reservacion)) {
                return true;
            } else {
                remove(renta);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean setCrearRenta(List<PrendaUtil> prenda, AlquilarUtil alquilar, Usuario usuario) {
        try {
            Renta r = new Renta();
            r.setClienteId(clienteEJB.clientePorIdentificacion(alquilar.getClienteId()));
            Calendar cal = Calendar.getInstance();
            r.setDiaRenta(Integer.parseInt(alquilar.getDiaRenta()));
            r.setEstadoId(em.find(Estado.class, EstadoEnum.ALQUILADO.getId()));
            r.setRentaIdFecha(cal);
            cal.add(Calendar.DATE, Integer.parseInt(alquilar.getDiaRenta()));
            r.setRentaReinEstadomentFecha(cal);
            int rentaTotal = 0;
            for (PrendaUtil p : prenda) {
                rentaTotal += Integer.parseInt(p.getValor());
            }
            r.setRentaTot(rentaTotal);
            r.setUsuarioId(usuario);
            create(r);
            for (PrendaUtil p : prenda) {
                if (!detalleRentaEJB.setCrearDetalleRenta(r, em.find(Prenda.class, Integer.parseInt(p.getPrendaId())), Integer.parseInt(p.getValor()))) {
                    remove(r);
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean setCrearVenta(List<PrendaUtil> prenda, AlquilarUtil alquilar, Usuario usuario) {
        try {
            Renta r = new Renta();
            r.setClienteId(clienteEJB.clientePorIdentificacion(alquilar.getClienteId()));
            Calendar cal = Calendar.getInstance();
            r.setEstadoId(em.find(Estado.class, EstadoEnum.VENDIDO.getId()));
            r.setRentaIdFecha(cal);

            int rentaTotal = 0;
            for (PrendaUtil p : prenda) {
                rentaTotal += Integer.parseInt(p.getValor());
            }
            r.setRentaTot(rentaTotal);
            r.setUsuarioId(usuario);
            create(r);
            for (PrendaUtil p : prenda) {
                if (!detalleRentaEJB.setCrearDetalleRenta(r, em.find(Prenda.class, Integer.parseInt(p.getPrendaId())), Integer.parseInt(p.getValor()))) {
                    remove(r);
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
