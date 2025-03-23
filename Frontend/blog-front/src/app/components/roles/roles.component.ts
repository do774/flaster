import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-roles',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './roles.component.html',
  styleUrls: ['./roles.component.css']
})
export class RolesComponent implements OnInit {
  users: any[] = [];
  private adminUrl = 'http://localhost:8080/api/admin/users';
  constructor(private http: HttpClient) {}
  ngOnInit(): void {
    this.loadUsers();
  }
  loadUsers(): void {
    this.http.get<any[]>(this.adminUrl).subscribe({
      next: data => {
        this.users = data;
      },
      error: err => console.error(err)
    });
  }
  updateRole(userId: number, role: string): void {
    this.http.put<any>(`${this.adminUrl}/${userId}/role`, { role }).subscribe({
      next: () => {
        alert(`User role updated successfully`);
        this.loadUsers();
      },
      error: err => console.error(err)
    });
  }
}
