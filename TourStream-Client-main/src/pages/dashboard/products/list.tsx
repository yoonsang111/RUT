import { Helmet } from 'react-helmet-async';

import ProductListView from 'src/sections/products/views/product-list-view';

export default function ProductsListPage() {
  return (
    <>
      <Helmet>
        <title>상품 목록</title>
      </Helmet>

      <ProductListView />
    </>
  );
}
