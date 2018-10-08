package com.paty.projeto.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Comentarios.
 */
@Entity
@Table(name = "comentarios")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Comentarios implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "autor")
    private String autor;

    @Column(name = "url")
    private String url;

    @Lob
    @Column(name = "texto")
    private String texto;

    @Column(name = "rate")
    private Double rate;

    @ManyToOne
    @JoinColumn(name = "local_id", updatable = false, insertable = false, nullable = false)
    private Local local;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAutor() {
        return autor;
    }

    public Comentarios autor(String autor) {
        this.autor = autor;
        return this;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getUrl() {
        return url;
    }

    public Comentarios url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTexto() {
        return texto;
    }

    public Comentarios texto(String texto) {
        this.texto = texto;
        return this;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Double getRate() {
        return rate;
    }

    public Comentarios rate(Double rate) {
        this.rate = rate;
        return this;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Local getLocal() {
        return local;
    }

    public Comentarios local(Local local) {
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
        Comentarios comentarios = (Comentarios) o;
        if(comentarios.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, comentarios.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Comentarios{" +
            "id=" + id +
            ", autor='" + autor + "'" +
            ", url='" + url + "'" +
            ", texto='" + texto + "'" +
            ", rate='" + rate + "'" +
            '}';
    }
}
