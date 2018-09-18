/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.ejb.alquilerventas;

import com.universitaria.atelier.web.jpa.AbstractFacade;
import com.universitaria.atelier.web.jpa.Renta;
import javax.ejb.Stateless;

/**
 *
 * @author SoulHunter
 */
@Stateless
public class AlquilerEJB extends AbstractFacade<Renta> {

    public AlquilerEJB() {
        super(Renta.class);
    }

}
