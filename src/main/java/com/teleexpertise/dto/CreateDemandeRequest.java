package com.teleexpertise.dto;

import com.teleexpertise.enums.Priorite;

public class CreateDemandeRequest {

    private Long consultationId;
    private Long specialisteId;
    private String question;
    private Priorite priorite;

    public CreateDemandeRequest(){
    }

    public Long getConsultationId(){
        return consultationId;
    }

    public void SetConsultationId(Long consultationId){
        this.consultationId = consultationId;
    }

    public Long getSpecialisteId(){
        return specialisteId;
    }

    public void setSpecialisteId(Long  specialisteId){
        this.specialisteId = specialisteId;
    }

    public String getQuestion(){
        return question;
    }

    public void setQuestion(String question){
        this.question = question;
    }

    public Priorite getPriorite(){
        return priorite;
    }

    public void setPriorite(Priorite priorite){
        this.priorite = priorite;
    }
}
