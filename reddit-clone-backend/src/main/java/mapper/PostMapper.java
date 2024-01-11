package mapper;

import static model.VoteType.DOWNVOTE;
import static model.VoteType.UPVOTE;

import java.util.Optional;

import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.mapstruct.Mapping;

import com.github.marlonlom.utilities.timeago.TimeAgo;

import dto.PostRequest;
import dto.PostResponse;
import model.Post;
import model.Subreddit;
import model.User;
import model.Vote;
import model.VoteType;
import repository.CommentRepository;
import repository.VoteRepository;
import service.AuthService;

@Mapper(componentModel = "spring")
public abstract class PostMapper {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private AuthService authService;

   
    @Mappings({
        @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())"),
        @Mapping(target = "description", source = "postRequest.description"),
        @Mapping(target = "subreddit", source = "subreddit"),
        @Mapping(target = "voteCount", constant = "0"),
        @Mapping(target = "user", source = "user")
    })
    public abstract Post map(PostRequest postRequest, Subreddit subreddit, User user);

    @Mappings({
        @Mapping(target = "id", source = "postId"),
        @Mapping(target = "subredditName", source = "subreddit.name"),
        @Mapping(target = "userName", source = "user.username"),
        @Mapping(target = "commentCount", expression = "java(commentCount(post))"),
        @Mapping(target = "duration", expression = "java(getDuration(post))"),
        @Mapping(target = "upVote", expression = "java(isPostUpVoted(post))"),
        @Mapping(target = "downVote", expression = "java(isPostDownVoted(post))")
    })
    public abstract PostResponse mapToDto(Post post);


    Integer commentCount(Post post) {
        return commentRepository.findByPost(post).size();
    }

    String getDuration(Post post) {
        return TimeAgo.using(post.getCreatedDate().toEpochMilli());
    }

    boolean isPostUpVoted(Post post) {
        return checkVoteType(post, UPVOTE);
    }

    boolean isPostDownVoted(Post post) {
        return checkVoteType(post, DOWNVOTE);
    }

    private boolean checkVoteType(Post post, VoteType voteType) {
        if (authService.isLoggedIn()) {
            Optional<Vote> voteForPostByUser =
                    voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post,
                            authService.getCurrentUser());
            return voteForPostByUser.filter(vote -> vote.getVoteType().equals(voteType))
                    .isPresent();
        }
        return false;
    }

}