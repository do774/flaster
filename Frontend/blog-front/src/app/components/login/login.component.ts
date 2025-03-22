import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  username = '';
  password = '';
  errorMessage = '';
  private apiUrl = 'http://localhost:8080/api/users';

  constructor(private router: Router, private http: HttpClient) {}

  login() {
    const basicAuth = btoa(`${this.username}:${this.password}`);
    const headers = new HttpHeaders({
      Authorization: 'Basic ' + basicAuth
    });
    this.http.get<any>(this.apiUrl, { headers }).subscribe({
      next: response => {
        localStorage.setItem('basicAuth', basicAuth);
        localStorage.setItem('username', this.username);
        this.router.navigate(['/']);
      },
      error: err => {
        this.errorMessage = 'Invalid username or password!';
        console.error('Login failed', err);
      }
    });
  }
}
