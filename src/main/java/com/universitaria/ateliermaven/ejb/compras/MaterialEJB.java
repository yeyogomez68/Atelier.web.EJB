/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.ejb.compras;

import com.universitaria.atelier.web.jpa.AbstractFacade;
import com.universitaria.atelier.web.jpa.Marca;
import com.universitaria.atelier.web.jpa.Material;
import com.universitaria.atelier.web.jpa.Materialtipo;
import com.universitaria.atelier.web.jpa.Stockmateriales;
import com.universitaria.atelier.web.utils.MaterialRequerimientoUtil;
import com.universitaria.atelier.web.utils.MaterialUtil;
import com.universitaria.ateliermaven.ejb.inventario.StockMaterialesEJB;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;
import javax.persistence.NoResultException;

/**
 *
 * @author jeisson.gomez
 */
@Stateless
public class MaterialEJB extends AbstractFacade<Material> {

    @EJB
    private StockMaterialesEJB stockMaterialesEJB;

    public StockMaterialesEJB getStockMaterialesEJB() {
        return stockMaterialesEJB;
    }

    public void setStockMaterialesEJB(StockMaterialesEJB stockMaterialesEJB) {
        this.stockMaterialesEJB = stockMaterialesEJB;
    }

    public MaterialEJB() {
        super(Material.class);
    }

    public List<Material> getMateriales() {
        try {
            return (ArrayList<Material>) em.createNamedQuery("Material.findAll", Material.class).getResultList();
        } catch (NullPointerException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<SelectItem> getSelectItemMaterial() {
        List<SelectItem> lista = new ArrayList<>();
        try {
            for (Material mate : (ArrayList<Material>) em.createNamedQuery("Material.findAll", Material.class).getResultList()) {
                lista.add(new SelectItem(mate.getMaterialId(), mate.getMaterialNombre()));
            }
            return lista;
        } catch (NullPointerException e) {
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean setCrearMaterial(MaterialUtil material) {
        try {
            Material mate = new Material();
            mate.setMaterialNombre(material.getNombre());
            mate.setMaterialReference(material.getReferencia());
            mate.setMaterialTipoId(em.find(Materialtipo.class, Integer.valueOf(material.getTipoId())));
            mate.setMarcaId(em.find(Marca.class, Integer.valueOf(material.getMarcaId())));
            mate.setUbicacion(material.getUbicacion());
            create(mate);
            Stockmateriales sm = new Stockmateriales();
            sm.setCantidad(0);
            sm.setMaterialId(mate);
            stockMaterialesEJB.setCrearStockMaterial(sm);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean setModificarMaterial(MaterialUtil material) {
        try {
            Material mate = em.find(Material.class, Integer.parseInt(material.getMaterialId()));
            mate.setMaterialNombre(material.getNombre());
            mate.setMaterialReference(material.getReferencia());
            mate.setMaterialTipoId(em.find(Materialtipo.class, Integer.valueOf(material.getTipoId())));
            edit(mate);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<MaterialRequerimientoUtil> getMaterialesRequerimiento() {
        List<MaterialRequerimientoUtil> list = new ArrayList<>();
        try {
            for (Material mat : (ArrayList<Material>) em.createNamedQuery("Material.findAll", Material.class).getResultList()) {
                MaterialRequerimientoUtil util = new MaterialRequerimientoUtil();
                util.setMaterialId(mat.getMaterialId().toString());
                util.setNombre(mat.getMaterialNombre());
                util.setMarcaId(mat.getMarcaId().getMarcaNombre());
                util.setReferencia(mat.getMaterialReference());
                util.setTipoId(mat.getMaterialTipoId().getMaterialTipoDescript());
                util.setCantidad("0.0");
                list.add(util);
            }
            return list;
        } catch (NullPointerException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<MaterialRequerimientoUtil> getMaterialesRequerimientoCross(List<MaterialRequerimientoUtil> target) {
        List<MaterialRequerimientoUtil> list = new ArrayList<>();
        int flag = 0;
        try {
            for (Material mat : (ArrayList<Material>) em.createNamedQuery("Material.findAll", Material.class).getResultList()) {
                flag = 0;
                for (MaterialRequerimientoUtil matRq : target) {
                    if (mat.getMaterialId() == Integer.parseInt(matRq.getMaterialId())) {
                        flag++;
                    }
                }
                if (flag == 0) {
                    MaterialRequerimientoUtil util = new MaterialRequerimientoUtil();
                    util.setMaterialId(mat.getMaterialId().toString());
                    util.setNombre(mat.getMaterialNombre());
                    util.setMarcaId(mat.getMarcaId().getMarcaNombre());
                    util.setReferencia(mat.getMaterialReference());
                    util.setTipoId(mat.getMaterialTipoId().getMaterialTipoDescript());
                    util.setCantidad("0.0");
                    list.add(util);
                }

            }
            return list;
        } catch (NullPointerException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<MaterialUtil> getMaterialesUtil() {
        List<MaterialUtil> list = new ArrayList<>();
        try {
            for (Material mat : (ArrayList<Material>) em.createNamedQuery("Material.findAll", Material.class).getResultList()) {
                MaterialUtil util = new MaterialUtil();
                util.setMaterialId(mat.getMaterialId().toString());
                util.setNombre(mat.getMaterialNombre());
                util.setMarcaId(mat.getMarcaId().getMarcaNombre());
                util.setReferencia(mat.getMaterialReference());
                util.setTipoId(mat.getMaterialTipoId().getMaterialTipoDescript());
                util.setCantidad("0");
                list.add(util);
            }
            return list;
        } catch (NullPointerException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean existeMaterial(String referencia) {
        try {
            return (em.createNamedQuery("Material.findByMaterialReference").setParameter("materialReference", referencia).getSingleResult() != null);
        } catch (NoResultException nre) {
            System.out.println("com.universitaria.ateliermaven.ejb.compras.MaterialEJB.existeMaterial()");
            nre.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
