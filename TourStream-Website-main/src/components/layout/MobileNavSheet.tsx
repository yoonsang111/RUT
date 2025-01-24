"use client";

import { Sheet, SheetContent } from "@/components/ui/sheet";
import useMobileNavSheetStore from "@/stores/use-mobile-nav-sheet-store";
import Link from "next/link";
import { Button } from "../ui/button";
import { useCallback } from "react";

export default function MobileNavSheet() {
  const { isShow, setIsShow } = useMobileNavSheetStore();

  const handleLinkClick = useCallback(() => {
    setIsShow(false);
  }, [setIsShow]);

  return (
    <Sheet open={isShow} onOpenChange={setIsShow}>
      <SheetContent side="right" className="pt-16 flex flex-col gap-4">
          <Link
            target="_blank"
            href="https://www.tourstream.co.kr/"
            onClick={handleLinkClick}
          >
            <Button className="w-full" variant="outline" size="lg">
              TourStream 바로가기
            </Button>
          </Link>

          <Link href="/inquiry" onClick={handleLinkClick}>
            <Button className="w-full" size="lg">도입 문의</Button>
          </Link>
      </SheetContent>
    </Sheet>
  );
}
