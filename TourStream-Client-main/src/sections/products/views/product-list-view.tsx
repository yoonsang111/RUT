import { useSnackbar } from 'notistack';
import { useForm } from 'react-hook-form';
import { useState, useCallback } from 'react';
import { yupResolver } from '@hookform/resolvers/yup';
import { useQueryClient } from '@tanstack/react-query';

import LoadingButton from '@mui/lab/LoadingButton';
import { Stack, Button, Drawer, Container, Typography, CircularProgress } from '@mui/material';

import { paths } from 'src/routes/paths';
import { RouterLink } from 'src/routes/components';

import { useResponsive } from 'src/hooks/use-responsive';

import api, { apiEndpoints } from 'src/utils/tourstream-api';

import { useGetProducts } from 'src/api';
import { queryKeys } from 'src/api/query-keys';

import Iconify from 'src/components/iconify';
import EmptyContent from 'src/components/empty-content';
import { ConfirmDialog } from 'src/components/custom-dialog';
import { useSettingsContext } from 'src/components/settings';
import CustomBreadcrumbs from 'src/components/custom-breadcrumbs';
import FormProvider from 'src/components/hook-form/form-provider';

import { IOptionItem } from 'src/types/options';
import { IProductItem } from 'src/types/product';

import { PageSectionType, ProductActionType } from '../types';
import OptionsErrorAlert from '../components/options-error-alert';
import ProductList from '../components/product-list/product-list';
import ProductNewEditForm from '../components/product-new-edit-form';
import ProductSearch from '../components/product-list/product-search';
import OptionsNewEditTable from '../components/options-new-edit-table';
import ProductRegistrationSchema from '../schemas/product-registreation-schema';
import { extractOptionIds, optionsToSingleArray, productRegistrationDefaultValues } from '../utils';

// ----------------------------------------------------------------------

