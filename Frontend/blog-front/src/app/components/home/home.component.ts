import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  posts: any[] = [];
  isLoggedIn = false;
  private apiUrl = 'http://localhost:8080/api/posts';
  
  constructor(private http: HttpClient) {}
  
  ngOnInit(): void {
    this.isLoggedIn = !!localStorage.getItem('token');
    this.http.get<any[]>(this.apiUrl).subscribe(data => this.posts = data);
  }
  
  likePost(postId: number): void {
    if (!this.isLoggedIn) {
      alert('Please log in to like posts!');
      return;
    }
    console.log('Liking post', postId);
  }
  
  commentPost(postId: number): void {
    if (!this.isLoggedIn) {
      alert('Please log in to comment on posts!');
      return;
    }
    console.log('Commenting on post', postId);
  }
}
