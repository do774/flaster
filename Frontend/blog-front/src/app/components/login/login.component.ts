import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { jwtDecode }from 'jwt-decode';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  username: string = '';
  password: string = '';
  errorMessage: string = '';
  
  constructor(private authService: AuthService, private router: Router) { }
  
  login() {
    this.authService.login({ username: this.username, password: this.password })
    .subscribe({
      next: (response: any) => {
        const token = response.token;
        localStorage.setItem('jwt', token);
        const decoded: any = jwtDecode(token);
        localStorage.setItem('role', decoded.role);
        localStorage.setItem('username', this.username);
        this.router.navigate(['/']);
      },
      error: err => {
        this.errorMessage = 'Neispravni korisnički podaci';
      }
    });
  }
}
