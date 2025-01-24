import * as Yup from 'yup';

// ----------------------------------------------------------------------

const ProductSchema = Yup.object().shape({
  productId: Yup.number(),
  // Infos
  productName: Yup.string().required('상품명을 입력해 주세요'),
  description: Yup.string().required('한 줄 설명을 입력해 주세요'),
  isGuided: Yup.boolean().required('가이드 여부를 선택해 주세요'),
  operationStartTime: Yup.string().required('운영 시작 시간을 입력해 주세요'),
  operationEndTime: Yup.string().required('운영 종료 시간을 입력해 주세요'),
  productImages: Yup.array()
    .of(
      Yup.object({
        productImageId: Yup.number().required(),
        productImageUrl: Yup.string().required(),
        isRepresentative: Yup.boolean().required(),
      })
    )
    .required('상품 이미지를 업로드 해주세요'),
  // Transport / Pickup
  transportation: Yup.string().required('이동수단을 선택해 주세요'),
  pickupType: Yup.string().required('픽업 유형을 선택해 주세요'),
  pickupInformation: Yup.string().required('픽업 정보를 선택해 주세요'),
  pickupLocation: Yup.string().required('픽업 장소를 입력해 주세요'),
  // Contact
  inquiryType: Yup.string().required('문의 형식을 선택해 주세요'),
  customerServiceContact: Yup.string().required('고객 센터 연락처를 입력해 주세요'),
  emergencyContact: Yup.string().required('비상 연락처를 입력해 주세요'),
  // Details
  notice: Yup.string().required('공지 상항을 입력해 주세요'),
  content: Yup.string().required('상품 내용을 입력해 주세요'),
  tourCourse: Yup.string().required('투어 코스를 입력해 주세요'),
  includedContent: Yup.string().required('포함 사항을 입력해 주세요'),
  excludedContent: Yup.string().required('불포함 사항을 입력해 주세요'),
  otherContent: Yup.string().required('기타 사항을 입력해 주세요'),
  // Policy
  minDepartureNumber: Yup.number()
    .required('최소 출발 인원을 입력해 주세요')
    .min(1, '최소 출발 인원은 1이상 이어야 합니다'),
  paymentPlan: Yup.string(),
  refundDetails: Yup.array().required().min(1, '최소 한개 이상의 환불 규정을 작성해 주세요'),
});

// ----------------------------------------------------------------------

const CommonOptionSchema = Yup.object().shape({
  id: Yup.number(),
  name: Yup.string().required('옵션명을 입력해 주세요'),
  salesStatus: Yup.string().required(),
  stockQuantity: Yup.number().required().min(1, '옵션 최소 수량은 1 이상이어야 합니다'),
  siteCurrency: Yup.string().required('사이트 판매 통화를 선택해 주세요'),
  sitePrice: Yup.number().required().min(0, '사이트 판매가는 0 이상이어야 합니다'),
  platformCurrency: Yup.string().required('플랫폼 판매 통화를 선택해 주세요'),
  platformPrice: Yup.number().required().min(0, '플랫폼 판매가는 0 이상이어야 합니다'),
  salesStartDate: Yup.string().required('판매 시작 일을 선택해 주세요'),
  salesEndDate: Yup.string()
    .required('판매 종료 일을 선택해 주세요')
    .test(
      'is-greater',
      '판매 종료일은 판매 시작일보다 크거나 같아야 합니다',
      function checkIsGreater(value) {
        const { salesStartDate } = this.parent;
        // 시작일과 종료일을 Date 객체로 변환
        const startDate = new Date(salesStartDate);
        const endDate = new Date(value);
        // 시작일이 종료일보다 이전이거나 같은지 확인
        return startDate <= endDate;
      }
    ),
  applicationDay: Yup.string().required(),
});

const SingleOptionSchema = CommonOptionSchema.concat(
  Yup.object().shape({
    subOptions: Yup.array().of(CommonOptionSchema).optional(),
  })
);

const OptionsSchema = Yup.object().shape({
  options: Yup.array().of(SingleOptionSchema).required(),
});

// ----------------------------------------------------------------------

const ProductRegistrationSchema = ProductSchema.concat(OptionsSchema);

export default ProductRegistrationSchema;
