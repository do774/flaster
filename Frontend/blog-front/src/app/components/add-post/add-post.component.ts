import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-post',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './add-post.component.html'
})
export class AddPostComponent {
  title = '';
  content = '';
  private postsUrl = 'http://localhost:8080/api/posts';

  constructor(private http: HttpClient, private router: Router) {}

  addPost(): void {
    const post = { title: this.title, content: this.content };
    this.http.post<any>(this.postsUrl, post).subscribe({
      next: () => {
        this.router.navigate(['/']);
      },
      error: err => console.error(err)
    });
  }
}
