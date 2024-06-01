import styled from "styled-components";
import userIcon from "../../../img/icon/userIcon.png";
import editIcon from "../../../img/icon/editIcon.svg";
import smileIcon from "../../../img/icon/smileIcon.svg";
import dateUtils from "../../../utils/DateUtils";

export interface CommentProps {
  commentId: number;
  author: string;
  isAuthor: boolean;
  content: string;
  publishedAt: string;
}

const IMAGE_REGEX = /<img src="(.*?)" \/>/g;

const parseContent = (content: string) =>
  content.split("\n").map((line) => {
    const images = Array.from(line.matchAll(IMAGE_REGEX));
    let textWithoutImages = line.replace(IMAGE_REGEX, "");

    return (
      <span>
        {textWithoutImages}
        {images.map((imgMatch) => (
          <ContentImage src={imgMatch[1]} />
        ))}
        <br />
      </span>
    );
  });

function Comment({ commentId, author: commentor, isAuthor, content, publishedAt }: CommentProps) {
  const contentTexts = parseContent(content);

  return (
    <CommentTable>
      <InfoTab>
        <UserInfo>
          <img src={userIcon} />
          <UserName>{commentor}</UserName>
          <PublishedAt>{dateUtils.parseTimeDifference(publishedAt)}</PublishedAt>
        </UserInfo>
        <ToggleWrapper>
          {isAuthor && (
            <AuthorTag>
              <span>작성자</span>
            </AuthorTag>
          )}
          <ToggleButton>
            <img src={editIcon} />
            <span>편집</span>
          </ToggleButton>
          <ToggleButton>
            <img src={smileIcon} />
            <span>반응</span>
          </ToggleButton>
        </ToggleWrapper>
      </InfoTab>
      <Content>{contentTexts}</Content>
    </CommentTable>
  );
}

const CommentTable = styled.div`
  width: 100%;
  border: 1px solid #d9dbe9;
  border-radius: 0.725em;
  overflow: hidden;
`;

const InfoTab = styled.div`
  padding: 0 1em;
  height: 4em;
  background-color: transparent;
  display: flex;
  justify-content: space-between;
  align-items: center;
`;

const UserInfo = styled.div`
  display: flex;
  align-items: center;
  gap: 0.5em;
`;

const UserName = styled.span`
  color: #4e4b66;
`;

const PublishedAt = styled.span`
  color: #6e7191;
`;

const ToggleWrapper = styled.div`
  height: 1.5em;
  display: flex;
  align-items: center;
  gap: 1em;
`;

const AuthorTag = styled.div`
  height: 2em;
  padding: 0 0.66em;
  font-size: 0.75em;
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 1em;
  color: #6e7191;
  background-color: #eff0f6;
  border: 1px solid #d9dbe9;
  border-radius: 1em;
`;

const ToggleButton = styled.button`
  height: 2.66em;
  padding: 0;
  font-size: 0.75em;
  display: flex;
  gap: 0.33em;
  align-items: center;
  background-color: transparent;
  border: none;
`;

const Content = styled.div`
  box-sizing: border-box;
  padding: 1.5em;
  min-height: 4em;
  border-top: 1px solid #d9dbe9;
  background-color: #fff;
`;

const ContentImage = styled.img`
  width: fit-content;
  max-height: 16em;
`;

export default Comment;
