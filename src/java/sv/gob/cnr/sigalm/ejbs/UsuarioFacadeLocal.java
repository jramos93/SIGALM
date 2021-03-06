/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.cnr.sigalm.ejbs;

import java.util.List;
import javax.ejb.Local;
import sv.gob.cnr.sigalm.entities.Rol;
import sv.gob.cnr.sigalm.entities.Usuario;

/**
 *
 * @author javii
 */
@Local
public interface UsuarioFacadeLocal {

    void create(Usuario usuario);

    void edit(Usuario usuario);

    void remove(Usuario usuario);

    Usuario find(Object id);

    List<Usuario> findAll();

    List<Usuario> findRange(int[] range);

    int count();
    
    List<Rol> listAllRoles();
    
    Usuario login(Usuario u);
}
