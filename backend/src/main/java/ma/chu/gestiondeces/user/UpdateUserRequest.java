package ma.chu.gestiondeces.user;

public class UpdateUserRequest {

    private String nom;
    private String email;

    /**
     * Optionnel : si null ou vide, l'ancien mot de passe est conservé.
     */
    private String password;

    private Role role;

    public UpdateUserRequest() {
    }

    public String getNom() {
        return nom;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}