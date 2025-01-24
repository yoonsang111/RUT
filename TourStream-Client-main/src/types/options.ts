type SalesStatusType = 'SALE' | 'SOLD_OUT';

export type ApplicationDay =
  | 'TOTAL'
  | 'WEEKDAY'
  | 'WEEKEND'
  | 'HOLIDAY'
  | 'MONDAY'
  | 'TUESDAY'
  | 'WEDNESDAY'
  | 'THURSDAY'
  | 'FRIDAY'
  | 'SATURDAY'
  | 'SUNDAY';

export type IOptionItem = {
  id: number;
  name: string;
  salesStatus: SalesStatusType;
  stockQuantity: number;
  siteCurrency: string;
  platformCurrency: string;
  sitePrice: number;
  platformPrice: number;
  salesStartDate: string;
  salesEndDate: string;
  applicationDay: ApplicationDay;
  subOptions?: {
    id: number;
    name: string;
    salesStatus: SalesStatusType;
    stockQuantity: number;
    siteCurrency: string;
    platformCurrency: string;
    sitePrice: number;
    platformPrice: number;
    salesStartDate: string;
    salesEndDate: string;
    applicationDay: ApplicationDay;
  }[];
};
