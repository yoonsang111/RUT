import * as Yup from 'yup';

import { IProductListItem } from 'src/types/product';

import ProductRegistrationSchema from './schemas/product-registreation-schema';

// ----------------------------------------------------------------------

// 상품목록 상품 액션 선택 타입
export type ProductActionType = {
  type: 'CLOSE' | 'OPTION_EDIT' | 'CONTENT_EDIT' | 'COPY';
  product: IProductListItem;
};

// ----------------------------------------------------------------------

// 상품등록 페이지 타입
export type PageSectionType = 'Product' | 'Option';

// ----------------------------------------------------------------------

// 상품 스키마 타입
export type ProductRegistrationSchemaType = Yup.InferType<typeof ProductRegistrationSchema>;
