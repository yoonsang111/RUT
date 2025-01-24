import { cn } from "@/lib/utils";
import { ClassValue } from "clsx";
import { Button } from "../ui/button";
import Link from "next/link";

interface Props {
  className?: ClassValue;
}

export default function InquiryButton({ className }: Props) {
  return (
    <Link href="/inquiry">
      <Button size="lg" className={cn("rounded-full", className)}>
        도입 문의
      </Button>
    </Link>
  );
}
