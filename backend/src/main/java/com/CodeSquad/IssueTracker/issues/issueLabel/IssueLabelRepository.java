package com.CodeSquad.IssueTracker.issues.issueLabel;

import com.CodeSquad.IssueTracker.issues.issueLabel.dto.LabelRequest;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Repository
public interface IssueLabelRepository extends CrudRepository<IssueLabel, Long> {

    @Query("select * from issueLabel where issue_id = :issueId")
    List<LabelRequest> findByIssueId(Long issueId);

    @Query("SELECT il.label_id, la.label_name, la.text_color, la.bg_color FROM issueLabel il JOIN labels la ON il.label_id = la.label_id WHERE il.issue_id = :issueId")
    List<LabelRequest> getLabelRequestByIssueId(Long issueId);

    @Modifying
    @Transactional
    @Query("DELETE FROM issueLabel WHERE issue_id = :issueId AND label_id = :labelId")
    void removeLabelFromIssue(@Param("issueId") Long issueId, @Param("labelId") Long labelId);

    @Modifying
    @Transactional
    @Query("INSERT INTO issueLabel (issue_id, label_id) VALUES (:issueId, :labelId)")
    int addLabelToIssue(@Param("issueId") Long issueId, @Param("labelId") Long labelId);

    @Query("SELECT label_id FROM issueLabel WHERE issue_id = :issueId")
    Set<Long> findLabelIdsByIssueId(Long issueId);

    @Modifying
    @Query("DELETE FROM issueLabel WHERE issue_id = :issueId AND label_id IN (:labelIds)")
    void deleteByIssueIdAndLabelIds(Long issueId, Set<Long> labelIds);
}