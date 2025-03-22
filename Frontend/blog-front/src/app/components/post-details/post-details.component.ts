import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-post-details',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './post-details.component.html',
  styleUrls: ['./post-details.component.css']
})
export class PostDetailsComponent implements OnInit {
  post: any = {};
  newComment = '';
  private apiUrl = 'http://localhost:8080/api/posts';
  private commentsUrl = 'http://localhost:8080/api/comments';
  postId: number = 0;
  constructor(private route: ActivatedRoute, private http: HttpClient) {}
  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      this.postId = Number(params.get('id'));
      this.http.get<any>(`${this.apiUrl}/${this.postId}`).subscribe(data => this.post = data);
      // Load comments if your backend returns them as part of the post
    });
  }
  addComment() {
    this.http.post<any>(`${this.commentsUrl}/${this.postId}`, this.newComment)
      .subscribe(response => {
        this.newComment = '';
        this.ngOnInit();
      });
  }
}
