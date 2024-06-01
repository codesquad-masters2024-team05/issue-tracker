import { Meta, StoryFn } from "@storybook/react/*";
import Comment, { CommentProps } from "../components/issue/detail/Comment";

export default {
  title: "Components/Comment",
  component: Comment,
} as Meta;

const Template: StoryFn<CommentProps> = (args) => <Comment {...args} />;

export const DefaultComment = Template.bind({});
DefaultComment.args = {
  commentId: 1,
  author: "schnee",
  isAuthor: true,
  content: "안녕하세요 슈니입니다\n안녕하십니까 슈니입니다\n안녕하세요\n안녕하세요",
  publishedAt: "2024-05-17 11:23:31.0",
};
