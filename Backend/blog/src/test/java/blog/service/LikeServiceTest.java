package blog.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.flaster.blog.model.Like;
import com.flaster.blog.model.Post;
import com.flaster.blog.model.User;
import com.flaster.blog.repository.LikeRepository;
import com.flaster.blog.service.LikeService;

class LikeServiceTest {
    @Mock
    private LikeRepository likeRepository;
    
    @InjectMocks
    private LikeService likeService;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void testAddLikeWhenNotExists() {
        Post post = new Post();
        post.setId(1L);
        User user = new User();
        user.setId(1L);
        when(likeRepository.existsByPostIdAndUserId(1L, 1L)).thenReturn(false);
        when(likeRepository.save(any(Like.class))).thenAnswer(i -> {
            Like l = (Like) i.getArguments()[0];
            l.setId(1L);
            return l;
        });
        
        Like like = likeService.addLike(post, user);
        assertNotNull(like);
        assertEquals(1L, like.getPost().getId());
        verify(likeRepository, times(1)).save(any(Like.class));
    }
    
    @Test
    void testAddLikeWhenExists() {
        Post post = new Post();
        post.setId(1L);
        User user = new User();
        user.setId(1L);
        when(likeRepository.existsByPostIdAndUserId(1L, 1L)).thenReturn(true);
        
        Like like = likeService.addLike(post, user);
        assertNull(like);
    }
    
    @Test
    void testCountLikes() {
        Post post = new Post();
        post.setId(1L);
        Like like1 = new Like();
        like1.setPost(post);
        Like like2 = new Like();
        like2.setPost(post);
        when(likeRepository.findAll()).thenReturn(java.util.List.of(like1, like2));
        
        long count = likeService.countLikes(post);
        assertEquals(2, count);
    }
    
    @Test
    void testRemoveLikeWhenPresent() {
        Post post = new Post();
        post.setId(1L);
        User user = new User();
        user.setId(1L);
        Like like = new Like();
        like.setPost(post);
        like.setUser(user);
        when(likeRepository.findByPostIdAndUserId(1L, 1L)).thenReturn(Optional.of(like));
        
        boolean removed = likeService.removeLike(post, user);
        assertTrue(removed);
        verify(likeRepository, times(1)).delete(like);
    }
    
    @Test
    void testRemoveLikeWhenNotPresent() {
        Post post = new Post();
        post.setId(1L);
        User user = new User();
        user.setId(1L);
        when(likeRepository.findByPostIdAndUserId(1L, 1L)).thenReturn(Optional.empty());
        
        boolean removed = likeService.removeLike(post, user);
        assertFalse(removed);
    }
    
    @Test
    void testIsLikedByUser() {
        Post post = new Post();
        post.setId(1L);
        User user = new User();
        user.setId(1L);
        when(likeRepository.existsByPostIdAndUserId(1L, 1L)).thenReturn(true);
        
        boolean liked = likeService.isLikedByUser(post, user);
        assertTrue(liked);
    }
}
