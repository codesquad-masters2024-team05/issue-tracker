package com.CodeSquad.IssueTracker.labels;

import com.CodeSquad.IssueTracker.Exception.label.*;
import com.CodeSquad.IssueTracker.labels.dto.LabelDetailResponse;
import com.CodeSquad.IssueTracker.labels.dto.LabelListResponse;
import com.CodeSquad.IssueTracker.labels.dto.LabelRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class LabelService {

    private final LabelRepository labelRepository;

    public LabelService(LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }

    public List<Label> getAllLabels() {
        return (List<Label>) labelRepository.findAll();
    }

    public List<LabelDetailResponse> getAllLabelDetails() {
        List<Label> labels = (List<Label>) labelRepository.findAll();
        List<LabelDetailResponse> labelDetailResponseList = new ArrayList<>();

        for (Label label : labels) {
            labelDetailResponseList.add(getLabelDetail(label));
        }
        log.info("모든 라벨 조회 요청");
        return labelDetailResponseList;
    }

    public LabelDetailResponse getLabelDetail(Label label) {
        return LabelDetailResponse.builder()
                .labelId(label.getLabelId())
                .labelName(label.getLabelName())
                .description(label.getDescription())
                .textColor(label.getTextColor())
                .bgColor(label.getBgColor())
                .build();
    }

    public Label getLabelById(Long id) {
        if (id <= 0) {
            throw new InvalidLabelIdException("유효하지 않은 라벨 ID: " + id);
        }
        log.info("라벨 id: {} 조회 요청", id);
        return labelRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("해당 라벨이 존재하지 않습니다: {}", id);
                    return new LabelNotFoundException("해당 라벨이 존재하지 않습니다: " + id);
                });
    }

    public Set<Label> findAllByIds(Set<Long> ids) {
        return labelRepository.findAllById(ids);
    }

    public void validateLabels(Set<Long> labels) {
        Set<Label> allByIds = findAllByIds(labels);
        if (allByIds.size() != labels.size())
            throw new LabelNotFoundException("존재하지 않는 라벨이 포함되어 있습니다.");
    }

    public void createLabel(LabelRequest labelRequest) {
        log.info("새 라벨 생성 요청: {}", labelRequest);
        validateLabelRequest(labelRequest, false);
        Label label = Label.builder()
                .labelName(labelRequest.labelName())
                .description(labelRequest.description())
                .textColor(labelRequest.textColor())
                .bgColor(labelRequest.bgColor())
                .build();

        labelRepository.save(label);
    }

    public void validateLabelRequest(LabelRequest newLabel, boolean isUpdate) {
        if (isUpdate) {
            if (labelRepository.findByLabelName(newLabel.labelName()).isPresent())
                throw new DuplicateLabelNameException("이미 존재하는 라벨 이름입니다: " + newLabel.labelName());
        }
    }

    public void updateLabel(Long id, LabelRequest updatedLabel) {
        log.info("라벨 id: {} 업데이트 요청: {}", id, updatedLabel);
        Label label = labelRepository.findById(id)
                .orElseThrow(() -> new LabelNotFoundException("해당 라벨이 존재하지 않습니다."));

        if (label.getLabelName().equals(updatedLabel.labelName())){
            validateLabelRequest(updatedLabel, false);
        }else {
            validateLabelRequest(updatedLabel, true);
        }

        labelRepository.findById(id).map(existingLabel -> {
            existingLabel.setLabelName(updatedLabel.labelName());
            existingLabel.setDescription(updatedLabel.description());
            existingLabel.setTextColor(updatedLabel.textColor());
            existingLabel.setBgColor(updatedLabel.bgColor());
            return labelRepository.save(existingLabel);
        }).orElseThrow(() -> new LabelNotFoundException("라벨 id: " + id + " 업데이트 실패, 해당 라벨이 존재하지 않습니다."));
    }

    public void deleteLabel(Long id) {
        log.info("라벨 id: {} 삭제 요청", id);
        if (!labelRepository.existsById(id)) {
            throw new LabelNotFoundException("라벨 id: " + id + " 삭제 실패, 해당 라벨이 존재하지 않습니다.");
        }
        labelRepository.deleteById(id);
    }

    public List<LabelListResponse> getLabelList() {
        List<Label> labels = getAllLabels();
        List<LabelListResponse> labelListResponses = new ArrayList<>();

        for (Label label : labels) {
            labelListResponses.add(LabelListResponse.builder()
                    .labelId(label.getLabelId())
                    .labelName(label.getLabelName())
                    .labelBgColor(label.getBgColor())
                    .labelTextColor(label.getTextColor())
                    .build());
        }

        return labelListResponses;
    }
}