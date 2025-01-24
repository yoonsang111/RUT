"use client";

import IntroductionInquiryDialogButton from "@/components/layout/InquiryButton";
import Image from "next/image";
import { motion } from "framer-motion";
import { usePathname } from "next/navigation";
import TourStreamLogo from "./logos/TourStreamLogo";

export default function InquiryPromoteCard() {
  const pathName = usePathname();

  if (pathName === "/inquiry") return null;

  return (
    <div className="px-4">
      <motion.div
        className="w-full max-w-screen-lg mx-auto relative rounded-2xl
        overflow-hidden py-10 px-4 sm:px-20 gap-4 flex items-center justify-between"
        initial={{
          y: 100,
          opacity: 0,
        }}
        whileInView={{
          y: 0,
          opacity: 1,
        }}
      >
        <Image
          className="-z-50"
          src="/images/blue-gradient.webp"
          sizes="(max-width: 768px) 100vw, 50vw"
          alt="gradient-background"
          fill
        />

        <div className="flex flex-col text-white gap-1">
          <TourStreamLogo variant="white" className="text-2xl sm:text-3xl" />
          <p className="text-xs sm:text-sm text-neutral-200 font-light">
            Tour & Activity 통합 관리 솔루션
          </p>
        </div>

        <IntroductionInquiryDialogButton className="border border-white shadow-sm shadow-white px-8 py-6 text-base" />
      </motion.div>
    </div>
  );
}
