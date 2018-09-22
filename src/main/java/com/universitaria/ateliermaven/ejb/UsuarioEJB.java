/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.ejb;

import com.universitaria.atelier.web.jpa.AbstractFacade;
import com.universitaria.atelier.web.jpa.Ciudad;
import com.universitaria.atelier.web.jpa.Estado;
import com.universitaria.atelier.web.jpa.Roll;
import com.universitaria.atelier.web.jpa.Usuario;
import com.universitaria.atelier.web.utils.UsuarioUtil;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author jeisson.gomez
 */
@Stateless
public class UsuarioEJB extends AbstractFacade<Usuario> {

    public UsuarioEJB() {
        super(Usuario.class);
    }

    public Usuario getAccess(String user, String password) {
        try {
//            Query q1= em.createNativeQuery("Select * from Usuario Where UsuarioEmail = ?1 And UsuarioPassword = ?2 ",Usuario.class)
//                    .setParameter(1, user)
//                    .setParameter(2, password);
            Query q1 = em.createNamedQuery("Usuario.findByLogin", Usuario.class).setParameter("usuarioEmail", user).setParameter("usuarioPassword", password);
            return (Usuario) q1.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("UsuarioFacade.getAccess() NoResultException");
        } catch (Exception e) {
            System.out.println("UsuarioFacade.getAccess() Exception");
            e.printStackTrace();
        }
        return null;
    }

    public List<Usuario> getUsuarios() {
        try {
            return (ArrayList<Usuario>) em.createNamedQuery("Usuario.findAll", Usuario.class).getResultList();
        } catch (NullPointerException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean setCrearUsuario(UsuarioUtil usuarioCrear) {
        try {
            Usuario usuari = new Usuario();
            usuari.setUsuarioIdentificacion(Integer.parseInt(usuarioCrear.getIdentificacion()));
            usuari.setUsuarioNombre(usuarioCrear.getNombre());
            usuari.setUsuarioApellido(usuarioCrear.getApellido());
            usuari.setUsuarioEmail(usuarioCrear.getEmail());
            usuari.setUsuarioPassword(usuarioCrear.getPassword());
            usuari.setUsuarioDireccion(usuarioCrear.getDireccion());
            usuari.setUsuarioCel(usuarioCrear.getCelular());
            usuari.setEstadoId(em.find(Estado.class, Integer.parseInt(usuarioCrear.getEstadoId())));
            usuari.setRollId(em.find(Roll.class, Integer.parseInt(usuarioCrear.getRollId())));
            usuari.setCiudadId(em.find(Ciudad.class, Integer.parseInt(usuarioCrear.getCiudadId())));
            create(usuari);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<SelectItem> getSelectItemUsuarios() {
        List<SelectItem> lista = new ArrayList<>();
        try {
            for (Usuario usuario : (ArrayList<Usuario>) em.createNamedQuery("Usuario.findAll", Usuario.class).getResultList()) {
                lista.add(new SelectItem(usuario.getUsuarioId(), usuario.getUsuarioNombre() + " " + usuario.getUsuarioApellido()));
            }
            return lista;
        } catch (NullPointerException e) {
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

   public boolean getExisteUsuario(String usuarioNombre) {
        try {
            return (em.createNamedQuery("Usuario.findByUsuarioIdentificacion").setParameter("usuarioIdentificacion", usuarioNombre).getSingleResult() != null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

     
    public boolean setModificarUsuario(UsuarioUtil usuarioModificar) {
        try {
            Usuario usuari = new Usuario();
            usuari = em.find(Usuario.class,usuarioModificar.getUsuarioId());
//            usuari.setUsuarioIdentificacion(Integer.parseInt(usuarioModificar.getIdentificacion())); //No estas modificando
            usuari.setUsuarioNombre(usuarioModificar.getNombre());
            usuari.setUsuarioApellido(usuarioModificar.getApellido());
            usuari.setUsuarioEmail(usuarioModificar.getEmail());
            usuari.setUsuarioCel(usuarioModificar.getCelular()); 
            edit(usuari);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }  
    
    
    
}
