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
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import sv.gob.cnr.sigalm.ejbs.RolFacadeLocal;
import sv.gob.cnr.sigalm.entities.Rol;
import sv.gob.cnr.sigalm.entities.Usuario;
import sv.gob.cnr.sigalm.util.Mensaje;
import sv.gob.cnr.sigalm.util.Util;


/**
 *
 * @author javii
 */
@Named
@ViewScoped
public class RolController implements Serializable{
    private static final long serialVersionUID = 1L;
    /**
     * Creates a new instance of RolController
     */
    @EJB
    private RolFacadeLocal rolFacadeLocal;
    private Rol rol;
    private List<Rol> rolesList;
    private Mensaje m;
    private Boolean perAdmin=false;
    
    @PostConstruct
    public void init(){
        rol = new Rol();
        rolesList = rolFacadeLocal.findAll();
        setPermisos();
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public List<Rol> getRolesList() {
        return rolesList;
    }

    public void setRolesList(List<Rol> rolesList) {
        this.rolesList = rolesList;
    }
    
    public void nuevoRol(){
        m = new Mensaje();
        Date d = new Date();
        try {
            this.rol.setFecCrea(d);
            this.rol.setUsuCrea("Jramos");
            this.rol.setRegActivo((short)1);
            rolFacadeLocal.create(this.rol);
            m.info("Rol ingresado");
            init();
        } catch (Exception e) {
            m.error("Error en la transacción");
        }
    }
    
    public void editarRol(){
        m = new Mensaje();
        Date d = new Date();
        try {
            this.rol.setFecModi(d);
            this.rol.setUsuModi("JR");
            rolFacadeLocal.edit(this.rol);
            m.info("Rol actualizado");
            init();
        } catch (Exception e) {
            m.error("Error en la transacción");
        }
    }
    
    public void eliminarRol(){
        m = new Mensaje();
        try {
            rolFacadeLocal.remove(rol);
            m.info("Rol eliminado");
            init();
        } catch (Exception e) {
            m.error("Error en la transacción");
        }
    }

    public Boolean getPerAdmin() {
        return perAdmin;
    }

    public void setPerAdmin(Boolean perAdmin) {
        this.perAdmin = perAdmin;
    }
    
    
    public void setPermisos(){
        HttpSession httpSession = Util.getSession();
        Usuario usr = (Usuario) httpSession.getAttribute("usuario");
        if(usr.getUsrRolId().getRolNombre().equals("Administrador")){
            setPerAdmin(true);
        }
    }
}
