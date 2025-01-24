import { Helmet } from 'react-helmet-async';

import { ProductRegistrationView } from 'src/sections/products/views';

export default function ProductsRegistrationPage() {
  return (
    <>
      <Helmet>
        <title>상품 등록</title>
      </Helmet>

      <ProductRegistrationView />
    </>
  );
}
