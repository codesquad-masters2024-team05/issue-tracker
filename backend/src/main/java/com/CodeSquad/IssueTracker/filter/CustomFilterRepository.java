package com.CodeSquad.IssueTracker.filter;

import com.CodeSquad.IssueTracker.issues.Issue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class CustomFilterRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CustomFilterRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Issue> findFilteredIssues(Boolean isClosed, String assignee, List<String> labels, String milestone, String author) {
        StringBuilder sql = new StringBuilder("SELECT i.* FROM issues i ");

        if(assignee != null && !assignee.isEmpty()) {
            sql.append("JOIN assignees a ON i.issue_id = a.issue_id ");
        }

        if (labels != null && !labels.isEmpty()) {
            sql.append("JOIN issueLabel il ON i.issue_id = il.issue_id ")
                    .append("JOIN labels l ON il.label_id = l.label_id ");
        }

        if (milestone != null && !milestone.isEmpty()) {
            sql.append("JOIN milestone m ON i.milestone_id = m.milestone_id ");
        }

        sql.append("WHERE i.is_closed = ? ");

        List<Object> params = new ArrayList<>();
        params.add(isClosed);

        if (assignee != null && !assignee.isEmpty()) {
            sql.append("AND a.user_id = ? ");
            params.add(assignee);
        }

        if (milestone != null && !milestone.isEmpty()) {
            sql.append("AND m.title = ? ");
            params.add(milestone);
        }

        if (author != null && !author.isEmpty()) {
            sql.append("AND i.author = ? ");
            params.add(author);
        }

        if (labels != null && !labels.isEmpty()) {
            String placeholders = String.join(",", Collections.nCopies(labels.size(), "?"));
            sql.append("AND l.label_name IN (").append(placeholders).append(") ");
            params.addAll(labels);

            sql.append("GROUP BY i.issue_id HAVING COUNT(DISTINCT l.label_name) = ? ");
            params.add(labels.size());
        }

        return jdbcTemplate.query(sql.toString(), params.toArray(), issueRowMapper());
    }

    private RowMapper<Issue> issueRowMapper() {
        return (rs, rowNum) -> new Issue(
                rs.getLong("issue_id"),
                rs.getString("title"),
                rs.getString("author"),
                rs.getTimestamp("published_at").toLocalDateTime(),
                rs.getBoolean("is_closed"),
                rs.getLong("milestone_id")
        );
    }
}
