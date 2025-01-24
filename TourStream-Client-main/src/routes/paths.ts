// ----------------------------------------------------------------------

const ROOTS = {
  AUTH: '/auth',
  DASHBOARD: '/dashboard',
};

// ----------------------------------------------------------------------

export const paths = {
  // AUTH
  auth: {
    login: `${ROOTS.AUTH}/login`,
  },
  // DASHBOARD
  dashboard: {
    root: ROOTS.DASHBOARD,
    // Reservations
    reservations: {
      root: `${ROOTS.DASHBOARD}/reservations`,
      status: `${ROOTS.DASHBOARD}/reservations/status`,
      detail: `${ROOTS.DASHBOARD}/reservations/details`,
    },
    // Products
    products: {
      root: `${ROOTS.DASHBOARD}/products`,
      registration: `${ROOTS.DASHBOARD}/products/registration`,
      list: `${ROOTS.DASHBOARD}/products/list`,
    },
  },
};
