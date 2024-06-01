import styled from "styled-components";
import Header from "../../header/Header";
import closedIssueIcon from "../../../img/icon/closedIssueIcon.svg";
import openedIssueIcon from "../../../img/icon/openedIssueIcon.svg";
import useIssueDetailLogic from "../../../hooks/logics/useIssueDetailLogic";
import dateUtils from "../../../utils/DateUtils";
import DetailHeader from "./DetailHeader";
import DetailContent from "./DetailContent";

function IssueDetail() {
  const { issueContent } = useIssueDetailLogic();
  const { author, publishedAt, comments, closed } = issueContent || {};

  return (
    <Wrapper>
      <Header />
      <DetailHeader />
      <IssueInfo>
        <IssueStateText>
          <img src={closed ? closedIssueIcon : openedIssueIcon} />
          <span>{closed ? "닫힌 이슈" : "열린 이슈"}</span>
        </IssueStateText>
        <LastChangedTime>
          이 이슈가 {publishedAt ? dateUtils.parseTimeDifference(publishedAt) : "알 수 없는 시간"}에{" "}
          {author || "알 수 없음"}님에 의해 열렸습니다. - 코멘트 {comments?.length || 0}개
        </LastChangedTime>
      </IssueInfo>
      <BodyBoundary />
      <DetailContent />
    </Wrapper>
  );
}

const Wrapper = styled.div`
  width: 1280px;
  display: block;
`;

const IssueInfo = styled.div`
  display: flex;
  align-items: center;
  gap: 0.5em;
  margin-top: 1.5em;
`;

const IssueStateText = styled.div`
  height: 2em;
  padding: 0 1em;
  display: flex;
  gap: 0.5em;
  align-items: center;
  background-color: #595959;
  color: #fff;
  border: 1px solid #595959;
  border-radius: 1em;
`;

const LastChangedTime = styled.span`
  font-weight: 500;
  line-height: 1.5em;
  color: #6e7191;
`;

const BodyBoundary = styled.hr`
  margin: 2em 0;
  height: 1px;
  color: #eceef5;
`;

export default IssueDetail;
