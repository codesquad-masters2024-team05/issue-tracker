import styled from "styled-components";
import filterClickIcon from "../../img/icon/filterClickIcon.svg";
import searchIcon from "../../img/icon/searchIcon.svg";
import labelIcon from "../../img/icon/labelIcon.svg";
import milestoneIcon from "../../img/icon/milestoneIcon.svg";
import plusIcon from "../../img/icon/plusIcon.svg";
import { useNavigate } from "react-router-dom";
import useFilterLogic from "../../hooks/logics/useFilterLogic";

function Filter() {
  const { labels, milestones } = useFilterLogic();
  const navigate = useNavigate();

  return (
    <>
      <FilterTab>
        <FilterBox>
          <MyFilterBar>
            <MyFilterTitle>필터</MyFilterTitle>
            <FilterIcon src={filterClickIcon} />
          </MyFilterBar>
          <SearchBar>
            <SmallIcon src={searchIcon} />
            <SearchTitle>is:issue is:open</SearchTitle>
          </SearchBar>
        </FilterBox>
        <RightBox>
          <FilterBox>
            <LabelBar>
              <SmallIcon src={labelIcon} />
              <LargeTitle onClick={() => navigate("/labels")}>레이블({(labels && labels.length) || 0})</LargeTitle>
            </LabelBar>
            <MilestoneBar>
              <SmallIcon src={milestoneIcon} />
              <LargeTitle onClick={() => navigate("/milestones")}>
                마일스톤({(milestones && milestones.length) || 0})
              </LargeTitle>
            </MilestoneBar>
          </FilterBox>
          <NewIssueButton onClick={() => navigate("/new")}>
            <img src={plusIcon} />
            <span>이슈 작성</span>
          </NewIssueButton>
        </RightBox>
      </FilterTab>
    </>
  );
}

const FilterTab = styled.div`
  display: flex;
  width: 100%;
  height: 40px;
  margin-top: 2em;
  justify-content: space-between;
`;

const FilterBox = styled.div`
  display: flex;
  border: 1px solid #d9dbe9;
  border-radius: 12px;
  overflow: hidden;
`;

const RightBox = styled.div`
  display: flex;
  gap: 1em;
`;

const MyFilterBar = styled.div`
  width: 5em;
  padding: 0 1.5em;
  display: flex;
  justify-content: space-between;
  align-items: center;
`;

const MyFilterTitle = styled.span`
  color: #4e4b66;
`;

const FilterIcon = styled.img`
  width: 0.5em;
  height: 0.25em;
`;

const SearchBar = styled.div`
  width: 24em;
  padding: 0 1.5em;
  display: flex;
  align-items: center;
  gap: 0.5em;
  border-left: 1px solid #d9dbe9;
  background-color: #eff0f6;
`;

const SmallIcon = styled.img`
  width: 1em;
  height: 1em;
`;

const SearchTitle = styled.span`
  height: 1em;
  color: #6e7191;
`;

const LargeTitle = styled.span`
  height: 1em;
  margin-left: 0.5em;
  font-size: 1em;
`;

const LabelBar = styled.div`
  width: 8.4em;
  padding: 0 0.725em;
  display: flex;
  justify-content: center;
  align-items: center;
  color: #4e4b66;
`;

const MilestoneBar = styled.div`
  width: 8.4em;
  padding: 0 0.725em;
  display: flex;
  justify-content: center;
  align-items: center;
  border-left: 1px solid #d9dbe9;
  color: #4e4b66;
`;

const NewIssueButton = styled.button`
  width: 9.5em;
  padding: 0 1em;
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 0.5em;
  color: white;
  background-color: #595959;
  border: 0;
  border-radius: 0.725em;
  cursor: pointer;
`;

export default Filter;
