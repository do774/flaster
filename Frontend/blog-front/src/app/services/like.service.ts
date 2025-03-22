import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LikeService {
  private apiUrl = 'http://localhost:8080/api/likes';

  constructor(private http: HttpClient) {}

  likePost(postId: number): Observable<any> {
    return this.http.post(`${this.apiUrl}/${postId}`, null);
  }

  countLikes(postId: number): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/count/${postId}`);
  }
}
