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
import com.universitaria.ateliermaven.ejb.constantes.EstadoEnum;
import com.universitaria.ateliermaven.ejb.inventario.StockPrendaEJB;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author SoulHunter
 */
@Stateless
public class DetalleRentaEJB extends AbstractFacade<Rentadeta> {

    @EJB
    private StockPrendaEJB stockPrendaEJB;

    public DetalleRentaEJB() {
        super(Rentadeta.class);
    }

    public List<Rentadeta> getRentaDetalle() {
        try {
            return (ArrayList<Rentadeta>) em.createNamedQuery("Rentadeta.findAll", Rentadeta.class).getResultList();
        } catch (NullPointerException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Rentadeta> getRentaDetalleRenta(Renta renta) {
        try {
            return (ArrayList<Rentadeta>) em.createNamedQuery("Rentadeta.findByRentaIdEstadoId", Rentadeta.class).setParameter("rentaId", renta).setParameter("estadoId", em.find(Estado.class, EstadoEnum.ALQUILADO.getId())).getResultList();
        } catch (NullPointerException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean setCrearDetalleRentaReservacion(Renta renta, Reservacion reservacion) {
        try {
            Rentadeta rd = new Rentadeta();
            rd.setEstadoId(renta.getEstadoId());
            rd.setPrendaId(reservacion.getPrendaId());
            rd.setRentaDetaFecha(renta.getRentaIdFecha());
            rd.setRentaDetaReinEstadomentFecha(renta.getRentaReinEstadomentFecha());
            rd.setRentaId(renta);
            rd.setRentaVu(renta.getRentaTot());
            rd.setReservacionId(reservacion);
            create(rd);
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    public boolean setCrearDetalleRenta(Renta renta, Prenda prenda, int vu) {
        try {
            Rentadeta rd = new Rentadeta();
            rd.setEstadoId(renta.getEstadoId());
            rd.setPrendaId(prenda);
            rd.setRentaDetaFecha(renta.getRentaIdFecha());
            rd.setRentaDetaReinEstadomentFecha(renta.getRentaReinEstadomentFecha());
            rd.setRentaId(renta);
            rd.setRentaVu(vu);
            rd.setReservacionId(null);
            create(rd);
            stockPrendaEJB.setModificarStockPrenda(prenda, -1);
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    public boolean reintegrarPrenda(Rentadeta renta) {
        try {
            renta.setEstadoId(em.find(Estado.class, EstadoEnum.RETORNADO.getId()));
            edit(renta);
            if (stockPrendaEJB.setModificarStockPrenda(renta.getPrendaId(), 1)) {
                return true;
            } else {
                renta.setEstadoId(em.find(Estado.class, EstadoEnum.ALQUILADO.getId()));
                edit(renta);
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
