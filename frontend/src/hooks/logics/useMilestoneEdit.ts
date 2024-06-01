import { useEffect, useRef, useState } from "react";
import { useMutation, useQueryClient } from "react-query";
import { MilestoneContent, postNewMilestone, sendPutMilestoneRequest } from "../../api/MilestoneAPI";
import dateUtils from "../../utils/DateUtils";
import { MilestoneDetailType } from "../../contexts/MilestoneContext";

type EditType = "new" | "edit";

export interface MilestoneEditBoxProps {
  editType: EditType;
  milestoneId?: number;
  content?: MilestoneDetailType;
  closeEditBox: () => void;
}

const NAME_ERROR_MESSAGE = "이름을 입력해주세요.";
const DATE_ERROR_MESSAGE = "정확한 형식의 날짜를 입력해주세요. (YYYY. MM. DD)";

const defaultContent = {
  milestoneId: 0,
  title: "",
  description: "",
  deadline: "",
  totalIssue: 0,
  closedIssue: 0,
  isClosed: false,
};

const useMilestoneEdit = ({
  editType,
  milestoneId = 0,
  content = defaultContent,
  closeEditBox,
}: MilestoneEditBoxProps) => {
  const client = useQueryClient();
  const titleRef = useRef<HTMLInputElement>(null);
  const deadlineRef = useRef<HTMLInputElement>(null);
  const descriptionRef = useRef<HTMLInputElement>(null);
  const [errorMessage, setErrorMessage] = useState("");

  const { mutate: fetchNewMilestone } = useMutation(postNewMilestone, {
    onSuccess: () => {
      client.invalidateQueries("milestones");
      closeEditBox();
    },
  });

  const { mutate: fetchPutMilestone } = useMutation(sendPutMilestoneRequest, {
    onSuccess: () => {
      client.invalidateQueries("milestones");
      closeEditBox();
    },
  });

  const fetchOnSubmit = (milestoneContent: MilestoneContent) =>
    editType === "new" ? fetchNewMilestone(milestoneContent) : fetchPutMilestone({ milestoneId, ...milestoneContent });
  const handleSubmitClick = () => {
    const title = titleRef.current?.value;
    const deadline = deadlineRef.current?.value;
    const description = descriptionRef.current?.value;

    if (!title) {
      setErrorMessage(NAME_ERROR_MESSAGE);
      return;
    }

    if (deadline !== "" && !dateUtils.isValidDateText(deadline)) {
      setErrorMessage(DATE_ERROR_MESSAGE);
      return;
    }

    const parsedDeadline = dateUtils.parseDateText(deadline);

    fetchOnSubmit({ title, deadline: parsedDeadline, description });
  };

  useEffect(() => {
    const { title, deadline, description } = content;

    if (titleRef.current) titleRef.current.value = title;
    if (deadlineRef.current && dateUtils.isValidDateText(deadline))
      deadlineRef.current.value = dateUtils.parseTimestampText(deadline);
    if (descriptionRef.current) descriptionRef.current.value = description;
  }, [content]);

  return { editType, titleRef, deadlineRef, descriptionRef, errorMessage, closeEditBox, handleSubmitClick };
};

export default useMilestoneEdit;
