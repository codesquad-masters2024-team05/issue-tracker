package com.CodeSquad.IssueTracker.labels;

import com.CodeSquad.IssueTracker.Exception.label.*;
import com.CodeSquad.IssueTracker.labels.dto.LabelListResponse;
import com.CodeSquad.IssueTracker.labels.utils.ColorValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class LabelService {

    @Autowired
    private LabelRepository labelRepository;

    public List<Label> getAllLabels() {
        log.info("모든 라벨 조회 요청");
        return (List<Label>) labelRepository.findAll();
    }

    public Optional<Label> getLabelById(Long id) {
        if (id <= 0) {
            throw new InvalidLabelIdException("유효하지 않은 라벨 ID: " + id);
        }
        log.info("라벨 id: {} 조회 요청", id);
        return labelRepository.findById(id);
    }

    public Set<Label> findAllByIds(Set<Long> ids) {
        return labelRepository.findAllById(ids);
    }

    public void validateLabels(Set<Long> labels) {
        Set<Label> allByIds = findAllByIds(labels);
        if (allByIds.size() != labels.size())
            throw new LabelNotFoundException("존재하지 않는 라벨이 포함되어 있습니다.");
    }

    public Label createLabel(Label label) {
        log.info("새 라벨 생성 요청: {}", label);

        if (label.getLabelName() == null || label.getLabelName().isBlank()) {
            throw new InvalidLabelNameException("라벨 이름이 없습니다.");
        }

        if (labelRepository.findByLabelName(label.getLabelName()).isPresent()) {
            throw new DuplicateLabelNameException("이미 존재하는 라벨 이름입니다: " + label.getLabelName());
        }

        if (!ColorValidator.isValidColor(label.getTextColor()) || !ColorValidator.isValidColor(label.getBgColor())) {
            throw new InvalidLabelColorException("유효하지 않은 색상 코드입니다.");
        }

        return labelRepository.save(label);
    }


    public Label updateLabel(Long id, Label updatedLabel) {
        log.info("라벨 id: {} 업데이트 요청: {}", id, updatedLabel);
        return labelRepository.findById(id).map(existingLabel -> {
            existingLabel.setLabelName(updatedLabel.getLabelName());
            existingLabel.setDescription(updatedLabel.getDescription());
            existingLabel.setTextColor(updatedLabel.getTextColor());
            existingLabel.setBgColor(updatedLabel.getBgColor());
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
                    .build());
        }

        return labelListResponses;
    }
}