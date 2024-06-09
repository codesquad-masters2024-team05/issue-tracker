package com.CodeSquad.IssueTracker.issues;

import com.CodeSquad.IssueTracker.issues.dto.IssueIds;
import com.CodeSquad.IssueTracker.issues.dto.IssueListResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/issues")
public class IssuesController {
    private final IssueService issueService;
    private final static long PAGE_LIMIT = 15;

    public IssuesController(IssueService issueService) {
        this.issueService = issueService;
    }

    @GetMapping("/open")
    public ResponseEntity<List<IssueListResponse>> getOpenIssues(@RequestParam(value = "page", defaultValue = "1") long page) {
        List<IssueListResponse> openIssues = issueService.findIssues(page, PAGE_LIMIT, false);
        return ResponseEntity.ok(openIssues);
    }

    @GetMapping("/close")
    public ResponseEntity<List<IssueListResponse>> getCloseIssues(@RequestParam(value = "page", defaultValue = "1") long page) {
        List<IssueListResponse> openIssues = issueService.findIssues(page, PAGE_LIMIT, true);
        return ResponseEntity.ok(openIssues);
    }

    @PatchMapping("/open")
    public ResponseEntity<Void> openIssues(@RequestBody IssueIds issueIds) {
        issueService.openIssues(issueIds);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/close")
    public ResponseEntity<Void> closeIssues(@RequestBody IssueIds issueIds) {
        issueService.closeIssues(issueIds);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
