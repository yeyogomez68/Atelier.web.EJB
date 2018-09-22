/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.ejb.administrador;

import com.universitaria.atelier.web.jpa.AbstractFacade;
import com.universitaria.atelier.web.jpa.Color;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;

/**
 *
 * @author jeisson.gomez
 */
@Stateless
public class ColorEJB extends AbstractFacade<Color> {

    public ColorEJB() {
        super(Color.class);
    }

    public List<Color> getColores() {
        try {
            return (ArrayList<Color>) em.createNamedQuery("Color.findAll", Color.class).getResultList();
        } catch (NullPointerException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean setCrearColor(String colorDescripcion) {
        try {
            Color color = new Color();
            color.setColorDescrip(colorDescripcion);
            create(color);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean getexisteColor(String colorDescripcion) {
        try {
            return (em.createNamedQuery("Color.findByColorDescrip").setParameter("colorDescrip", colorDescripcion).getSingleResult() != null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean setModificarColor(String colorId, String nuevaDescripcion) {
        try {
            Color color = em.find(Color.class, Integer.parseInt(colorId));
            if (!nuevaDescripcion.equalsIgnoreCase(color.getColorDescrip())) {
                color.setColorDescrip(nuevaDescripcion);
                edit(color);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

      public List<SelectItem> getSelectItemColores(){
        List<SelectItem> lista = new ArrayList<>();
        try {
            for(Color col :(ArrayList<Color>) em.createNamedQuery("Color.findAll",Color.class).getResultList()){
                lista.add(new SelectItem(col.getColorId(),col.getColorDescrip()));
            }
            return lista;
        } catch (NullPointerException e){
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
        }            
        return lista;
    }
    
}
