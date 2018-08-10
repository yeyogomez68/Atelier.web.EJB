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
    
    public boolean getAccess (String user, String password){
        try {
//            Query q1= em.createNativeQuery("Select * from Usuario Where UsuarioEmail = ?1 And UsuarioPassword = ?2 ",Usuario.class)
//                    .setParameter(1, user)
//                    .setParameter(2, password);
            Query q1 = em.createNamedQuery("Usuario.findByLogin", Usuario.class).setParameter("usuarioEmail", user).setParameter("usuarioPassword", password);           
            return (q1.getSingleResult() != null);
        } catch(NoResultException e){
            System.out.println("UsuarioFacade.getAccess() NoResultException");
        } catch (Exception e) {
            System.out.println("UsuarioFacade.getAccess() Exception");
            e.printStackTrace();
        }
        return false;
    }
    
    public List<Usuario> getUsuarios(){
        try {
            return (ArrayList<Usuario>) em.createNamedQuery("Usuario.findAll",Usuario.class).getResultList();
        } catch (NullPointerException e){
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }       
        return null;
    }
    
    public boolean setCrearUsuario(UsuarioUtil usuarioCrear){
        try {
            Usuario usuario = new Usuario();            
            usuario.setUsuarioIdentificacion(Integer.parseInt(usuarioCrear.getIdentificacion()));
            usuario.setUsuarioNombre(usuarioCrear.getNombre());
            usuario.setUsuarioApellido(usuarioCrear.getApellido());
            usuario.setUsuarioEmail(usuarioCrear.getEmail());
            usuario.setUsuarioPassword(usuarioCrear.getPassword());
            usuario.setUsuarioDireccion(usuarioCrear.getDireccion());
            usuario.setUsuarioCel(usuarioCrear.getCelular()); 
            usuario.setEstadoId(em.find(Estado.class, Integer.parseInt(usuarioCrear.getEstadoId())));
            usuario.setRollId(em.find(Roll.class,Integer.parseInt(usuarioCrear.getRollId())));
            usuario.setCiudadId(em.find(Ciudad.class, Integer.parseInt(usuarioCrear.getCiudadId()))); 
            create(usuario);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
