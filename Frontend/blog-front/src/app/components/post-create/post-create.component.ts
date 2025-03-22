import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-post-create',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './post-create.component.html',
  styleUrls: ['./post-create.component.css']
})
export class PostCreateComponent {
  title = '';
  content = '';
  private apiUrl = 'http://localhost:8080/api/posts';
  constructor(private router: Router, private http: HttpClient) {}
  createPost() {
    this.http.post<any>(this.apiUrl, { title: this.title, content: this.content })
      .subscribe({
        next: response => this.router.navigate(['/posts']),
        error: err => console.error('Error creating post', err)
      });
  }
}
