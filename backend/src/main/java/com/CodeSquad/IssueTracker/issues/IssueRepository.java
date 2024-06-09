package com.CodeSquad.IssueTracker.issues;

import com.CodeSquad.IssueTracker.issues.dto.IssueDetailAccess;
import com.CodeSquad.IssueTracker.issues.dto.IssueListResponse;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepository extends CrudRepository<Issue, Long> {

    @Query("SELECT issue_id FROM issues WHERE is_closed = :isClosed ORDER BY issue_id DESC LIMIT :limit OFFSET :offset")
    List<Long> findIssueIds(boolean isClosed, long limit, long offset);

    @Query("SELECT i.issue_id, i.title, i.author, i.published_at, i.is_closed, i.milestone_id, " +
            "a.user_id AS assignee, l.label_id , l.label_name, l.bg_color, l.text_color " +
            "FROM issues i " +
            "LEFT JOIN assignees a ON i.issue_id = a.issue_id " +
            "LEFT JOIN issueLabel il ON i.issue_id = il.issue_id " +
            "LEFT JOIN labels l ON il.label_id = l.label_id " +
            "WHERE i.issue_id IN (:issueIds)" +
            "ORDER BY i.issue_id DESC")
    List<IssueDetailAccess> getIssueDetailByIds(List<Long> issueIds);

    @Query("SELECT * FROM issues WHERE is_closed = false ORDER BY issue_id DESC LIMIT :limit OFFSET :offset")
    List<IssueListResponse> findOpenIssues(long limit, long offset);

    @Query("SELECT * FROM issues WHERE is_closed = true ORDER BY issue_id DESC LIMIT :limit OFFSET :offset")
    List<IssueListResponse> findCloseIssues(long limit, long offset);

    @Modifying
    @Query("UPDATE issues SET is_closed = false WHERE issue_id = :issueId")
    void openIssue(long issueId);

    @Modifying
    @Query("UPDATE issues SET is_closed = true WHERE issue_id = :issueId")
    void closeIssue(long issueId);

    @Query("SELECT COUNT(issue_id) FROM issues WHERE is_closed = false")
    long countOpenIssues();

    @Query("SELECT COUNT(issue_id) FROM issues WHERE is_closed = true")
    long countCloseIssues();

    @Modifying
    @Query("UPDATE issues SET title = :title WHERE issue_id = :issueId")
    void updateIssueTitle(Long issueId, String title);

    @Modifying
    @Query("UPDATE issues SET milestone_id = :milestoneId WHERE issue_id = :issueId")
    void updateMilestoneIdForIssue(Long issueId, Long milestoneId);

    @Modifying
    @Query("UPDATE issues SET milestone_id = NULL WHERE issue_id = :issueId")
    void removeMilestoneFromIssue(Long issueId);
}
