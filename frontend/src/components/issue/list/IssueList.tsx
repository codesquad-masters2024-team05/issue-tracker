import styled from "styled-components";
import IssueTab from "./IssueTab";
import IssueHeadline from "./IssueHeadline";
import { MutableRefObject } from "react";
import { Headline } from "../../../hooks/stores/useIssueStore";
import useIssueListLogic, { IssueType } from "../../../hooks/logics/useIssueListLogic";
import Loading from "../../login/Loading";

const renderHeadlines = (issues: Headline[], lastIssueRef: MutableRefObject<null>) =>
  issues.map((issue, index) =>
    index === issues.length - 1 ? (
      <IssueHeadline ref={lastIssueRef} key={`issue-headline-${issue.issueId}`} {...issue} />
    ) : (
      <IssueHeadline key={`issue-headline-${issue.issueId}`} {...issue} />
    )
  );

function IssueList() {
  const {
    focusedTab,
    setFocusedTab,
    issues,
    setIssues,
    lastIssueRef,
    filterQuery: { isLoading },
  } = useIssueListLogic();

  if (isLoading) return <Loading />;

  return (
    <Wrapper>
      <IssueTab
        focusedTab={focusedTab}
        handleFocusedTabClick={(tabDescription: IssueType) => {
          setFocusedTab(tabDescription);
          if (tabDescription !== focusedTab) setIssues([]);
        }}
      />
      <ScrollableArea>{renderHeadlines(issues, lastIssueRef)}</ScrollableArea>
    </Wrapper>
  );
}

const Wrapper = styled.div`
  width: 1280px;
  margin-top: 1.5em;
  border: 1px solid #d9dbe9;
  border-radius: 0.725em;
  overflow: hidden;
`;

const ScrollableArea = styled.div`
  max-height: 42.5em;
  overflow-y: scroll;
  height: calc(100% - 4em);
  &::-webkit-scrollbar {
    display: none;
  }
`;

export default IssueList;