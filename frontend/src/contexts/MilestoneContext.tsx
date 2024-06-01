import { Dispatch, FC, PropsWithChildren, SetStateAction, createContext, useState } from "react";

export interface MilestoneDetailType {
  milestoneId: number;
  title: string;
  description: string;
  deadline: string;
  totalIssue: number;
  closedIssue: number;
  isClosed: boolean;
}

export type MilestoneType = "open" | "close";

type MilestoneContextType = {
  openMilestones: MilestoneDetailType[];
  setOpenMilestones: Dispatch<SetStateAction<MilestoneDetailType[]>>;
  closeMilestones: MilestoneDetailType[];
  setCloseMilestones: Dispatch<SetStateAction<MilestoneDetailType[]>>;
  isToAdd: boolean;
  setIsToAdd: Dispatch<SetStateAction<boolean>>;
  focusedTab: MilestoneType;
  setFocusedTab: Dispatch<SetStateAction<MilestoneType>>;
};

export const MilestoneContext = createContext<MilestoneContextType>({
  openMilestones: [],
  setOpenMilestones: () => {},
  closeMilestones: [],
  setCloseMilestones: () => {},
  isToAdd: false,
  setIsToAdd: () => {},
  focusedTab: "open",
  setFocusedTab: () => {},
});

export const MilestoneProvider: FC<PropsWithChildren> = ({ children }) => {
  const [openMilestones, setOpenMilestones] = useState<MilestoneDetailType[]>([]);
  const [closeMilestones, setCloseMilestones] = useState<MilestoneDetailType[]>([]);
  const [isToAdd, setIsToAdd] = useState(false);
  const [focusedTab, setFocusedTab] = useState<MilestoneType>("open");

  return (
    <MilestoneContext.Provider
      value={{
        openMilestones,
        setOpenMilestones,
        closeMilestones,
        setCloseMilestones,
        isToAdd,
        setIsToAdd,
        focusedTab,
        setFocusedTab,
      }}
    >
      {children}
    </MilestoneContext.Provider>
  );
};
