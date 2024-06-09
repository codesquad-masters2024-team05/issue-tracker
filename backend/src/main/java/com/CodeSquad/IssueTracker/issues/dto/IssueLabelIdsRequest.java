package com.CodeSquad.IssueTracker.issues.dto;

import java.util.Set;

public record IssueLabelIdsRequest(
        Set<Long> labels)
{ }
