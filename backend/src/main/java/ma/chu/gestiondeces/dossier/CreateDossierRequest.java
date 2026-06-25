package ma.chu.gestiondeces.dossier;

public class CreateDossierRequest {

    private String ipp;
    private String codeCadavre;
    private String emailAgentBaf;

    public CreateDossierRequest() {
    }

    public String getIpp() {
        return ipp;
    }

    public String getCodeCadavre() {
        return codeCadavre;
    }

    public String getEmailAgentBaf() {
        return emailAgentBaf;
    }

    public void setIpp(String ipp) {
        this.ipp = ipp;
    }

    public void setCodeCadavre(String codeCadavre) {
        this.codeCadavre = codeCadavre;
    }

    public void setEmailAgentBaf(String emailAgentBaf) {
        this.emailAgentBaf = emailAgentBaf;
    }
}