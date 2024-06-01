import { useCallback, useContext, useState } from "react";
import { LabelContext } from "../../contexts/LabelContext";
import { sendDeleteLabelRequest, sendLabelsRequest } from "../../api/LabelAPI";

const useLabelDetail = (labelId: number) => {
  const { setLabels } = useContext(LabelContext);
  const [isToEdit, setIsToEdit] = useState(false);

  const toggleEdit = useCallback(() => {
    setIsToEdit(prev => !prev);
  }, []);

  const handleDeleteClick = useCallback(async () => {
    await sendDeleteLabelRequest(labelId);
    const updatedLabels = await sendLabelsRequest();
    setLabels(updatedLabels);
  }, [labelId, setLabels]);

  return {
    isToEdit,
    toggleEdit,
    handleDeleteClick,
  };
}

export default useLabelDetail;