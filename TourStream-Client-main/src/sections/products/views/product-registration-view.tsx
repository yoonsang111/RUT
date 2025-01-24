import { useSnackbar } from 'notistack';
import { useForm } from 'react-hook-form';
import { useState, useCallback } from 'react';
import { yupResolver } from '@hookform/resolvers/yup';

import { Stack, Button } from '@mui/material';
import Container from '@mui/material/Container';
import LoadingButton from '@mui/lab/LoadingButton';

import { paths } from 'src/routes/paths';
import { useRouter } from 'src/routes/hooks';

import api, { apiEndpoints } from 'src/utils/tourstream-api';

import { useSettingsContext } from 'src/components/settings';
import CustomBreadcrumbs from 'src/components/custom-breadcrumbs';
import FormProvider from 'src/components/hook-form/form-provider';

import { PageSectionType } from '../types';
import { productRegistrationDefaultValues } from '../utils';
import OptionsErrorAlert from '../components/options-error-alert';
import ProductNewEditForm from '../components/product-new-edit-form';
import OptionsNewEditTable from '../components/options-new-edit-table';
import ProductRegistrationSchema from '../schemas/product-registreation-schema';

// ----------------------------------------------------------------------

export default function ProductRegistrationView() {
  const settings = useSettingsContext();

  const router = useRouter();

  const { enqueueSnackbar } = useSnackbar();

  const [pageSection, setPageSection] = useState<PageSectionType>('Product');

  const methods = useForm({
    resolver: yupResolver(ProductRegistrationSchema),
    defaultValues: productRegistrationDefaultValues,
  });

  const {
    handleSubmit,
    formState: { isSubmitting, errors },
  } = methods;

  const onSubmit = handleSubmit(
    // On Valid
    async (data) => {
      if (pageSection === 'Product') {
        setPageSection('Option');
        return;
      }

      try {
        // 상품 등록 LOGIC
        const res = await api.post(apiEndpoints.products.root, { ...data });

        if (res.status === 'SUCCESS') {
          enqueueSnackbar('상품이 등록되었습니다');
          router.push(paths.dashboard.products.root);
        }
      } catch (error) {
        console.error(error);
        enqueueSnackbar('상품 등록 오류', { variant: 'error' });
      }
    },
    // On InValid
    () => {
      enqueueSnackbar('양식을 다시 확인해 주세요', { variant: 'error' });
    }
  );

  const onBackward = useCallback(() => {
    setPageSection('Product');
  }, []);

  return (
    <Container maxWidth={settings.themeStretch ? false : 'lg'}>
      <CustomBreadcrumbs
        heading="상품 등록"
        links={[
          {
            name: '전체 개요',
            href: paths.dashboard.root,
          },
          {
            name: '상품 목록',
            href: paths.dashboard.products.root,
          },
          { name: '상품 등록' },
        ]}
        sx={{
          mb: { xs: 3, md: 5 },
        }}
      />

      <FormProvider methods={methods} onSubmit={onSubmit}>
        {pageSection === 'Product' && <ProductNewEditForm />}

        {pageSection === 'Option' && <OptionsNewEditTable />}

        <Stack sx={{ mt: 3 }} spacing={2}>
          <OptionsErrorAlert optionsErrors={errors.options} />

          <Stack justifyContent="flex-end" direction="row" spacing={2}>
            {pageSection === 'Option' && <Button onClick={onBackward}>뒤로</Button>}

            <LoadingButton type="submit" variant="contained" size="large" loading={isSubmitting}>
              {pageSection === 'Product' ? '다음' : '상품 등록'}
            </LoadingButton>
          </Stack>
        </Stack>
      </FormProvider>
    </Container>
  );
}
