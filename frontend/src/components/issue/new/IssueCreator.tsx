import styled, { keyframes } from "styled-components";
import Header from "../../header/Header";
import CreatorForm from "./CreatorForm";
<<<<<<< HEAD:frontend/src/components/issue/new/IssueCreator.tsx
import userIcon from "../../../img/icon/userIcon.png";
import plusIcon from "../../../img/icon/plusIcon_dark.svg";
import uploadIcon from "../../../img/icon/uploadIcon.svg";
import Sidebar from "../Sidebar";
import useIssueCreatorLogic from "../../../hooks/logics/useIssueCreatorLogic";

function IssueCreator() {
  const {
    titleRef,
    commentRef,
    fileInputRef,
    commentCount,
    isSubmitable,
    handleOnChange,
    handleUploadClick,
    handleFileChange,
    handleCancel,
    handleSubmit,
  } = useIssueCreatorLogic();
=======
import userIcon from "../../img/icon/userIcon.png";
import plusIcon from "../../img/icon/plusIcon_dark.svg";
import Sidebar from "../issue/Sidebar";
<<<<<<< be-dev
import { useEffect, useRef, useState } from "react";
import useUserStore from "../../hooks/useUserStore";
import { useNavigate } from "react-router-dom";
import { postNewIssue } from "../../api/IssueAPI";

function IssueCreator() {
  const { userId } = useUserStore();
  const titleRef = useRef<HTMLTextAreaElement>(null);
  const commentRef = useRef<HTMLTextAreaElement>(null);
  const [commentLength, setCommentLength] = useState(0);
  const [isSubmitable, setIsSubmitable] = useState(false);
  const navigate = useNavigate();

  const handleOnChange = () => {
    const title = titleRef.current?.value;
    const content = commentRef.current?.value;
    const currentIsSubmitable = !!(title && content);

    if (currentIsSubmitable === !isSubmitable) setIsSubmitable(currentIsSubmitable);
  };
  const handleCancel = () => navigate("/");
  const handleSubmit = () => {
    const title = titleRef.current?.value;
    const content = commentRef.current?.value;

    // 추후 /issue/{issueId} 로 라우팅 예정
    if (title && content) postNewIssue({ title, content, userId }).then(() => navigate("/"));
  };

  useEffect(() => {
    const handleCommentChange = () => {
      const length = commentRef.current?.value.length || 0;
      setCommentLength(length);
      handleOnChange();
    };
    const commentElement = commentRef.current;
    commentElement?.addEventListener("input", handleCommentChange);

    return () => {
      commentElement?.removeEventListener("input", handleCommentChange);
    };
  }, []);
=======
import useIssueCreatorLogic from "../../hooks/logics/useIssueCreatorLogic";

function IssueCreator() {
  const { titleRef, commentRef, commentCount, isSubmitable, handleOnChange, handleCancel, handleSubmit } =
    useIssueCreatorLogic();
>>>>>>> team-05
>>>>>>> fbddf75021ef0567b96dc573d978bbe094d13531:frontend/src/components/creator/IssueCreator.tsx

  return (
    <Wrapper>
      <Header />
      <TitleWrapper>
        <PageTitle>새로운 이슈 작성</PageTitle>
      </TitleWrapper>
      <BodyBoundary />
      <BodyWrapper>
        <UserIcon src={userIcon} />
        <FormWrapper>
          <CreatorForm ref={titleRef} labelText="제목" height="3.5em" onChange={handleOnChange} />
<<<<<<< HEAD:frontend/src/components/issue/new/IssueCreator.tsx
          <CommentWrapper>
            <CreatorForm
              ref={commentRef}
              labelText="코멘트를 입력하세요."
              height="calc(100% - 8em)"
              onChange={handleOnChange}
            />
            <ExtensionWrapper>
              <ContentWordCount key={`word-count-${commentCount}`}>띄어쓰기 포함 {commentCount}자</ContentWordCount>
              <DashedLine />
              <FileUploadWrapper>
                <img src={uploadIcon} />
                <FileUploadInput type="file" ref={fileInputRef} onChange={handleFileChange} />
                <FileUploadButton onClick={handleUploadClick}>파일 첨부하기</FileUploadButton>
              </FileUploadWrapper>
            </ExtensionWrapper>
          </CommentWrapper>
=======
          <CreatorForm ref={commentRef} labelText="코멘트를 입력하세요." height="100%" onChange={handleOnChange} />
          <ExtensionWrapper>
<<<<<<< be-dev
          <ContentWordCount key={`word-count-${commentLength}`}>띄어쓰기 포함 {commentLength}자</ContentWordCount>
          <DashedLine />
          <FileImageButton><img /> 파일 첨부하기</FileImageButton>
=======
            <ContentWordCount key={`word-count-${commentCount}`}>띄어쓰기 포함 {commentCount}자</ContentWordCount>
            <DashedLine />
            <FileImageButton>
              <img /> 파일 첨부하기
            </FileImageButton>
>>>>>>> team-05
          </ExtensionWrapper>
>>>>>>> fbddf75021ef0567b96dc573d978bbe094d13531:frontend/src/components/creator/IssueCreator.tsx
        </FormWrapper>
        <Sidebar />
      </BodyWrapper>
      <BodyBoundary />
      <ButtonsWrapper>
        <SubmitButton isSubmitable={isSubmitable} onClick={handleSubmit}>
          완료
        </SubmitButton>
        <CancelWrapper onClick={handleCancel}>
          <CancelIcon src={plusIcon} />
          <CancelText>작성 취소</CancelText>
        </CancelWrapper>
      </ButtonsWrapper>
    </Wrapper>
  );
}

const FadeOut = keyframes`
  from {
    opacity: 1;
  } to {
    opacity: 0;
  }
<<<<<<< be-dev
`
=======
`;
>>>>>>> team-05

const Wrapper = styled.div`
  position: block;
`;

const TitleWrapper = styled.div`
  margin-top: 2em;
`;

const PageTitle = styled.span`
  font-size: 2em;
  font-weight: 700;
`;

const BodyBoundary = styled.hr`
  margin: 2em 0;
  height: 1px;
  color: #eceef5;
`;

const BodyWrapper = styled.div`
  width: 80em;
  height: 38em;
  display: flex;
  gap: 1.5em;
`;

const UserIcon = styled.img`
  width: 2em;
  height: 2em;
`;

const FormWrapper = styled.div`
  width: 57em;
  height: 100%;
  display: flex;
  flex-direction: column;
  gap: 0.5em;
`;

const ButtonsWrapper = styled.div`
  display: flex;
  flex-direction: row-reverse;
  gap: 4em;
`;

const CancelWrapper = styled.div`
  width: fit-content;
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 0.5em;
  cursor: pointer;
`;

const CancelIcon = styled.img`
  transform: rotate(45deg);
`;

const CancelText = styled.span`
  color: #4e4b66;
`;

const SubmitButton = styled.button<{ isSubmitable: boolean }>`
  width: 12em;
  height: 2.8em;
  padding: 0 1.2em;
  font-size: 1.25em;
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 0.5em;
  color: white;
  background-color: #595959;
  border: 0;
  border-radius: 0.725em;
  cursor: pointer;
  opacity: ${({ isSubmitable }) => (isSubmitable ? "1" : "0.5")};
  transition: all 0.5s ease;
`;

const CommentWrapper = styled.div`
  height: 100%;
  background-color: #eceef5;
  border: 1px solid transparent;
  border-radius: 0.75em;
`;

const ExtensionWrapper = styled.div`
  width: 100%;
  height: 8em;
  display: flex;
  flex-direction: column;
`;

const ContentWordCount = styled.span`
  padding: 0 2em;
  text-align: right;
  animation: ${FadeOut} 2s ease-out;
  animation-fill-mode: forwards;
`;

const DashedLine = styled.hr`
<<<<<<< be-dev
  border-top: 1px dashed #D9DBE9;
=======
  border-top: 1px dashed #d9dbe9;
>>>>>>> team-05
  border-bottom: none;
  margin: 1.5em 0;
`;

const FileUploadWrapper = styled.div`
  display: flex;
  padding: 0 1em;
  gap: 0.5em;
`;

const FileUploadInput = styled.input`
  display: none;
`;

const FileUploadButton = styled.button`
  width: fit-content;
  background-color: transparent;
  border: none;
  text-align: left;
<<<<<<< be-dev
`
=======
`;
>>>>>>> team-05

export default IssueCreator;
