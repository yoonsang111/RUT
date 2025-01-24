import React from "react";
import InquiryFormCard from "./_components/InquiryFormCard";
import InquiryBanner from "./_components/InquiryBanner";
import PageWrapper from "@/components/layout/PageWrapper";

export default function InquiryPage() {
  return (
    <PageWrapper className="flex flex-col lg:flex-row items-center justify-between">
      <InquiryBanner />
      <InquiryFormCard />
    </PageWrapper>
  );
}
