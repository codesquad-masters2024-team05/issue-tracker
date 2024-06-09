package com.CodeSquad.IssueTracker.labels;

import com.CodeSquad.IssueTracker.Exception.label.DuplicateLabelNameException;
import com.CodeSquad.IssueTracker.Exception.label.InvalidLabelColorException;
import com.CodeSquad.IssueTracker.Exception.label.InvalidLabelNameException;
import com.CodeSquad.IssueTracker.labels.dto.LabelDetailResponse;
import com.CodeSquad.IssueTracker.labels.dto.LabelRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LabelController {

    private final LabelService labelService;

    public LabelController(LabelService labelService) {
        this.labelService = labelService;
    }

    @GetMapping("/labels")
    public ResponseEntity<List<LabelDetailResponse>> getAllLabels() {
        List<LabelDetailResponse> labels = labelService.getAllLabelDetails();
        return ResponseEntity.ok(labels);
    }

    @PostMapping("/label")
    public ResponseEntity<Void> createLabel(@RequestBody LabelRequest labelRequest) {
        try {
            labelService.createLabel(labelRequest);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (InvalidLabelNameException | InvalidLabelColorException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (DuplicateLabelNameException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping("/label/{id}")
    public ResponseEntity<LabelDetailResponse> getLabelById(@PathVariable Long id) {
        Label label = labelService.getLabelById(id);
        LabelDetailResponse labelDetailResponse = labelService.getLabelDetail(label);
        return ResponseEntity.ok(labelDetailResponse);
    }

    @PutMapping("/label/{id}")
    public ResponseEntity<Void> updateLabel(@PathVariable Long id, @RequestBody LabelRequest updatedLabel) {
        labelService.updateLabel(id, updatedLabel);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/label/{id}")
    public ResponseEntity<Void> deleteLabel(@PathVariable Long id) {
        labelService.deleteLabel(id);
        return ResponseEntity.noContent().build();
    }
}