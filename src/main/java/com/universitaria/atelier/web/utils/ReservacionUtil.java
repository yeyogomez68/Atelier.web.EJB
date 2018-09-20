/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.atelier.web.utils;

import java.util.Date;

/**
 *
 * @author SoulHunter
 */
public class ReservacionUtil {

    private String reservacionId;
    private String usuarioReservacionId;
    private Date reservacionFecha;
    private String reservacionLimit;
    private Date reservacionLimitFecha;
    private String estadoId;
    private String usuarioId;
    private String prendaId;

    public String getReservacionId() {
        return reservacionId;
    }

    public void setReservacionId(String reservacionId) {
        this.reservacionId = reservacionId;
    }

    public String getUsuarioReservacionId() {
        return usuarioReservacionId;
    }

    public void setUsuarioReservacionId(String usuarioReservacionId) {
        this.usuarioReservacionId = usuarioReservacionId;
    }

    public Date getReservacionFecha() {
        return reservacionFecha;
    }

    public void setReservacionFecha(Date reservacionFecha) {
        this.reservacionFecha = reservacionFecha;
    }

    public String getReservacionLimit() {
        return reservacionLimit;
    }

    public void setReservacionLimit(String reservacionLimit) {
        this.reservacionLimit = reservacionLimit;
    }

    public Date getReservacionLimitFecha() {
        return reservacionLimitFecha;
    }

    public void setReservacionLimitFecha(Date reservacionLimitFecha) {
        this.reservacionLimitFecha = reservacionLimitFecha;
    }

    public String getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(String estadoId) {
        this.estadoId = estadoId;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getPrendaId() {
        return prendaId;
    }

    public void setPrendaId(String prendaId) {
        this.prendaId = prendaId;
    }

}
