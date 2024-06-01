import styled from "styled-components";
import plusIconInDark from "../../../img/icon/plusIcon_dark.svg";
import submitIconInDark from "../../../img/icon/submitIcon_dark.svg";
import editIcon from "../../../img/icon/editIcon.svg";
import openedIssueIconInDark from "../../../img/icon/openedIssueIcon_dark.svg";
import archieveIcon from "../../../img/icon/archieveIcon.svg";
import { SetStateAction } from "react";

interface TitleActionContainerProps {
  closed: boolean;
  isTitleEditable: boolean;
  isTitleSubmitable: boolean;
  setIsTitleEditable: (value: SetStateAction<boolean>) => void;
  handleStateToggleClick: () => void;
  handleTitleEditSubmit: () => void;
}

function TitleActionContainer({
  closed,
  isTitleEditable,
  isTitleSubmitable,
  setIsTitleEditable,
  handleStateToggleClick,
  handleTitleEditSubmit,
}: TitleActionContainerProps) {
  return isTitleEditable ? (
    <>
      <IssueToggleButton onClick={() => setIsTitleEditable(!isTitleEditable)}>
        <CancelImage src={plusIconInDark} />
        편집 취소
      </IssueToggleButton>
      <SubmitButton isSubmitable={isTitleSubmitable} onClick={handleTitleEditSubmit}>
        <img src={submitIconInDark} />
        편집 완료
      </SubmitButton>
    </>
  ) : (
    <>
      <IssueToggleButton onClick={() => setIsTitleEditable(!isTitleEditable)}>
        <img src={editIcon} />
        제목 편집
      </IssueToggleButton>
      <IssueToggleButton onClick={handleStateToggleClick}>
        {closed ? (
          <>
            <img src={openedIssueIconInDark} />
            <span>다시 열기</span>
          </>
        ) : (
          <>
            <img src={archieveIcon} />
            <span>이슈 닫기</span>
          </>
        )}
      </IssueToggleButton>
    </>
  );
}

const IssueToggleButton = styled.button`
  width: 10.66em;
  height: 3.33em;
  font-size: 0.75em;
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 0.5em;
  background-color: transparent;
  border: 1px solid #595959;
  border-radius: 1em;
  cursor: pointer;
`;

const SubmitButton = styled.button<{ isSubmitable: boolean }>`
  width: 10.7em;
  height: 3.3em;
  font-size: 0.75em;
  border: 1px solid #595959;
  border-radius: 1.3em;
  background-color: #595959;
  color: #fff;
  display: flex;
  justify-content: center;
  align-items: center;
  opacity: ${({ isSubmitable }) => (isSubmitable ? "1" : "0.5")};
  transition: all 0.5s ease;
`;

const CancelImage = styled.img`
  transform: rotate(45deg);
`;

export default TitleActionContainer;
