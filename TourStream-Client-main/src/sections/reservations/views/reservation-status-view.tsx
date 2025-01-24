import React from 'react';

import { Container } from '@mui/system';

import { paths } from 'src/routes/paths';

import { useSettingsContext } from 'src/components/settings';
import CustomBreadcrumbs from 'src/components/custom-breadcrumbs/custom-breadcrumbs';

// ----------------------------------------------------------------------

export default function ReservationStatusView() {
  const settings = useSettingsContext();

  return (
    <Container maxWidth={settings.themeStretch ? false : 'lg'}>
      <CustomBreadcrumbs
        heading="실시간 예약 현황"
        links={[
          {
            name: '전체 개요',
            href: paths.dashboard.root,
          },
          {
            name: '실시간 예약 현황',
          },
        ]}
        sx={{
          mb: { xs: 3, md: 5 },
        }}
      />
    </Container>
  );
}
