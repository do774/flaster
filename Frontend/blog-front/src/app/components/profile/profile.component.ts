import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule, FormsModule, MatIconModule],
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  user: any = {};
  isEditing = false;
  private userUrl = 'http://localhost:8080/api/users';
  constructor(private http: HttpClient) {}
  ngOnInit(): void {
    this.loadUser();
  }
  loadUser(): void {
    const username = localStorage.getItem('username');
    this.http.get<any[]>(this.userUrl).subscribe({
      next: users => { this.user = users.find(u => u.username === username) || {}; },
      error: err => console.error(err)
    });
  }
  toggleEdit(): void {
    this.isEditing = !this.isEditing;
  }
  updateProfile(): void {
    const payload = {
      username: this.user.username,
      email: this.user.email,
      country: this.user.country,
      age: this.user.age,
      image: this.user.image || null
    };
    this.http.put<any>(`${this.userUrl}/${this.user.id}`, payload).subscribe({
      next: data => {
        localStorage.setItem('username', data.username);
        localStorage.setItem('role', data.role);
        this.toggleEdit();
      },
      error: err => console.error(err)
    });
  }
  onImageSelected(event: any): void {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = () => {
        this.user.image = reader.result as string;
      };
      reader.readAsDataURL(file);
    }
  }
}
