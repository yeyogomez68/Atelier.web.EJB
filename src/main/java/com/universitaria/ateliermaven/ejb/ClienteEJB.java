/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.ejb;

import com.universitaria.atelier.web.jpa.AbstractFacade;
import com.universitaria.atelier.web.jpa.Ciudad;
import com.universitaria.atelier.web.jpa.Cliente;
import com.universitaria.atelier.web.jpa.Estado;
import com.universitaria.atelier.web.jpa.Roll;
import com.universitaria.atelier.web.utils.ClienteUtil;
import com.universitaria.ateliermaven.ejb.security.EncryptDataEJB;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;
import javax.persistence.NoResultException;
import javax.persistence.Query;
/**
 *
 * @author SoulHunter
 */
@Stateless
public class ClienteEJB extends AbstractFacade<Cliente> {

    
    @EJB
    private EncryptDataEJB encryptDataEJB;
    
    public ClienteEJB() {

        super(Cliente.class);
    }

    public List<Cliente> getClientes() {
        try {            
            return (ArrayList<Cliente>) em.createNamedQuery("Cliente.findAll", Cliente.class).getResultList();
        } catch (NullPointerException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean setCrearCliente(ClienteUtil clienteCrear) {
        try {
            Cliente cliente = new Cliente();
            cliente.setClienteIdentificacion(clienteCrear.getIdentificacion());
            cliente.setClienteNombre(clienteCrear.getNombre());
            cliente.setClienteApellido(clienteCrear.getApellido());
            cliente.setClienteEmail(clienteCrear.getEmail());
            cliente.setClientePassword(encryptDataEJB.encryptText(clienteCrear.getPassword()));
            cliente.setClienteDireccion(clienteCrear.getDireccion());
            cliente.setClienteCel(clienteCrear.getCelular());
            cliente.setEstadoId(em.find(Estado.class, Integer.parseInt(clienteCrear.getEstadoId())));
            cliente.setRollId(em.find(Roll.class, Integer.parseInt(clienteCrear.getRollId())));
            cliente.setCiudadId(em.find(Ciudad.class, Integer.parseInt(clienteCrear.getCiudadId())));
            create(cliente);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
     public List<SelectItem> getSelectItemClientes() {
        List<SelectItem> lista = new ArrayList<>();
        try {
            for (Cliente cliente : (ArrayList<Cliente>) em.createNamedQuery("Cliente.findAll", Cliente.class).getResultList()) {
                lista.add(new SelectItem(cliente.getClienteId(), cliente.getClienteNombre()+ " " + cliente.getClienteApellido()));
            }
            return lista;
        } catch (NullPointerException e) {
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
    
    public List<SelectItem> getSelectItemClientes(String roll) {
        List<SelectItem> lista = new ArrayList<>();
        try {
            for (Cliente cliente : (ArrayList<Cliente>) em.createNamedQuery("Cliente.findByRoll", Cliente.class).setParameter("rollId", roll).getResultList()) {
                lista.add(new SelectItem(cliente.getClienteId(), cliente.getClienteNombre() + " " + cliente.getClienteApellido()));
            }
            return lista;
        } catch (NullPointerException e) {
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
    
    public boolean getExisteCliente(String clienteNombre) {
        try {
            return (em.createNamedQuery("Cliente.findByClienteIdentificacion").setParameter("clienteIdentificacion", clienteNombre).getSingleResult() != null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean setModificarCliente(ClienteUtil clienteModificar) {
        try {
            Cliente client = new Cliente();
            client = em.find(Cliente.class,clienteModificar.getClienteId());
            client.setClienteNombre(clienteModificar.getNombre());
            client.setClienteApellido(clienteModificar.getApellido());
            client.setClienteEmail(clienteModificar.getEmail());
            client.setClienteCel(clienteModificar.getCelular()); 
            edit(client);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
  
    public Cliente clientePorIdentificacion(String identificacion) {
        try {
            return em.createNamedQuery("Cliente.findByClienteIdentificacion", Cliente.class).setParameter("clienteIdentificacion", identificacion).getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
