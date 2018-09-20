/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.ejb.compras;

import com.universitaria.atelier.web.jpa.AbstractFacade;
import com.universitaria.atelier.web.jpa.Estado;
import com.universitaria.atelier.web.jpa.Ordencompradeta;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author jeisson.gomez
 */
@Stateless
public class OrdenCompraDetaEJB extends AbstractFacade<Ordencompradeta> {

    public OrdenCompraDetaEJB() {
        super(Ordencompradeta.class);
    }

    public List<Ordencompradeta> getOrdenCompraDetaPorEstado(int estado) {

        try {
            return em.createNamedQuery("Ordencompradeta.findByEstadoId", Ordencompradeta.class).setParameter("estadoId", em.find(Estado.class, estado)).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean setActualizarEstadoOrderCompraDeta(Ordencompradeta ordencompradeta, int estado) {
        try {
            ordencompradeta.setEstadoId(em.find(Estado.class, estado));
            edit(ordencompradeta);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
