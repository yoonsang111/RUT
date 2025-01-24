/* eslint-disable no-restricted-syntax */

import { IOptionItem, ApplicationDay } from 'src/types/options';
import {
  PicupTypeEnum,
  InquiryTypeEnum,
  PaymentPlanEnum,
  TransportationEnum,
} from 'src/types/product';

import { ProductRegistrationSchemaType } from './types';

// ----------------------------------------------------------------------

export const productRegistrationDefaultValues: ProductRegistrationSchemaType = {
  // Infos
  productName: '',
  description: '',
  isGuided: false,
  operationStartTime: '',
  operationEndTime: '',
  productImages: [],
  // Transport / Pickup
  transportation: TransportationEnum.WALK,
  pickupType: PicupTypeEnum.ACCOMMODATION,
  pickupInformation: '',
  pickupLocation: '',
  // Contact
  inquiryType: InquiryTypeEnum.ALL,
  customerServiceContact: '',
  emergencyContact: '',
  // Details
  notice: '',
  content: '',
  tourCourse: '',
  includedContent: '',
  excludedContent: '',
  otherContent: '',
  // Policy
  minDepartureNumber: 1,
  paymentPlan: PaymentPlanEnum.ALL,
  refundDetails: [],
  // Options
  options: [],
};
// ----------------------------------------------------------------------

export const CURRENCY_LIST = ['KRW', 'USD'];

export const APPLICATION_DAYS: ApplicationDay[] = [
  'TOTAL',
  'WEEKDAY',
  'WEEKEND',
  'HOLIDAY',
  'MONDAY',
  'TUESDAY',
  'WEDNESDAY',
  'THURSDAY',
  'FRIDAY',
  'SATURDAY',
  'SUNDAY',
];

export const ApplicationDayToLabel = {
  TOTAL: '전체',
  WEEKDAY: '월~금',
  WEEKEND: '주말(토,일)',
  HOLIDAY: '공휴일',
  MONDAY: '월',
  TUESDAY: '화',
  WEDNESDAY: '수',
  THURSDAY: '목',
  FRIDAY: '금',
  SATURDAY: '토',
  SUNDAY: '일',
};

// ----------------------------------------------------------------------

export function optionsToSingleArray(options: IOptionItem[]) {
  const result: IOptionItem[] = []; // 중첩 배열을 1차원 배열로 변환
  options.forEach((parentOption) => {
    // Push Parent
    result.push({
      ...parentOption,
      subOptions: parentOption?.subOptions ? parentOption.subOptions : [],
    } as IOptionItem);

    // Push Sub
    parentOption.subOptions?.forEach((subOption) => {
      result.push(subOption);
    });
  });
  return result;
}

// ----------------------------------------------------------------------

export function extractOptionIds(options: IOptionItem[]) {
  return optionsToSingleArray(options).map(({ id }) => id);
}

// ----------------------------------------------------------------------

export function haveDifferentValues<T extends Record<string, any>>(
  originalObj: T,
  targetObj: T
): boolean {
  const keys = Object.keys(originalObj);

  for (const key of keys) {
    if (key === 'subOptions') {
      // eslint-disable-next-line no-continue
      continue;
    }

    if (originalObj[key] !== targetObj[key]) {
      return true;
    }
  }

  // 모든 키가 있으며 모든 값이 동일
  return false;
}
