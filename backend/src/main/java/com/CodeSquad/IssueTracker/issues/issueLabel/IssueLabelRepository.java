package com.CodeSquad.IssueTracker.issues.issueLabel;

import com.CodeSquad.IssueTracker.issues.issueLabel.dto.LabelRequest;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueLabelRepository extends CrudRepository<IssueLabel, Long> {
    @Query("SELECT il.label_id, la.label_name, la.text_color, la.bg_color FROM issueLabel il JOIN labels la ON il.label_id = la.label_id WHERE il.issue_id = :issueId")
    List<LabelRequest> getLabelRequestByIssueId(Long issueId);
}