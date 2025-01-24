"use client";

import { motion, useInView } from "framer-motion";
import Image from "next/image";
import { useRef } from "react";

export default function ProductListImage() {
  const wrapperRef = useRef<HTMLDivElement>(null);
  const isInView = useInView(wrapperRef, { amount: 0.8 });

  return (
    <motion.div
      ref={wrapperRef}
      className="relative w-full h-full"
      animate={isInView ? "view" : "hidden"}
    >
      {/* 상품 목록 이미지 */}
      <Image
        className="object-contain"
        src={"/images/product-list.webp"}
        alt="product-list-image"
        fill
        sizes="(max-width: 768px) 100vw, 50vw"
      />

      {/* 상품 카드 팝업 이미지 */}
      <motion.div
        className="absolute left-0 top-1/2 w-1/2 h-1/2"
        transition={{
          type: "spring",
          damping: 15,
        }}
        variants={{
          hidden: {
            opacity: 0,
            x: "-10%",
            y: "-50%",
          },
          view: {
            x: "-15%",
            y: "-70%",
            opacity: 1,
          },
        }}
      >
        <Image
          className="object-contain"
          src={"/images/product-list-card.webp"}
          alt="product-list-card"
          sizes="(max-width: 768px) 50vw, (max-width:1200px) 25vw, 20vw"
          fill
        />
      </motion.div>

      {/* 상품 수정 팝업 이미지 */}
      <motion.div
        className="absolute right-0 top-1/2 h-[80%] w-1/2"
        transition={{
          type: "spring",
          damping: 15,
        }}
        variants={{
          hidden: {
            scale: 0.8,
            opacity: 0,
            y: "-50%",
            x: "10%",
          },
          view: {
            scale: 1.1,
            opacity: 1,
            x: "25%",
            y: "-50%",
          },
        }}
      >
        <Image
          className="object-contain"
          src={"/images/product-edit.webp"}
          alt="product-list-card"
          sizes="(max-width: 768px) 50vw, (max-width:1200px) 35vw, 20vw"
          fill
        />
      </motion.div>
    </motion.div>
  );
}