export default function ProductListView() {
  const settings = useSettingsContext();

  const mdUp = useResponsive('up', 'md');

  const { enqueueSnackbar } = useSnackbar();

  const queryClient = useQueryClient();

  const { data: products = [], isFetching } = useGetProducts();
  const [originalOptions, setOriginalOptions] = useState<IOptionItem[]>([]);

  const [filterQuery, setFilterQuery] = useState('');

  const [productAction, setProductAction] = useState<ProductActionType | null>(null);

  const [pageSection, setPageSection] = useState<PageSectionType>('Product');

  const methods = useForm({
    resolver: yupResolver(ProductRegistrationSchema),
    defaultValues: productRegistrationDefaultValues,
  });

  const {
    reset,
    setValue,
    handleSubmit,
    formState: { errors, isSubmitting },
  } = methods;

  const [isValueUpdated, setIsValueUpdated] = useState(false);

  const filteredProducts = filterQuery
    ? products.filter((product) =>
        product.productName.toLowerCase().includes(filterQuery.toLowerCase())
      )
    : products;

  const handleSearchSubmit = useCallback((query: string) => {
    setFilterQuery(query);
  }, []);

  const onSubmit = handleSubmit(
    async (data) => {
      if (!productAction) return;

      // Copy
      if (productAction.type === 'COPY') {
        if (pageSection === 'Product') {
          setPageSection('Option');
          return;
        }

        try {
          const res = await api.post(apiEndpoints.products.root, data);
          if (res.status === 'SUCCESS') {
            enqueueSnackbar('상품이 등록되었습니다');
            queryClient.invalidateQueries({
              queryKey: queryKeys.getProducts,
            });
            setProductAction(null);
          }
        } catch (error) {
          console.error(error);
          enqueueSnackbar('상품 등록 오류', { variant: 'error' });
        }
        return;
      }

      // 내용 수정
      if (productAction.type === 'CONTENT_EDIT') {
        try {
          const res = await api.put(
            apiEndpoints.products.productId(productAction.product.productId),
            data
          );
          if (res.status === 'SUCCESS') {
            enqueueSnackbar('수정 되었습니다');
            queryClient.invalidateQueries({
              queryKey: queryKeys.getProducts,
            });
          }
        } catch (error) {
          console.error(error);
          enqueueSnackbar('내용 수정 오류', { variant: 'error' });
        }
      }

      // 옵션 수정
      if (productAction.type === 'OPTION_EDIT') {
        try {
          // deleteOptionIds Logic
          const deletedOptionIds: number[] = [];
          const submitedOptionIds = extractOptionIds(data.options as IOptionItem[]);
          // 원래 옵션에서 사라진 id를 추가
          optionsToSingleArray(originalOptions).forEach((o) => {
            if (!submitedOptionIds.includes(o.id)) {
              deletedOptionIds.push(o.id);
            }
          });

          // 옵션의 id 삭제
          const updatedOptions = data.options.map((po) => {
            const parentOptionCopy = { ...po };
            delete parentOptionCopy.id;

            const subOptionsResult = po?.subOptions?.map((so) => {
              const subOptionCopy = { ...so };
              delete subOptionCopy.id;
              return subOptionCopy;
            });

            return {
              ...parentOptionCopy,
              subOptions: subOptionsResult,
            };
          });

          const res = await api.put(apiEndpoints.options.root, {
            productId: data.productId,
            deleteOptionIds: deletedOptionIds,
            updateOptions: updatedOptions,
          });

          if (res.status === 'SUCCESS') {
            enqueueSnackbar('옵션이 수정되었습니다');
            setProductAction(null);
          }
        } catch (error) {
          console.error(error);
          enqueueSnackbar('옵션 수정 오류', { variant: 'error' });
        }
      }
    },
    // form 에러 발생시
    () => {
      enqueueSnackbar('양식을 다시 확인해 주세요', { variant: 'error' });
    }
  );

  const onBackward = useCallback(() => {
    setPageSection('Product');
  }, []);

  const handleProductActionClick = useCallback(
    async (prodAction: ProductActionType) => {
      reset();
      setPageSection('Product');
      setProductAction(prodAction);

      // 단일 상품 Api 호출
      const res = await api.get<IProductItem>(
        apiEndpoints.products.productId(prodAction.product.productId)
      );

      if (res.status !== 'SUCCESS') {
        enqueueSnackbar('상품 데이터 불러오기 오류', { variant: 'error' });
        return;
      }

      // form value 변경
      const productItem = res.data;
      Object.entries(productItem).forEach(([key, value]) => {
        setValue(key as keyof IProductItem, value);
      });

      // originalOptions 설정
      setOriginalOptions(productItem.options);

      // 업데이트 완료 상태로 변경
      setIsValueUpdated(true);
    },
    [reset, enqueueSnackbar, setValue]
  );

  const handleDrawerClose = useCallback(() => {
    setProductAction(null);
    setIsValueUpdated(false);
  }, []);

  const handleCloseProduct = useCallback(async () => {
    if (!productAction?.product.productId) return;

    const res = await api.put(
      `${apiEndpoints.products.root}/${productAction.product.productId}/close`
    );

    if (res.status !== 'SUCCESS') {
      enqueueSnackbar('상품 마감 오류', { variant: 'error' });
      return;
    }

    enqueueSnackbar('상품이 마감되었습니다');
    queryClient.invalidateQueries({
      queryKey: queryKeys.getProducts,
    });
    setProductAction(null);
  }, [enqueueSnackbar, productAction?.product.productId, queryClient]);

  const renderFilters = (
    <Stack
      spacing={3}
      justifyContent="space-between"
      alignItems={{ xs: 'flex-end', sm: 'center' }}
      direction={{ xs: 'column', sm: 'row' }}
    >
      <ProductSearch onSubmit={handleSearchSubmit} />
    </Stack>
  );

  const renderSubmitButton = (
    <Stack sx={{ mt: 3 }} direction="row" justifyContent="flex-end">
      <OptionsErrorAlert optionsErrors={errors.options} />

      <LoadingButton type="submit" variant="contained" size="large" loading={isSubmitting}>
        {productAction?.type === 'CONTENT_EDIT' && '내용 수정 제출'}
        {productAction?.type === 'OPTION_EDIT' && '옵션 수정 제출'}
      </LoadingButton>
    </Stack>
  );

  const renderCopyActions = (
    <Stack sx={{ mt: 3 }} spacing={2}>
      <OptionsErrorAlert optionsErrors={errors.options} />

      <Stack justifyContent="flex-end" direction="row" spacing={2}>
        {pageSection === 'Option' && <Button onClick={onBackward}>뒤로</Button>}

        <LoadingButton type="submit" variant="contained" size="large" loading={isSubmitting}>
          {pageSection === 'Product' ? '다음' : '상품 등록'}
        </LoadingButton>
      </Stack>
    </Stack>
  );

  return (
    <Container maxWidth={settings.themeStretch ? false : 'lg'}>
      <CustomBreadcrumbs
        heading="상품 목록"
        links={[
          {
            name: '전체 개요',
            href: paths.dashboard.root,
          },
          {
            name: '상품 목록',
          },
        ]}
        sx={{
          mb: { xs: 3, md: 5 },
        }}
        action={
          <Button
            component={RouterLink}
            href={paths.dashboard.products.registration}
            variant="contained"
            startIcon={<Iconify icon="mingcute:add-line" />}
          >
            새 상품 등록
          </Button>
        }
      />

      <Stack
        spacing={2.5}
        sx={{
          mb: { xs: 3, md: 5 },
        }}
      >
        {renderFilters}
      </Stack>

      {!isFetching && filteredProducts.length === 0 && (
        <EmptyContent title="상품이 없습니다" filled sx={{ py: 10 }} />
      )}

      {isFetching ? (
        <Stack direction="row" justifyContent="center">
          <CircularProgress />
        </Stack>
      ) : (
        <ProductList products={filteredProducts} onProductActionClick={handleProductActionClick} />
      )}

      <Drawer
        open={!!productAction && productAction.type !== 'CLOSE'}
        onClose={handleDrawerClose}
        anchor={mdUp ? 'right' : 'bottom'}
        slotProps={{
          backdrop: { invisible: true },
        }}
        PaperProps={{
          sx: { width: 1, height: 1, maxWidth: mdUp ? 0.5 : 1, maxHeight: mdUp ? 1 : 0.8, p: 2 },
        }}
      >
        <FormProvider methods={methods} onSubmit={onSubmit}>
          {!isValueUpdated ? (
            <Stack alignItems="center" justifyContent="center">
              <CircularProgress size={30} />
            </Stack>
          ) : (
            <>
              {(productAction?.type === 'CONTENT_EDIT' ||
                (productAction?.type === 'COPY' && pageSection === 'Product')) && (
                <ProductNewEditForm drawer />
              )}

              {(productAction?.type === 'OPTION_EDIT' ||
                (productAction?.type === 'COPY' && pageSection === 'Option')) && (
                <OptionsNewEditTable />
              )}

              {productAction?.type === 'COPY' ? renderCopyActions : renderSubmitButton}
            </>
          )}
        </FormProvider>
      </Drawer>

      <ConfirmDialog
        open={!!productAction && productAction.type === 'CLOSE'}
        onClose={() => setProductAction(null)}
        title="상품 마감 확인"
        content={
          <Stack>
            <Typography variant="subtitle1">{`"${
              productAction?.product.productName || ''
            }"`}</Typography>
            <Typography variant="body2">해당 상품을 마감 하시겠습니까?</Typography>
          </Stack>
        }
        action={
          <Button variant="contained" color="error" onClick={handleCloseProduct}>
            상품 마감
          </Button>
        }
      />
    </Container>
  );
}
