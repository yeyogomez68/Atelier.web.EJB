/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.atelier.web.inventario;

import com.universitaria.atelier.web.jpa.AbstractFacade;
import com.universitaria.atelier.web.jpa.Ciudad;
import com.universitaria.atelier.web.jpa.Estado;
import com.universitaria.atelier.web.jpa.Proveedor;
import com.universitaria.atelier.web.utils.ProveedorUtil;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SoulHunter
 */
public class ProveedorEJB extends AbstractFacade<Proveedor> {

    public ProveedorEJB() {
        super(Proveedor.class);
    }

    public List<Proveedor> getProveedores() {
        try {
            return (ArrayList<Proveedor>) em.createNamedQuery("Proveedor.findAll", Proveedor.class).getResultList();
        } catch (NullPointerException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean setCrearProveedor(ProveedorUtil nuevoProveedor) {
        try {
            Proveedor proveedor = new Proveedor();
            proveedor.setNit(nuevoProveedor.getNit());
            proveedor.setProveedorNombre(nuevoProveedor.getNombre());
            proveedor.setProveedorDireccion(nuevoProveedor.getDireccion());
            proveedor.setProveedorPhone(nuevoProveedor.getTelefono());
            proveedor.setEstadoId(em.find(Estado.class, Integer.parseInt(nuevoProveedor.getEstadoId())));
            proveedor.setCiudadId(em.find(Ciudad.class, Integer.parseInt(nuevoProveedor.getCiudadId())));
            create(proveedor);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
