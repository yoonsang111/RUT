import { Outlet } from 'react-router';
import { lazy, Suspense } from 'react';

import { AuthGuard } from 'src/auth/guard';
import DashboardLayout from 'src/layouts/dashboard';

import { LoadingScreen } from 'src/components/loading-screen';

// ----------------------------------------------------------------------

// Overview
const OverViewRootPage = lazy(() => import('src/pages/dashboard/overview/root'));
// Reservations
const ReservationsStatusPage = lazy(() => import('src/pages/dashboard/reservations/status'));
const ReservationsDetailsPage = lazy(() => import('src/pages/dashboard/reservations/details'));
// Products
const ProductsListPage = lazy(() => import('src/pages/dashboard/products/list'));
const ProductRegistrationPage = lazy(() => import('src/pages/dashboard/products/registration'));

// ----------------------------------------------------------------------

export const dashboardRoutes = [
  {
    path: '/dashboard',
    element: (
      <AuthGuard>
        <DashboardLayout>
          <Suspense fallback={<LoadingScreen />}>
            <Outlet />
          </Suspense>
        </DashboardLayout>
      </AuthGuard>
    ),
    children: [
      { element: <OverViewRootPage />, index: true },
      {
        path: 'reservations',
        children: [
          { element: <ReservationsStatusPage />, index: true },
          { path: 'status', element: <ReservationsStatusPage /> },
          { path: 'details', element: <ReservationsDetailsPage /> },
        ],
      },
      {
        path: 'products',
        children: [
          { element: <ProductsListPage />, index: true },
          { path: 'list', element: <ProductsListPage /> },
          { path: 'registration', element: <ProductRegistrationPage /> },
        ],
      },
    ],
  },
];
