/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paty.projeto.service;

import com.google.gson.annotations.SerializedName;
import com.ibm.watson.developer_cloud.service.model.GenericModel;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Trait")
public class Trait extends GenericModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
     
     @SerializedName(value = "trait_id")
    private String traitId;
    private String name;
    private String category;
    private Double percentile;
    @SerializedName(value = "raw_score")
    private Double rawScore;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Trait> children;

    public Trait() {
    }
    /**
     * @return the traitId
     */
    public String getTraitId() {
        return traitId;
    }

    /**
     * @param traitId the traitId to set
     */
    public void setTraitId(String traitId) {
        this.traitId = traitId;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * @return the percentile
     */
    public Double getPercentile() {
        return percentile;
    }

    /**
     * @param percentile the percentile to set
     */
    public void setPercentile(Double percentile) {
        this.percentile = percentile;
    }

    /**
     * @return the rawScore
     */
    public Double getRawScore() {
        return rawScore;
    }

    /**
     * @param rawScore the rawScore to set
     */
    public void setRawScore(Double rawScore) {
        this.rawScore = rawScore;
    }

    /**
     * @return the children
     */
    public List<Trait> getChildren() {
        return children;
    }

    /**
     * @param children the children to set
     */
    public void setChildren(List<Trait> children) {
        this.children = children;
    }

   

   
}
