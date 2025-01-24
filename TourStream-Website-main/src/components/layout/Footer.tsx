import React from "react";
import RUTLogo from "./logos/RUTLogo";

export default function Footer() {
  return (
    <footer className="w-full border-t py-10 px-4 sm:px-20">
      <div className="w-full max-w-screen-lg mx-auto flex flex-col gap-6">
        <div className="w-full flex items-end justify-between gap-20">
          <RUTLogo />

          <p className="text-xs text-muted-foreground font-light">
            Copyright © RUT INC
          </p>
        </div>

        <div className="text-sm font-light text-muted-foreground space-y-1">
          <p>상호명: 주식회사 알유티</p>
          <p>사업자등록번호: 885-81-03412</p>
          <p>
            주소: 인천광역시 남동구 논고개로 123번길 45, 4층 403-P35호(논현동)
          </p>
        </div>
      </div>
    </footer>
  );
}
