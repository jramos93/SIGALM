/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.cnr.sigalm.controllers;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import sv.gob.cnr.sigalm.ejbs.UsuarioFacadeLocal;
import sv.gob.cnr.sigalm.entities.Usuario;
import sv.gob.cnr.sigalm.util.Mensaje;
import sv.gob.cnr.sigalm.util.Util;

/**
 *
 * @author javii
 */
@SessionScoped
@Named
public class LoginController implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private UsuarioFacadeLocal usuarioFacadeLocal;
    private Usuario usuario;
    private Mensaje m;
    private boolean session;

    @PostConstruct
    public void init() {
        usuario = new Usuario();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Boolean getSession() {
        return session;
    }

    public void setSession(Boolean session) {
        this.session = session;
    }

    public String inciarSesion() {
        m = new Mensaje();
        String respuesta = null;
        try {
            Usuario u = usuarioFacadeLocal.login(this.usuario);
            if (u != null) {
                session = Boolean.TRUE;

                HttpSession httpSession = Util.getSession();
                HttpServletRequest request = Util.getRequest();

                httpSession.setAttribute("usuario", u);

                this.usuario.setUsrNombre(u.getUsrNombre());
                this.usuario.setUsrApellido(u.getUsrApellido());
                respuesta = "principal?faces-redirect=true"; // Navegación explicita
            } else {
                m.warn("Datos incorrectos");
            }
        } catch (Exception e) {
            m.error("Error");
        }
        return respuesta;
    }

    public String cerrarSesion() {
        String respuesta = null;
        try {
            session = Boolean.FALSE;
            this.usuario = null;
            respuesta = "index?faces-redirect=true"; // Navegación explicita
            // Invalido la sesión actual
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        } catch (Exception e) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);
        }
        return respuesta;
    }
}
