/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paty.projeto.service;

import com.google.gson.annotations.SerializedName;
import com.ibm.watson.developer_cloud.service.model.GenericModel;
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
@Table(name = "warning")
public class Warning extends GenericModel {

    
     @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idWarning;

    @SerializedName(value = "warning_id")
    private String id;
    private String message;

    public Warning() {
        // compiled code
    }


    
    /**
     * @return the idWarning
     */
    public Long getIdWarning() {
        return idWarning;
    }

    /**
     * @param idWarning the idWarning to set
     */
    public void setIdWarning(Long idWarning) {
        this.idWarning = idWarning;
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
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

   
}
