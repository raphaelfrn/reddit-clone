package repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import model.Post;
import model.Subreddit;
import model.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
   
	  List<Post> findAllBySubreddit(Subreddit subreddit);

	    List<Post> findByUser(User user);
}
