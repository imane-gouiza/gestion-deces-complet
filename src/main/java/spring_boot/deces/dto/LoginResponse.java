package spring_boot.deces.dto;

public class LoginResponse {
    private String message;
    private String role;
    private boolean success;

    // IMPORTANT : Il faut exactement ce constructeur
    public LoginResponse(String message, String role, boolean success) {
        this.message = message;
        this.role = role;
        this.success = success;
    }

    // Getters
    public String getMessage() { return message; }
    public String getRole() { return role; }
    public boolean isSuccess() { return success; }

    // Setters (Optionnels mais conseillés)
    public void setMessage(String message) { this.message = message; }
    public void setRole(String role) { this.role = role; }
    public void setSuccess(boolean success) { this.success = success; }
}