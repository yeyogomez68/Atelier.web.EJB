/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.atelier.web.utils;

import com.universitaria.atelier.web.jpa.AbstractFacade;
import com.universitaria.atelier.web.jpa.Cliente;
import javax.ejb.Stateless;

/**
 *
 * @author SoulHunter
 */
@Stateless
public class ClienteEJB extends AbstractFacade<Cliente> {

    public ClienteEJB() {

        super(Cliente.class);
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
