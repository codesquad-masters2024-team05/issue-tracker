package com.CodeSquad.IssueTracker.issues;

import com.CodeSquad.IssueTracker.issues.dto.*;
import com.CodeSquad.IssueTracker.milestone.dto.MilestoneResponse;
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
    public ResponseEntity<Void> openIssue(@PathVariable("issueId") long issueId) {
        issueService.findIssueById(issueId);
        issueService.openIssue(issueId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{issueId}/close")
    public ResponseEntity<Void> closeIssue(@PathVariable("issueId") long issueId) {
        issueService.findIssueById(issueId);
        issueService.closeIssue(issueId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{issueId}/title")
    public ResponseEntity<Void> editIssueTitle(@PathVariable("issueId") long issueId,
                                            @Valid @RequestBody IssueTitleRequest issueTitleRequest) {
        issueService.updateIssueTitle(issueId, issueTitleRequest);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{issueId}/milestone")
    public ResponseEntity<MilestoneResponse> updateIssueMilestone(@PathVariable Long issueId,
                                                        @RequestBody(required = false) IssueMilestoneRequest issueMilestoneRequest) {
        MilestoneResponse milestone = issueService.updateMilestoneIdForIssue(issueId, issueMilestoneRequest);
        return ResponseEntity.ok(milestone);
    }

    @PatchMapping("/{issueId}/labels")
    public ResponseEntity<Void> updateLabelsToIssue(@PathVariable("issueId") Long issueId,
                                                    @RequestBody IssueLabelIdsRequest labelIdsRequest) {
        issueService.updateLabels(issueId, labelIdsRequest.labels());
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{issueId}/assignees")
    public ResponseEntity<Void> updateAssigneesToIssue(@PathVariable("issueId") Long issueId,
                                                       @RequestBody IssueAssigneeIdsRequest assigneeIdsRequest) {
        issueService.updateAssignees(issueId, assigneeIdsRequest.assignees());
        return ResponseEntity.ok().build();
    }

    // 로드밸런서 확인용 엔드포인트
    @GetMapping("/check")
    public ResponseEntity<String> checkLoadBalancer() {
        return ResponseEntity.ok("Load balancer check successful!");
    }
}