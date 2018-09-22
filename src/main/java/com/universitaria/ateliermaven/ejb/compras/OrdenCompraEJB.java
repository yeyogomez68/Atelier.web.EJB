/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.ejb.compras;

import com.universitaria.atelier.web.jpa.AbstractFacade;
import com.universitaria.atelier.web.jpa.Ordencompra;
import com.universitaria.atelier.web.jpa.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author jeisson.gomez
 */
@Stateless
public class OrdenCompraEJB extends AbstractFacade<Ordencompra> {

    @EJB
    private DetalleRequerimientoEJB detalleRequerimientoEJB;
    
    public OrdenCompraEJB() {
        super(Ordencompra.class);
    }
    
    public List<Ordencompra> getOrdenesCompras(){
        try {
            return (ArrayList<Ordencompra>) em.createNamedQuery("Ordencompra.findAll",Ordencompra.class).getResultList();
        } catch (NullPointerException e){
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }       
        return null;
    }
    
    public List<Ordencompra> getOrdenesComprasByUser(Usuario us){
        try {
            return (ArrayList<Ordencompra>) em.createNamedQuery("Ordencompra.findByOrdenByUserId",Ordencompra.class).setParameter("usuarioId", us.getUsuarioId()).getResultList();
        } catch (NullPointerException e){
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }       
        return null;
    }
    
    
}
