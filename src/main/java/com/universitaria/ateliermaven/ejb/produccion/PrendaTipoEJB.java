package com.universitaria.ateliermaven.ejb.produccion;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.universitaria.atelier.web.jpa.AbstractFacade;
import com.universitaria.atelier.web.jpa.Prendatipo;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;
import javax.persistence.NoResultException;

/**
 *
 * @author jeisson.gomez
 */
@Stateless
public class PrendaTipoEJB extends AbstractFacade<Prendatipo> {

    public PrendaTipoEJB() {
        super(Prendatipo.class);
    }

    public List<Prendatipo> getPrendatipos() {
        try {
            return (ArrayList<Prendatipo>) em.createNamedQuery("Prendatipo.findAll", Prendatipo.class).getResultList();
        } catch (NullPointerException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<SelectItem> getSelectItemPrendaTipos() {
        List<SelectItem> lista = new ArrayList<>();
        try {
            for (Prendatipo pt : (ArrayList<Prendatipo>) em.createNamedQuery("Prendatipo.findAll", Prendatipo.class).getResultList()) {
                lista.add(new SelectItem(pt.getPrendaTipoId(), pt.getPrendaTipoDescripcion()));
            }
            return lista;
        } catch (NullPointerException e) {
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean setCrearTipoPrenda(String prendaTipoDescripcion) {
        try {
            Prendatipo prendaTipo = new Prendatipo();
            prendaTipo.setPrendaTipoDescripcion(prendaTipoDescripcion);
            create(prendaTipo);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean existePrendaTipo(String prendaTipoDescripcion) {
        try {
            return (em.createNamedQuery("Prendatipo.findByPrendaTipoDescripcion").setParameter("prendaTipoDescripcion", prendaTipoDescripcion).getSingleResult() != null);
        } catch (NoResultException nre) {
            System.out.println("com.universitaria.ateliermaven.ejb.produccion.PrendaTipoEJB.existePrendaTipo()");
            nre.getMessage();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    
    public boolean setModificarPrendaTipo(String prendaTipoId, String nuevaDescripcion) {
        try {
            Prendatipo prendatip = new Prendatipo();
            prendatip = em.find(Prendatipo.class, Integer.parseInt(prendaTipoId));
            prendatip.setPrendaTipoDescripcion(nuevaDescripcion);
            edit(prendatip);
                return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
