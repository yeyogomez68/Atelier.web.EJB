/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.ejb.administrador;

import com.universitaria.atelier.web.jpa.AbstractFacade;
import com.universitaria.atelier.web.jpa.Color;
import com.universitaria.atelier.web.jpa.Estado;
import com.universitaria.atelier.web.jpa.Material;
import com.universitaria.atelier.web.jpa.Ocasion;
import com.universitaria.atelier.web.jpa.Prenda;
import com.universitaria.atelier.web.jpa.Prendatipo;
import com.universitaria.atelier.web.utils.PrendaUtil;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;


/**
 *
 * @author Jeisson Gomez
 */
@Stateless
public class PrendaEJB extends AbstractFacade<Prenda> {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    public PrendaEJB() {
        super(Prenda.class);
    }

    public List<Prenda> getPrendas(){
        try {
            return (ArrayList<Prenda>) em.createNamedQuery("Prenda.findAll",Prenda.class).getResultList();
        } catch (NullPointerException e){
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
                lista.add(new SelectItem(prenda.getPrendaId(),prenda.getPrendaNombre()));
            }
            return lista;
        } catch (NullPointerException e) {
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean setCrearPrenda(PrendaUtil nuevaPrenda) {
        try {
            Prenda prend = new Prenda();
            prend.setPrendaNombre(nuevaPrenda.getPrendaNombre());
            prend.setPrendaDescripcion(nuevaPrenda.getPrendaDescripcion());
            prend.setPrendaTipoId(em.find(Prendatipo.class, Integer.parseInt(nuevaPrenda.getPrendaTipoId())));
            prend.setMaterialId(em.find(Material.class,Integer.parseInt(nuevaPrenda.getMaterialId())));
            prend.setColorId(em.find(Color.class,Integer.parseInt(nuevaPrenda.getColorId())));
            prend.setOcasionId(em.find(Ocasion.class,Integer.parseInt(nuevaPrenda.getOcasionId())));
            prend.setEstadoId(em.find(Estado.class,Integer.parseInt(nuevaPrenda.getEstadoId())));
            create(prend);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean getExistePrenda(String prendaDescripcion) {
        try {
            return (em.createNamedQuery("Prenda.findByPrendaDescripcion").setParameter("prendaDescripcion", prendaDescripcion).getSingleResult() != null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean setModificarPrenda(PrendaUtil prenda) {
        try {
            Prenda prend = new Prenda();
            prend = em.find(Prenda.class, Integer.parseInt(prenda.getPrendaId()));
            prend.setPrendaNombre(prenda.getPrendaNombre());
            prend.setPrendaDescripcion(prenda.getPrendaDescripcion());
            prend.setPrendaTipoId(em.find(Prendatipo.class, Integer.valueOf(prenda.getPrendaTipoId())));
            prend.setMaterialId(em.find(Material.class,Integer.valueOf(prenda.getMaterialId())));
            prend.setColorId(em.find(Color.class,Integer.valueOf(prenda.getColorId())));
            prend.setOcasionId(em.find(Ocasion.class,Integer.valueOf(prenda.getOcasionId())));
            prend.setEstadoId(em.find(Estado.class,Integer.valueOf(prenda.getEstadoId())));
            edit(prend);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
