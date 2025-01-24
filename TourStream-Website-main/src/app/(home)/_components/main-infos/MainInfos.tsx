import ProductListImage from "./ProductListImage";
import ProductRegistrationImage from "./ProductRegistrationImage";
import ReservationStatusImage from "./ReservationStatusImage";

export default function MainInfos() {
  return (
    <>
      <div className="flex flex-col items-center gap-16">
        <div className="w-full h-[200px] sm:h-[300px] md:h-[400px] lg:h-[500px]">
          <ReservationStatusImage />
        </div>
        <h2 className="text-lg sm:text-xl font-semibold text-center">
          가지고 있는 모든 플랫폼의 예약 관리를 한번에 할 수 있습니다.
        </h2>
        {/* <BrandLogoSlider /> */}
      </div>

      <div className="flex flex-col lg:flex-row gap-16 lg:gap-32 items-center justify-between ">
        <div className="w-full h-[200px] sm:h-[300px] md:h-[450px]">
          <ProductListImage />
        </div>
        <h2 className="text-lg sm:text-xl font-semibold text-center lg:text-left">
          등록되어 있는 플랫폼의 예약을 자동으로 확정, 취소를 하고 한번에 수정할
          수 있습니다.
        </h2>
      </div>

      <div className="flex flex-col lg:flex-row gap-16 sm:gap-28 lg:gap-32 items-center justify-between">
        <h2 className="text-lg sm:text-xl font-semibold max-lg:order-2 text-center lg:text-left">
          상품등록, 수정, 판매가, 수량을 일괄로 관리하세요
        </h2>
        <div className="w-full h-[200px] sm:h-[300px] md:h-[450px]">
          <ProductRegistrationImage />
        </div>
      </div>
    </>
  );
}
