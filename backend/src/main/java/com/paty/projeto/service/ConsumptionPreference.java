/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paty.projeto.service;

import com.google.gson.annotations.SerializedName;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author abceducation
 */
  @Entity
  @Table(name = "consumptionPreference")
public class ConsumptionPreference {
       
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long idPreference;
         
        @SerializedName(value = "consumption_preference_id")
        private String id;
        private String name;
        private Double score;

        public ConsumptionPreference() {
            // compiled code
        }
        /**
    /**
     * @return the idPreference
     */
    public Long getIdPreference() {
        return idPreference;
    }

    /**
     * @param idPreference the idPreference to set
     */
    public void setIdPreference(Long idPreference) {
        this.idPreference = idPreference;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
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
     * @return the score
     */
    public Double getScore() {
        return score;
    }

    /**
     * @param score the score to set
     */
    public void setScore(Double score) {
        this.score = score;
    }

       
        
    
}
