import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CommentService {
  private apiUrl = 'http://localhost:8080/api/comments';

  constructor(private http: HttpClient) {}

  getCommentsForPost(postId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/${postId}`);
  }

  addComment(postId: number, content: string): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/${postId}`, content);
  }
}
