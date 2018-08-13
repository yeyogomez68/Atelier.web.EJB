/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.web.ejb.compras;

import com.universitaria.atelier.web.jpa.AbstractFacade;
import com.universitaria.atelier.web.jpa.Material;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

/**
 *
 * @author jeisson.gomez
 */
@Stateless
public class MaterialEJB extends AbstractFacade<Material>{

    public MaterialEJB() {
        super(Material.class);
    }
    
    
}
