package ma.chu.gestiondeces.anomalie;

import ma.chu.gestiondeces.user.Role;

public class CreateAnomalieRequest {

    private String codeCadavre;
    private String typeAnomalie;
    private String description;
    private Role signaleeParRole;

    public CreateAnomalieRequest() {
    }

    public String getCodeCadavre() {
        return codeCadavre;
    }

    public String getTypeAnomalie() {
        return typeAnomalie;
    }

    public String getDescription() {
        return description;
    }

    public Role getSignaleeParRole() {
        return signaleeParRole;
    }

    public void setCodeCadavre(String codeCadavre) {
        this.codeCadavre = codeCadavre;
    }

    public void setTypeAnomalie(String typeAnomalie) {
        this.typeAnomalie = typeAnomalie;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSignaleeParRole(Role signaleeParRole) {
        this.signaleeParRole = signaleeParRole;
    }
}