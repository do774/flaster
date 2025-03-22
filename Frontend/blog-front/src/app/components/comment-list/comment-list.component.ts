import { Component, Input, OnInit } from '@angular/core';
import { CommentService } from '../../services/comment.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-comment-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './comment-list.component.html'
})
export class CommentListComponent implements OnInit {
  @Input() postId!: number;
  comments: any[] = [];

  constructor(private commentService: CommentService) {}

  ngOnInit() {
    this.loadComments();
  }

  loadComments() {
    this.commentService.getCommentsForPost(this.postId).subscribe(data => {
      this.comments = data;
    });
  }
}
