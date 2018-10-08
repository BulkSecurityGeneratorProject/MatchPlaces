/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paty.projeto.service;

import com.google.gson.stream.JsonReader;
import com.ibm.watson.developer_cloud.language_translator.v2.LanguageTranslator;
import com.ibm.watson.developer_cloud.language_translator.v2.model.Language;
import com.ibm.watson.developer_cloud.personality_insights.v3.PersonalityInsights;
import com.ibm.watson.developer_cloud.personality_insights.v3.model.AcceptLanguage;
import com.ibm.watson.developer_cloud.personality_insights.v3.model.Profile;
import com.ibm.watson.developer_cloud.personality_insights.v3.model.ProfileOptions;
import java.util.ArrayList;

//import com.ibm.watson.developer_cloud.util.GsonSingleton;
//import static java.awt.SystemColor.text;
//import java.io.FileReader;
//import java.util.Arrays;
//import java.util.Date;
import org.springframework.stereotype.Service;

/**
 *
 * @author abceducation
 */
@Service
public class WatsonService {

    public String translator(String texto) {
        LanguageTranslator service = new LanguageTranslator();
        //  service.setUsernameAndPassword("c59b906c-1daf-4f16-b8b7-59df91c8b6dd", "hPJOHOiOA2J8");//paty
        //   service.setUsernameAndPassword("b06ce291-aabd-4377-ade0-1a0b9bf9b5ff", "VQoXSP32bNOJ");//pizza
        service.setUsernameAndPassword("ccfd83fe-fdcf-450b-8ecb-5c61d875c30e", "PDvbOYBYq08p");//paty II

        com.ibm.watson.developer_cloud.language_translator.v2.model.TranslationResult translationResult = service.translate(texto, Language.PORTUGUESE, Language.ENGLISH).execute();

//        TranslationResult translationResult = service.translate(texto, Language.PORTUGUESE, Language.ENGLISH).execute();
        return translationResult.getFirstTranslation();
    }

    public Profile personal(String texto) {
        PersonalityInsights service = new PersonalityInsights("2016-10-19");
////            service.setUsernameAndPassword("f2fcc585-c5e8-4e0b-b3fe-57bf6324f851", "V5wxlv2cEEyn");//paty
//            service.setUsernameAndPassword("59ebd09f-8283-472c-95eb-e26484a99efa", "0dJz7EWIMnsB");//pizza
        service.setUsernameAndPassword("e2a1a249-0017-4abf-b180-bb49770d3b17", "iBwPBRKpzKao");//pizza

        ProfileOptions options = new ProfileOptions.Builder()
                .text(texto)
                .acceptLanguage(AcceptLanguage.ENGLISH)
                //            .consumptionPreferences(true)
                //            .rawScores(true)
                .build();
        Profile profile = service.getProfile(options).execute();

        //https://www.ibm.com/watson/developercloud/personality-insights/api/v3/?java#profile
//        ProfileOptions options;
//        options = new ProfileOptions.Builder().text(texto).language(com.ibm.watson.developer_cloud.personality_insights.v2.model.Language.SPANISH).build();
//        Profile profile = service.getProfile(options).execute();
        return profile;
    }

    public com.paty.projeto.service.Profile converte(Profile profile) {
        com.paty.projeto.service.Profile profileConverte = new com.paty.projeto.service.Profile();
        profileConverte.setWordCount(profile.getWordCount());
        profileConverte.setProcessedLanguage(profile.getProcessedLanguage());
        profileConverte.setWordCountMessage(profile.getWordCountMessage());

        //// **getPersonality**//
        ArrayList personality = new ArrayList();
        for (com.ibm.watson.developer_cloud.personality_insights.v3.model.Trait trait : profile.getPersonality()) {
            Trait traitConverte = new Trait();
            traitConverte.setCategory(trait.getCategory());
            traitConverte.setName(trait.getName());
            traitConverte.setPercentile(trait.getPercentile());
            traitConverte.setRawScore(trait.getRawScore());
            traitConverte.setTraitId(trait.getTraitId());
            ArrayList children = new ArrayList();

            for (com.ibm.watson.developer_cloud.personality_insights.v3.model.Trait traitChildren : trait.getChildren()) {
                Trait tChildren = new Trait();
                tChildren.setCategory(traitChildren.getCategory());
                tChildren.setName(traitChildren.getName());
                tChildren.setPercentile(traitChildren.getPercentile());
                tChildren.setRawScore(traitChildren.getRawScore());
                tChildren.setTraitId(traitChildren.getTraitId());
                children.add(tChildren);
            }
            traitConverte.setChildren(children);
            personality.add(traitConverte);
        }

        ////  **getNeeds**//
        ArrayList needs = new ArrayList();
        for (com.ibm.watson.developer_cloud.personality_insights.v3.model.Trait trait : profile.getNeeds()) {
            Trait traitConverte = new Trait();
            traitConverte.setCategory(trait.getCategory());
            traitConverte.setName(trait.getName());
            traitConverte.setPercentile(trait.getPercentile());
            traitConverte.setRawScore(trait.getRawScore());
            traitConverte.setTraitId(trait.getTraitId());
            needs.add(traitConverte);
        }
        ////  **getValues**//
        ArrayList values = new ArrayList();
        for (com.ibm.watson.developer_cloud.personality_insights.v3.model.Trait trait : profile.getValues()) {
            Trait traitConverte = new Trait();
            traitConverte.setCategory(trait.getCategory());
            traitConverte.setName(trait.getName());
            traitConverte.setPercentile(trait.getPercentile());
            traitConverte.setRawScore(trait.getRawScore());
            traitConverte.setTraitId(trait.getTraitId());
            values.add(traitConverte);
        }

        //**getConsumptionPreferences**//
//        ArrayList consumptionPreferences=new ArrayList();
//        for (com.ibm.watson.developer_cloud.personality_insights.v3.model.ConsumptionPreferences consumption:profile.getConsumptionPreferences()){
////            Trait traitConverte =new Trait();
//            ConsumptionPreferences consumptionConvert = new ConsumptionPreferences();
//            consumptionConvert.setCategoryId(consumption.getCategoryId());
//            consumptionConvert.setName(consumption.getName());
//            
//             ArrayList consumptionChildren =new ArrayList(); 
//            
//             for(com.ibm.watson.developer_cloud.personality_insights.v3.model.ConsumptionPreferences.ConsumptionPreference ConsumptionPreferencesChildren :consumption.getConsumptionPreferences()){
//                 ConsumptionPreference cChildren =new ConsumptionPreference();
//                 cChildren.setId(ConsumptionPreferencesChildren.getId());
//                 cChildren.setName(ConsumptionPreferencesChildren.getName());
//                 cChildren.setScore(ConsumptionPreferencesChildren.getScore());
//                 consumptionChildren.add(cChildren);
//            }
//            
//            consumptionConvert.setConsumptionPreferences(consumptionChildren);
////            traitConverte.setChildren(children);
//            consumptionPreferences.add(consumptionConvert);
//        }
        profileConverte.setPersonality(personality);
        profileConverte.setNeeds(needs);
        profileConverte.setValues(values);
//            profileConverte.setValues(consumptionPreferences);

        return profileConverte;
    }

}
