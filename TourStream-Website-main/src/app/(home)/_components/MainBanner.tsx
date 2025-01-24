import IntroductionInquiryDialogButton from "@/components/layout/InquiryButton";
import TourStreamLogo from "@/components/layout/logos/TourStreamLogo";
import { Sansita_Swashed } from "next/font/google";
import { LuChevronDown } from "react-icons/lu";

const sansitaSwashed = Sansita_Swashed({
  subsets: ["latin"],
  weight: ["400", "700"],
});

export default function MainBanner() {
  return (
    <div className="w-full bg-slate-100 pt-40 xl:pt-56 pb-32">
      <div className="flex flex-col items-center gap-y-6 animate-in slide-in-from-bottom-[7rem] duration-700 fade-in-0">
        <h1 className="flex flex-col items-center">
          <TourStreamLogo variant="slate" className="text-[3rem] sm:text-[4.5rem] lg:text-[6rem]" />
          <span className="text-xl font-light text-muted-foreground">
            은 무엇인가요?
          </span>
        </h1>

        <div className="flex flex-col items-center">
          <LuChevronDown className="h-6 w-6 animate-bounce text-blue-500" />
          <IntroductionInquiryDialogButton className="px-14 py-6 text-lg" />
        </div>
      </div>
    </div>
  );
}
