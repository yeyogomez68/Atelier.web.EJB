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
import com.universitaria.atelier.web.jpa.Reservacion;
import com.universitaria.atelier.web.jpa.Usuario;
import com.universitaria.atelier.web.utils.AlquilarUtil;
import com.universitaria.ateliermaven.ejb.ClienteEJB;
import com.universitaria.atelier.web.utils.PrendaUtil;
import com.universitaria.ateliermaven.ejb.constantes.EstadoEnum;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author SoulHunter
 */
@Stateless
public class RentaEJB extends AbstractFacade<Renta> {

    @EJB
    private DetalleRentaEJB detalleRentaEJB;
    @EJB
    private ClienteEJB clienteEJB;

    public RentaEJB() {
        super(Renta.class);
    }

    public List<Renta> getRentas() {
        try {
            return (ArrayList<Renta>) em.createNamedQuery("Renta.findAll", Renta.class).getResultList();
        } catch (NullPointerException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Renta> getRentasActivas() {
        try {
            return (ArrayList<Renta>) em.createNamedQuery("Renta.findByRentaEstadoId", Renta.class).setParameter("estadoId", em.find(Estado.class, EstadoEnum.ALQUILADO.getId())).getResultList();
        } catch (NullPointerException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean reintegrarRenta(Renta renta) {
        try {
            renta.setEstadoId(em.find(Estado.class, EstadoEnum.RETORNADO.getId()));
            edit(renta);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean setCrearRentaReservacion(Renta renta, Reservacion reservacion) {
        try {
            System.out.println("com.universitaria.ateliermaven.ejb.alquilerventas.RentaEJB.setCrearRentaReservacion()");
            create(renta);
            if (detalleRentaEJB.setCrearDetalleRentaReservacion(renta, reservacion)) {
                return true;
            } else {
                remove(renta);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean setCrearRentaReservacion(Renta renta, Reservacion reservacion, List<File> listadeArchivos) {
        try {
            create(renta);
            File fileImage = null;
            if (listadeArchivos != null && !listadeArchivos.isEmpty()) {
                for (File f : listadeArchivos) {
                    String ex = f.getName().substring(f.getName().indexOf("."));
                    if (f.getName().equals(reservacion.getPrendaId().getPrendaNombre() + ex)) {
                        fileImage = f;
                    }
                }

            }
            if (detalleRentaEJB.setCrearDetalleRentaReservacion(renta, reservacion, fileImage)) {
                return true;
            } else {
                remove(renta);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean setCrearRenta(List<PrendaUtil> prenda, AlquilarUtil alquilar, Usuario usuario, List<File> listadeArchivos) {
        try {
            Renta r = new Renta();
            r.setClienteId(clienteEJB.clientePorIdentificacion(alquilar.getClienteId()));
            Calendar cal = Calendar.getInstance();
            r.setDiaRenta(Integer.parseInt(alquilar.getDiaRenta()));
            r.setEstadoId(em.find(Estado.class, EstadoEnum.ALQUILADO.getId()));
            r.setRentaIdFecha(cal);
            Calendar cal2 = Calendar.getInstance();
            cal2.add(Calendar.DATE, Integer.parseInt(alquilar.getDiaRenta()));
            r.setRentaReinEstadomentFecha(cal2);
            int rentaTotal = 0;
            for (PrendaUtil p : prenda) {
                rentaTotal += Integer.parseInt(p.getValor());
            }
            r.setRentaTot(rentaTotal);
            r.setUsuarioId(usuario);
            create(r);
            for (PrendaUtil p : prenda) {
                File fileImage = null;

                for (File f : listadeArchivos) {
                    String ex = f.getName().substring(f.getName().indexOf("."));
                    if (f.getName().equals(p.getPrendaNombre() + ex)) {
                        fileImage = f;
                    }
                }

                if (!detalleRentaEJB.setCrearDetalleRenta(r, em.find(Prenda.class, Integer.parseInt(p.getPrendaId())), Integer.parseInt(p.getValor()), fileImage)) {
                    remove(r);
                    return false;
                }

            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public InputStream getStreamImages(String img) {

        try {
            File f = new File(img);
            if (f.exists()) {
                System.out.println("com.universitaria.ateliermaven.ejb.alquilerventas.RentaEJB.getStreamImages()" + "Si existe");
            }
            return new FileInputStream(f);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RentaEJB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    public boolean setCrearVenta(List<PrendaUtil> prenda, AlquilarUtil alquilar, Usuario usuario) {
        try {
            Renta r = new Renta();
            r.setClienteId(clienteEJB.clientePorIdentificacion(alquilar.getClienteId()));
            Calendar cal = Calendar.getInstance();
            r.setEstadoId(em.find(Estado.class, EstadoEnum.VENDIDO.getId()));
            r.setRentaIdFecha(cal);

            int rentaTotal = 0;
            for (PrendaUtil p : prenda) {
                rentaTotal += Integer.parseInt(p.getValor());
            }
            r.setRentaTot(rentaTotal);
            r.setUsuarioId(usuario);
            create(r);
            for (PrendaUtil p : prenda) {

                if (!detalleRentaEJB.setCrearDetalleRenta(r, em.find(Prenda.class, Integer.parseInt(p.getPrendaId())), Integer.parseInt(p.getValor()))) {
                    remove(r);
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
