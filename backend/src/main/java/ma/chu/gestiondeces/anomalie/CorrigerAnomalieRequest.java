package ma.chu.gestiondeces.anomalie;

public class  CorrigerAnomalieRequest {

    private String emailBaf;
    private String actionCorrective;
    private String nouveauCodeCadavre;

    public CorrigerAnomalieRequest() {
    }

    public String getEmailBaf() {
        return emailBaf;
    }

    public String getActionCorrective() {
        return actionCorrective;
    }

    public String getNouveauCodeCadavre() {
        return nouveauCodeCadavre;
    }

    public void setEmailBaf(String emailBaf) {
        this.emailBaf = emailBaf;
    }

    public void setActionCorrective(String actionCorrective) {
        this.actionCorrective = actionCorrective;
    }

    public void setNouveauCodeCadavre(String nouveauCodeCadavre) {
        this.nouveauCodeCadavre = nouveauCodeCadavre;
    }
}