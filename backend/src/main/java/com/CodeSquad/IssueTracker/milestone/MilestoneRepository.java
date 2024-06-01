package com.CodeSquad.IssueTracker.milestone;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MilestoneRepository extends CrudRepository<Milestone, Long> {
    @Query("SELECT * FROM milestone WHERE is_closed = 1")
    List<Milestone> findAllCloseMilestones();

    @Query("SELECT * FROM milestone WHERE is_closed = 0")
    List<Milestone> findAllOpenMilestones();

    @Query("SELECT * FROM milestone WHERE title = :title")
    Milestone findByTitle(String title);

    @Modifying
    @Query("UPDATE milestone SET total_issue = total_issue + 1 WHERE milestone_id = :milestoneId")
    void incrementTotalIssue(Long milestoneId);

    @Modifying
    @Query("UPDATE milestone SET total_issue = total_issue - 1 WHERE milestone_id = :milestoneId")
    void decrementTotalIssue(Long milestoneId);

    @Modifying
    @Query("UPDATE milestone SET closed_issue = closed_issue + 1 WHERE milestone_id = :milestoneId")
    void incrementClosedIssue(Long milestoneId);

    @Modifying
    @Query("UPDATE milestone SET closed_issue = closed_issue - 1 WHERE milestone_id = :milestoneId")
    void decrementClosedIssue(Long milestoneId);

    @Modifying
    @Query("UPDATE issues SET milestone_id = NULL WHERE milestone_id = :milestoneId")
    void deleteAllIssueReferences(Long milestoneId);
}
