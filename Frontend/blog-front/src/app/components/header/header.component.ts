import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {
  constructor(private router: Router) {}
  get isLoggedIn(): boolean {
    return !!localStorage.getItem('jwt');
  }
  get username(): string {
    return localStorage.getItem('username') || '';
  }
  get role(): string {
    return localStorage.getItem('role') || '';
  }
  get isAdmin(): boolean {
    return this.role === 'ADMIN';
  }
  get isAuthorOrAdmin(): boolean {
    return this.role === 'AUTHOR' || this.role === 'ADMIN';
  }
  logout(): void {
    localStorage.removeItem('jwt');
    localStorage.removeItem('username');
    localStorage.removeItem('role');
    this.router.navigate(['/login']);
  }
}
