import { useMemo } from 'react';

import { paths } from 'src/routes/paths';

import SvgColor from 'src/components/svg-color';

// ----------------------------------------------------------------------

const icon = (name: string) => (
  <SvgColor src={`/assets/icons/navbar/${name}.svg`} sx={{ width: 1, height: 1 }} />
  // OR
  // <Iconify icon="fluent:mail-24-filled" />
  // https://icon-sets.iconify.design/solar/
  // https://www.streamlinehq.com/icons
);

const ICONS = {
  job: icon('ic_job'),
  blog: icon('ic_blog'),
  chat: icon('ic_chat'),
  mail: icon('ic_mail'),
  user: icon('ic_user'),
  file: icon('ic_file'),
  lock: icon('ic_lock'),
  tour: icon('ic_tour'),
  order: icon('ic_order'),
  label: icon('ic_label'),
  blank: icon('ic_blank'),
  kanban: icon('ic_kanban'),
  folder: icon('ic_folder'),
  banking: icon('ic_banking'),
  booking: icon('ic_booking'),
  invoice: icon('ic_invoice'),
  product: icon('ic_product'),
  calendar: icon('ic_calendar'),
  disabled: icon('ic_disabled'),
  external: icon('ic_external'),
  menuItem: icon('ic_menu_item'),
  ecommerce: icon('ic_ecommerce'),
  analytics: icon('ic_analytics'),
  dashboard: icon('ic_dashboard'),
};

// ----------------------------------------------------------------------

export function useNavData() {
  const data = useMemo(
    () => [
      // OVERVIEW
      // ----------------------------------------------------------------------
      {
        subheader: '개요',
        items: [{ title: '전체 개요', path: paths.dashboard.root, icon: ICONS.dashboard }],
      },

      // MANAGEMENT
      // ----------------------------------------------------------------------
      {
        subheader: '세부 항목',
        items: [
          {
            title: '예약',
            path: paths.dashboard.reservations.root,
            icon: ICONS.calendar,
            children: [
              { title: '실시간 예약 현황', path: paths.dashboard.reservations.status },
              { title: '예약 상세 내역 조회', path: paths.dashboard.reservations.detail },
            ],
          },
          {
            title: '상품',
            path: paths.dashboard.products.root,
            icon: ICONS.tour,
            children: [
              { title: '상품 목록', path: paths.dashboard.products.list },
              { title: '상품 등록', path: paths.dashboard.products.registration },
            ],
          },
        ],
      },
    ],
    []
  );

  return data;
}
