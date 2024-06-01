import styled from "styled-components";
import Header from "../header/Header";
import MilestoneHeader from "./MilestoneHeader";
import { useContext } from "react";
import { MilestoneContext, MilestoneDetailType } from "../../contexts/MilestoneContext";
import { useQuery } from "react-query";
import { sendMilestonesRequest } from "../../api/MilestoneAPI";
import MilestoneDetail from "./MilestoneDetail";
import MilestoneEditBox from "./MilestoneEditBox";

const OPEN_MILESTONES_INDEX = 0;
const CLOSE_MILESTONES_INDEX = 1;

function MilestoneList() {
  const {
    openMilestones,
    setOpenMilestones,
    closeMilestones,
    setCloseMilestones,
    isToAdd,
    setIsToAdd,
    focusedTab,
    setFocusedTab,
  } = useContext(MilestoneContext);

  const fetchMilestones = () => Promise.all([sendMilestonesRequest("open"), sendMilestonesRequest("close")]);

  useQuery("milestones", fetchMilestones, {
    onSuccess: (data) => {
      setOpenMilestones(data[OPEN_MILESTONES_INDEX]);
      setCloseMilestones(data[CLOSE_MILESTONES_INDEX]);
    },
  });

  return (
    <Wrapper>
      <Header />
      <MilestoneHeader />
      {isToAdd && (
        <EditBoxWrapper>
          <MilestoneEditBox editType="new" closeEditBox={() => setIsToAdd(!isToAdd)} />
        </EditBoxWrapper>
      )}
      <MilestoneTable>
        <MilestoneTab>
          <MilestoneTypeText isFocused={focusedTab === "open"} onClick={() => setFocusedTab("open")}>
            열린 마일스톤({(openMilestones && openMilestones.length) || 0})
          </MilestoneTypeText>
          <MilestoneTypeText isFocused={focusedTab === "close"} onClick={() => setFocusedTab("close")}>
            닫힌 마일스톤({(closeMilestones && closeMilestones.length) || 0})
          </MilestoneTypeText>
        </MilestoneTab>
        <ScrollableArea>
          {focusedTab === "open"
            ? openMilestones.map((milestone: MilestoneDetailType) => <MilestoneDetail {...milestone} />)
            : closeMilestones.map((milestone: MilestoneDetailType) => <MilestoneDetail {...milestone} />)}
        </ScrollableArea>
      </MilestoneTable>
    </Wrapper>
  );
}

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2em;
`;

const EditBoxWrapper = styled.div`
  border: 1px solid #d9dbe9;
  border-radius: 0.75em;
`;

const MilestoneTable = styled.div`
  width: 80em;
  border: 1px solid #d9dbe9;
  border-radius: 0.725em;
  overflow: scroll;
  &::-webkit-scrollbar {
    display: none;
  }
`;

const MilestoneTab = styled.div`
  width: 100%;
  height: 4em;
  padding: 0 2em;
  box-sizing: border-box;
  background-color: #eff0f6;
  display: flex;
  align-items: center;
  gap: 2em;
  border-bottom: 1px solid #d9dbe9;
`;

const MilestoneTypeText = styled.span<{ isFocused: boolean }>`
  display: flex;
  gap: 0.25em;
  ${({ isFocused }) => isFocused && "font-weight: 700;"}
`;

const ScrollableArea = styled.div`
  overflow-y: scroll;
  height: calc(100% - 4em);
  &::-webkit-scrollbar {
    display: none;
  }
`;

export default MilestoneList;
