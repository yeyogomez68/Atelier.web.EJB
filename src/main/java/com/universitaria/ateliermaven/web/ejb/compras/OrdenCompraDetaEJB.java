/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.web.ejb.compras;

import com.universitaria.atelier.web.jpa.AbstractFacade;
import com.universitaria.atelier.web.jpa.Ordencompradeta;
import javax.ejb.Stateless;

/**
 *
 * @author jeisson.gomez
 */
@Stateless
public class OrdenCompraDetaEJB  extends AbstractFacade<Ordencompradeta>{
    
    public OrdenCompraDetaEJB() {
        super(Ordencompradeta.class);
    }
    
}
