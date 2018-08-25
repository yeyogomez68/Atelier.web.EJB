/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.ejb.administrador;

import com.universitaria.atelier.web.jpa.AbstractFacade;
import com.universitaria.atelier.web.jpa.Departamento;
import com.universitaria.atelier.web.jpa.Pais;
import com.universitaria.atelier.web.utils.DepartamentoUtil;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;

/**
 *
 * @author SoulHunter
 */
@Stateless
public class DepartamentoEJB extends AbstractFacade<Departamento> {

    public DepartamentoEJB() {
        super(Departamento.class);
    }

    public List<Departamento> getDepartamentos() {
        try {
            return (ArrayList<Departamento>) em.createNamedQuery("Departamento.findAll", Departamento.class).getResultList();
        } catch (NullPointerException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean setCrearDepartamento(DepartamentoUtil departamentoUtil) {
        try {
            Departamento departamento = new Departamento();
            departamento.setDepartamentoNombre(departamentoUtil.getDepartamentoNombre());
            departamento.setPaisId(em.find(Pais.class, Integer.parseInt(departamentoUtil.getPaisId())));
            create(departamento);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean existeDepartamento(String departamentoDescripcion) {
        try {
            return (em.createNamedQuery("Departamento.findByDepartamentoNombre").setParameter("departamentoNombre", departamentoDescripcion).getSingleResult() != null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean setModificarDepartamento(Departamento departamento, String paisId) {
        try {
            departamento.setPaisId(em.find(Pais.class, Integer.parseInt(paisId)));
                edit(departamento);
                return true;
  
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<SelectItem> getSelectItemDepartamentos() {
        List<SelectItem> lista = new ArrayList<>();
        try {
            for (Departamento dep : (ArrayList<Departamento>) em.createNamedQuery("Departamento.findAll", Departamento.class).getResultList()) {
                lista.add(new SelectItem(dep.getDepartamentoId(), dep.getDepartamentoNombre()));
            }
            return lista;
        } catch (NullPointerException e) {
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

}
