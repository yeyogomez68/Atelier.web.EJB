/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.ejb.produccion;

import com.universitaria.atelier.web.jpa.AbstractFacade;
import com.universitaria.atelier.web.jpa.Color;
import com.universitaria.atelier.web.jpa.Estado;
import com.universitaria.atelier.web.jpa.Material;
import com.universitaria.atelier.web.jpa.Ocasion;
import com.universitaria.atelier.web.jpa.Prenda;
import com.universitaria.atelier.web.jpa.Prendatipo;
import com.universitaria.atelier.web.jpa.Stockprenda;
import com.universitaria.atelier.web.jpa.Talla;
import com.universitaria.atelier.web.utils.PrendaUtil;
import com.universitaria.ateliermaven.ejb.inventario.StockPrendaEJB;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;
import javax.persistence.NoResultException;

/**
 *
 * @author SoulHunter
 */
@Stateless
public class PrendasEJB extends AbstractFacade<Prenda> {

    @EJB
    private StockPrendaEJB stockPrendaEJB;

    public PrendasEJB() {
        super(Prenda.class);
    }

    public List<Prenda> getPrendas() {
        try {
            return (ArrayList<Prenda>) em.createNamedQuery("Prenda.findAll", Prenda.class).getResultList();
        } catch (NullPointerException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<SelectItem> getSelectItemPrenda() {
        List<SelectItem> lista = new ArrayList<>();
        try {
            for (Prenda prenda : (ArrayList<Prenda>) em.createNamedQuery("Prenda.findAll", Prenda.class).getResultList()) {
                lista.add(new SelectItem(prenda.getPrendaId(), prenda.getPrendaNombre()));
            }
            return lista;
        } catch (NullPointerException e) {
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean setCrearPrenda(PrendaUtil prendaUtil) {
        try {
            Prenda prenda = new Prenda();

            prenda.setPrendaNombre(prendaUtil.getPrendaNombre());
            prenda.setPrendaTipoId(em.find(Prendatipo.class, Integer.parseInt(prendaUtil.getPrendaTipoId())));
            prenda.setColorId(em.find(Color.class, Integer.parseInt(prendaUtil.getColorId())));
            prenda.setPrendaDescripcion(prendaUtil.getPrendaDescripcion());
            prenda.setOcasionId(em.find(Ocasion.class, Integer.parseInt(prendaUtil.getOcasionId())));
            prenda.setEstadoId(em.find(Estado.class, Integer.parseInt(prendaUtil.getEstadoId())));
            prenda.setTallaId(em.find(Talla.class, Integer.parseInt(prendaUtil.getTallaId())));
            prenda.setUbicacion(prendaUtil.getUbicacion());
            prenda.setUrl(prendaUtil.getURL());
            create(prenda);
            Stockprenda sp = new Stockprenda();
            sp.setStockPrendaCant(Float.valueOf(0));
            sp.setPrendaId(em.createNamedQuery("Prenda.findByPrendaNombre", Prenda.class).setParameter("prendaNombre", prendaUtil.getPrendaNombre()).getSingleResult());
            sp.setTallaId(em.find(Talla.class, Integer.parseInt(prendaUtil.getTallaId())));
            stockPrendaEJB.setCrearStockPrenda(sp);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean getexistePrenda(String prendaNombre) {
        try {
            return (em.createNamedQuery("Prenda.findByPrendaNombre").setParameter("prendaNombre", prendaNombre).getSingleResult() != null);
        } catch (NoResultException nre) {
            System.out.println("com.universitaria.ateliermaven.ejb.produccion.PrendasEJB.existePrenda()");
            nre.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean setModificarPrenda(Prenda prenda) {
        try {
            edit(prenda);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<PrendaUtil> getPrendasProduccion() {
        List<PrendaUtil> list = new ArrayList<>();
        try {
            for (Prenda prenda : (ArrayList<Prenda>) em.createNamedQuery("Prenda.findAll", Prenda.class).getResultList()) {
                PrendaUtil util = new PrendaUtil();
                util.setCantidad("0");
                util.setColorId(String.valueOf(prenda.getColorId().getColorId()));
                util.setEstadoId(String.valueOf(prenda.getEstadoId().getEstadoId()));
                util.setOcasionId(String.valueOf(prenda.getOcasionId().getOcasionId()));
                util.setPrendaDescripcion(prenda.getPrendaDescripcion());
                util.setPrendaId(String.valueOf(prenda.getPrendaId()));
                util.setPrendaNombre(prenda.getPrendaNombre());
                util.setPrendaTipoId(String.valueOf(prenda.getPrendaTipoId().getPrendaTipoId()));
                util.setCantidad("0");
                list.add(util);
            }
            return list;
        } catch (NullPointerException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<PrendaUtil> getPrendasProduccionDisponibles() {
        List<PrendaUtil> list = new ArrayList<>();
        try {

            for (Stockprenda sp : stockPrendaEJB.getStockPrenda()) {

                if (sp.getStockPrendaCant() > 0) {
                    PrendaUtil util = new PrendaUtil();
                    util.setCantidad("0");
                    util.setColorId(String.valueOf(sp.getPrendaId().getColorId().getColorId()));
                    util.setEstadoId(String.valueOf(sp.getPrendaId().getEstadoId().getEstadoId()));
                    util.setOcasionId(String.valueOf(sp.getPrendaId().getOcasionId().getOcasionId()));
                    util.setPrendaDescripcion(sp.getPrendaId().getPrendaDescripcion());
                    util.setPrendaId(String.valueOf(sp.getPrendaId().getPrendaId()));
                    util.setPrendaNombre(sp.getPrendaId().getPrendaNombre());
                    util.setPrendaTipoId(String.valueOf(sp.getPrendaId().getPrendaTipoId().getPrendaTipoId()));
                    util.setValor("0");
                    list.add(util);

                }

            }
            return list;
        } catch (NullPointerException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public StockPrendaEJB getStockPrendaEJB() {
        return stockPrendaEJB;
    }

    public void setStockPrendaEJB(StockPrendaEJB stockPrendaEJB) {
        this.stockPrendaEJB = stockPrendaEJB;
    }

    public Prenda traerPrendaNombre(String nombre) {

        try {
            return em.createNamedQuery("Prenda.findByPrendaNombre", Prenda.class).setParameter("prendaNombre", nombre).getSingleResult();
        } catch (NoResultException nre) {
            System.out.println("com.universitaria.ateliermaven.ejb.produccion.PrendasEJB.traerPrendaNombre()");
            nre.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
