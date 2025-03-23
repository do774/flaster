import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent {
  username = '';
  email = '';
  password = '';
  age: number | null = null;
  country = '';
  private apiUrl = 'http://localhost:8080/api/users/signup';
  constructor(private router: Router, private http: HttpClient) {}
  signUp() {
    this.http.post<any>(this.apiUrl, { 
      username: this.username, 
      email: this.email, 
      password: this.password, 
      age: this.age, 
      country: this.country, 
      role: 'READER' 
    }).subscribe({
      next: response => {
        this.router.navigate(['/login']);
      },
      error: err => {
        console.error('Signup failed', err);
      }
    });
  }
}
