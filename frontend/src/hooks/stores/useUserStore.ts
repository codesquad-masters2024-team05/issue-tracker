import { create } from "zustand";

interface UserStoreState {
  userId: string;
  isLoggedIn: boolean;
  setUserId: (userId: string) => void;
  setIsLoggedIn: (isLoggedIn: boolean) => void;
}

// deprecated, 서버에서 sessionId로 userId를 새로운 이슈 작성, 코멘트 편집에 적용하는 기능 추가 시 삭제 
const useUserStore = create<UserStoreState>((set) => ({
  userId: "schnee",
  isLoggedIn: false,
  setUserId: (userId: string) => set(() => ({ userId })),
  setIsLoggedIn: (isLoggedIn: boolean) => set(() => ({ isLoggedIn })),
}));

export default useUserStore;
