/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.cnr.sigalm.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author javii
 */
@Entity
@Table(name = "MENU")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Menu.findAll", query = "SELECT m FROM Menu m"),
    @NamedQuery(name = "Menu.findByMnuId", query = "SELECT m FROM Menu m WHERE m.mnuId = :mnuId"),
    @NamedQuery(name = "Menu.findByMnuNombre", query = "SELECT m FROM Menu m WHERE m.mnuNombre = :mnuNombre"),
    @NamedQuery(name = "Menu.findByMnuUrl", query = "SELECT m FROM Menu m WHERE m.mnuUrl = :mnuUrl"),
    @NamedQuery(name = "Menu.findByMnuDescripcion", query = "SELECT m FROM Menu m WHERE m.mnuDescripcion = :mnuDescripcion"),
    @NamedQuery(name = "Menu.findByFecCrea", query = "SELECT m FROM Menu m WHERE m.fecCrea = :fecCrea"),
    @NamedQuery(name = "Menu.findByUsuCrea", query = "SELECT m FROM Menu m WHERE m.usuCrea = :usuCrea"),
    @NamedQuery(name = "Menu.findByFecModi", query = "SELECT m FROM Menu m WHERE m.fecModi = :fecModi"),
    @NamedQuery(name = "Menu.findByUsuModi", query = "SELECT m FROM Menu m WHERE m.usuModi = :usuModi"),
    @NamedQuery(name = "Menu.findByRegActivo", query = "SELECT m FROM Menu m WHERE m.regActivo = :regActivo")})
public class Menu implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @SequenceGenerator(name = "MENU_MNU_ID_GENERATOR", sequenceName = "SEQ_MENU", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MENU_MNU_ID_GENERATOR")
    @NotNull
    @Column(name = "MNU_ID")
    private BigDecimal mnuId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "MNU_NOMBRE")
    private String mnuNombre;
    @Size(max = 250)
    @Column(name = "MNU_URL")
    private String mnuUrl;
    @Size(max = 4000)
    @Column(name = "MNU_DESCRIPCION")
    private String mnuDescripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FEC_CREA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecCrea;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "USU_CREA")
    private String usuCrea;
    @Column(name = "FEC_MODI")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecModi;
    @Size(max = 6)
    @Column(name = "USU_MODI")
    private String usuModi;
    @Basic(optional = false)
    @NotNull
    @Column(name = "REG_ACTIVO")
    private short regActivo;
    @OneToMany(mappedBy = "mnuPadreId")
    private List<Menu> menuList;
    @JoinColumn(name = "MNU_PADRE_ID", referencedColumnName = "MNU_ID")
    @ManyToOne
    private Menu mnuPadreId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "perMnuId")
    private List<Permiso> permisoList;

    public Menu() {
    }

    public Menu(BigDecimal mnuId) {
        this.mnuId = mnuId;
    }

    public Menu(BigDecimal mnuId, String mnuNombre, Date fecCrea, String usuCrea, short regActivo) {
        this.mnuId = mnuId;
        this.mnuNombre = mnuNombre;
        this.fecCrea = fecCrea;
        this.usuCrea = usuCrea;
        this.regActivo = regActivo;
    }

    public BigDecimal getMnuId() {
        return mnuId;
    }

    public void setMnuId(BigDecimal mnuId) {
        this.mnuId = mnuId;
    }

    public String getMnuNombre() {
        return mnuNombre;
    }

    public void setMnuNombre(String mnuNombre) {
        this.mnuNombre = mnuNombre;
    }

    public String getMnuUrl() {
        return mnuUrl;
    }

    public void setMnuUrl(String mnuUrl) {
        this.mnuUrl = mnuUrl;
    }

    public String getMnuDescripcion() {
        return mnuDescripcion;
    }

    public void setMnuDescripcion(String mnuDescripcion) {
        this.mnuDescripcion = mnuDescripcion;
    }

    public Date getFecCrea() {
        return fecCrea;
    }

    public void setFecCrea(Date fecCrea) {
        this.fecCrea = fecCrea;
    }

    public String getUsuCrea() {
        return usuCrea;
    }

    public void setUsuCrea(String usuCrea) {
        this.usuCrea = usuCrea;
    }

    public Date getFecModi() {
        return fecModi;
    }

    public void setFecModi(Date fecModi) {
        this.fecModi = fecModi;
    }

    public String getUsuModi() {
        return usuModi;
    }

    public void setUsuModi(String usuModi) {
        this.usuModi = usuModi;
    }

    public short getRegActivo() {
        return regActivo;
    }

    public void setRegActivo(short regActivo) {
        this.regActivo = regActivo;
    }

    @XmlTransient
    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }

    public Menu getMnuPadreId() {
        return mnuPadreId;
    }

    public void setMnuPadreId(Menu mnuPadreId) {
        this.mnuPadreId = mnuPadreId;
    }

    @XmlTransient
    public List<Permiso> getPermisoList() {
        return permisoList;
    }

    public void setPermisoList(List<Permiso> permisoList) {
        this.permisoList = permisoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mnuId != null ? mnuId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Menu)) {
            return false;
        }
        Menu other = (Menu) object;
        if ((this.mnuId == null && other.mnuId != null) || (this.mnuId != null && !this.mnuId.equals(other.mnuId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cnr.gob.entidades.Menu[ mnuId=" + mnuId + " ]";
    }
    
}
