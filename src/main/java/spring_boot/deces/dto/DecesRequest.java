package spring_boot.deces.dto;

public class DecesRequest {
    private String ipp;
    private String codeCadavre;

    // Constructeurs
    public DecesRequest() {}

    public DecesRequest(String ipp, String codeCadavre) {
        this.ipp = ipp;
        this.codeCadavre = codeCadavre;
    }

    // Getters et Setters
    public String getIpp() { return ipp; }
    public void setIpp(String ipp) { this.ipp = ipp; }

    public String getCodeCadavre() { return codeCadavre; }
    public void setCodeCadavre(String codeCadavre) { this.codeCadavre = codeCadavre; }
}