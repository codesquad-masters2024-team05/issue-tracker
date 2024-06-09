package com.CodeSquad.IssueTracker.labels;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Builder
@Table("labels")
public class Label {

    @Id
    private Long labelId;

    private String labelName;

    private String description;

    private String textColor;

    private String bgColor;
}