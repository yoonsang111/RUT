import React from 'react';
import { Helmet } from 'react-helmet-async';

import ReservationDetailView from 'src/sections/reservations/views/reservation-detail-view';

export default function ReservationsDetailsPage() {
  return (
    <>
      <Helmet>
        <title>예약 상세 내역 조회</title>
      </Helmet>

      <ReservationDetailView />
    </>
  );
}
