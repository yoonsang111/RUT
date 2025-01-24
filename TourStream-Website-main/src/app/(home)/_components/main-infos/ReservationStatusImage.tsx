"use client";

import { motion, useInView } from "framer-motion";
import Image from "next/image";
import { useRef } from "react";

export default function ReservationStatusImage() {
  const wrapperRef = useRef<HTMLDivElement>(null);
  const isInView = useInView(wrapperRef, { amount: 0.5 });

  return (
    <motion.div
      className="relative w-full h-full"
      ref={wrapperRef}
      animate={ isInView ? "view" : "hidden"}
      transition={{
        type: "spring",
        damping: 15,
      }}
      variants={{
        hidden: {
          rotateX: 45,
          scale: 0.8,
        },
        view: {
          rotateX: 0,
          scale: 1,
        },
      }}
    >
      <Image
        className="object-contain"
        src="/images/reservation-status.webp"
        alt="reservation-status"
        sizes="(max-width: 1000px) 90vw, 70vw"
        fill
      />

      <motion.div
        className="w-1/2 h-1/2 absolute -right-4 -bottom-4"
        transition={{
          type: "spring",
          damping: 15,
        }}
      >
        <Image
          className="object-contain"
          src="/images/reservation-status-side.webp"
          alt="reservation-status-side"
          sizes="(max-width: 1000px) 50vw, 40vw"
          fill
        />
      </motion.div>
    </motion.div>
  );
}
