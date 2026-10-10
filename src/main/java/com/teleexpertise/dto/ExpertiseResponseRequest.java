package com.teleexpertise.dto;

public class ExpertiseResponseRequest {

    private String avis;
    private String recommandations;

    // 1. Mandatory no-argument constructor for Jackson deserialization
    public ExpertiseResponseRequest() {
    }

    public ExpertiseResponseRequest(String avis, String recommandations) {
        this.avis = avis;
        this.recommandations = recommandations;
    }

    // 2. Standard JavaBean getters and setters (lowercase 'set' prefix)
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
}
