/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.cnr.sigalm.controllers;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;
import sv.gob.cnr.sigalm.ejbs.MenuFacadeLocal;
import sv.gob.cnr.sigalm.ejbs.PermisoFacadeLocal;
import sv.gob.cnr.sigalm.entities.Menu;
import sv.gob.cnr.sigalm.entities.Permiso;
import sv.gob.cnr.sigalm.entities.Usuario;
import sv.gob.cnr.sigalm.util.Util;

/**
 *
 * @author Jramos93
 */
@Named
@SessionScoped
public class MenuController implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private MenuFacadeLocal menuFacadeLocal;
    @EJB
    private PermisoFacadeLocal permisoFacadeLocal;
    // Lista de menus
    private List<Menu> menuList;
    // Lista de permisos
    private List<Permiso> permisoList;
    // Para el menu-> ver el showcase de primefaces https://www.primefaces.org/showcase/ui/menu/menu.xhtml
    private MenuModel model;

    @PostConstruct
    public void init() {
        menuList = menuFacadeLocal.findAll();
        permisoList = permisoFacadeLocal.findAll();
        // Para el menu-> ver el showcase de primefaces https://www.primefaces.org/showcase/ui/menu/menu.xhtml
        model = new DefaultMenuModel();
        this.establecerPermisos();
    }

    public void establecerPermisos() {
        boolean menuConHijos = false;
        // Obtengo la sesion almacenada en el LoginController
        HttpSession httpSession = Util.getSession();
        Usuario usr = (Usuario) httpSession.getAttribute("usuario");
        DefaultMenuItem it = new DefaultMenuItem("Inicio");
        it.setUrl("http://localhost:8080/SIGALM/principal.xhtml");
        model.addElement(it);
        // Recorro los menus
        for (Menu m : menuList) {
            // Recorro los permisos
            for (Permiso per : permisoList) {
                // Busco si el rol logueado y el menu correspondiente están en la tabla permisos
                if ((per.getPerRolId().getRolId() == usr.getUsrRolId().getRolId())
                        && (per.getPerMnuId().getMnuId() == m.getMnuId())) {
                    // Busco los menus padres
                    if (m.getMnuPadreId() == null) {
                        DefaultSubMenu firstSubmenu = new DefaultSubMenu(m.getMnuNombre());
                        // Recorro los submenus
                        for (Menu sub : menuList) {
                            for (Permiso p : permisoList) {
                                Menu submenu = sub.getMnuPadreId();
                                if (submenu != null) {
                                    if ((p.getPerRolId().getRolId() == usr.getUsrRolId().getRolId())
                                            && (p.getPerMnuId().getMnuId() == sub.getMnuId())) {
                                        // Si es igual quiere decir que el menu padre tiene hijos
                                        if (submenu.getMnuId() == m.getMnuId()) {
                                            menuConHijos = true;
                                            DefaultMenuItem item = new DefaultMenuItem(sub.getMnuNombre());
                                            item.setUrl(sub.getMnuUrl());
                                            firstSubmenu.addElement(item);
                                        }
                                    }
                                }
                            }
                        }
                        // Si el firstSubMenu tiene hijos lo hago submenu, sino lo hago un simple item
                        if (menuConHijos) {
                            model.addElement(firstSubmenu);
                        } else {
                            DefaultMenuItem item = new DefaultMenuItem(m.getMnuNombre());
                            item.setUrl(m.getMnuUrl());
                            model.addElement(item);
                        }
                    }
                }
            }
        }
        DefaultSubMenu firstSubmenu = new DefaultSubMenu("Sesion");
        DefaultMenuItem item = new DefaultMenuItem("Cambiar Contraseña");
        firstSubmenu.addElement(item);
        item = new DefaultMenuItem("Cerrar Sesión");
        item.setCommand("#{loginController.cerrarSesion()}");
        firstSubmenu.addElement(item);
        model.addElement(firstSubmenu);
    }

    public MenuModel getModel() {
        return model;
    }

    public void setModel(MenuModel model) {
        this.model = model;
    }

}
