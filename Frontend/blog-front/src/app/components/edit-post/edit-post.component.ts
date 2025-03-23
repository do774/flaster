import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-edit-post',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './edit-post.component.html'
})
export class EditPostComponent implements OnInit {
  postId: string | null = null;
  postData: any = { title: '', content: '' };
  private postsUrl = 'http://localhost:8080/api/posts';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private http: HttpClient
  ) {}

  ngOnInit(): void {
    this.postId = this.route.snapshot.paramMap.get('id');
    if (this.postId) {
      this.loadPost(this.postId);
    }
  }

  loadPost(id: string): void {
    this.http.get<any>(`${this.postsUrl}/${id}`).subscribe({
      next: data => {
        this.postData = data;
      },
      error: err => console.error(err)
    });
  }

  updatePost(): void {
    if (!this.postId) return;
    this.http.put<any>(`${this.postsUrl}/${this.postId}`, this.postData).subscribe({
      next: () => {
        this.router.navigate(['/']);
      },
      error: err => console.error(err)
    });
  }
}
