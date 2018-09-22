/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.atelier.web.utils;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author SoulHunter
 */
public class ProduccionUtil {

    private String produccionId;
    private Calendar produccionFecha;
    private Date produccionFechaDate;
    private String produccionDiaEstimated;
    private String estadoId;
    private String usuarioCreador;
    private String produccionDescripcion;
    private String prendaId;
    private String avance;

    public String getProduccionId() {
        return produccionId;
    }

    public void setProduccionId(String produccionId) {
        this.produccionId = produccionId;
    }

    public Calendar getProduccionFecha() {
        return produccionFecha;
    }

    public void setProduccionFecha(Calendar produccionFecha) {
        this.produccionFecha = produccionFecha;
    }

    public String getProduccionDiaEstimated() {
        return produccionDiaEstimated;
    }

    public void setProduccionDiaEstimated(String produccionDiaEstimated) {
        this.produccionDiaEstimated = produccionDiaEstimated;
    }

    public String getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(String estadoId) {
        this.estadoId = estadoId;
    }

    public String getUsuarioCreador() {
        return usuarioCreador;
    }

    public void setUsuarioCreador(String usuarioCreador) {
        this.usuarioCreador = usuarioCreador;
    }

    public String getProduccionDescripcion() {
        return produccionDescripcion;
    }

    public void setProduccionDescripcion(String produccionDescripcion) {
        this.produccionDescripcion = produccionDescripcion;
    }

    public Date getProduccionFechaDate() {
        return produccionFechaDate;
    }

    public void setProduccionFechaDate(Date produccionFechaDate) {
        this.produccionFechaDate = produccionFechaDate;
    }

    public String getAvance() {
        return avance;
    }

    public void setAvance(String avance) {
        this.avance = avance;
    }

    public String getPrendaId() {
        return prendaId;
    }

    public void setPrendaId(String prendaId) {
        this.prendaId = prendaId;
    }

}
