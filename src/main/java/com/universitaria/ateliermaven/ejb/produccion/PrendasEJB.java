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
import com.universitaria.atelier.web.utils.PrendaUtil;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;

/**
 *
 * @author SoulHunter
 */
@Stateless
public class PrendasEJB extends AbstractFacade<Prenda> {

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
            prenda.setMaterialId(em.find(Material.class, Integer.parseInt(prendaUtil.getMaterialId())));
            prenda.setColorId(em.find(Color.class, Integer.parseInt(prendaUtil.getColorId())));
            prenda.setPrendaDescripcion(prendaUtil.getPrendaDescripcion());
            prenda.setOcasionId(em.find(Ocasion.class, Integer.parseInt(prendaUtil.getOcasionId())));
            prenda.setEstadoId(em.find(Estado.class, Integer.parseInt(prendaUtil.getEstadoId())));
            create(prenda);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean existePrenda(String prendaNombre) {
        try {
            return (em.createNamedQuery("Prenda.findByPrendaNombre").setParameter("prendaNombre", prendaNombre).getSingleResult() != null);
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

}