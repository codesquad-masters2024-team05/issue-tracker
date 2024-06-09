package com.CodeSquad.IssueTracker.filter;

import com.CodeSquad.IssueTracker.filter.dto.FilterListResponse;

import com.CodeSquad.IssueTracker.issues.dto.IssueListResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FilterController {
    private final static long PAGE_LIMIT = 15;

    private final FilterService filterService;

    public FilterController(FilterService filterService) {
        this.filterService = filterService;
    }

    @GetMapping("/filters")
    public ResponseEntity<FilterListResponse> getIssueNumber() {
        FilterListResponse filterListResponse = filterService.getFilterListResponse();
        return ResponseEntity.ok(filterListResponse);
    }

    @GetMapping("/filters/issues")
    public ResponseEntity<List<IssueListResponse>> getIssues(
            @RequestParam(value = "is", required = false) String isClosed,
            @RequestParam(value = "assignee", required = false) String assignee,
            @RequestParam(value = "label", required = false) List<String> labelTitles,
            @RequestParam(value = "milestone", required = false) String milestoneTitle,
            @RequestParam(value = "author", required = false) String author,
            @RequestParam(value = "page", defaultValue = "1") Long page){
        List<IssueListResponse> filteredIssues = filterService.findFilteredIssues(isClosed, assignee, labelTitles, milestoneTitle, author, page, PAGE_LIMIT);
        return ResponseEntity.ok(filteredIssues);
    }
}
