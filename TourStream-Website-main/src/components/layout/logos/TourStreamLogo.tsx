import { cn } from "@/lib/utils";
import { type VariantProps, cva } from "class-variance-authority";
import { ClassValue } from "clsx";
import { Sansita_Swashed } from "next/font/google";
import React from "react";

const sansitaSwashed = Sansita_Swashed({
  subsets: ["latin"],
  weight: ["500", "700"],
});

const tourStreamLogoVariants = cva("tracking-tight text-2xl font-semibold", {
  variants: {
    variant: {
      default:
        "bg-gradient-to-r from-blue-600 to-blue-400 bg-clip-text text-transparent",
      slate:
        "bg-gradient-to-r from-slate-950 to-slate-400 bg-clip-text text-transparent",
      white:
        "text-white",
    },
  },
  defaultVariants: {
    variant: "default",
  },
});

interface TourStreamLogoProps
  extends VariantProps<typeof tourStreamLogoVariants> {
  className?: ClassValue;
}

export default function TourStreamLogo({
  className,
  variant,
}: TourStreamLogoProps) {
  return (
    <span
      className={cn(
        tourStreamLogoVariants({ variant, className }),
        sansitaSwashed.className
      )}
    >
      TourStream
    </span>
  );
}
