/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.ejb.administrador;

import com.universitaria.atelier.web.jpa.AbstractFacade;
import com.universitaria.atelier.web.jpa.Ciudad;

import com.universitaria.atelier.web.jpa.Departamento;

import com.universitaria.atelier.web.utils.CiudadUtil;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;

/**
 *
 * @author jeisson.gomez
 */
@Stateless
public class CiudadEJB extends AbstractFacade<Ciudad> {

    public CiudadEJB() {
        super(Ciudad.class);
    }

    public List<Ciudad> getCiudades() {
        try {
            return (ArrayList<Ciudad>) em.createNamedQuery("Ciudad.findAll", Ciudad.class).getResultList();
        } catch (NullPointerException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<SelectItem> getSelectItemCiudad() {
        List<SelectItem> lista = new ArrayList<>();
        try {
            for (Ciudad ciudad : (ArrayList<Ciudad>) em.createNamedQuery("Ciudad.findAll", Ciudad.class).getResultList()) {
                lista.add(new SelectItem(ciudad.getCiudadId(), ciudad.getCiudadNombre()));
            }
            return lista;
        } catch (NullPointerException e) {
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean setCrearCiudad(CiudadUtil ciudadUtil) {
        try {
            Ciudad ciudad = new Ciudad();
            ciudad.setCiudadNombre(ciudadUtil.getCiudadNombre());
            ciudad.setDepartamentoId(em.find(Departamento.class, Integer.parseInt(ciudadUtil.getDepartamentoId())));
            create(ciudad);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean existeCiudad(String ciudadDescripcion) {
        try {
            return (em.createNamedQuery("Ciudad.findByCiudadNombre").setParameter("ciudadNombre", ciudadDescripcion).getSingleResult() != null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean setModificarCiudad(String ciudadId, String nuevaDescripcion) {
        try {
            Ciudad ciudad = em.find(Ciudad.class, Integer.parseInt(ciudadId));
            if (!nuevaDescripcion.equalsIgnoreCase(ciudad.getCiudadNombre())) {
                ciudad.setCiudadNombre(nuevaDescripcion);
                edit(ciudad);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
