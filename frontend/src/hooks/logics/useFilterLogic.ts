import { useEffect } from 'react';
import useIssueStore from '../stores/useIssueStore';

const useFilterLogic = () => {
  const { labels, milestones } = useIssueStore();

  useEffect(() => {}, [labels, milestones]);

  return { labels, milestones };
}

export default useFilterLogic;