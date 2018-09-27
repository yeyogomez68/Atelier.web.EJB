/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.ejb.alquilerventas;

import com.universitaria.atelier.web.jpa.AbstractFacade;
import com.universitaria.atelier.web.jpa.Estado;
import com.universitaria.atelier.web.jpa.Prenda;
import com.universitaria.atelier.web.jpa.Renta;
import com.universitaria.atelier.web.jpa.Rentadeta;
import com.universitaria.atelier.web.jpa.Reservacion;
import com.universitaria.ateliermaven.ejb.constantes.EstadoEnum;
import com.universitaria.ateliermaven.ejb.inventario.StockPrendaEJB;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author SoulHunter
 */
@Stateless
public class DetalleRentaEJB extends AbstractFacade<Rentadeta> {

    @EJB
    private StockPrendaEJB stockPrendaEJB;

    private final String pathImages = "c:/atelier/imagenes/renta/";

    public DetalleRentaEJB() {
        super(Rentadeta.class);
    }

    public List<Rentadeta> getRentaDetalle() {
        try {
            return (ArrayList<Rentadeta>) em.createNamedQuery("Rentadeta.findAll", Rentadeta.class).getResultList();
        } catch (NullPointerException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Rentadeta> getRentaDetalleRenta(Renta renta) {
        try {
            return (ArrayList<Rentadeta>) em.createNamedQuery("Rentadeta.findByRentaIdEstadoId", Rentadeta.class).setParameter("rentaId", renta).setParameter("estadoId", em.find(Estado.class, EstadoEnum.ALQUILADO.getId())).getResultList();
        } catch (NullPointerException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean setCrearDetalleRentaReservacion(Renta renta, Reservacion reservacion, File file) {
        try {
            Rentadeta rd = new Rentadeta();
            rd.setEstadoId(renta.getEstadoId());
            rd.setPrendaId(reservacion.getPrendaId());
            rd.setRentaDetaFecha(renta.getRentaIdFecha());
            rd.setRentaDetaReinEstadomentFecha(renta.getRentaReinEstadomentFecha());
            rd.setRentaId(renta);
            rd.setRentaVu(renta.getRentaTot());
            rd.setReservacionId(reservacion);
            create(rd);

            if (!crearArchivo(file, new File(pathImages, rd.getRentaDetaId() + "_" + file.getName()))) {
                remove(rd);
                return false;
            }

            return true;
        } catch (Exception e) {
        }
        return false;
    }

    public boolean setCrearDetalleRentaReservacion(Renta renta, Reservacion reservacion) {
        try {
            Rentadeta rd = new Rentadeta();
            rd.setEstadoId(renta.getEstadoId());
            rd.setPrendaId(reservacion.getPrendaId());
            rd.setRentaDetaFecha(renta.getRentaIdFecha());
            rd.setRentaDetaReinEstadomentFecha(renta.getRentaReinEstadomentFecha());
            rd.setRentaId(renta);
            rd.setRentaVu(renta.getRentaTot());
            rd.setReservacionId(reservacion);
            create(rd);
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    public boolean setCrearDetalleRenta(Renta renta, Prenda prenda, int vu, File file) {
        try {
            Rentadeta rd = new Rentadeta();
            rd.setEstadoId(renta.getEstadoId());
            rd.setPrendaId(prenda);
            rd.setRentaDetaFecha(renta.getRentaIdFecha());
            rd.setRentaDetaReinEstadomentFecha(renta.getRentaReinEstadomentFecha());
            rd.setRentaId(renta);
            rd.setRentaVu(vu);
            rd.setReservacionId(null);
            create(rd);
            stockPrendaEJB.setModificarStockPrenda(prenda, -1);

            if (!crearArchivo(file, new File(pathImages, rd.getRentaDetaId() + "_" + file.getName()))) {
                remove(rd);
                stockPrendaEJB.setModificarStockPrenda(prenda, 1);
                return false;
            }
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    public boolean setCrearDetalleRenta(Renta renta, Prenda prenda, int vu) {
        try {
            Rentadeta rd = new Rentadeta();
            rd.setEstadoId(renta.getEstadoId());
            rd.setPrendaId(prenda);
            rd.setRentaDetaFecha(renta.getRentaIdFecha());
            rd.setRentaDetaReinEstadomentFecha(renta.getRentaReinEstadomentFecha());
            rd.setRentaId(renta);
            rd.setRentaVu(vu);
            rd.setReservacionId(null);
            create(rd);
            stockPrendaEJB.setModificarStockPrenda(prenda, -1);
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    public boolean reintegrarPrenda(Rentadeta renta) {
        try {
            renta.setEstadoId(em.find(Estado.class, EstadoEnum.RETORNADO.getId()));
            edit(renta);
            if (stockPrendaEJB.setModificarStockPrenda(renta.getPrendaId(), 1)) {
                return true;
            } else {
                renta.setEstadoId(em.find(Estado.class, EstadoEnum.ALQUILADO.getId()));
                edit(renta);
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean crearArchivo(File sourceFile, File destFile) throws IOException {

        File fp = new File(destFile.getPath());

        if (!destFile.exists()) {
            destFile.createNewFile();
        }
        FileChannel origen = null;
        FileChannel destino = null;
        try {
            origen = new FileInputStream(sourceFile).getChannel();
            destino = new FileOutputStream(destFile).getChannel();

            long count = 0;
            long size = origen.size();
            while ((count += destino.transferFrom(origen, count, size - count)) < size);
            sourceFile.delete();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (origen != null) {
                origen.close();
            }
            if (destino != null) {
                destino.close();
            }
        }
        return false;
    }

    public File traerImagenesPorRenta(Rentadeta rentadeta) {
        File images = null;
        try {
            File f = new File(pathImages);
            for (String name : f.list()) {
                String ex = name.substring(name.indexOf("."));
                String namef = rentadeta.getRentaDetaId() + "_" + rentadeta.getPrendaId().getPrendaNombre() + ex;
                System.out.println("com.universitaria.ateliermaven.ejb.alquilerventas.DetalleRentaEJB.traerImagenesPorRenta()" + namef);
                if (name.equals(namef)) {
                    images = new File(pathImages + name);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return images;
    }

}
