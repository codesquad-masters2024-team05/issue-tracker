import styled from "styled-components";
import useMilestoneEdit, { MilestoneEditBoxProps } from "../../hooks/logics/useMilestoneEdit";

type EditType = "new" | "edit";

function MilestoneEditBox(props: MilestoneEditBoxProps) {
  const { editType, titleRef, deadlineRef, descriptionRef, errorMessage, closeEditBox, handleSubmitClick } =
    useMilestoneEdit(props);

  return (
    <Wrapper type={editType}>
      <Title>{editType === "new" ? "새로운 마일스톤 추가" : "마일스톤 편집"}</Title>
      <Content>
        <FormWrapper>
          <Form>
            <FormType>이름</FormType>
            <FormInput ref={titleRef} required />
          </Form>
          <Form>
            <FormType>완료일(선택)</FormType>
            <FormInput ref={deadlineRef} required />
          </Form>
        </FormWrapper>
        <FormWrapper>
          <DescriptionForm>
            <FormType>설명(선택)</FormType>
            <FormInput ref={descriptionRef} required />
          </DescriptionForm>
        </FormWrapper>
      </Content>
      <BottomWrapper>
        <ErrorMessage>{errorMessage && errorMessage}</ErrorMessage>
        <ButtonWrapper>
          <CancelButton onClick={closeEditBox}>
            <CancelImage />
            취소
          </CancelButton>
          <SubmitButton onClick={handleSubmitClick}>
            <img />
            완료
          </SubmitButton>
        </ButtonWrapper>
      </BottomWrapper>
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
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 1em;
`;

const FormWrapper = styled.div`
  display: flex;
  gap: 1em;
`;

const Form = styled.div`
  box-sizing: border-box;
  width: 100%;
  height: 2.5em;
  display: flex;
  gap: 4px;
  padding: 0 1em;
  background-color: #eff0f6;
  border: 1px solid #eff0f6;
  border-radius: 12px;
`;

const FormType = styled.span`
  width: 7em;
  font-size: 0.75em;
  display: flex;
  align-items: center;
`;

const FormInput = styled.input`
  width: 100%;
  box-sizing: border-box;
  background-color: transparent;
  border: none;
`;

const DescriptionForm = styled.div`
  box-sizing: border-box;
  width: 100%;
  height: 2.5em;
  display: flex;
  gap: 4px;
  padding: 0 1em;
  background-color: #eff0f6;
  border: 1px solid #eff0f6;
  border-radius: 12px;
`;

const BottomWrapper = styled.div`
  box-sizing: border-box;
  padding-left: 1em;
  display: flex;
  justify-content: space-between;
  align-items: center;
`;

const ErrorMessage = styled.span`
  color: red;
`;

const ButtonWrapper = styled.div`
  display: flex;
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

export default MilestoneEditBox;
