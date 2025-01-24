import { cn } from "@/lib/utils";
import { ClassValue } from "clsx";
import React from "react";

interface RUTLogoProps {
  className?: ClassValue
}

export default function RUTLogo({className}: RUTLogoProps) {
  return (
    <span>
      <span
        className={cn(
          `text-3xl font-black tracking-tight 
          bg-gradient-to-r from-blue-600 to-blue-400 
          bg-clip-text text-transparent`,
          className
        )}
      >
        RUT
      </span>
      <span className="text-sm font-light tracking-tight text-muted-foreground">
        INC
      </span>
    </span>
  );
}
