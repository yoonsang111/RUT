import Box from '@mui/material/Box';

import { IProductListItem } from 'src/types/product';

import ProductItem from './product-item';
import { ProductActionType } from '../../types';

// ----------------------------------------------------------------------

type Props = {
  products: IProductListItem[];
  onProductActionClick: (productAction: ProductActionType) => void;
};

export default function ProductList({ products, onProductActionClick }: Props) {
  return (
    <Box
      gap={3}
      display="grid"
      gridTemplateColumns={{
        xs: 'repeat(1, 1fr)',
        sm: 'repeat(2, 1fr)',
        md: 'repeat(3, 1fr)',
      }}
    >
      {products.map((product) => (
        <ProductItem
          key={product.productId}
          product={product}
          onProductActionClick={onProductActionClick}
        />
      ))}
    </Box>
  );
}
