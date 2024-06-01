package com.CodeSquad.IssueTracker.issues;

import com.CodeSquad.IssueTracker.issues.dto.IssueIds;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/issues")
public class IssuesController {

    private final IssueService issueService;
    private final static long DEFAULT_OFFSET = 15;


    public IssuesController(IssueService issueService) {
        this.issueService = issueService;
    }

    @GetMapping
    public ResponseEntity<List<Issue>> getAllIssues() {
        List<Issue> issues = issueService.getAllIssues();
        return new ResponseEntity<>(issues, HttpStatus.OK);
    }

    @GetMapping("/open")
    public ResponseEntity<List<Issue>> getOpenIssues(@RequestParam(value = "page", defaultValue = "1") long page) {
        List<Issue> openIssues = issueService.findOpenIssues(page, DEFAULT_OFFSET);
        return ResponseEntity.ok(openIssues);
    }

    @GetMapping("/close")
    public ResponseEntity<List<Issue>> getCloseIssues(@RequestParam(value = "page", defaultValue = "1") long page) {
        List<Issue> openIssues = issueService.findCloseIssues(page, DEFAULT_OFFSET);
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
