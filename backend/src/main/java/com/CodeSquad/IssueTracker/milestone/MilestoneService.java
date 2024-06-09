package com.CodeSquad.IssueTracker.milestone;

import com.CodeSquad.IssueTracker.Exception.milestone.InvalidMilestoneRequestException;
import com.CodeSquad.IssueTracker.Exception.milestone.MilestoneNotFoundException;
import com.CodeSquad.IssueTracker.milestone.dto.MilestoneInIssue;
import com.CodeSquad.IssueTracker.milestone.dto.MilestoneListResponse;
import com.CodeSquad.IssueTracker.milestone.dto.MilestoneRequest;
import com.CodeSquad.IssueTracker.milestone.dto.MilestoneResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.CodeSquad.IssueTracker.milestone.utils.TimestampParser.parseDeadline;

@Slf4j
@Service
public class MilestoneService {
    private final MilestoneRepository milestoneRepository;

    public MilestoneService(MilestoneRepository milestoneRepository) {
        this.milestoneRepository = milestoneRepository;
    }

    public void createMilestone(MilestoneRequest milestoneRequest) {
        log.info("마일스톤 생성 요청: {}", milestoneRequest);
        validateMilestoneRequest(milestoneRequest, true);

        LocalDateTime localDateTime;
        try {
            localDateTime= parseDeadline(milestoneRequest.deadline());
        }catch (IllegalArgumentException  e){
            log.error("마감일 형식이 잘못되었습니다: {}", milestoneRequest.deadline(), e);
            throw new InvalidMilestoneRequestException("Invalid deadline format");
        }

        Milestone milestone = new Milestone(
                milestoneRequest.milestoneId(),
                milestoneRequest.title(),
                milestoneRequest.description(),
                localDateTime
        );

        milestoneRepository.save(milestone);
        log.info("마일스톤 생성 완료: {}", milestone);
    }

    public void deleteMilestone(Long milestoneId) {
        log.info("마일스톤 삭제 요청: {}", milestoneId);
        Milestone milestone = getMilestoneById(milestoneId);

        milestoneRepository.deleteAllIssueReferences(milestoneId);
        milestoneRepository.delete(milestone);
        log.info("마일스톤 삭제 완료: {}", milestone);
    }

    public void validateMilestoneId(Long milestoneId) {
        milestoneRepository.findById(milestoneId)
                .orElseThrow(() ->
                        new MilestoneNotFoundException("존재하지 않는 마일스톤입니다."));
    }

    public Milestone getMilestoneById(Long milestoneId) {
        log.info("마일스톤 조회 요청: {}", milestoneId);
        return milestoneRepository.findById(milestoneId)
                .orElseThrow(() -> {
                    log.error("해당 마일스톤이 존재하지 않습니다: {}", milestoneId);
                    return new MilestoneNotFoundException("해당 마일스톤이 존재하지 않습니다: " + milestoneId);
                });
    }

    public MilestoneResponse getMilestoneResponse(Milestone milestone) {
        return MilestoneResponse.builder()
                .milestoneId(milestone.getMilestoneId())
                .title(milestone.getTitle())
                .description(milestone.getDescription())
                .deadline(milestone.getDeadline())
                .totalIssue(milestone.getTotalIssue())
                .closedIssue(milestone.getClosedIssue())
                .isClosed(milestone.getIsClosed())
                .build();
    }

    public List<MilestoneResponse> getOpenMilestones() {
        log.info("열린 마일스톤 조회 요청");
        return createResponseList(milestoneRepository.findAllOpenMilestones());
    }

    public List<MilestoneResponse> getCloseMilestones() {
        log.info("닫힌 마일스톤 조회 요청");
        return createResponseList(milestoneRepository.findAllCloseMilestones());
    }

    public List<MilestoneResponse> createResponseList(List<Milestone> milestones) {
        List<MilestoneResponse> responseList = new ArrayList<>();
        for (Milestone milestone : milestones) {
            responseList.add(getMilestoneResponse(milestone));
        }
        return responseList;
    }

    private void validateMilestoneRequest(MilestoneRequest milestoneRequest, Boolean checkSameTitle) {
        if (milestoneRequest.title().isEmpty()) {
            log.error("제목이 비어있습니다: {}", milestoneRequest);
            throw new InvalidMilestoneRequestException("제목이 비어있습니다.");
        }
        if (checkSameTitle){
            if (milestoneRepository.findByTitle(milestoneRequest.title())!= null){
                throw new InvalidMilestoneRequestException("같은 제목의 마일스톤이 존재합니다.");
            }
        }
    }

    public void editMilestone(Long milestoneId, MilestoneRequest milestoneRequest) {
        if (getMilestoneById(milestoneId).getTitle().equals(milestoneRequest.title())){
            validateMilestoneRequest(milestoneRequest, false);
        }else{
            validateMilestoneRequest(milestoneRequest, true);
        }

        Milestone milestone = getMilestoneById(milestoneId);

        log.info("마일스톤 편집 milestone: {}", milestoneId);
        milestone.setTitle(milestoneRequest.title());
        milestone.setDescription(milestoneRequest.description());
        milestone.setDeadline(parseDeadline(milestoneRequest.deadline()));
        milestoneRepository.save(milestone);
    }
    public void closeMilestone(Long milestoneId) {
        Milestone milestone = getMilestoneById(milestoneId);
        milestone.setIsClosed(true);
        log.info("마일스톤 상태 변경 close milestone: {}", milestoneId);
        milestoneRepository.save(milestone);
    }

    public void openMilestone(Long milestoneId) {
        Milestone milestone = getMilestoneById(milestoneId);
        milestone.setIsClosed(false);
        log.info("마일스톤 상태 변경 open milestone: {}", milestoneId);
        milestoneRepository.save(milestone);
    }

    public List<MilestoneListResponse> getOpenMilestoneList() {
        List<Milestone> openMilestones = milestoneRepository.findAllOpenMilestones();
        log.info("열려있는 마일스톤 리스트로 조회");

        return openMilestones.stream()
                .map(milestone -> MilestoneListResponse.builder()
                        .milestoneId(milestone.getMilestoneId())
                        .title(milestone.getTitle())
                        .build())
                .collect(Collectors.toList());
    }

    public void incrementTotalIssue(Long milestoneId){
        getMilestoneById(milestoneId);
        milestoneRepository.incrementTotalIssue(milestoneId);
        log.info("마일스톤의 총 이슈 증가: {}", milestoneId);
    }

    public void decrementTotalIssue(Long milestoneId){
        getMilestoneById(milestoneId);
        milestoneRepository.decrementTotalIssue(milestoneId);
        log.info("마일스톤의 총 이슈 감소: {}", milestoneId);
    }

    public void incrementClosedIssue(Long milestoneId){
        getMilestoneById(milestoneId);
        milestoneRepository.incrementClosedIssue(milestoneId);
        log.info("마일스톤의 닫힌 이슈 증가: {}", milestoneId);
    }

    public void decrementClosedIssue(Long milestoneId){
        getMilestoneById(milestoneId);
        milestoneRepository.decrementClosedIssue(milestoneId);
        log.info("마일스톤의 닫힌 이슈 감소: {}", milestoneId);
    }

    public MilestoneInIssue getMilestoneInIssue(long issueId) {
        return milestoneRepository.getMilestoneInIssueById(issueId);
    }
}
