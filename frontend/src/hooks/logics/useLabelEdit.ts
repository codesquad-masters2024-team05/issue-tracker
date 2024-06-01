import { ChangeEvent, Dispatch, SetStateAction, useContext, useState } from "react";
import { LabelDetailType, LabelContext } from "../../contexts/LabelContext";
import { LabelContent, postNewLabel, sendLabelsRequest, sendPutLabelRequest } from "../../api/LabelAPI";

type EditType = "new" | "edit";

const defaultContent = {
  labelId: 0,
  labelName: "Label",
  description: "",
  textColor: "#000000",
  bgColor: "#FFFFFF",
};

const useLabelEdit = (
  type: EditType,
  labelId: number = 0,
  content: LabelDetailType = defaultContent,
  handleCancelClick: () => void
) => {
  const { setLabels } = useContext(LabelContext);
  const [labelName, setLabelName] = useState(content.labelName);
  const [description, setDescription] = useState(content.description);
  const [bgColor, setBgColor] = useState(content.bgColor);
  const [textColor, setTextColor] = useState(content.textColor);

  const handleInputChange =
    (setter: Dispatch<SetStateAction<string>>) =>
    ({ target: { value } }: ChangeEvent<HTMLInputElement | HTMLSelectElement>) =>
      setter(value);

  const fetchOnSubmit = async (labelData: LabelContent) =>
    type === "new" ? postNewLabel(labelData) : sendPutLabelRequest(labelId, labelData);

  const updateLabels = async () => {
    const data = await sendLabelsRequest();
    setLabels(data);
    handleCancelClick();
  };

  const handleSubmitClick = async () => {
    const labelData = { labelName, description, bgColor, textColor };

    await fetchOnSubmit(labelData);
    updateLabels();
  };

  return {
    labelName,
    description,
    bgColor,
    textColor,
    handleLabelNameChange: handleInputChange(setLabelName),
    handleDescriptionChange: handleInputChange(setDescription),
    handleBgColorChange: handleInputChange(setBgColor),
    handleTextColorChange: handleInputChange(setTextColor),
    handleSubmitClick,
  };
};

export default useLabelEdit;
