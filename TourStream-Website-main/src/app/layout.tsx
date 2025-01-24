import Footer from "@/components/layout/Footer";
import InquiryPromoteCard from "@/components/layout/InquiryPromoteCard";
import MobileNavSheet from "@/components/layout/MobileNavSheet";
import TopNavBar from "@/components/layout/TopNavBar";
import { cn } from "@/lib/utils";
import type { Metadata } from "next";
import { Noto_Sans_KR } from "next/font/google";
import "./globals.css";

const notoSansKR = Noto_Sans_KR({ subsets: ["latin"] });

export const metadata: Metadata = {
  title: "TourStream",
  description:
    "TourStream은 투어 액티비티를 제공하는 업체들을 위한 다양한 솔루션을 제공해 주고 있습니다. TourStream은 업체의 투어, 액티비티 상품을 통합 관리하여 보다 효율적으로 업무를 할 수 있게 해주고, 업무의 프로세스를 한눈에 파악을 할 수 있게 해줍니다.",
  keywords: [
    "투어스트림",
    "TourStream",
    "투어 통합관리",
    "액티비티 통합관리",
    "투어 운영자 솔루션",
    "액티비티 운영자 솔루션",
    "투어 운영 프로그램",
    "투어 운영 소프트웨어",
    "액티비티 운영 프로그램",
    "액티비티 운영 소프트웨어",
    "투어 액티비티",
    "투어 관리",
    "액티비티 관리",
    "소규모 투어 운영자를 위한 솔루션",
    "기업 여행 활동 솔루션",
  ],
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="ko">
      <body
        className={cn(notoSansKR.className, "min-h-[100svh] flex flex-col")}
      >
        <MobileNavSheet />
        <TopNavBar />
        {children}
        <Footer />
      </body>
    </html>
  );
}
