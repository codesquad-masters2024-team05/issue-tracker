package com.CodeSquad.IssueTracker.issues.issueLabel;

import com.CodeSquad.IssueTracker.issues.issueLabel.dto.LabelRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IssueLabelService {

    private final IssueLabelRepository issueLabelRepository;

    public IssueLabelService(IssueLabelRepository issueLabelRepository) {
        this.issueLabelRepository = issueLabelRepository;
    }

    public List<LabelRequest> getLabelsByIssueId(Long issueId) {
        return issueLabelRepository.getLabelRequestByIssueId(issueId);
    }
}
