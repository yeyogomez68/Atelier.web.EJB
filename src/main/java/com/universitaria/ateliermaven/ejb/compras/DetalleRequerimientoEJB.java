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
import java.util.ArrayList;
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
    
    public List<MaterialRequerimientoUtil> obtenerDetalleRq(String Id){
        List<MaterialRequerimientoUtil> lista = new ArrayList<>();
        try {
            for (Requestdeta rqDeta : em.createNamedQuery("Requestdeta.findByIdRq", Requestdeta.class).setParameter("idRq", Integer.valueOf(Id)).getResultList()) {
                MaterialRequerimientoUtil mat =  new MaterialRequerimientoUtil();
                mat.setMaterialId(rqDeta.getMaterialId().getMaterialId().toString());
                mat.setReferencia(rqDeta.getMaterialId().getMaterialReference());
                mat.setMarcaId(rqDeta.getMaterialId().getMarcaId().getMarcaNombre());
                mat.setCantidad(String.valueOf(rqDeta.getRequestDetaCantidad()));                
                mat.setTipoId(rqDeta.getMaterialId().getMaterialTipoId().getMaterialTipoDescript());
                mat.setNombre(rqDeta.getMaterialId().getMaterialNombre());
                lista.add(mat);
            }
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
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
    
    public boolean modificarDetalleRequerimiento(String IdRq,List<MaterialRequerimientoUtil> listMaterial, Usuario user){
        int flag = 0;
        try {
            //Elimina los items que no esten.
            for (Requestdeta rqDeta : em.createNamedQuery("Requestdeta.findByIdRq", Requestdeta.class).setParameter("idRq", Integer.valueOf(IdRq)).getResultList()) {
                flag=0;
                for (MaterialRequerimientoUtil mat : listMaterial) {                
                    if (rqDeta.getMaterialId().getMaterialId()==Integer.parseInt(mat.getMaterialId())) {
                        flag++;
                        rqDeta.setRequestDetaCantidad(Float.parseFloat(mat.getCantidad()));
                        listMaterial.remove(mat);
                        break;
                    }
                } 
                if(flag==0){
                    deleteItemdeta(rqDeta);
                }else if(flag>0){
                    updateItem(rqDeta);
                }
            }
            crearDetalleRequerimiento(Integer.parseInt(IdRq), listMaterial, user); 
            return  true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public void deleteItemdeta(Requestdeta rqDeta){
        try {
            remove(rqDeta);
        } catch (Exception e) {
        }
    }
    
    public void updateItem(Requestdeta rqDeta){
        try {
            edit(rqDeta);
        } catch (Exception e) {
        }
    }
}
