import { Meta, StoryFn } from "@storybook/react";
import IssueTab, { IssueTabProps } from "../components/issue/list/IssueTab";
import { QueryClient, QueryClientProvider } from "react-query";

const queryClient = new QueryClient();

export default {
  title: "Issue/IssueTab",
  component: IssueTab,
  argTypes: {
    focusedTab: { control: "text" },
    setFocusedTab: { action: "clicked" },
  },
  decorators: [
    (Story: StoryFn) => (
      <QueryClientProvider client={queryClient}>
        <Story />
      </QueryClientProvider>
    ),
  ],
} as Meta;

const Template: StoryFn<IssueTabProps> = (args) => <IssueTab {...args} />;

export const DefaultTab = Template.bind({});
DefaultTab.args = {
  focusedTab: "open",
};