/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.cnr.sigalm.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.New;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import sv.gob.cnr.sigalm.ejbs.RolFacadeLocal;
import sv.gob.cnr.sigalm.ejbs.UsuarioFacadeLocal;
import sv.gob.cnr.sigalm.entities.Rol;
import sv.gob.cnr.sigalm.entities.Usuario;
import sv.gob.cnr.sigalm.util.Mensaje;

/**
 *
 * @author javii
 */

@Named
@ViewScoped
public class UsuarioController implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @EJB
    private UsuarioFacadeLocal usuarioFacadeLocal;
    @EJB
    private RolFacadeLocal rolFacadeLocal;
    
    private Usuario usuario;
    private Rol rol;
    
    private List<Usuario> usuariosList;
    private Mensaje m;
    private List<Rol> roles;
    
    @PostConstruct
    public void init(){
        usuario = new Usuario();
        rol = new Rol();
        usuariosList = usuarioFacadeLocal.findAll();
        roles = rolFacadeLocal.findAll();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Usuario> getUsuariosList() {
        return usuariosList;
    }

    public void setUsuariosList(List<Usuario> usuariosList) {
        this.usuariosList = usuariosList;
    }

    public List<Rol> getRoles() {
        return roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
    
    public void nuevoUsuario(){
        m = new Mensaje();
        Date d = new Date();
        try {
            this.usuario.setUsrRolId(this.rol);
            this.usuario.setFecCrea(d);
            this.usuario.setUsuCrea("Jramos");
            this.usuario.setRegActivo((short)1);
            usuarioFacadeLocal.create(this.usuario);
            m.info("Usuario ingresado");
            init();
        } catch (Exception e) {
            m.error("Error en la transacción");
        }
    }
    
    public void editarUsuario(){
        m = new Mensaje();
        Date d = new Date();
        try {
            this.usuario.setFecModi(d);
            this.usuario.setUsuModi("JR");
            usuarioFacadeLocal.edit(this.usuario);
            m.info("Usuario actualizado");
            init();
        } catch (Exception e) {
            m.error("Error en la transacción");
        }
    }
    
    public void eliminarUsuario(){
        m = new Mensaje();
        try {
            usuarioFacadeLocal.remove(usuario);
            m.info("Usuario eliminado");
            init();
        } catch (Exception e) {
            m.error("Error en la transacción");
        }
    }
}
