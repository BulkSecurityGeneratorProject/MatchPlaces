package com.paty.projeto.domain;

import com.paty.projeto.service.Profile;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Pessoa.
 */
@Entity
@Table(name = "pessoa")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Pessoa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "id_twitter")
    private String idTwitter;

    @Column(name = "token_twitter")
    private String tokenTwitter;

    @Column(name = "secret_twitter")
    private String secretTwitter;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(unique = true)
    private Profile profile;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdTwitter() {
        return idTwitter;
    }

    public Pessoa idTwitter(String idTwitter) {
        this.idTwitter = idTwitter;
        return this;
    }

    public void setIdTwitter(String idTwitter) {
        this.idTwitter = idTwitter;
    }

    public String getTokenTwitter() {
        return tokenTwitter;
    }

    public Pessoa tokenTwitter(String tokenTwitter) {
        this.tokenTwitter = tokenTwitter;
        return this;
    }

    public void setTokenTwitter(String tokenTwitter) {
        this.tokenTwitter = tokenTwitter;
    }

    public String getSecretTwitter() {
        return secretTwitter;
    }

    public Pessoa secretTwitter(String secretTwitter) {
        this.secretTwitter = secretTwitter;
        return this;
    }

    public void setSecretTwitter(String secretTwitter) {
        this.secretTwitter = secretTwitter;
    }

    public Profile getProfile() {
        return profile;
    }

    public Pessoa profile(Profile profile) {
        this.profile = profile;
        return this;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pessoa pessoa = (Pessoa) o;
        if(pessoa.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, pessoa.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Pessoa{" +
            "id=" + id +
            ", idTwitter='" + idTwitter + "'" +
            ", tokenTwitter='" + tokenTwitter + "'" +
            ", secretTwitter='" + secretTwitter + "'" +
            '}';
    }
}
