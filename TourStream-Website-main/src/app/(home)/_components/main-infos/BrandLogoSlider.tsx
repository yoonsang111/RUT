/* eslint-disable @next/next/no-img-element */

export default function BrandLogoSlider() {
  return (
    <div
      className="max-w-screen-xl mx-auto w-full flex flex-nowrap overflow-hidden 
      [mask-image:_linear-gradient(to_right,transparent_0,_black_128px,_black_calc(100%-200px),transparent_100%)]"
    >
      <Logos />
      <Logos />
      <Logos />
    </div>
  );
}

const Logos = () => {
  return (
    <ul
      className="flex items-center justify-center md:justify-start 
      [&_li]:mx-4 [&_li]:md:mx-8 [&_img]:h-[15px] [&_img]:md:h-[20px] [&_img]:max-w-none [&_img]:pointer-events-none 
      animate-infinite-slide [&_img]:select-none"
    >
      <li>
        <img src="/brand-logos/naver.svg" alt="naver-logo" />
      </li>
      <li>
        <img src="/brand-logos/coupang.svg" alt="coupang-logo" />
      </li>
      <li>
        <img src="/brand-logos/myrealtrip.svg" alt="coupang-logo" />
      </li>
      <li>
        <img src="/brand-logos/tmon.svg" alt="coupang-logo" />
      </li>
      <li>
        <img src="/brand-logos/zoomzoom.svg" alt="coupang-logo" />
      </li>
    </ul>
  );
};
