import { useCallback, useState } from "react";
import { useMutation, useQueryClient } from "react-query";
import { deleteMilestone, toggleMilestoneState } from "../../api/MilestoneAPI";

const useMilestoneDetail = (milestoneId: number) => {
  const client = useQueryClient();
  const [isToEdit, setIsToEdit] = useState(false);

  const toggleEdit = useCallback(() => setIsToEdit((prev) => !prev), []);

  const { mutate: fetchMilestoneState } = useMutation(toggleMilestoneState, {
    onSuccess: () => client.invalidateQueries("milestones"),
  });

  const { mutate: fetchDeleteMilestone } = useMutation(deleteMilestone, {
    onSuccess: () => client.invalidateQueries("milestones"),
  });

  const handleOpenButtonClick = () => fetchMilestoneState({ milestoneType: "open", milestoneId });
  const handleCloseButtonClick = () => fetchMilestoneState({ milestoneType: "close", milestoneId });
  const handleDeleteClick = () => fetchDeleteMilestone(milestoneId);

  return { isToEdit, toggleEdit, handleOpenButtonClick, handleCloseButtonClick, handleDeleteClick };
};

export default useMilestoneDetail;
