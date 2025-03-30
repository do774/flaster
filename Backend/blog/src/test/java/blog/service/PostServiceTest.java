package blog.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.flaster.blog.model.Post;
import com.flaster.blog.model.User;
import com.flaster.blog.repository.PostRepository;
import com.flaster.blog.repository.LikeRepository;
import com.flaster.blog.repository.CommentRepository;
import com.flaster.blog.service.PostService;
import com.flaster.blog.service.UserService;

class PostServiceTest {
    @Mock
    private PostRepository postRepository;
    
    @Mock
    private UserService userService;
    
    @Mock
    private LikeRepository likeRepository;
    
    @Mock
    private CommentRepository commentRepository;
    
    @InjectMocks
    private PostService postService;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void testGetAllPosts() {
        Post post1 = new Post();
        Post post2 = new Post();
        when(postRepository.findAll()).thenReturn(Arrays.asList(post1, post2));
        
        List<Post> posts = postService.getAllPosts();
        assertEquals(2, posts.size());
    }
    
    @Test
    void testCreatePost() {
        Post post = new Post();
        post.setTitle("Title");
        post.setContent("Content");
        when(postRepository.save(any(Post.class))).thenAnswer(i -> {
            Post p = (Post) i.getArguments()[0];
            p.setId(1L);
            p.setCreatedAt(LocalDateTime.now());
            p.setUpdatedAt(LocalDateTime.now());
            return p;
        });
        
        Post created = postService.createPost(post);
        assertNotNull(created.getId());
        assertNotNull(created.getCreatedAt());
        assertNotNull(created.getUpdatedAt());
    }
    
    @Test
    void testUpdatePostAuthorized() {
        User author = new User();
        author.setUsername("author");
        author.setRole("AUTHOR");
        
        Post existing = new Post();
        existing.setId(1L);
        existing.setTitle("Old Title");
        existing.setContent("Old Content");
        existing.setAuthor(author);
        
        Post updatedPost = new Post();
        updatedPost.setTitle("New Title");
        updatedPost.setContent("New Content");
        
        when(postRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(postRepository.save(any(Post.class))).thenAnswer(i -> i.getArguments()[0]);
        
        Post result = postService.updatePost(1L, updatedPost, author);
        assertEquals("New Title", result.getTitle());
        assertEquals("New Content", result.getContent());
        verify(postRepository, times(1)).save(any(Post.class));
    }
    
    @Test
    void testUpdatePostNotAuthorized() {
        User author = new User();
        author.setUsername("author");
        author.setRole("AUTHOR");
        
        Post existing = new Post();
        existing.setId(1L);
        existing.setTitle("Old Title");
        existing.setContent("Old Content");
        existing.setAuthor(author);
        
        User another = new User();
        another.setUsername("another");
        another.setRole("READER");
        
        when(postRepository.findById(1L)).thenReturn(Optional.of(existing));
        
        Post updatedPost = new Post();
        updatedPost.setTitle("New Title");
        updatedPost.setContent("New Content");
        
        assertThrows(RuntimeException.class, () -> {
            postService.updatePost(1L, updatedPost, another);
        });
    }
    
    @Test
    void testDeletePostAuthorized() {
        User author = new User();
        author.setUsername("author");
        author.setRole("AUTHOR");
        
        Post existing = new Post();
        existing.setId(1L);
        existing.setAuthor(author);
        
        when(postRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(likeRepository.findByPost(existing)).thenReturn(Arrays.asList());
        when(commentRepository.findByPost(existing)).thenReturn(Arrays.asList());
        
        postService.deletePost(1L, author);
        verify(postRepository, times(1)).delete(existing);
    }
    
    @Test
    void testFindUserByUsername() {
        User user = new User();
        user.setUsername("user");
        when(userService.findByUsername("user")).thenReturn(user);
        
        User result = postService.findUserByUsername("user");
        assertEquals("user", result.getUsername());
    }
}
