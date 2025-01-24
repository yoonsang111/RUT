"use client";

import { motion, useInView } from "framer-motion";
import { useRef } from "react";
import {
  FaBoxesStacked,
  FaCalculator,
  FaCalendarCheck,
  FaChartPie,
  FaHandshake,
  FaMagnifyingGlassDollar
} from "react-icons/fa6";
import FeatureCard from "./FeatureCard";

const FEATURES = [
  {
    title: "통합 상품 관리",
    description: "TourStream에서 상품 등록/수정을 한번에",
    icon: FaBoxesStacked,
    iconClassName: "text-sky-600",
  },
  {
    title: "예약 관리",
    description:
      "각 사이트에서 들어온 예약을 한번에 관리하며, 자동으로 예약을 확정",
    icon: FaCalendarCheck,
    iconClassName: "text-sky-600",
  },
  {
    title: "매출 관리",
    description: "각 플랫폼 사이트에서 판매된 상품의 매출을 한눈에 확인",
    icon: FaChartPie,
    iconClassName: "text-sky-600",
  },
  {
    title: "매출 신고",
    description:
      "각 플랫폼 사이트에서 발생된 매출에 대한 부가세 신고 금액을 확인",
    icon: FaMagnifyingGlassDollar,
    iconClassName: "text-sky-600",
  },
  {
    title: "정산 관리",
    description: "현지 여행사의 지급 해야하는 정산대금을 확인",
    icon: FaCalculator,
    iconClassName: "text-sky-600",
  },
  {
    title: "지속적인 협력",
    description:
      "파트너사와의 지속적인 의견교류와 협력을 통해 맞춤형 기능을 제공해 드립니다.",
    icon: FaHandshake,
    iconClassName: "text-sky-600",
  },
];

export default function MainFeatures() {
  const wrapperRef = useRef<HTMLDivElement>(null);
  const isInView = useInView(wrapperRef, { amount: 0.2 });

  return (
    <motion.div
      ref={wrapperRef}
      className="flex flex-col items-center space-y-8"
      animate={isInView ? "view" : "hidden"}
      variants={{
        view: {
          transition: {
            staggerChildren: 0.15,
          },
        },
      }}
    >
      <div className="w-full flex flex-col lg:flex-row items-center justify-between gap-4 ">
        <motion.h3
          className="text-nowrap text-3xl font-bold tracking-tighter"
          transition={{
            damping: 50,
          }}
          variants={{
            hidden: {
              y: 100,
              opacity: 0,
            },
            view: {
              y: 0,
              opacity: 1,
            },
          }}
        >
          주요 기능
        </motion.h3>
        <motion.p
          className="text-muted-foreground text-center font-light"
          transition={{
            damping: 50,
          }}
          variants={{
            hidden: {
              y: 100,
              opacity: 0,
            },
            view: {
              y: 0,
              opacity: 1,
            },
          }}
        >
          TourStream은 파트너사의 업무 능률 향상과 수익 증대를 최우선으로
          생각합니다.
        </motion.p>
      </div>

      <motion.div className="w-full grid  grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-5">
        {FEATURES.map((feature) => (
          <motion.div
            className="w-full h-full"
            key={feature.title}
            variants={{
              hidden: {
                opacity: 0,
              },
              view: {
                opacity: 1,
              },
            }}
          >
            <FeatureCard
              title={feature.title}
              description={feature.description}
              icon={feature.icon}
              iconClassName={feature.iconClassName}
            />
          </motion.div>
        ))}
      </motion.div>
    </motion.div>
  );
}
