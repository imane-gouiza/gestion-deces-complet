package ma.chu.gestiondeces.auth;

import ma.chu.gestiondeces.user.Role;

public class LoginResponse {

    private Long id;
    private String nom;
    private String email;
    private Role role;
    private String token;

    public LoginResponse(Long id, String nom, String email, Role role, String token) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.role = role;
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    public String getToken() {
        return token;
    }
}