package com.CodeSquad.IssueTracker.milestone;

import com.CodeSquad.IssueTracker.milestone.dto.MilestoneListResponse;
import com.CodeSquad.IssueTracker.milestone.dto.MilestoneRequest;
import com.CodeSquad.IssueTracker.milestone.dto.MilestoneResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MilestoneController {
    private final MilestoneService milestoneService;

    public MilestoneController(MilestoneService milestoneService) {
        this.milestoneService = milestoneService;
    }

    @PostMapping("/milestone")
    public ResponseEntity<Void> createMilestone(@RequestBody MilestoneRequest milestoneRequest) {
        milestoneService.createMilestone(milestoneRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/milestones/open")
    public ResponseEntity<List<MilestoneResponse>> getAllOpenMilestones(){
        List<MilestoneResponse> milestones = milestoneService.getOpenMilestones();
        return ResponseEntity.ok(milestones);
    }

    @GetMapping("/milestones/close")
    public ResponseEntity<List<MilestoneResponse>> getAllCloseMilestones(){
        List<MilestoneResponse> milestones = milestoneService.getCloseMilestones();
        return ResponseEntity.ok(milestones);
    }

    @GetMapping("/milestone/{milestoneId}")
    public ResponseEntity<MilestoneResponse> getMilestone(@PathVariable Long milestoneId) {
        Milestone milestone = milestoneService.getMilestoneById(milestoneId);
        MilestoneResponse milestoneResponse = milestoneService.getMilestoneResponse(milestone);
        return ResponseEntity.ok(milestoneResponse);
    }

    @DeleteMapping("/milestone/{milestoneId}")
    public ResponseEntity<Void> deleteMilestone(@PathVariable Long milestoneId) {
        milestoneService.deleteMilestone(milestoneId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/milestone/{milestoneId}")
    public ResponseEntity<Void> updateMilestone(@PathVariable Long milestoneId, @RequestBody MilestoneRequest milestoneRequest) {
        milestoneService.editMilestone(milestoneId, milestoneRequest);
        return ResponseEntity.ok().build();
    }

    @PatchMapping ("/milestone/{milestoneId}/open")
    public ResponseEntity<Void> openMilestone(@PathVariable Long milestoneId ) {
        milestoneService.openMilestone(milestoneId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping ("/milestone/{milestoneId}/close")
    public ResponseEntity<Void> closeMilestone(@PathVariable Long milestoneId ) {
        milestoneService.closeMilestone(milestoneId);
        return ResponseEntity.ok().build();
    }

    @GetMapping ("/milestone/list")
    public ResponseEntity<List<MilestoneListResponse>> getMilestoneList() {
        List<MilestoneListResponse> milestoneList = milestoneService.getOpenMilestoneList();
        return ResponseEntity.ok(milestoneList);
    }
}
