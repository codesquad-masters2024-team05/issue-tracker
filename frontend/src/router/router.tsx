import { createBrowserRouter } from "react-router-dom";
import Login from "../components/login/Login";
import Registration from "../components/registration/Registration";
import Main from "../components/Main";
import IssueCreator from "../components/issue/new/IssueCreator";
import IssueDetail from "../components/issue/detail/IssueDetail";
import LabelList from "../components/label/LabelList";
import MilestoneList from "../components/milestone/MilestoneList";
import { LabelProvider } from "../contexts/LabelContext";
import { MilestoneProvider } from "../contexts/MilestoneContext";

export const router = createBrowserRouter([
  {
    path: "/",
    element: <Main />,
  },
  {
    path: "/login",
    element: <Login />,
  },
  {
    path: "/registration",
    element: <Registration />,
  },
  {
    path: "/new",
    element: <IssueCreator />,
  },
  {
    path: "/issue/:issueId",
    element: <IssueDetail />,
  },
  {
    path: "/labels",
    element: (
      <LabelProvider>
        <LabelList />
      </LabelProvider>
    ),
  },
  {
    path: "/milestones",
    element: (
      <MilestoneProvider>
        <MilestoneList />
      </MilestoneProvider>
    ),
  },
]);
