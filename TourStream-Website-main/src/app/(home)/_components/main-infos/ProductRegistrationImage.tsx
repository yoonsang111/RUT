"use client";

import { motion, useInView } from "framer-motion";
import Image from "next/image";
import { useRef } from "react";

export default function ProductRegistrationImage() {
  const wrapperRef = useRef<HTMLDivElement>(null);
  const isInView = useInView(wrapperRef, { amount: 0.8 });

  return (
    <motion.div
      ref={wrapperRef}
      className="relative w-full h-full"
      animate={isInView ? "view" : "hidden"}
    >
      <Image
        className="object-contain"
        src={"/images/product-registration-option.webp"}
        alt="product-list-image"
        sizes="(max-width: 768px) 100vw, 50vw"
        fill
      />

      <motion.div
        className="absolute bottom-0 w-full h-2/3"
        transition={{
          type: "spring",
          damping: 15,
        }}
        variants={{
          hidden: {
            opacity: 0,
            y: "30%",
          },
          view: {
            y: "30%",
            opacity: 1,
            scale: 1.1,
          },
        }}
      >
        <Image
          className="object-contain"
          src={"/images/option-max.webp"}
          alt="product-list-card"
          sizes="(max-width: 768px) 100vw, 70vw"
          fill
        />
      </motion.div>
    </motion.div>
  );
}
