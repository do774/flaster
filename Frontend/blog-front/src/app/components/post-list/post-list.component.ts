import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-post-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './post-list.component.html',
  styleUrls: ['./post-list.component.css']
})
export class PostListComponent implements OnInit {
  posts: any[] = [];
  private apiUrl = 'http://localhost:8080/api/posts';
  constructor(private http: HttpClient) {}
  ngOnInit() {
    this.http.get<any[]>(this.apiUrl).subscribe(data => this.posts = data);
  }
}
