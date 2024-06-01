package com.CodeSquad.IssueTracker.issues;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepository extends CrudRepository<Issue, Long> {

    @Query("SELECT * FROM issues WHERE is_closed = false ORDER BY issue_id DESC LIMIT :limit OFFSET :offset")
    List<Issue> findOpenIssues(long limit, long offset);

    @Query("SELECT * FROM issues WHERE is_closed = true ORDER BY issue_id DESC LIMIT :limit OFFSET :offset")
    List<Issue> findCloseIssues(long limit, long offset);

    @Modifying
    @Query("UPDATE issues SET is_closed = false WHERE issue_id = :issueId")
    void openIssue(long issueId);

    @Modifying
    @Query("UPDATE issues SET is_closed = true WHERE issue_id = :issueId")
    void closeIssue(long issueId);

    @Modifying
    @Query("UPDATE issues SET is_closed = false WHERE issue_id IN (:issueIds)")
    void openIssues(List<Long> issueIds);

    @Modifying
    @Query("UPDATE issues SET is_closed = true WHERE issue_id IN (:issueIds)")
    void closeIssues(List<Long> issueIds);

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
