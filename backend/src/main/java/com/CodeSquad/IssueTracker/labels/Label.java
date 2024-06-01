package com.CodeSquad.IssueTracker.labels;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table("labels")
public class Label {

    @Id
    private Long labelId;

    private String labelName;

    private String description;

    private String textColor;

    private String bgColor;
}