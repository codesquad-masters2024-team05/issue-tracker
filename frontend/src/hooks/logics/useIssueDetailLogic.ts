import { useEffect, useRef, useState } from "react";
import { useMutation, useQuery, useQueryClient } from "react-query";
import { closeIssue, openIssue, postNewComment, sendIssueRequestById, sendTitleEditRequest } from "../../api/IssueAPI";
import { useParams } from "react-router-dom";
import useUserStore from "../stores/useUserStore";

interface Comment {
  commentId: number;
  content: string;
  author: string;
  publishedAt: string;
}

interface IssueContent {
  title: string;
  author: string;
  publishedAt: string;
  comments: Comment[];
  closed: boolean;
}

const useIssueDetailLogic = () => {
  const client = useQueryClient();
  const { userId } = useUserStore();
  const commentRef = useRef<HTMLTextAreaElement>(null);
  const [isCommentSubmitable, setIsCommentSubmitable] = useState(false);
  const [isTitleSubmitable, setIsTitleSubmitable] = useState(false);
  const [isTitleEditable, setIsTitleEditable] = useState(false);
  const [issueContent, setIssueContent] = useState<IssueContent | null>(null);
  const titleInputRef = useRef<HTMLInputElement>(null);
  const { issueId } = useParams();
  const numericIssueId = Number(issueId);

  useQuery([`issue-${numericIssueId}`, issueId], () => sendIssueRequestById(numericIssueId), {
    onSuccess: (data) => setIssueContent(data),
  });

  const { mutate: fetchOpenIssue } = useMutation(openIssue, {
    onSuccess: () => client.invalidateQueries(`issue-${issueId}`),
  });

  const { mutate: fetchCloseIssue } = useMutation(closeIssue, {
    onSuccess: () => client.invalidateQueries(`issue-${issueId}`),
  });

  const { mutate: fetchTitleEdit } = useMutation(sendTitleEditRequest, {
    onSuccess: () => {
      client.invalidateQueries(`issue-${issueId}`);
      setIsTitleEditable(false);
    },
    onError: () => setIsTitleEditable(false),
  });

  const { mutate: fetchNewComment } = useMutation(postNewComment, {
    onSuccess: () => client.invalidateQueries(`issue-${issueId}`),
  });

  const handleStateToggleClick = () => {
    if (issueContent?.closed) {
      fetchOpenIssue(numericIssueId);
      return;
    }

    fetchCloseIssue(numericIssueId);
  };

  const handleCommentChange = () => {
    const comment = commentRef.current?.value;
    const currentIsSubmitable = !!comment;

    if (currentIsSubmitable === !isCommentSubmitable) setIsCommentSubmitable(currentIsSubmitable);
  };
  const handleTitleChange = () => {
    const titleInput = titleInputRef.current?.value;
    const currentIsSubmitable = !!titleInput;

    if (currentIsSubmitable === !isTitleSubmitable) setIsTitleSubmitable(currentIsSubmitable);
  };
  const handleCommentSubmit = () => {
    const comment = commentRef.current?.value;

    if (comment) fetchNewComment({ issueId: numericIssueId, author: userId, content: comment });
  };
  const handleTitleEditSubmit = () => {
    const titleInput = titleInputRef.current?.value;

    if (titleInput) {
      fetchTitleEdit({ issueId: numericIssueId, title: titleInput });
      titleInputRef.current.value = "";
    }
  };

  useEffect(() => setIsTitleSubmitable(false), [isTitleEditable]);

  return {
    issueId,
    issueContent,
    commentRef,
    titleInputRef,
    isTitleSubmitable,
    isCommentSubmitable,
    isTitleEditable,
    setIsTitleEditable,
    setIsTitleSubmitable,
    setIsCommentSubmitable,
    handleCommentChange,
    handleTitleChange,
    handleStateToggleClick,
    handleCommentSubmit,
    handleTitleEditSubmit,
  };
};

export default useIssueDetailLogic;
