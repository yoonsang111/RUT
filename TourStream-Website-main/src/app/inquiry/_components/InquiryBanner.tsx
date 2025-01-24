import TourStreamLogo from "@/components/layout/logos/TourStreamLogo";

export default function InquiryBanner() {
  return (
    <div className="w-full flex flex-col items-center gap-4 py-8">
      <h1>
        <TourStreamLogo className="text-3xl" />
      </h1>

      <p className="max-w-md text-center text-sm md:text-base text-muted-foreground">
        문의 내용에 대해서 최대한 빠르고 신속한 답변을 도와드립니다.
        TourStream과 함께 최고의 경험을 해보세요.
      </p>
    </div>
  );
}
