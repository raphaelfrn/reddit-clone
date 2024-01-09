package repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import model.Post;
import model.User;
import model.Vote;

@Repository
public interface VoteRepository  extends JpaRepository<Vote, Long>{
	 Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);
}
