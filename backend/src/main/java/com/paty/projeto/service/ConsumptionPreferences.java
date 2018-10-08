package com.paty.projeto.service;

import com.google.gson.annotations.SerializedName;
import com.ibm.watson.developer_cloud.service.model.GenericModel;
import java.util.List;
import com.google.gson.annotations.SerializedName;
import com.ibm.watson.developer_cloud.service.model.GenericModel;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "consumptionPreferences")
public class ConsumptionPreferences extends GenericModel {

      @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
     private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    @SerializedName(value = "consumption_preferences")
    private List<ConsumptionPreference> consumptionPreferences;
    @SerializedName(value = "consumption_preference_category_id")
    private String categoryId;
    private String name;
    
    public ConsumptionPreferences(){
    }

    
    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the consumptionPreferences
     */
    public List<ConsumptionPreference> getConsumptionPreferences() {
        return consumptionPreferences;
    }

    /**
     * @param consumptionPreferences the consumptionPreferences to set
     */
    public void setConsumptionPreferences(List<ConsumptionPreference> consumptionPreferences) {
        this.consumptionPreferences = consumptionPreferences;
    }

    /**
     * @return the categoryId
     */
    public String getCategoryId() {
        return categoryId;
    }

    /**
     * @param categoryId the categoryId to set
     */
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
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
//    
   
   
}

