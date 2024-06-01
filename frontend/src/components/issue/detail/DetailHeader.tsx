import styled from "styled-components";
import useIssueDetailLogic from "../../../hooks/logics/useIssueDetailLogic";
import TitleActionContainer from "./TitleActionContainer";

function DetailHeader() {
  const {
    issueId,
    issueContent,
    titleInputRef,
    isTitleSubmitable,
    isTitleEditable,
    setIsTitleEditable,
    handleTitleChange,
    handleStateToggleClick,
    handleTitleEditSubmit,
  } = useIssueDetailLogic();
  const { title, closed } = issueContent || {};

  return (
    <TopWrapper>
      {isTitleEditable ? (
        <TitleEditBox>
          <BoxTypeText>제목</BoxTypeText>
          <TitleEditInput ref={titleInputRef} onChange={handleTitleChange} required />
        </TitleEditBox>
      ) : (
        <TitleDescription>
          <TitleText>{title || "제목 없음"}</TitleText>
          <IssueNumber>#{issueId}</IssueNumber>
        </TitleDescription>
      )}
      <ButtonWrapper>
        {closed !== undefined &&
          TitleActionContainer({
            closed,
            isTitleEditable,
            isTitleSubmitable,
            setIsTitleEditable,
            handleStateToggleClick,
            handleTitleEditSubmit,
          })}
      </ButtonWrapper>
    </TopWrapper>
  );
}

const TopWrapper = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-between;
  gap: 1em;
`;

const TitleEditBox = styled.div`
  box-sizing: border-box;
  width: 100%;
  display: flex;
  gap: 4px;
  padding: 0 8px;
  background-color: #eff0f6;
  border: 1px solid #eff0f6;
  border-radius: 12px;
`;

const BoxTypeText = styled.span`
  width: 5.3em;
  font-size: 0.75em;
  display: flex;
  align-items: center;
`;

const TitleEditInput = styled.input`
  width: 100%;
  box-sizing: border-box;
  background-color: transparent;
  border: none;
`;

const TitleDescription = styled.div`
  display: flex;
  font-size: 2em;
  font-weight: 700;
  gap: 0.25em;
`;

const TitleText = styled.span`
  color: #14142b;
`;

const IssueNumber = styled.span`
  color: #6e7191;
`;

const ButtonWrapper = styled.div`
  display: flex;
  gap: 0.5em;
`;

export default DetailHeader;
