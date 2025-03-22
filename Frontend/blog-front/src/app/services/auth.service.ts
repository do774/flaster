import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private router: Router) {}
  login(username: string, password: string): void {
    const token = btoa(username + ':' + password);
    localStorage.setItem('basicAuth', token);
    localStorage.setItem('username', username);
    this.router.navigate(['/']);
  }
  logout(): void {
    localStorage.removeItem('basicAuth');
    localStorage.removeItem('username');
    this.router.navigate(['/login']);
  }
  isLoggedIn(): boolean {
    return !!localStorage.getItem('basicAuth');
  }
}
