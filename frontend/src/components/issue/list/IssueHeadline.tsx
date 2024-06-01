import styled from "styled-components";
import userIcon from "../../../img/icon/userIcon.png";
import blueOpenedIssueIcon from "../../../img/icon/blueOpenedIssueIcon.svg";
import violetClosedIssueIcon from "../../../img/icon/violetClosedIssueIcon.svg";
import milestoneIcon from "../../../img/icon/milestoneIcon.svg";
import dateUtils from "../../../utils/DateUtils";
import { useNavigate } from "react-router-dom";
import React from "react";
import useIssueStore, { Headline, Label, LabelResponse, Milestone } from "../../../hooks/stores/useIssueStore";

const matchedLabels = (labels: Label[], labelResponses: LabelResponse[]) =>
  labels.filter((label) => labelResponses.some((response) => response.labelId === label.labelId));

const renderLabels = (labels: Label[], labelResponses: LabelResponse[]) =>
  matchedLabels(labels, labelResponses).map(({ labelName, labelBgColor, labelTextColor }) => (
    <LabelBox labelBgColor={labelBgColor} labelTextColor={labelTextColor}>
      {labelName}
    </LabelBox>
  ));

const renderMilestone = (milestones: Milestone[], milestoneId: number) => {
  const milestone = milestones.find((milestone) => milestoneId === milestone.milestoneId);
  return milestone ? (
    <>
      <img src={milestoneIcon} />
      <span>{milestone.title}</span>
    </>
  ) : null;
};

const IssueHeadline = React.forwardRef<HTMLDivElement, Headline>((props, ref) => {
  const { issueId, title, author, publishedAt, isClosed, labels: labelResponses, milestoneId } = props;
  const { labels, milestones } = useIssueStore();
  const navigate = useNavigate();

  return (
    <Wrapper ref={ref}>
      <IssueCheckBox type="checkbox" />
      <IssueDescriptions>
        <IssueTitleDescription>
          <img src={isClosed ? violetClosedIssueIcon : blueOpenedIssueIcon} />
          <TitleText onClick={() => navigate(`/issue/${issueId}`)}>{title}</TitleText>
          {renderLabels(labels, labelResponses)}
        </IssueTitleDescription>
        <IssueInfo>
          <span>#{issueId}</span>
          <span>{`이 이슈가 ${dateUtils.parseTimeDifference(publishedAt)}, ${author}님에 의해 작성되었습니다.`}</span>
          {milestoneId && renderMilestone(milestones, milestoneId)}
        </IssueInfo>
      </IssueDescriptions>
      <UserIconContainer>
        <UserIcon src={userIcon} />
      </UserIconContainer>
    </Wrapper>
  );
});

const Wrapper = styled.div`
  width: 95%;
  padding: 0 2em;
  height: 6em;
  display: flex;
  align-items: start;
  border-top: 1px solid #d9dbe9;
`;

const IssueCheckBox = styled.input`
  margin-top: 1.7em;
`;

const IssueDescriptions = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  gap: 1em;
  margin: 0 2em;
`;

const IssueTitleDescription = styled.div`
  display: flex;
  align-items: center;
  gap: 0.5em;
`;

const TitleText = styled.a`
  height: 1.125em;
  font-size: 1.25em;
  font-weight: 500;
  cursor: pointer;
`;

const LabelBox = styled.div<{ labelBgColor: string; labelTextColor: string }>`
  height: 1.5em;
  padding: 0 0.75em;
  font-size: 0.75em;
  display: flex;
  align-items: center;
  border: 1px solid ${({ labelBgColor }) => labelBgColor};
  border-radius: 1em;
  background-color: ${({ labelBgColor }) => labelBgColor};
  color: ${({ labelTextColor }) => labelTextColor};
`;

const IssueInfo = styled.div`
  display: flex;
  gap: 1em;
  color: #6e7191;
`;

const UserIcon = styled.img`
  width: 1.25em;
  height: 1.25em;
`;

const UserIconContainer = styled.div`
  height: 100%;
  margin-right: 1.5em;
  display: flex;
  align-items: center;
`;

export default IssueHeadline;
