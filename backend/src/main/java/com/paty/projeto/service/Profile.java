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
@Table(name = "profile")
public class Profile extends GenericModel {
     @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
   
    @SerializedName(value = "word_count")
    private Integer wordCount;
    @SerializedName(value = "word_count_message")
    private String wordCountMessage;
    @SerializedName(value = "processed_language")
    private String processedLanguage;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Trait> personality;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Trait> needs;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Trait> values;
    @OneToMany
    private List<Behavior> behavior;
    @OneToMany(cascade = CascadeType.ALL)
    @SerializedName(value = "consumption_preferences")
    private List<ConsumptionPreferences> consumptionPreferences;
    @OneToMany
    private List<Warning> warnings;

    public Profile() {
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
     * @return the wordCount
     */
    public Integer getWordCount() {
        return wordCount;
    }

    /**
     * @param wordCount the wordCount to set
     */
    public void setWordCount(Integer wordCount) {
        this.wordCount = wordCount;
    }

    /**
     * @return the wordCountMessage
     */
    public String getWordCountMessage() {
        return wordCountMessage;
    }

    /**
     * @param wordCountMessage the wordCountMessage to set
     */
    public void setWordCountMessage(String wordCountMessage) {
        this.wordCountMessage = wordCountMessage;
    }

    /**
     * @return the processedLanguage
     */
    public String getProcessedLanguage() {
        return processedLanguage;
    }

    /**
     * @param processedLanguage the processedLanguage to set
     */
    public void setProcessedLanguage(String processedLanguage) {
        this.processedLanguage = processedLanguage;
    }

    /**
     * @return the personality
     */
    public List<Trait> getPersonality() {
        return personality;
    }

    /**
     * @param personality the personality to set
     */
    public void setPersonality(List<Trait> personality) {
        this.personality = personality;
    }

    /**
     * @return the needs
     */
    public List<Trait> getNeeds() {
        return needs;
    }

    /**
     * @param needs the needs to set
     */
    public void setNeeds(List<Trait> needs) {
        this.needs = needs;
    }

    /**
     * @return the values
     */
    public List<Trait> getValues() {
        return values;
    }

    /**
     * @param values the values to set
     */
    public void setValues(List<Trait> values) {
        this.values = values;
    }

    /**
     * @return the behavior
     */
    public List<Behavior> getBehavior() {
        return behavior;
    }

    /**
     * @param behavior the behavior to set
     */
    public void setBehavior(List<Behavior> behavior) {
        this.behavior = behavior;
    }

    /**
     * @return the consumptionPreferences
     */
    public List<ConsumptionPreferences> getConsumptionPreferences() {
        return consumptionPreferences;
    }

    /**
     * @param consumptionPreferences the consumptionPreferences to set
     */
    public void setConsumptionPreferences(List<ConsumptionPreferences> consumptionPreferences) {
        this.consumptionPreferences = consumptionPreferences;
    }

    /**
     * @return the warnings
     */
    public List<Warning> getWarnings() {
        return warnings;
    }

    /**
     * @param warnings the warnings to set
     */
    public void setWarnings(List<Warning> warnings) {
        this.warnings = warnings;
    }
    
   
   
}
