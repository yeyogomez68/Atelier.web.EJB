/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.ejb.inventario;

import com.universitaria.atelier.web.jpa.AbstractFacade;
import com.universitaria.atelier.web.jpa.Ciudad;
import com.universitaria.atelier.web.jpa.Estado;
import com.universitaria.atelier.web.jpa.Proveedor;
import static com.universitaria.atelier.web.jpa.Proveedor_.proveedorId;
import com.universitaria.atelier.web.utils.ProveedorUtil;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;

/**
 *
 * @author SoulHunter
 */

@Stateless
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

  public List<SelectItem> getSelectItemProveedor() {
        List<SelectItem> lista = new ArrayList<>();
        try {
            for (Proveedor proveedor : (ArrayList<Proveedor>) em.createNamedQuery("Proveedor.findAll", Proveedor.class).getResultList()) {
                lista.add(new SelectItem(proveedor.getProveedorId(),proveedor.getProveedorNombre()));
            }
            return lista;
        } catch (NullPointerException e) {
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    } 
    
   public boolean getExisteProveedor(String proveedorNit) {
        try {
            return (em.createNamedQuery("Proveedor.findByNit").setParameter("nit", proveedorNit).getSingleResult() != null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }  
  
    public boolean setModificarProveedor(ProveedorUtil proveedorModificar) {
        try {
            Proveedor proveedor = new Proveedor();
            proveedor = em.find(Proveedor.class,proveedorModificar.getProveedorId());
            proveedor.setNit(proveedorModificar.getNit());
            proveedor.setProveedorNombre(proveedorModificar.getNombre());
            proveedor.setProveedorDireccion(proveedorModificar.getDireccion());
            proveedor.setProveedorPhone(proveedorModificar.getTelefono());
            edit(proveedor);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    } 
}
