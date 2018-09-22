/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.ejb.compras;

import com.universitaria.atelier.web.jpa.AbstractFacade;
import com.universitaria.atelier.web.jpa.Estado;
import com.universitaria.atelier.web.jpa.Ordencompradeta;
import com.universitaria.atelier.web.jpa.Requestdeta;
import com.universitaria.ateliermaven.ejb.constantes.EstadoEnum;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author jeisson.gomez
 */
@Stateless
public class OrdenCompraDetaEJB  extends AbstractFacade<Ordencompradeta>{
    @EJB
    private DetalleRequerimientoEJB detalleRequerimientoEJB;
    
    public OrdenCompraDetaEJB() {
        super(Ordencompradeta.class);
    }
    
    public List<Ordencompradeta> getDetalleRqForOc(){
        List<Ordencompradeta> list = new ArrayList<>();        
        try {
            for (Requestdeta rqDeta : detalleRequerimientoEJB.obtenerDetallePendCompra()) {
                Ordencompradeta orCo = new Ordencompradeta();
                orCo.setEncabezadoRequerimientoId(rqDeta.getEncabezadoRequerimientoId());
                orCo.setMaterialId(rqDeta.getMaterialId());
                orCo.setOrdenCompraCantidad(rqDeta.getRequestDetaCantidad());                
                orCo.setOrdenCompraValorUnit(new Double(0));
                orCo.setOrdenCompraDetaTotBruto(new Double(0));
                orCo.setOrdenCompraIVA(new Double(0));
                orCo.setOrdenCompraValorTot(new Double(0));
                orCo.setEstadoId(em.find(Estado.class, EstadoEnum.COMPRAS.getId()));
                list.add(orCo);
            }
        } catch (Exception e) {
        }
        return list;
    }
    
}
