/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.ejb.constantes;

/**
 *
 * @author jeisson.gomez
 */
public enum EstadoEnum {

    ACTIVO(1),
    INACTIVO(2),
    PENDIENTE(3),
    APROBADO(4),
    RECHAZADO(5),
    EN_APROBACION(6),
    CONDICIONADO(7),
    DISPONIBLE(8),
    RESERVADO(9),
    ALQUILADO(10),
    VENDIDO(11),
    RETORNADO(12),
    COMPRAS(13),
    PATRONAJE(14),
    CORTE(15),
    ARMADO(16),
    TERMINADO(17);

    private int id;

    private EstadoEnum(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
