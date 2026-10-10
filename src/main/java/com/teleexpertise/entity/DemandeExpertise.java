package com.teleexpertise.entity;

import com.teleexpertise.enums.Priorite;
import com.teleexpertise.enums.StatutDemandeExpertise;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "demandes_expertise")
public class DemandeExpertise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "consultation_id", nullable = false)
    private Long consultationId;

    @Column(name = "specialiste_id", nullable = false)
    private Long specialisteId;

    @Column(nullable = false)
    private String question;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Priorite priorite;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutDemandeExpertise statut;

    private String avis;

    private String recommandations;

    @Column(name = "date_reponse")
    private LocalDateTime dateReponse;

    @Column(name = "date_creation", nullable = false)
    private LocalDateTime dateCreation;

    public DemandeExpertise() {
    }

    public DemandeExpertise(
            Long consultationId,
            Long specialisteId,
            String question,
            Priorite priorite) {
        this.consultationId = consultationId;
        this.specialisteId = specialisteId;
        this.question = question;
        this.priorite = priorite;
        this.statut = StatutDemandeExpertise.EN_ATTENTE;
        this.dateCreation = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Long getConsultationId() {
        return consultationId;
    }

    public void setConsultationId(Long consultationId) {
        this.consultationId = consultationId;
    }

    public Long getSpecialisteId() {
        return specialisteId;
    }

    public void setSpecialisteId(Long specialisteId) {
        this.specialisteId = specialisteId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Priorite getPriorite() {
        return priorite;
    }

    public void setPriorite(Priorite priorite) {
        this.priorite = priorite;
    }

    public StatutDemandeExpertise getStatut() {
        return statut;
    }

    public void setStatut(StatutDemandeExpertise statut) {
        this.statut = statut;
    }

    public String getAvis() {
        return avis;
    }

    public void setAvis(String avis) {
        this.avis = avis;
    }

    public String getRecommandations() {
        return recommandations;
    }

    public void setRecommandations(String recommandations) {
        this.recommandations = recommandations;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public LocalDateTime getDateReponse() {
        return dateReponse;
    }

    public void setDateReponse(LocalDateTime dateReponse) {
        this.dateReponse = dateReponse;
    }

    @PrePersist
    protected void onCreate() {
        if (this.dateCreation == null) {
            this.dateCreation = LocalDateTime.now();
        }
    }

    public void repondre(String avis, String recommandations) {
        if (this.statut == StatutDemandeExpertise.TERMINEE) {
            throw new IllegalStateException("Cette demande a déjà été traitée");
        }
        this.avis = avis;
        this.recommandations = recommandations;
        this.dateReponse = LocalDateTime.now();
        this.statut = StatutDemandeExpertise.TERMINEE;
    }
}