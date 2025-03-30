package blog.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.flaster.blog.model.Comment;
import com.flaster.blog.model.Post;
import com.flaster.blog.model.User;
import com.flaster.blog.repository.CommentRepository;
import com.flaster.blog.service.CommentService;

class CommentServiceTest {
    @Mock
    private CommentRepository commentRepository;
    
    @InjectMocks
    private CommentService commentService;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void testGetCommentsByPost() {
        Post post = new Post();
        post.setId(1L);
        Comment comment1 = new Comment();
        comment1.setPost(post);
        Comment comment2 = new Comment();
        comment2.setPost(post);
        when(commentRepository.findAll()).thenReturn(Arrays.asList(comment1, comment2));
        
        List<Comment> comments = commentService.getCommentsByPost(post);
        assertEquals(2, comments.size());
    }
    
    @Test
    void testCreateComment() {
        Post post = new Post();
        post.setId(1L);
        User user = new User();
        Comment comment = new Comment();
        comment.setPost(post);
        comment.setUser(user);
        comment.setContent("Test");
        when(commentRepository.save(any(Comment.class))).thenAnswer(i -> {
            Comment c = (Comment) i.getArguments()[0];
            c.setId(1L);
            c.setCreatedAt(LocalDateTime.now());
            return c;
        });
        
        Comment created = commentService.createComment(post, user, "Test");
        assertNotNull(created.getId());
        assertNotNull(created.getCreatedAt());
    }
    
    @Test
    void testDeleteComment() {
        Long commentId = 1L;
        commentService.deleteComment(commentId);
        verify(commentRepository, times(1)).deleteById(commentId);
    }
}
