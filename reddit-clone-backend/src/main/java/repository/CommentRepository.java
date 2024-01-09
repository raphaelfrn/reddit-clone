package repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import model.Comment;
import model.Post;
import model.User;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
	
	  List<Comment> findByPost(Post post);

	    List<Comment> findAllByUser(User user);

}
