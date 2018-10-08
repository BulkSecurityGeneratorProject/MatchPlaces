package com.paty.projeto.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.paty.projeto.service.Profile;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Local.
 */
@Entity
@Table(name = "local")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Local implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Lob
    @Column(name = "photo_reference")
    private String photo_reference;

    @Column(name = "place_id")
    private String placeId;

    @Lob
    @Column(name = "comentario")
    private String comentario;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "endereco")
    private String endereco;

    @Column(name = "rate")
    private Double rate;

    @Column(name = "website")
    private String website;

    @Column(name = "bairro")
    private String bairro;

    @Lob
    @Column(name = "imagem")
    private String imagem;

    @OneToOne
    @JoinColumn(unique = true)
    private Profile profile;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "local_id", nullable = false)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnoreProperties({"local"})
    private Set<TipoLocal> tipoLocals = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "local_id", nullable = false)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnoreProperties({"local"})
    private Set<Fotos> fotos = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "local_id", nullable = false)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnoreProperties({"local"})
    private Set<Comentarios> comentarios = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Local name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Local latitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Local longitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getPhoto_reference() {
        return photo_reference;
    }

    public Local photo_reference(String photo_reference) {
        this.photo_reference = photo_reference;
        return this;
    }

    public void setPhoto_reference(String photo_reference) {
        this.photo_reference = photo_reference;
    }

    public String getPlaceId() {
        return placeId;
    }

    public Local placeId(String placeId) {
        this.placeId = placeId;
        return this;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getComentario() {
        return comentario;
    }

    public Local comentario(String comentario) {
        this.comentario = comentario;
        return this;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getTelefone() {
        return telefone;
    }

    public Local telefone(String telefone) {
        this.telefone = telefone;
        return this;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public Local endereco(String endereco) {
        this.endereco = endereco;
        return this;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Double getRate() {
        return rate;
    }

    public Local rate(Double rate) {
        this.rate = rate;
        return this;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getWebsite() {
        return website;
    }

    public Local website(String website) {
        this.website = website;
        return this;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getBairro() {
        return bairro;
    }

    public Local bairro(String bairro) {
        this.bairro = bairro;
        return this;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getImagem() {
        return imagem;
    }

    public Local imagem(String imagem) {
        this.imagem = imagem;
        return this;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public Profile getProfile() {
        return profile;
    }

    public Local profile(Profile profile) {
        this.profile = profile;
        return this;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Set<TipoLocal> getTipoLocals() {
        return tipoLocals;
    }

    public Local tipoLocals(Set<TipoLocal> tipoLocals) {
        this.tipoLocals = tipoLocals;
        return this;
    }

    public Local addTipoLocal(TipoLocal tipoLocal) {
        tipoLocals.add(tipoLocal);
        tipoLocal.setLocal(this);
        return this;
    }

    public Local removeTipoLocal(TipoLocal tipoLocal) {
        tipoLocals.remove(tipoLocal);
        tipoLocal.setLocal(null);
        return this;
    }

    public void setTipoLocals(Set<TipoLocal> tipoLocals) {
        this.tipoLocals = tipoLocals;
    }

    public Set<Fotos> getFotos() {
        return fotos;
    }

    public Local fotos(Set<Fotos> fotos) {
        this.fotos = fotos;
        return this;
    }

    public void setFotos(Set<Fotos> fotos) {
        this.fotos = fotos;
    }

    public Set<Comentarios> getComentarios() {
        return comentarios;
    }

    public Local comentarios(Set<Comentarios> comentarios) {
        this.comentarios = comentarios;
        return this;
    }

    public void setComentarios(Set<Comentarios> comentarios) {
        this.comentarios = comentarios;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Local local = (Local) o;
        if(local.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, local.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Local{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", latitude='" + latitude + "'" +
            ", longitude='" + longitude + "'" +
            ", photo_reference='" + photo_reference + "'" +
            ", placeId='" + placeId + "'" +
            ", comentario='" + comentario + "'" +
            ", telefone='" + telefone + "'" +
            ", endereco='" + endereco + "'" +
            ", rate='" + rate + "'" +
            ", website='" + website + "'" +
            ", bairro='" + bairro + "'" +
            ", imagem='" + imagem + "'" +
            '}';
    }
}
