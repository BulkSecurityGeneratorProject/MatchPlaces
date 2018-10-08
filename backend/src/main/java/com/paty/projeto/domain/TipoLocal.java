package com.paty.projeto.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A TipoLocal.
 */
@Entity
@Table(name = "tipo_local")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TipoLocal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "tipo")
    private String tipo;

    @ManyToOne
    @JoinColumn(name = "local_id", updatable = false, insertable = false, nullable = false)
    private Local local;

    public TipoLocal() {
    }
    
    public TipoLocal(String type) {
        this.tipo(type);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public TipoLocal tipo(String tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Local getLocal() {
        return local;
    }

    public TipoLocal local(Local local) {
        this.local = local;
        return this;
    }

    public void setLocal(Local local) {
        this.local = local;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TipoLocal tipoLocal = (TipoLocal) o;
        if(tipoLocal.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, tipoLocal.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TipoLocal{" +
            "id=" + id +
            ", tipo='" + tipo + "'" +
            '}';
    }
}
