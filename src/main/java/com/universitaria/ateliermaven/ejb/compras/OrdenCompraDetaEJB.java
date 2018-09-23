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
import com.universitaria.atelier.web.jpa.Ordencompra;

import com.universitaria.atelier.web.jpa.Ordencompradeta;
import com.universitaria.atelier.web.jpa.Requestdeta;
import com.universitaria.atelier.web.utils.OrdenCompraDetaUtil;
import com.universitaria.ateliermaven.ejb.constantes.EstadoEnum;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;

/**
 *
 * @author jeisson.gomez
 */
@Stateless
public class OrdenCompraDetaEJB extends AbstractFacade<Ordencompradeta> {

    @EJB
    private DetalleRequerimientoEJB detalleRequerimientoEJB;

    public OrdenCompraDetaEJB() {
        super(Ordencompradeta.class);
    }
    public List<OrdenCompraDetaUtil> getDetalleRqForOc(){
        List<OrdenCompraDetaUtil> list = new ArrayList<>();        
        try {
            for (Requestdeta rqDeta : detalleRequerimientoEJB.obtenerDetallePendCompra()) {
                OrdenCompraDetaUtil orCo = new OrdenCompraDetaUtil();
                orCo.setEncabezado(String.valueOf(rqDeta.getEncabezadoRequerimientoId().getEncabezadoRequerimientoId()));
                orCo.setMaterial(rqDeta.getMaterialId().getMaterialNombre());
                orCo.setMaterialId(String.valueOf(rqDeta.getMaterialId().getMaterialId()));
                orCo.setCatidad(String.valueOf(rqDeta.getRequestDetaCantidad()));                
                orCo.setValorUnitario("0");
                orCo.setValorBruto("0");
                orCo.setValorIva("0");
                orCo.setValorTotal("0");
                list.add(orCo);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<Ordencompradeta> getOrdenCompraDetaPorEstado() {

        try {
            return em.createNamedQuery("Ordencompradeta.findByEstadoId", Ordencompradeta.class).setParameter("estadoId", em.find(Estado.class, EstadoEnum.COMPRAS.getId())).getResultList();
        } catch (NoResultException nre) {
            System.out.println("com.universitaria.ateliermaven.ejb.compras.OrdenCompraDetaEJB.getOrdenCompraDetaPorEstado()");
            nre.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean setActualizarEstadoOrderCompraDeta(Ordencompradeta ordencompradeta, int estado) {
        try {
            ordencompradeta.setEstadoId(em.find(Estado.class, estado));
            edit(ordencompradeta);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean crearDetalleCompra(Integer ocId, List<OrdenCompraDetaUtil> list){
        try {
            for (OrdenCompraDetaUtil ordenCompraDetaUtil : list) {
                Ordencompradeta ocDeta = new Ordencompradeta();
                ocDeta.setOrdenCompraId(em.find(Ordencompra.class, ocId));
                ocDeta.setMaterialId(em.find(Material.class, Integer.parseInt(ordenCompraDetaUtil.getMaterialId())));
                ocDeta.setOrdenCompraCantidad(new Double(ordenCompraDetaUtil.getCatidad()));
                ocDeta.setOrdenCompraValorUnit(new Double(ordenCompraDetaUtil.getValorUnitario()));
                ocDeta.setOrdenCompraDetaTotBruto(new Double(ordenCompraDetaUtil.getValorBruto()));
                ocDeta.setOrdenCompraIVA(new Double(ordenCompraDetaUtil.getValorIva()));
                ocDeta.setOrdenCompraValorTot(new Double(ordenCompraDetaUtil.getValorTotal()));
                ocDeta.setEncabezadoRequerimientoId(em.find(Encabezadorequerimiento.class, Integer.parseInt(ordenCompraDetaUtil.getEncabezado())));
                ocDeta.setEstadoId(em.find(Estado.class, EstadoEnum.COMPRAS.getId()));
                create(ocDeta);
                detalleRequerimientoEJB.actualizarEstadoaCompras(ocId, ocDeta.getMaterialId().getMaterialId());
            }
            return true;
        } catch (Exception e) {
            System.out.println("com.universitaria.ateliermaven.ejb.compras.OrdenCompraDetaEJB.crearDetalleCompra()" + e);
        }
        return false;
    }
    
    public List<Ordencompradeta> obtenerOrdenesCompraPorId(Integer idOc){
        try {
            return em.createNamedQuery("Ordencompradeta.findByOcId",Ordencompradeta.class).setParameter("ordenCompraId", idOc).getResultList();
        } catch (Exception e) {
            System.out.println("com.universitaria.ateliermaven.ejb.compras.OrdenCompraDetaEJB.obtenerOrdenesCompraPorId()" + e);
        }
        return null;
    }
}
