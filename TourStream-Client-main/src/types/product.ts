import { IOptionItem } from './options';

// ----------------------------------------------------------------------

export enum TransportationEnum {
  WALK = 'WALK',
  CAR = 'CAR',
}

export enum PicupTypeEnum {
  ACCOMMODATION = 'ACCOMMODATION',
  MEETING_PLACE = 'MEETING_PLACE',
}

export enum InquiryTypeEnum {
  ALL = 'ALL',
  DIRECT = 'DIRECT',
  INSTANT_PAYMENT = 'INSTANT_PAYMENT',
}

export enum PaymentPlanEnum {
  ALL = 'ALL',
  FULL_PRE_PAYMENT = 'FULL_PRE_PAYMENT',
  DEPOSIT_PAYMENT = 'DEPOSIT_PAYMENT',
}

// ----------------------------------------------------------------------

export type IProductListItem = {
  productId: number;
  productName: string;
  isClosed: boolean;
  productImageUrl: string;
  productImageName: string;
};

export type IProductImage = {
  productImageId: number;
  productImageUrl: string;
  productImageName: string;
  isRepresentative: boolean;
};

export type IRefundDetail = {
  refundDetailId?: number;
  refundPolicy: 'RATE' | 'AMOUNT';
  value: number;
  startNumber: number;
  endNumber: number;
};

export interface IProductItem {
  // Infos
  productId: number;
  productName: string;
  description: string;
  isGuided: boolean;
  operationStartTime: string;
  operationEndTime: string;
  productImages: IProductImage[];
  // Transport / Pickup
  transportation: string;
  pickupType: string;
  pickupInformation: string;
  pickupLocation: string;
  // Contact
  inquiryType: string;
  customerServiceContact: string;
  emergencyContact: string;
  // Details
  notice: string;
  content: string;
  tourCourse: string;
  includedContent: string;
  excludedContent: string;
  otherContent: string;
  // Policy
  minDepartureNumber: number;
  paymentPlan: string;
  refundDetails: IRefundDetail[];
  // Options
  options: IOptionItem[];
}
