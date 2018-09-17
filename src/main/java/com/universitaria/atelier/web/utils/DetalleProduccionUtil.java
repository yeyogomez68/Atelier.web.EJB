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
public class DetalleProduccionUtil {

    private String produccionDetaId;
    private String materialId;
    private String produccionDetaCant;
    private Date produccionDetaFecha;
    private String produccionId;
    private String usuarioId;
    private String estadoId;

    public String getProduccionDetaId() {
        return produccionDetaId;
    }

    public void setProduccionDetaId(String produccionDetaId) {
        this.produccionDetaId = produccionDetaId;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getProduccionDetaCant() {
        return produccionDetaCant;
    }

    public void setProduccionDetaCant(String produccionDetaCant) {
        this.produccionDetaCant = produccionDetaCant;
    }

    public Date getProduccionDetaFecha() {
        return produccionDetaFecha;
    }

    public void setProduccionDetaFecha(Date produccionDetaFecha) {
        this.produccionDetaFecha = produccionDetaFecha;
    }

    public String getProduccionId() {
        return produccionId;
    }

    public void setProduccionId(String produccionId) {
        this.produccionId = produccionId;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(String estadoId) {
        this.estadoId = estadoId;
    }

}
