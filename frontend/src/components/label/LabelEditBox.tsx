import styled from "styled-components";
import plusIcon from "../../img/icon/plusIcon_dark.svg";
import submitIcon from "../../img/icon/editSubmit.svg";
import { LabelDetailType } from "../../contexts/LabelContext";
import useLabelEdit from '../../hooks/logics/useLabelEdit';

type EditType = "new" | "edit";

interface LabelEditBoxProps {
  editType: EditType;
  labelId?: number;
  content?: LabelDetailType;
  closeEditBox: () => void;
}

const defaultContent = {
  labelId: 0,
  labelName: "Label",
  description: "",
  textColor: "#000000",
  bgColor: "#FFFFFF",
};

function LabelEditBox({ editType, labelId = 0, content = defaultContent, closeEditBox }: LabelEditBoxProps) {
  const {
    labelName,
    description,
    bgColor,
    textColor,
    handleLabelNameChange,
    handleDescriptionChange,
    handleBgColorChange,
    handleTextColorChange,
    handleSubmitClick,
  } = useLabelEdit(editType, labelId, content, closeEditBox);

  return (
    <Wrapper type={editType}>
      <Title>{editType === "new" ? "새로운 레이블 추가" : "레이블 편집"}</Title>
      <Content>
        <LabelPreviewBox>
          <LabelPreview bgColor={bgColor} textColor={textColor}>
            {labelName}
          </LabelPreview>
        </LabelPreviewBox>
        <FormWrapper>
          <Form>
            <FormType>이름</FormType>
            <FormInput value={labelName} onChange={handleLabelNameChange} required />
          </Form>
          <Form>
            <FormType>설명(선택)</FormType>
            <FormInput value={description} onChange={handleDescriptionChange} required />
          </Form>
          <ColorFormWrapper>
            <BackgroundColorForm>
              <FormType>배경 색상</FormType>
              <FormInput value={bgColor} onChange={handleBgColorChange} required />
            </BackgroundColorForm>
            <TextColorSelection value={textColor} onChange={handleTextColorChange}>
              <TextColorOption value="#000000">어두운 색</TextColorOption>
              <TextColorOption value="#FFFFFF">밝은 색</TextColorOption>
            </TextColorSelection>
          </ColorFormWrapper>
        </FormWrapper>
      </Content>
      <ButtonWrapper>
        <CancelButton onClick={closeEditBox}>
          <CancelImage src={plusIcon} />
          취소
        </CancelButton>
        <SubmitButton onClick={handleSubmitClick}>
          <img src={submitIcon} />
          편집 완료
        </SubmitButton>
      </ButtonWrapper>
    </Wrapper>
  );
}

const Wrapper = styled.div<{ type: EditType }>`
  width: 80em;
  box-sizing: border-box;
  padding: 2em;
  display: flex;
  flex-direction: column;
  gap: 1em;
  ${({ type }) => type === "edit" && "border-top: 1px solid #d9dbe9;"}
`;

const Title = styled.span`
  font-size: 1.25em;
  font-weight: 700;
`;

const Content = styled.div`
  display: flex;
  gap: 1em;
`;

const LabelPreviewBox = styled.div`
  width: 18em;
  height: 9.5em;
  display: flex;
  justify-content: center;
  align-items: center;
  border: 1px solid #d9dbe9;
  border-radius: 0.75em;
`;

const LabelPreview = styled.div<{ bgColor: string; textColor: string }>`
  height: 1.5em;
  padding: 0 1em;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: ${({ bgColor }) => bgColor};
  color: ${({ textColor }) => textColor};
  border: 1px solid #d9dbe9;
  border-radius: 0.75em;
`;

const FormWrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1em;
`;

const ColorFormWrapper = styled.div`
  display: flex;
  align-items: center;
  gap: 1em;
`;

const BackgroundColorForm = styled.div`
  box-sizing: border-box;
  width: 15em;
  height: 2.5em;
  display: flex;
  gap: 4px;
  padding: 0 1em;
  background-color: #eff0f6;
  border: 1px solid #eff0f6;
  border-radius: 12px;
`;

const TextColorSelection = styled.select`
  width: 6em;
  display: flex;
  font-size: 16px;
  border: none;
  background-color: transparent;
`;

const TextColorOption = styled.option``;

const ButtonWrapper = styled.div`
  display: flex;
  justify-content: end;
  gap: 1em;
`;

const CancelButton = styled.button`
  width: 128px;
  height: 40px;
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 0.5em;
  border: 1px solid #595959;
  border-radius: 12px;
  background-color: transparent;
  color: #595959;
  cursor: pointer;
`;

const CancelImage = styled.img`
  transform: rotate(45deg);
`;

const SubmitButton = styled.button`
  width: 128px;
  height: 40px;
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 0.5em;
  border: 1px solid #595959;
  border-radius: 12px;
  background-color: #595959;
  color: white;
  cursor: pointer;
`;

const Form = styled.div`
  box-sizing: border-box;
  width: 56em;
  height: 2.5em;
  display: flex;
  gap: 4px;
  padding: 0 1em;
  background-color: #eff0f6;
  border: 1px solid #eff0f6;
  border-radius: 12px;
`;

const FormType = styled.span`
  width: 6em;
  font-size: 0.75em;
  display: flex;
  align-items: center;
`;

const FormInput = styled.input`
  width: calc(100% - 6em);
  box-sizing: border-box;
  background-color: transparent;
  border: none;
`;

export default LabelEditBox;
