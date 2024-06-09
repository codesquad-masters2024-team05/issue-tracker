package com.CodeSquad.IssueTracker.filter;

import com.CodeSquad.IssueTracker.Exception.issue.InvalidIssueDataException;
import com.CodeSquad.IssueTracker.filter.dto.FilterListResponse;
import com.CodeSquad.IssueTracker.issues.IssueService;
import com.CodeSquad.IssueTracker.issues.dto.IssueListResponse;
import com.CodeSquad.IssueTracker.labels.LabelService;
import com.CodeSquad.IssueTracker.milestone.MilestoneService;
import com.CodeSquad.IssueTracker.user.UserService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FilterService {
    private final CustomFilterRepository customFilterRepository;
    private final IssueService issueService;
    private final LabelService labelService;
    private final MilestoneService milestoneService;
    private final UserService userService;

    public FilterService(IssueService issueService, LabelService labelService,
                         MilestoneService milestoneService, CustomFilterRepository customFilterRepository,
                         UserService userService) {
        this.issueService = issueService;
        this.labelService = labelService;
        this.milestoneService = milestoneService;
        this.customFilterRepository = customFilterRepository;
        this.userService = userService;
    }

    public FilterListResponse getFilterListResponse() {
        return FilterListResponse.builder()
                .issueNumberResponse(issueService.getIssueNumber())
                .labelListResponse(labelService.getLabelList())
                .milestoneListResponse(milestoneService.getOpenMilestoneList())
                .userListResponse(userService.getAllUserIds())
                .build();
    }

    public List<IssueListResponse> findFilteredIssues(String isClosed, String assignee,
                                                      List<String> labelTitles, String milestoneTitle,
                                                      String author, Long page, Long offset){
        boolean closed = false;
        if(isClosed.equals("close")){
            closed = true;
        }else if(isClosed.equals("open")){
            closed = false;
        }else{
            throw new InvalidIssueDataException("데이터 형식에 맞지 않습니다.");
        }
        return customFilterRepository.findFilteredIssues(closed, assignee, labelTitles, milestoneTitle, author, page, offset);
    }
}
