import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  user: any = {};
  editMode = false;
  private apiUrl = 'http://localhost:8080/api/users/profile';

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.loadProfile();
  }

  loadProfile() {
    this.http.get<any>(this.apiUrl).subscribe(data => {
      this.user = data;
    });
  }

  toggleEdit() {
    this.editMode = !this.editMode;
  }

  updateProfile() {
    this.http.put<any>(this.apiUrl, this.user).subscribe({
      next: updated => {
        this.user = updated;
        this.editMode = false;
      },
      error: err => {
        console.error('Update failed', err);
      }
    });
  }
}
