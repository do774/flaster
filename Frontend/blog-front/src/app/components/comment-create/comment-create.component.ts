import { Component, Input } from '@angular/core';
import { CommentService } from '../../services/comment.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms'; 

@Component({
  selector: 'app-comment-create',
  standalone: true,
  imports: [CommonModule, FormsModule], 
  templateUrl: './comment-create.component.html'
})
export class CommentCreateComponent {
  @Input() postId!: number;
  content = '';

  constructor(private commentService: CommentService) {}

  addComment() {
    this.commentService.addComment(this.postId, this.content).subscribe(response => {
      console.log('Comment added:', response);
    });
  }
}
