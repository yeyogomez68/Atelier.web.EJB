/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.ejb.inventario;

import com.universitaria.atelier.web.jpa.AbstractFacade;
import com.universitaria.atelier.web.jpa.Prenda;
import com.universitaria.atelier.web.jpa.Stockprenda;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;

/**
 *
 * @author SoulHunter
 */
@Stateless
public class StockPrendaEJB extends AbstractFacade<Stockprenda> {

    public StockPrendaEJB() {
        super(Stockprenda.class);
    }

    public List<Stockprenda> getStockPrenda() {
        try {
            return (ArrayList<Stockprenda>) em.createNamedQuery("Stockprenda.findAll", Stockprenda.class).getResultList();
        } catch (NullPointerException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean setCrearStockPrenda(Stockprenda stockPrenda) {
        try {
            if (!existeStockPrenda(stockPrenda.getPrendaId())) {
                create(stockPrenda);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean existeStockPrenda(Prenda prenda) {
        try {
            return (em.createNamedQuery("Stockprenda.findByPrendaId").setParameter("prendaId", prenda).getSingleResult() != null);
        } catch (NoResultException nre) {
            System.out.println("com.universitaria.ateliermaven.ejb.inventario.StockPrendaEJB.existeStockPrenda()");
            System.err.println(nre.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean setModificarStockPrenda(Prenda prenda, int cantidad) {
        try {
            Stockprenda sp = em.createNamedQuery("Stockprenda.findByPrendaId", Stockprenda.class).setParameter("prendaId", prenda).getSingleResult();
            sp.setStockPrendaCant(sp.getStockPrendaCant() + cantidad);
            edit(sp);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
