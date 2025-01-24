import { useQuery } from '@tanstack/react-query';

import api, { apiEndpoints } from 'src/utils/tourstream-api';

import { IProductListItem } from 'src/types/product';

import { queryKeys } from './query-keys';

// ----------------------------------------------------------------------

export function useGetProducts() {
  const URL = apiEndpoints.products.root;

  return useQuery({
    queryKey: queryKeys.getProducts,
    queryFn: async () => {
      const res = await api.get<IProductListItem[]>(URL);
      return res?.data || [];
    },
  });
}

// ----------------------------------------------------------------------
