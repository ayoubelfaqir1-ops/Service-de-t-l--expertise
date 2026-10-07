package com.teleexpertise.entity;


import com.teleexpertise.enums.Specialite;
import jakarta.persistence.*;

@Entity
@Table(name = "specialistes")
public class Specialiste {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "utilisateur_id", nullable = false)
    private Long utilisateurId;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Specialite specialite;

    @Column(nullable = false)
    private double tarif;

    public Specialiste(){

    }

    public Specialiste(Long utilisateurId, Specialite specialite, double tarif){
        this.utilisateurId = utilisateurId;
        this.specialite = specialite;
        this.tarif = tarif;
    }

    public Long getId() {
        return id;
    }

    public Long getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(Long utilisateurId) {
        this.utilisateurId = utilisateurId;
    }

    public Specialite getSpecialite() {
        return specialite;
    }

    public void setSpecialite(Specialite specialite) {
        this.specialite = specialite;
    }

    public double getTarif() {
        return tarif;
    }

    public void setTarif(double tarif) {
        this.tarif = tarif;
    }


}
