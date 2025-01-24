import React from 'react';
import { Helmet } from 'react-helmet-async';

import ReservationStatusView from 'src/sections/reservations/views/reservation-status-view';

export default function ReservationsStatusPage() {
  return (
    <>
      <Helmet>
        <title>실시간 예약 현황</title>
      </Helmet>

      <ReservationStatusView />
    </>
  );
}
