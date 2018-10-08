/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.ejb.produccion;

import com.universitaria.atelier.web.jpa.AbstractFacade;
import com.universitaria.atelier.web.jpa.Estado;
import com.universitaria.atelier.web.jpa.Material;
import com.universitaria.atelier.web.jpa.Produccion;
import com.universitaria.atelier.web.jpa.Producciondeta;
import com.universitaria.atelier.web.jpa.Stockmateriales;
import com.universitaria.atelier.web.jpa.Usuario;
import com.universitaria.atelier.web.utils.DetalleProduccionUtil;
import com.universitaria.ateliermaven.ejb.constantes.EstadoEnum;
import com.universitaria.ateliermaven.ejb.inventario.StockMaterialesEJB;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;

/**
 *
 * @author SoulHunter
 */
@Stateless
public class DetalleProduccionEJB extends AbstractFacade<Producciondeta> {

    @EJB
    private ProduccionEJB produccionEJB;
    @EJB
    private StockMaterialesEJB stockMaterialesEJB;

    public DetalleProduccionEJB() {
        super(Producciondeta.class);
    }

    public List<Producciondeta> getDetalleProduccion() {
        try {
            return (ArrayList<Producciondeta>) em.createNamedQuery("Producciondeta.findAll", Producciondeta.class).getResultList();
        } catch (NullPointerException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<SelectItem> getSelectItemDetalleProduccion() {
        List<SelectItem> lista = new ArrayList<>();
        try {
            for (Producciondeta produccionDeta : (ArrayList<Producciondeta>) em.createNamedQuery("Producciondeta.findAll", Producciondeta.class).getResultList()) {
                lista.add(new SelectItem(produccionDeta.getProduccionDetaId(), produccionDeta.getProduccionId().getProduccionDescripcion()));
            }
            return lista;
        } catch (NullPointerException e) {
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean setCrearDetalleProduccion(DetalleProduccionUtil detalleProduccionUtil) {
        try {
            Producciondeta produccionDeta = new Producciondeta();
            Material material = em.find(Material.class, Integer.parseInt(detalleProduccionUtil.getMaterialId()));
            Double cantidad = Double.parseDouble(detalleProduccionUtil.getProduccionDetaCant());
            Stockmateriales sm = stockMaterialesEJB.getStockMaterial(material);
            if (sm.getCantidad() >= cantidad) {
                produccionDeta.setMaterialId(material);
                produccionDeta.setProduccionId(em.find(Produccion.class, Integer.parseInt(detalleProduccionUtil.getProduccionId())));
                produccionDeta.setProduccionDetaCant(new Double(cantidad));
                produccionDeta.setProduccionDetaFecha(detalleProduccionUtil.getProduccionDetaFecha());
                produccionDeta.setEstadoId(em.find(Estado.class, Integer.parseInt(detalleProduccionUtil.getEstadoId())));
                produccionDeta.setUsuarioAsignado(em.find(Usuario.class, Integer.parseInt(detalleProduccionUtil.getUsuarioId())));
                int avc = calcularAvance(produccionDeta, detalleProduccionUtil.getEstadoId());
                Produccion np = produccionDeta.getProduccionId();
                np.setAvance(avc);
                produccionEJB.setModificarProduccion(np, String.valueOf((avc == 100 ? EstadoEnum.TERMINADO.getId() : EstadoEnum.ACTIVO.getId())));
                create(produccionDeta);
                stockMaterialesEJB.setModificarStockMaterial(material, -cantidad);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean existeDetalleProduccion(String produccionDescripcion) {
        try {
            return (em.createNamedQuery("Produccion.findByProduccionDescripcion").setParameter("produccionDescripcion", produccionDescripcion).getSingleResult() != null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Producciondeta> getProduccionDetaforProduccion(Produccion p) {

        try {
            return em.createNamedQuery("Producciondeta.findByProduccion", Producciondeta.class).setParameter("produccionId", p).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean setModificarDetalleProduccion(Producciondeta produccionDeta, String usuarioId, String estadoId) {
        try {

            if (Integer.parseInt(estadoId) != EstadoEnum.TERMINADO.getId()
                    && produccionDeta.getProduccionId().getEstadoId().getEstadoId() != EstadoEnum.ACTIVO.getId()) {
                return false;
            } else {
                int avc = calcularAvance(produccionDeta, estadoId);
                Produccion np = produccionDeta.getProduccionId();
                np.setAvance(avc);
                produccionEJB.setModificarProduccion(np, String.valueOf((avc == 100 ? EstadoEnum.INACTIVO.getId() : EstadoEnum.ACTIVO.getId())));
            }
            produccionDeta.setEstadoId(em.find(Estado.class, Integer.parseInt(estadoId)));
            produccionDeta.setUsuarioAsignado(em.find(Usuario.class, Integer.parseInt(usuarioId)));
            edit(produccionDeta);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean setCrearDetalleProduccion(Producciondeta pd) {
        try {
            create(pd);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private int calcularAvance(Producciondeta produccionDeta, String estadoId) {
        boolean exist = false;
        try {
            List<Producciondeta> pd = getProduccionDetaforProduccion(produccionDeta.getProduccionId());

            if (pd != null && !pd.isEmpty()) {
                int inactives = 0;
                for (Producciondeta pdl : pd) {
                    if (Objects.equals(produccionDeta.getProduccionDetaId(), pdl.getProduccionDetaId())) {
                        exist = true;
                        if (Integer.parseInt(estadoId) == EstadoEnum.TERMINADO.getId()) {
                            inactives++;
                        }
                    } else if (pdl.getEstadoId().getEstadoId() == EstadoEnum.TERMINADO.getId()) {
                        inactives++;
                    }
                }
                return ((inactives * 100) / (exist ? pd.size() : pd.size() + 1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

}
