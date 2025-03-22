import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, FormsModule, MatIconModule],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  posts: any[] = [];
  private postsUrl = 'http://localhost:8080/api/posts';
  private commentsUrl = 'http://localhost:8080/api/comments';
  private likesUrl = 'http://localhost:8080/api/likes';

  showAllPosts = false;

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.loadPosts();
  }

  get isLoggedIn(): boolean {
    return !!localStorage.getItem('basicAuth');
  }

  get displayedPosts(): any[] {
    return this.showAllPosts ? this.posts : this.posts.slice(0, 3);
  }

  toggleShowAllPosts(): void {
    this.showAllPosts = !this.showAllPosts;
  }

  loadPosts(): void {
    this.http.get<any[]>(this.postsUrl).subscribe({
      next: data => {
        this.posts = data;
        this.posts.forEach(post => {
          if (!post.likeCount) post.likeCount = 0;
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
    if (post.showCommentBox && post.newComment.trim() === '') {
      post.showCommentBox = false;
    } else {
      post.showCommentBox = true;
    }
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

  toggleShowAllComments(post: any): void {
    post.showAllComments = !post.showAllComments;
  }

  displayedComments(post: any): any[] {
    if (!post.comments) return [];
    return post.showAllComments ? post.comments : post.comments.slice(0, 3);
  }
}
