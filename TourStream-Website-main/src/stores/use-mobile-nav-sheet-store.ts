import { create } from "zustand";

interface MobileNavSheetState {
  isShow: boolean;
  setIsShow: (isShow: boolean) => void;
}

const useMobileNavSheetStore = create<MobileNavSheetState>((set) => ({
  isShow: false,
  setIsShow: (isShow) => set({ isShow }),
}));

export default useMobileNavSheetStore;
