import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  posts: any[] = [];
  private postsUrl = 'http://localhost:8080/api/posts';
  private commentsUrl = 'http://localhost:8080/api/comments';
  private likesUrl = 'http://localhost:8080/api/likes';
  constructor(private http: HttpClient, private router: Router) {}
  ngOnInit(): void {
    console.log('role:', localStorage.getItem('role'));
    this.loadPosts();
  }
  get isLoggedIn(): boolean {
    return !!localStorage.getItem('basicAuth');
  }
  get userName(): string {
    return localStorage.getItem('username') || '';
  }
  get userRole(): string {
    return localStorage.getItem('role') || '';
  }
  get isAuthorOrAdmin(): boolean {
    const role = localStorage.getItem('role');
    return role === 'AUTHOR' || role === 'ADMIN';
  }
  loadPosts(): void {
    this.http.get<any[]>(this.postsUrl).subscribe({
      next: data => {
        this.posts = data;
        this.posts.forEach(post => {
          if (!post.likeCount) post.likeCount = 0;
          if (post.userLiked === undefined) post.userLiked = false;
          if (!post.comments) post.comments = [];
          // Dodajamo property za toggling komentara
          post.showCommentBox = false;
          post.newComment = '';
          post.showAllComments = false;
          this.loadCommentsForPost(post);
          this.loadLikesForPost(post);
        });
      },
      error: err => console.error(err)
    });
  }
  loadCommentsForPost(post: any): void {
    this.http.get<any[]>(`${this.commentsUrl}/${post.id}`).subscribe({
      next: comments => {
        post.comments = comments;
      },
      error: err => console.error(err)
    });
  }
  loadLikesForPost(post: any): void {
    this.http.get<any>(`${this.likesUrl}/${post.id}`).subscribe({
      next: res => {
        post.likeCount = res.likeCount;
        post.userLiked = res.userLiked;
      },
      error: err => console.error(err)
    });
  }
  toggleLike(post: any): void {
    if (!this.isLoggedIn) {
      alert('Please log in to like posts!');
      return;
    }
    if (!post.userLiked) {
      this.http.post<any>(`${this.likesUrl}/${post.id}`, {}).subscribe({
        next: response => {
          post.likeCount = response.likeCount;
          post.userLiked = true;
        },
        error: err => {
          if (err.status === 409 && err.error && err.error.message === 'Already liked') {
            post.userLiked = true;
          } else {
            console.error(err);
          }
        }
      });
    } else {
      this.http.delete<any>(`${this.likesUrl}/${post.id}`).subscribe({
        next: response => {
          post.likeCount = response.likeCount;
          post.userLiked = false;
        },
        error: err => {
          if (err.status === 409 && err.error && err.error.message === 'Not liked yet') {
            post.userLiked = false;
          } else {
            console.error(err);
          }
        }
      });
    }
  }
  toggleComment(post: any): void {
    if (!this.isLoggedIn) {
      alert('Please log in to comment on posts!');
      return;
    }
    post.showCommentBox = !post.showCommentBox;
  }
  addComment(post: any): void {
    if (!this.isLoggedIn) {
      alert('Please log in to comment on posts!');
      return;
    }
    const content = post.newComment.trim();
    if (!content) return;
    this.http.post<any>(`${this.commentsUrl}/${post.id}`, content).subscribe({
      next: newComment => {
        if (!post.comments) post.comments = [];
        post.comments.push(newComment);
        post.newComment = '';
        post.showCommentBox = false;
      },
      error: err => console.error(err)
    });
  }
  goToAddPost(): void {
    this.router.navigate(['/add-post']);
  }
  enableEdit(post: any): void {
    post.isEditing = true;
    post.editedTitle = post.title;
    post.editedContent = post.content;
  }
  saveEdit(post: any): void {
    const updatedPost = { title: post.editedTitle, content: post.editedContent };
    this.http.put<any>(`${this.postsUrl}/${post.id}`, updatedPost).subscribe({
      next: data => {
        post.title = data.title;
        post.content = data.content;
        post.isEditing = false;
      },
      error: err => console.error(err)
    });
  }
  cancelEdit(post: any): void {
    post.isEditing = false;
  }
  deletePost(post: any): void {
    if (!confirm('Are you sure you want to delete this post?')) return;
    this.http.delete(`${this.postsUrl}/${post.id}`).subscribe({
      next: () => {
        this.posts = this.posts.filter(p => p.id !== post.id);
      },
      error: err => console.error(err)
    });
  }
}
