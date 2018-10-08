/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paty.projeto.web.rest.dto;

/**
 *
 * @author abceducation
 */
public class UserpessoaDTO {
 private Long idpessoa;
 
 public UserpessoaDTO(){}
    /**
     * @return the idpessoa
     */
    public Long getIdpessoa() {
        return idpessoa;
    }

    /**
     * @param idpessoa the idpessoa to set
     */
    public void setIdpessoa(Long idpessoa) {
        this.idpessoa = idpessoa;
    }
    
      @Override
    public String toString() {
        return "Pessoa{" +
            "id=" + idpessoa + '}';
    }
   
    
}
