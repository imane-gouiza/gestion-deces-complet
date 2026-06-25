import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule],
  templateUrl: './login.html',
  styleUrls: ['./login.css']
})
export class LoginComponent {
  username = '';
  password = '';
  selectedRole = '';
  showPassword = false;
  isLoading = false;
  errorMessage = '';

  constructor(private http: HttpClient, private router: Router) {}

  togglePassword() {
    this.showPassword = !this.showPassword;
  }

  onLogin() {
    if (!this.username || !this.password || !this.selectedRole) {
      this.errorMessage = 'Veuillez remplir tous les champs';
      return;
    }

    this.isLoading = true;
    this.errorMessage = '';

    const loginData = {
      username: this.username,
      password: this.password
    };

    this.http.post<any>('http://localhost:8080/api/auth/login', loginData)
      .subscribe({
        next: (response) => {
          this.isLoading = false;
          if (response.success) {
            // Stocker les infos
            localStorage.setItem('token', 'authenticated');
            localStorage.setItem('role', response.role);
            localStorage.setItem('username', response.username);
            localStorage.setItem('nomComplet', response.nomComplet);
            
            // Redirection selon rôle
            switch(response.role) {
              case 'MEDECIN':
                this.router.navigate(['/medecin']);
                break;
              case 'INFIRMIER':
                this.router.navigate(['/infirmier']);
                break;
              case 'ADMIN':
                this.router.navigate(['/admin']);
                break;
              case 'TRANSPORT':
                this.router.navigate(['/transport']);
                break;
              default:
                this.router.navigate(['/']);
            }
          } else {
            this.errorMessage = response.message || 'Login échoué';
          }
        },
        error: (err) => {
          this.isLoading = false;
          this.errorMessage = 'Erreur de connexion au serveur';
          console.error(err);
        }
      });
  }
}