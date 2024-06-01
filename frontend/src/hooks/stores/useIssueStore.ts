import { create } from "zustand";

export interface LabelResponse {
  labelId: number;
}

export interface Headline {
  issueId: number;
  title: string;
  author: string;
  publishedAt: string;
  isClosed: boolean;
  labels: LabelResponse[];
  milestoneId: number | null;
}

interface IssueCounts {
  openIssueCount: number;
  closeIssueCount: number;
}

export interface Label {
  labelId: number;
  labelName: string;
  labelBgColor: string;
  labelTextColor: string;
}

export interface Milestone {
  milestoneId: number;
  title: string;
}

interface IssueStoreState {
  issues: Headline[];
  openIssueCount: number;
  closeIssueCount: number;
  labels: Label[];
  milestones: Milestone[];
  users: string[];
  setIssues: (issues: Headline[]) => void;
  setIssueCounts: ({ openIssueCount, closeIssueCount }: IssueCounts) => void;
  setLabels: (labels: Label[]) => void;
  setMilestones: (milestones: Milestone[]) => void;
  setUsers: (users: string[]) => void;
}

const useIssueStore = create<IssueStoreState>((set) => ({
  issues: [],
  openIssueCount: 0,
  closeIssueCount: 0,
  labels: [],
  milestones: [],
  users: [],
  setIssues: (issues: Headline[]) => set(() => ({ issues })),
  setIssueCounts: ({ openIssueCount, closeIssueCount }: IssueCounts) =>
    set(() => ({ openIssueCount, closeIssueCount })),
  setLabels: (labels: Label[]) => set(() => ({ labels })),
  setMilestones: (milestones: Milestone[]) => set(() => ({ milestones })),
  setUsers: (users: string[]) => set(() => ({ users })),
}));

export default useIssueStore;
