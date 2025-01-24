export const mutateKeys = {
  getProducts: ['get-products'],
  getProductById: (productId: number | undefined) => ['product', productId],
};
