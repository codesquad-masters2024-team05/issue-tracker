import React from "react";
import IssueHeadline from "../components/issue/list/IssueHeadline";
import { MemoryRouter } from "react-router-dom";
import { StoryFn } from "@storybook/react";
import { Headline } from '../hooks/stores/useIssueStore';

export default {
  title: "Issue/IssueHeadline",
  component: IssueHeadline,
  decorators: [
    (Story: StoryFn) => (
      <MemoryRouter>
        <Story />
      </MemoryRouter>
    ),
  ],
};

interface ReturnType extends React.FC<Headline> {
  args?: Headline;
}

const Template: ReturnType = (props: Headline) => <IssueHeadline {...props} />;

export const IssueDetail = Template.bind({});
IssueDetail.args = {
  issueId: 1,
  title: "FE 이슈트래커 개발",
  author: "schnee",
  publishedAt: "2024-05-14T12:34:56Z",
  isClosed: false,
  labels: [
    {
      labelId: 1,
    },
    {
      labelId: 2,
    },
  ],
  milestoneId: 1,
};
