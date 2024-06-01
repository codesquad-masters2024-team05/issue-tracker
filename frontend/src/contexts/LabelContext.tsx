import { Dispatch, FC, PropsWithChildren, SetStateAction, createContext, useState } from "react";

interface LabelState {
  isToAdd: boolean;
  isToEdit: boolean;
}

export interface LabelDetailType {
  labelId: number;
  labelName: string;
  description: string;
  textColor: string;
  bgColor: string;
}

type LabelContextType = {
  labelState: LabelState;
  setLabelState: Dispatch<SetStateAction<LabelState>>;
  labels: LabelDetailType[];
  setLabels: Dispatch<SetStateAction<LabelDetailType[]>>;
};

const defaultState: LabelState = {
  isToAdd: false,
  isToEdit: false,
};

export const LabelContext = createContext<LabelContextType>({
  labelState: defaultState,
  setLabelState: () => {},
  labels: [],
  setLabels: () => {},
});

export const LabelProvider: FC<PropsWithChildren> = ({ children }) => {
  const [labels, setLabels] = useState<LabelDetailType[]>([]);
  const [labelState, setLabelState] = useState<LabelState>(defaultState);

  return (
    <LabelContext.Provider value={{ labelState, setLabelState, labels, setLabels }}>
      {children}
    </LabelContext.Provider>
  );
};
