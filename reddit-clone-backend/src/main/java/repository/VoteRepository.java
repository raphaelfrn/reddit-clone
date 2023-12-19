package repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import model.Vote;

@Repository
public interface VoteRepository  extends JpaRepository<Vote, Long>{

}
