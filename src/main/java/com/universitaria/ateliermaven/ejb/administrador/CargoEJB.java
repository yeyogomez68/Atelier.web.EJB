/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.ejb.administrador;

import com.universitaria.atelier.web.jpa.AbstractFacade;
import com.universitaria.atelier.web.jpa.Cargo;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;
/**
 *
 * @author JorgeWilson
 */
@Stateless
public class CargoEJB  extends AbstractFacade<Cargo>  {

    public CargoEJB() {
        super(Cargo.class);
    }

    public List<Cargo> getCargos() {
        try {
            return (ArrayList<Cargo>) em.createNamedQuery("Cargo.findAll", Cargo.class).getResultList();
        } catch (NullPointerException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<SelectItem> getSelectItemCargo() {
        List<SelectItem> lista = new ArrayList<>();
        try {
            for (Cargo cargo : (ArrayList<Cargo>) em.createNamedQuery("Cargo.findAll", Cargo.class).getResultList()) {
                lista.add(new SelectItem(cargo.getCargoId(),cargo.getCargoDesc()));
            }
            return lista;
        } catch (NullPointerException e) {
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean setCrearCargo(String cargo) {
        try {
            Cargo car = new Cargo();
            car.setCargoDesc(cargo);
            create(car);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean getExisteCargo(String cargoDescripcion) {
        try {
            return (em.createNamedQuery("Cargo.findByCargoDesc").setParameter("cargoDesc", cargoDescripcion).getSingleResult() != null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean setModificarCargo(String id, String desc) {
        try {
            Cargo car = new Cargo();
            car = em.find(Cargo.class, Integer.parseInt(id));
            car.setCargoDesc(desc);
            edit(car);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
}
