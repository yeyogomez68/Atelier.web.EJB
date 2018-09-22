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
public class AlquilarUtil {

    private String RentaId;

    private String DiaRenta;

    private String RentaTot;
    private String UsuarioId;
    private String ClienteId;
    private String estadoId;

    public String getRentaId() {
        return RentaId;
    }

    public void setRentaId(String RentaId) {
        this.RentaId = RentaId;
    }

    public String getDiaRenta() {
        return DiaRenta;
    }

    public void setDiaRenta(String DiaRenta) {
        this.DiaRenta = DiaRenta;
    }

    public String getRentaTot() {
        return RentaTot;
    }

    public void setRentaTot(String RentaTot) {
        this.RentaTot = RentaTot;
    }

    public String getUsuarioId() {
        return UsuarioId;
    }

    public void setUsuarioId(String UsuarioId) {
        this.UsuarioId = UsuarioId;
    }

    public String getClienteId() {
        return ClienteId;
    }

    public void setClienteId(String ClienteId) {
        this.ClienteId = ClienteId;
    }

    public String getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(String estadoId) {
        this.estadoId = estadoId;
    }

}
