import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {
  constructor(private authService: AuthService) {}
  isLoggedIn(): boolean {
    return this.authService.isLoggedIn();
  }
  getUsername(): string {
    return localStorage.getItem('username') || '';
  }
  logout(): void {
    this.authService.logout();
  }
}
