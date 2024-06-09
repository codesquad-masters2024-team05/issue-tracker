package com.CodeSquad.IssueTracker.assignee;

import com.CodeSquad.IssueTracker.Exception.user.UserNotFoundException;
import com.CodeSquad.IssueTracker.user.User;
import com.CodeSquad.IssueTracker.user.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class AssigneeService {
    private final AssigneeRepository AssigneeRepository;
    private final UserService userService;

    public AssigneeService(com.CodeSquad.IssueTracker.assignee.AssigneeRepository assigneeRepository, UserService userService) {
        AssigneeRepository = assigneeRepository;
        this.userService = userService;
    }

    public void validateAssigneeIds(Set<String> assigneeIds) {
        Set<User> allByIds = userService.findAllByIds(assigneeIds);
        if (allByIds.size() != assigneeIds.size())
            throw new UserNotFoundException("존재하지 않는 유저가 포함되어 있습니다.");
    }

    public List<String> findUsersByIssueId(long issueId) {
        return AssigneeRepository.findUsersByIssueId(issueId);
    }
}
