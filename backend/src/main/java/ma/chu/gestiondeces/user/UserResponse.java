package ma.chu.gestiondeces.user;


public class UserResponse {

    private Long id;
    private String nom;
    private String email;
    private Role role;

    public UserResponse() {
    }

    public UserResponse(Long id, String nom, String email, Role role) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.role = role;
    }

    public static UserResponse fromEntity(User user) {
        return new UserResponse(
                user.getId(),
                user.getNom(),
                user.getEmail(),
                user.getRole()
        );
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
}