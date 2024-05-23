package com.CodeSquad.IssueTracker.filter;

import com.CodeSquad.IssueTracker.filter.dto.FilterListResponse;
import com.CodeSquad.IssueTracker.issues.Issue;
import com.CodeSquad.IssueTracker.issues.IssueService;
import com.CodeSquad.IssueTracker.labels.LabelService;
import com.CodeSquad.IssueTracker.milestone.MilestoneService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilterService {
    private final CustomFilterRepository customFilterRepository;
    IssueService issueService;
    LabelService labelService;
    MilestoneService milestoneService;

    public FilterService(IssueService issueService, LabelService labelService,
                         MilestoneService milestoneService, CustomFilterRepository customFilterRepository) {
        this.issueService = issueService;
        this.labelService = labelService;
        this.milestoneService = milestoneService;
        this.customFilterRepository = customFilterRepository;
    }

    public FilterListResponse getFilterListResponse() {
        return FilterListResponse.builder()
                .issueNumberResponse(issueService.getIssueNumber())
                .labelListResponse(labelService.getLabelList())
                .milestoneListResponse(milestoneService.getOpenMilestoneList())
                .authorListResponse(issueService.getAuthorList())
                .build();
    }

    public List<Issue> findFilteredIssues(Boolean isClosed, String assignee,
                                          List<String> labelTitles, String milestoneTitle,
                                          String author){
        return customFilterRepository.findFilteredIssues(isClosed, assignee, labelTitles, milestoneTitle, author);
    }
}
