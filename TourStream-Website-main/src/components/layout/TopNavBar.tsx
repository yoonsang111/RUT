"use client";

import {
  motion,
  useMotionValueEvent,
  useScroll,
  type Variants,
} from "framer-motion";
import Link from "next/link";
import React, { useCallback, useMemo } from "react";
import { Button } from "../ui/button";
import InquiryButton from "./InquiryButton";
import TourStreamLogo from "./logos/TourStreamLogo";
import { LuMenu } from "react-icons/lu";
import useMobileNavSheetStore from "@/stores/use-mobile-nav-sheet-store";

const navBarVariants: Variants = {
  visible: {
    y: 0,
  },
  hidden: {
    y: -100,
  },
};

export default function TopNavBar() {
  const { scrollY } = useScroll();
  const setIsShow = useMobileNavSheetStore((state) => state.setIsShow);
  const [hidden, setHidden] = React.useState(false);

  useMotionValueEvent(scrollY, "change", (latest) => {
    const prev = scrollY.getPrevious();
    if (!prev) return;
    if (latest < prev  || latest <= 100) {
      setHidden(false);
    } else if (latest > 100 && latest > prev) {
      setHidden(true);
    }
  });

  const handleMobileMenuClick = useCallback(() => {
    setIsShow(true);
  }, [setIsShow]);

  return (
    <header className="fixed top-2 md:top-4 px-2 md:px-4 w-full z-10">
      <motion.nav
        animate={hidden ? "hidden" : "visible"}
        variants={navBarVariants}
        className="bg-white shadow-lg w-full max-w-screen-lg mx-auto 
        px-6 py-4 flex items-center justify-between border rounded-full"
      >
        <Link href="/">
          <TourStreamLogo />
        </Link>

        <nav className="flex items-center gap-2 max-md:hidden">
          <Link target="_blank" href="https://admin.tourstream.co.kr/">
            <Button variant="link" className="text-muted-foreground">
              TourStream 바로가기
            </Button>
          </Link>
        </nav>

        <div className="max-md:hidden">
          <InquiryButton />
        </div>

        {/* MOBILE MENU BAR */}
        <div className="md:hidden">
          <Button
            variant="outline"
            className="h-11 w-11"
            size="icon"
            onClick={handleMobileMenuClick}
          >
            <LuMenu className="h-6 w-6" />
          </Button>
        </div>
      </motion.nav>
    </header>
  );
}
