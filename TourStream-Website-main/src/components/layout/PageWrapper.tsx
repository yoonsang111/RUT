import { cn } from "@/lib/utils";
import { ClassValue } from "clsx";
import React from "react";

interface PageWrapperProps {
  children: React.ReactNode;
  className?: ClassValue;
}

export default function PageWrapper({ children, className }: PageWrapperProps) {
  return (
    <main
      className={cn(
        `flex-1 w-full max-w-screen-2xl mx-auto 
        px-4 sm:px-20 py-28 lg:pt-40`,
        className
      )}
    >
      {children}
    </main>
  );
}
