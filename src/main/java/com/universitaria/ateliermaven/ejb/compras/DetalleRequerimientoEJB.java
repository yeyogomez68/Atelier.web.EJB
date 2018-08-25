/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.ejb.compras;

import com.universitaria.atelier.web.jpa.AbstractFacade;
import com.universitaria.atelier.web.jpa.Encabezadorequerimiento;
import com.universitaria.atelier.web.jpa.Estado;
import com.universitaria.atelier.web.jpa.Material;
import com.universitaria.atelier.web.jpa.Requestdeta;
import com.universitaria.atelier.web.jpa.Usuario;
import com.universitaria.atelier.web.utils.MaterialRequerimientoUtil;
import java.sql.Timestamp;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

/**
 *
 * @author jeisson.gomez
 */
@Stateless
@LocalBean
public class DetalleRequerimientoEJB extends AbstractFacade<Requestdeta> {

    public DetalleRequerimientoEJB() {
        super(Requestdeta.class);
    }
    
    public boolean crearDetalleRequerimiento(Integer idEncabeza, List<MaterialRequerimientoUtil> listMaterial, Usuario user){
        try {
            for (MaterialRequerimientoUtil mat : listMaterial) {
                Requestdeta rqDeta = new Requestdeta();
                rqDeta.setEncabezadoRequerimientoId(em.find(Encabezadorequerimiento.class, idEncabeza));
                rqDeta.setEstadoId(em.find(Estado.class, 1));
                rqDeta.setMaterialId(em.find(Material.class, Integer.parseInt(mat.getMaterialId())));
                rqDeta.setRequestDetaFecha(new Timestamp(System.currentTimeMillis()));
                rqDeta.setUsuarioId(user.getUsuarioId());
                rqDeta.setRequestDetaCantidad(Float.parseFloat(mat.getCantidad()));
                em.persist(rqDeta);                
            }   
            return  true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
