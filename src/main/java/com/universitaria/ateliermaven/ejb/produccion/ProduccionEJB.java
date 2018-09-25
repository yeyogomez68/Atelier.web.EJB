/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.ejb.produccion;

import com.universitaria.atelier.web.jpa.AbstractFacade;
import com.universitaria.atelier.web.jpa.Estado;
import com.universitaria.atelier.web.jpa.Prenda;
import com.universitaria.atelier.web.jpa.Prendamaterial;
import com.universitaria.atelier.web.jpa.Produccion;
import com.universitaria.atelier.web.jpa.Producciondeta;
import com.universitaria.atelier.web.jpa.Usuario;
import com.universitaria.atelier.web.utils.DetalleProduccionUtil;
import com.universitaria.atelier.web.utils.ProduccionUtil;
import com.universitaria.ateliermaven.ejb.constantes.EstadoEnum;
import com.universitaria.ateliermaven.ejb.inventario.StockPrendaEJB;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;
import javax.persistence.NoResultException;

/**
 *
 * @author SoulHunter
 */
@Stateless
public class ProduccionEJB extends AbstractFacade<Produccion> {

    @EJB
    private DetalleProduccionEJB detalleProduccionEJB;
    @EJB
    private StockPrendaEJB stockPrendaEJB;
    @EJB
    private DetallePrendaEJB detallePrendaEJB;

    public ProduccionEJB() {
        super(Produccion.class);
    }

    public List<Produccion> getProduccion() {
        try {
            return (ArrayList<Produccion>) em.createNamedQuery("Produccion.findAll", Produccion.class).getResultList();
        } catch (NullPointerException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<SelectItem> getSelectItemProduccion() {
        List<SelectItem> lista = new ArrayList<>();
        try {
            for (Produccion produccion : (ArrayList<Produccion>) em.createNamedQuery("Produccion.findAll", Produccion.class).getResultList()) {
                lista.add(new SelectItem(produccion.getProduccionId(), produccion.getProduccionDescripcion()));
            }
            return lista;
        } catch (NullPointerException e) {
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public String setCrearProduccion(ProduccionUtil produccionUtil) {
        String mensaje = "";
        boolean errorDetalle = false;

        try {
            Produccion produccion = new Produccion();
            Calendar cal = Calendar.getInstance();
            cal.setTime(produccionUtil.getProduccionFechaDate());
            produccion.setProduccionFecha(cal);
            produccion.setProduccionDiaEstimated(Float.parseFloat(produccionUtil.getProduccionDiaEstimated()));
            produccion.setEstadoId(em.find(Estado.class, Integer.parseInt(produccionUtil.getEstadoId())));
            produccion.setAvance(Integer.parseInt(produccionUtil.getAvance()));
            produccion.setPrendaId(em.find(Prenda.class, Integer.parseInt(produccionUtil.getPrendaId())));
            produccion.setCantidad(Integer.parseInt(produccionUtil.getCantidad()));
            Usuario usuario = em.find(Usuario.class, Integer.parseInt(produccionUtil.getUsuarioCreador()));
            produccion.setUsuarioCreador(usuario);
            produccion.setProduccionDescripcion(produccionUtil.getProduccionDescripcion());
            create(produccion);

            List<Prendamaterial> prendaMaterial = detallePrendaEJB.getMaterialesPorPrenda(produccionUtil.getPrendaId());
            if (prendaMaterial != null && !prendaMaterial.isEmpty()) {
                for (Prendamaterial pm : detallePrendaEJB.getMaterialesPorPrenda(produccionUtil.getPrendaId())) {
                    DetalleProduccionUtil dpu = new DetalleProduccionUtil();
                    dpu.setMaterialId(String.valueOf(pm.getMaterialId().getMaterialId()));
                    dpu.setProduccionDetaCant(String.valueOf(doubleRound(pm.getCantidad() * produccion.getCantidad(), 2)));
                    dpu.setProduccionDetaFecha(Calendar.getInstance().getTime());
                    dpu.setProduccionId(String.valueOf(produccion.getProduccionId()));
                    dpu.setEstadoId(String.valueOf(EstadoEnum.ACTIVO.getId()));
                    dpu.setUsuarioId(produccionUtil.getUsuarioCreador());
                    if (!detalleProduccionEJB.setCrearDetalleProduccion(dpu)) {
                        errorDetalle = true;
                    }
                }
            } else {
                remove(produccion);
                return "No se encontraron materiales asignados a esta prenda, no es posible registrar produccion";
            }
            if (errorDetalle) {
                remove(produccion);
            }
            mensaje = (errorDetalle ? "No se ha logrado crear la produccion, verifique existencia de materiales" : "Produccion registrada correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            mensaje = "Ha ocurrido un error registrando la produccion, contacte al administrador";
        }
        return mensaje;
    }

    private double doubleRound(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public boolean getexisteProduccion(String produccionDescripcion) {
        try {
            return (em.createNamedQuery("Produccion.findByProduccionDescripcion").setParameter("produccionDescripcion", produccionDescripcion).getSingleResult() != null);
        } catch (NoResultException nre) {
            System.out.println("com.universitaria.ateliermaven.ejb.produccion.ProduccionEJB.existeProduccion()");
            System.err.println(nre.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Produccion traerProduccionDes(String produccionDescripcion) {
        try {
            return (Produccion) em.createNamedQuery("Produccion.findByProduccionDescripcion").setParameter("produccionDescripcion", produccionDescripcion).getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean setModificarProduccion(Produccion produccion, String estadoId) {
        try {
            if (produccion.getEstadoId().getEstadoId() != EstadoEnum.APROBADO.getId()) {
                if (Integer.parseInt(estadoId) == EstadoEnum.INACTIVO.getId()) {
                    List<Producciondeta> pdl = detalleProduccionEJB.getProduccionDetaforProduccion(produccion);
                    if (pdl != null && !pdl.isEmpty()) {
                        for (Producciondeta pd : pdl) {
                            if (pd.getEstadoId().getEstadoId() == EstadoEnum.ACTIVO.getId()) {
                                detalleProduccionEJB.setModificarDetalleProduccion(pd, String.valueOf(pd.getUsuarioAsignado().getUsuarioId()), String.valueOf(EstadoEnum.INACTIVO.getId()));
                            }
                        }
                    }
                }

                if (Integer.parseInt(estadoId) == EstadoEnum.APROBADO.getId()) {
                    stockPrendaEJB.setModificarStockPrenda(produccion.getPrendaId(), produccion.getCantidad());
                }

                Estado estado = em.find(Estado.class, Integer.parseInt(estadoId));

                produccion.setEstadoId(estado);
                edit(produccion);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
