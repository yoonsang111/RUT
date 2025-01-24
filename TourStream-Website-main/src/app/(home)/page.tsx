import MainBanner from "@/app/(home)/_components/MainBanner";
import MainFeatures from "@/app/(home)/_components/main-features/MainFeatures";
import MainInfos from "@/app/(home)/_components/main-infos/MainInfos";
import InquiryPromoteCard from "@/components/layout/InquiryPromoteCard";

export default function HomePage() {
  return (
    <main>
      <MainBanner />
      <div className="py-20 px-4 sm:px-20 space-y-40 md:space-y-52 max-w-screen-2xl mx-auto">
        <MainInfos />
        <MainFeatures />
      </div>
      <div className="mb-12">
        <InquiryPromoteCard />
      </div>
    </main>
  );
}
