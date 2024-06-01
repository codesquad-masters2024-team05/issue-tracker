import styled from "styled-components";
import editIcon from "../../img/icon/editIcon_gray.svg";
import deleteIcon from "../../img/icon/deleteIcon.svg";
import { LabelDetailType } from "../../contexts/LabelContext";
import LabelEditBox from "./LabelEditBox";
import useLabelDetail from "../../hooks/logics/useLabelDetail";

function LabelDetail({ labelId, labelName, description, textColor, bgColor }: LabelDetailType) {
  const { isToEdit, toggleEdit, handleDeleteClick } = useLabelDetail(labelId);

  return (
    <>
      {isToEdit ? (
        <LabelEditBox
          editType="edit"
          labelId={labelId}
          content={{ labelId, labelName, description, textColor, bgColor }}
          closeEditBox={toggleEdit}
        />
      ) : (
        <Wrapper>
          <ContentWrapper>
            <LabelWrapper>
              <Label textColor={textColor} bgColor={bgColor}>
                {labelName}
              </Label>
            </LabelWrapper>
            <Description>{description}</Description>
          </ContentWrapper>
          <ButtonWrapper>
            <EditButton onClick={toggleEdit}>
              <img src={editIcon} alt="편집" />
              편집
            </EditButton>
            <DeleteButton onClick={handleDeleteClick}>
              <img src={deleteIcon} alt="삭제" />
              삭제
            </DeleteButton>
          </ButtonWrapper>
        </Wrapper>
      )}
    </>
  );
}

const Wrapper = styled.div`
  height: 7em;
  padding: 0 2em;
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-top: 1px solid #d9dbe9;
  gap: 2em;
`;

const ContentWrapper = styled.div`
  display: flex;
`;

const LabelWrapper = styled.div`
  display: flex;
  width: 9.75em;
  max-width: 9.75em;
`;

const Label = styled.div<{ textColor: string; bgColor: string }>`
  padding: 0 1em;
  height: 2em;
  border: 1px solid ${({ bgColor }) => `${bgColor}`};
  border-radius: 1em;
  font-size: 12px;
  background-color: ${({ bgColor }) => `${bgColor}`};
  color: ${({ textColor }) => `${textColor}`};
  display: flex;
  justify-content: center;
  align-items: center;
`;

const Description = styled.span`
  color: #6e7191;
`;

const ButtonWrapper = styled.div`
  display: flex;
  gap: 1em;
`;

const EditButton = styled.button`
  display: flex;
  gap: 0.5em;
  color: #4e4b66;
  background-color: transparent;
  border: none;
  cursor: pointer;
`;

const DeleteButton = styled.button`
  display: flex;
  gap: 0.5em;
  color: #ff3b30;
  background-color: transparent;
  border: none;
  cursor: pointer;
`;

export default LabelDetail;
