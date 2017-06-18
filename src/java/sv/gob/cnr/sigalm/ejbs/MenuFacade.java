/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.cnr.sigalm.ejbs;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import sv.gob.cnr.sigalm.entities.Menu;

/**
 *
 * @author javii
 */
@Stateless
public class MenuFacade extends AbstractFacade<Menu> implements MenuFacadeLocal {
    @PersistenceContext(unitName = "SIGALMPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MenuFacade() {
        super(Menu.class);
    }
    
}
