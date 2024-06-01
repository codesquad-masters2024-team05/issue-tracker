package com.CodeSquad.IssueTracker.issues;

import com.CodeSquad.IssueTracker.issues.dto.*;
import com.CodeSquad.IssueTracker.milestone.Milestone;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/issue")
public class IssueController {

    private final IssueService issueService;

    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }


    @PostMapping
    public ResponseEntity<IssueIdResponse> createIssue(@Valid @RequestBody IssueRequest issueRequest) {
        Long createdIssueId = issueService.createIssue(issueRequest);
        IssueIdResponse issueIdResponse = new IssueIdResponse(createdIssueId);
        return ResponseEntity.ok(issueIdResponse);
    }

    @GetMapping("/{issueId}")
    public ResponseEntity<IssueDetailResponse> getIssue(@PathVariable("issueId") long issueId) {
        IssueDetailResponse issueDetailResponse = issueService.getIssueById(issueId);
        return ResponseEntity.ok(issueDetailResponse);
    }

    @PatchMapping("/{issueId}/open")
    public ResponseEntity<Issue> openIssue(@PathVariable("issueId") long issueId) {
        issueService.openIssue(issueId);
        Issue issue = issueService.findIssueById(issueId);
        return ResponseEntity.ok(issue);
    }

    @PatchMapping("/{issueId}/close")
    public ResponseEntity<Issue> closeIssue(@PathVariable("issueId") long issueId) {
        issueService.closeIssue(issueId);
        Issue issue = issueService.findIssueById(issueId);
        return ResponseEntity.ok(issue);
    }

    @PatchMapping("/{issueId}/title")
    public ResponseEntity<?> editIssueTitle(@PathVariable("issueId") long issueId,
                                            @Valid @RequestBody IssueTitleRequest issueTitleRequest) {
        issueService.updateIssueTitle(issueId, issueTitleRequest);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{issueId}/milestone")
    public ResponseEntity<Milestone> editIssueMilestone(@PathVariable Long issueId,
                                                        @RequestBody(required = false) IssueMilestoneRequest issueMilestoneRequest) {
        Milestone milestone = issueService.updateMilestoneIdForIssue(issueId, issueMilestoneRequest);
        return ResponseEntity.ok(milestone);
    }

    @PostMapping("/{issueId}/labels/{labelId}")
    public ResponseEntity<Void> addLabelToIssue(@PathVariable Long issueId, @PathVariable Long labelId) {
        issueService.addLabelToIssue(issueId, labelId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{issueId}/labels/{labelId}")
    public ResponseEntity<Void> removeLabelFromIssue(@PathVariable Long issueId, @PathVariable Long labelId) {
        issueService.removeLabelFromIssue(issueId, labelId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{issueId}/labels")
    public ResponseEntity<Void> updateLabelsToIssue(@PathVariable("issueId") Long issueId,
                                                    @RequestBody IssueLabelIdsRequest labelIdsRequest) {
        issueService.updateLabels(issueId, labelIdsRequest.labels());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{issueId}/assignees")
    public ResponseEntity<Void> updateAssigneesToIssue(@PathVariable("issueId") Long issueId,
                                                       @RequestBody IssueAssigneeIdsRequest assigneeIdsRequest) {
        issueService.updateAssignees(issueId, assigneeIdsRequest.assignees());
        return ResponseEntity.ok().build();
    }
}