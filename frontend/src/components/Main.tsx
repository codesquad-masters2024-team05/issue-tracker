import styled from "styled-components";
import Header from "./header/Header";
import Filter from "./filter/Filter";
import IssueList from "./issue/list/IssueList";

function Main() {
  return (
    <Wrapper>
      <Header />
      <Filter />
      <IssueList />
    </Wrapper>
  );
}

const Wrapper = styled.div`
  display: block;
`;

export default Main;
