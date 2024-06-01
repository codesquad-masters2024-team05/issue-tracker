import styled from "styled-components";
import labelIcon from "../../img/icon/labelIcon.svg";
import milestoneIcon from "../../img/icon/milestoneIcon.svg";
import plusIcon from "../../img/icon/plusIcon.svg";
import useIssueStore from "../../hooks/stores/useIssueStore";
import { useNavigate } from "react-router-dom";
import { useContext } from "react";
import { MilestoneContext } from "../../contexts/MilestoneContext";

function MilestoneHeader() {
  const { labels } = useIssueStore();
  const { openMilestones, isToAdd, setIsToAdd } = useContext(MilestoneContext);
  const navigate = useNavigate();

  return (
    <Wrapper>
      <NavigateBox>
        <LabelBar>
          <SmallIcon src={labelIcon} />
          <LargeTitle onClick={() => navigate("/labels")}>레이블({(labels && labels.length) || 0})</LargeTitle>
        </LabelBar>
        <MilestoneBar>
          <SmallIcon src={milestoneIcon} />
          <LargeTitle onClick={() => navigate("/milestones")}>
            마일스톤({(openMilestones && openMilestones.length) || 0})
          </LargeTitle>
        </MilestoneBar>
      </NavigateBox>
      <AddButton isAvailable={isToAdd} onClick={() => setIsToAdd(!isToAdd)}>
        <img src={plusIcon} />
        마일스톤 추가
      </AddButton>
    </Wrapper>
  );
}

const Wrapper = styled.div`
  display: flex;
  justify-content: space-between;
  height: 2.5em;
`;

const NavigateBox = styled.div`
  height: 100%;
  display: flex;
  border: 1px solid #d9dbe9;
  border-radius: 12px;
  overflow: hidden;
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

const SmallIcon = styled.img`
  width: 1em;
  height: 1em;
`;

const LargeTitle = styled.span`
  height: 1em;
  margin-left: 0.5em;
  font-size: 1em;
`;

const AddButton = styled.button<{ isAvailable: boolean }>`
  width: 10.7em;
  height: 3.3em;
  font-size: 0.75em;
  border: 1px solid #595959;
  border-radius: 1.3em;
  background-color: #595959;
  color: #fff;
  display: flex;
  justify-content: center;
  align-items: center;
  opacity: ${({ isAvailable }) => (isAvailable ? 0.5 : 1)};
  transition: all 0.5s ease;
  cursor: pointer;
`;

export default MilestoneHeader;
