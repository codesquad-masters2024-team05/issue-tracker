package com.CodeSquad.IssueTracker.filter;

import com.CodeSquad.IssueTracker.issues.dto.IssueDetailAccess;
import com.CodeSquad.IssueTracker.issues.dto.IssueListResponse;
import com.CodeSquad.IssueTracker.issues.issueLabel.dto.LabelRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class CustomFilterRepository {
    private final JdbcTemplate jdbcTemplate;

    public CustomFilterRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<IssueListResponse> findFilteredIssues(Boolean isClosed, String assignee, List<String> labels, String milestone, String author, Long page, Long size) {
        List<Long> issueIds = findIssueIds(isClosed, assignee, labels, milestone, author, page, size);
        List<IssueDetailAccess> issues = findIssueDetailsByIds(issueIds);
        return transformToIssueListResponses(issues);
    }

    private List<Long> findIssueIds(Boolean isClosed, String assignee, List<String> labels, String milestone, String author, Long page, Long size) {
        StringBuilder sql = new StringBuilder("SELECT DISTINCT i.issue_id " +
                "FROM issues i ");

        if (assignee != null && !assignee.isEmpty()) {
            sql.append("LEFT JOIN assignees a ON i.issue_id = a.issue_id ");
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
            sql.append("AND i.issue_id IN (")
                    .append("SELECT il.issue_id FROM issueLabel il ")
                    .append("JOIN labels l ON il.label_id = l.label_id ")
                    .append("WHERE l.label_name IN (")
                    .append(String.join(",", Collections.nCopies(labels.size(), "?")))
                    .append(") ")
                    .append("GROUP BY il.issue_id ")
                    .append("HAVING COUNT(DISTINCT l.label_name) = ?) ");
            params.addAll(labels);
            params.add(labels.size());
        }
        sql.append("ORDER BY i.issue_id DESC ");
        Long offset = (page - 1) * size;
        sql.append("LIMIT ? OFFSET ?");
        params.add(size);
        params.add(offset);

        return jdbcTemplate.query(sql.toString(), params.toArray(), (rs, rowNum) -> rs.getLong("issue_id"));
    }

    private List<IssueDetailAccess> findIssueDetailsByIds(List<Long> issueIds) {
        if (issueIds.isEmpty()) {
            return Collections.emptyList();
        }

        String sql = "SELECT i.issue_id, i.title, i.author, i.published_at, i.is_closed, i.milestone_id, " +
                "a.user_id AS assignee, l.label_id, l.label_name, l.bg_color, l.text_color " +
                "FROM issues i " +
                "LEFT JOIN assignees a ON i.issue_id = a.issue_id " +
                "LEFT JOIN issueLabel il ON i.issue_id = il.issue_id " +
                "LEFT JOIN labels l ON il.label_id = l.label_id " +
                "WHERE i.issue_id IN (" + String.join(",", Collections.nCopies(issueIds.size(), "?")) + ")";

        return jdbcTemplate.query(sql, issueIds.toArray(), issueRowMapper());
    }

    private RowMapper<IssueDetailAccess> issueRowMapper() {
        return (rs, rowNum) -> new IssueDetailAccess(
                rs.getLong("issue_id"),
                rs.getString("title"),
                rs.getString("author"),
                rs.getTimestamp("published_at").toLocalDateTime(),
                rs.getBoolean("is_closed"),
                rs.getString("assignee"),
                rs.getLong("label_id"),
                rs.getString("label_name"),
                rs.getString("bg_color"),
                rs.getString("text_color"),
                rs.getLong("milestone_id")
        );
    }

    private List<IssueListResponse> transformToIssueListResponses(List<IssueDetailAccess> issueDetails) {
        Map<Long, IssueListResponse> issueMap = new LinkedHashMap<>();

        for (IssueDetailAccess detail : issueDetails) {
            IssueListResponse response = issueMap.computeIfAbsent(detail.getIssueId(), id -> IssueListResponse.builder()
                    .issueId(detail.getIssueId())
                    .title(detail.getTitle())
                    .author(detail.getAuthor())
                    .publishedAt(detail.getPublishedAt())
                    .isClosed(detail.getIsClosed())
                    .milestoneId(detail.getMilestoneId())
                    .assignees(new ArrayList<>())
                    .labels(new ArrayList<>())
                    .build()
            );

            if (detail.getAssignee() != null && !response.getAssignees().contains(detail.getAssignee())) {
                response.getAssignees().add(detail.getAssignee());
            }

            if (detail.getLabelId() != null && detail.getLabelName() != null && detail.getBgColor() != null && detail.getTextColor() != null) {
                LabelRequest label = new LabelRequest(detail.getLabelId(), detail.getLabelName(), detail.getBgColor(), detail.getTextColor());
                if (!response.getLabels().contains(label)) {
                    response.getLabels().add(label);
                }
            }
        }

        List<IssueListResponse> issueListResponses = new ArrayList<>(issueMap.values());
        Collections.reverse(issueListResponses); // 리스트를 역순으로 뒤집습니다.
        return issueListResponses;
    }
}
