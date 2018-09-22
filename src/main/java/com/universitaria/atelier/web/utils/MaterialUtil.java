/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.atelier.web.utils;

/**
 *
 * @author jeisson.gomez
 */
public class MaterialUtil {
    
    private int materialId;
    private String nombre;
    private String referencia;
    private String marcaId;
    private String tipoId;
    private String materialTipoId;

    public int getMaterialId() {
        return materialId;
    }

    public String getMaterialTipoId() {
        return materialTipoId;
    }

    public void setMaterialTipoId(String materialTipoId) {
        this.materialTipoId = materialTipoId;
    }

    public void setMaterialId(int materialId) {
        this.materialId = materialId;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getMarcaId() {
        return marcaId;
    }

    public void setMarcaId(String marcaId) {
        this.marcaId = marcaId;
    }

    public String getTipoId() {
        return tipoId;
    }

    public void setTipoId(String tipoId) {
        this.tipoId = tipoId;
    }
    
    
}
