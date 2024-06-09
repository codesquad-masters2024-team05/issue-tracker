package com.CodeSquad.IssueTracker.assignee;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AssigneeRepository extends CrudRepository<Assignee, Long> {

    @Query("SELECT user_id FROM assignees WHERE issue_id = :issueId")
    List<String> findUsersByIssueId(Long issueId);
}
