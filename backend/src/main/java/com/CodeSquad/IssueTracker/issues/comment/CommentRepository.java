package com.CodeSquad.IssueTracker.issues.comment;

import com.CodeSquad.IssueTracker.issues.comment.dto.CommentResponse;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {

    @Query("select * from comments where issue_id = :issueId")
    List<CommentResponse> findByIssueId(Long issueId);

    @Modifying
    @Query("UPDATE comments SET content = :newContent WHERE comment_id = :commentId")
    void updateComment(Long commentId, String newContent);
}
