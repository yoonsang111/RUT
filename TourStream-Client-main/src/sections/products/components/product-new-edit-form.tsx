import { useCallback } from 'react';
import { useSnackbar } from 'notistack';
import { useFormContext } from 'react-hook-form';

import Box from '@mui/material/Box';
import Card from '@mui/material/Card';
import Stack from '@mui/material/Stack';
import Grid from '@mui/material/Unstable_Grid2';
import CardHeader from '@mui/material/CardHeader';
import Typography from '@mui/material/Typography';
import { FormLabel, CircularProgress } from '@mui/material';

import { useResponsive } from 'src/hooks/use-responsive';

import api, { apiEndpoints } from 'src/utils/tourstream-api';

import { RHFTextField, RHFRadioGroup } from 'src/components/hook-form';

import {
  PicupTypeEnum,
  IProductImage,
  InquiryTypeEnum,
  PaymentPlanEnum,
  TransportationEnum,
} from 'src/types/product';

import RefundDetails from './refund-details';
import ProductImageUpload from './product-image-upload';
import { ProductRegistrationSchemaType } from '../types';

// ----------------------------------------------------------------------

interface ProductNewEditFormProps {
  drawer?: boolean; // drawer 레이아웃 적용여부
}

export default function ProductNewEditForm({ drawer }: ProductNewEditFormProps) {
  const mdUp = useResponsive('up', 'md');

  const { enqueueSnackbar, closeSnackbar } = useSnackbar();

  const { setValue, watch } = useFormContext<ProductRegistrationSchemaType>();

  const productImages = watch('productImages');

  // 이미지 업로드
  const handleFileDrop = useCallback(
    async (acceptedFiles: File[]) => {
      const loadingSnackbarKey = enqueueSnackbar('이미지 업로드중', {
        variant: 'info',
        persist: true,
        action: () => <CircularProgress size={20} />,
      });

      const formData = new FormData();

      acceptedFiles.forEach((file) => {
        formData.append('files', file);
      });

      const res = await api.post<{ productImageId: number; fileUrl: string }[]>(
        apiEndpoints.products.files.upload,
        formData
      );

      if (res.status !== 'SUCCESS') {
        closeSnackbar(loadingSnackbarKey);
        enqueueSnackbar('이미지 업로드 실패', { variant: 'error' });
        return;
      }

      const newProductImages = res.data.map(({ productImageId, fileUrl }) => ({
        productImageId,
        productImageUrl: fileUrl,
        isRepresentative: false,
      }));

      // 자동 대표 이미지 설정
      if (productImages.length === 0) {
        newProductImages[0].isRepresentative = true;
      }

      setValue('productImages', [...productImages, ...newProductImages]);

      closeSnackbar(loadingSnackbarKey);
      enqueueSnackbar('이미지 업로드');
    },
    [productImages, setValue, enqueueSnackbar, closeSnackbar]
  );

  // 단일 이미지 삭제
  const handleRemoveSingle = useCallback(
    async (prodImage: IProductImage) => {
      const res = await api.delete(
        `${apiEndpoints.products.files.root}/${prodImage.productImageId}`
      );

      if (res.status !== 'SUCCESS') {
        enqueueSnackbar('업로드 이미지 삭제 실패', { variant: 'error' });
        return;
      }

      const filteredProducts = productImages.filter(
        (productImage) => productImage.productImageId !== prodImage.productImageId
      );

      setValue('productImages', [...filteredProducts]);

      enqueueSnackbar('업로드 이미지 삭제');
    },
    [productImages, setValue, enqueueSnackbar]
  );

  // 모든 이미지 삭제
  const handleRemoveAll = useCallback(async () => {
    const productImageIds = productImages.map(({ productImageId }) => productImageId).join(',');

    const res = await api.delete(`${apiEndpoints.products.files.root}/${productImageIds}`);

    if (res.status !== 'SUCCESS') {
      enqueueSnackbar('모든 이미지 삭제 실패', { variant: 'error' });
      return;
    }

    setValue('productImages', []);

    enqueueSnackbar('모든 이미지 삭제');
  }, [productImages, enqueueSnackbar, setValue]);

  // 대표 이미지 설정
  const handleImageClick = useCallback(
    (selectedProdImage: IProductImage) => {
      const updatedProductImages = productImages.map((productImage) => ({
        ...productImage,
        isRepresentative: productImage === selectedProdImage,
      }));

      setValue('productImages', [...updatedProductImages]);
    },
    [setValue, productImages]
  );

  // Info
  const renderInfo = (
    <>
      {mdUp && !drawer && (
        <Grid md={4}>
          <Typography variant="h6" sx={{ mb: 0.5 }}>
            상품 정보
          </Typography>
          <Typography variant="body2" sx={{ color: 'text.secondary' }}>
            상품명, 한 줄 설명, 가이드 여부...
          </Typography>
        </Grid>
      )}

      <Grid xs={12} md={drawer ? 12 : 8}>
        <Card>
          {(!mdUp || drawer) && <CardHeader title="상품 정보" />}

          <Stack spacing={3} sx={{ p: 3 }}>
            <RHFTextField required name="productName" label="상품명" />

            <RHFTextField
              required
              name="description"
              label="한 줄 설명"
              placeholder="상품을 소개할 간단한 한 줄 설명을 입력해 주세요"
              InputLabelProps={{
                shrink: true,
              }}
            />

            <RHFRadioGroup
              required
              name="isGuided"
              label="가이드 여부"
              row
              options={[
                { label: '가이드 X', value: false },
                { label: '가이드 O', value: true },
              ]}
            />

            <Stack spacing={1.5}>
              <FormLabel component="legend" sx={{ typography: 'body2' }} required>
                운영 시간
              </FormLabel>
              <Stack direction="row" alignItems="center" spacing={1.5}>
                <RHFTextField
                  name="operationStartTime"
                  type="time"
                  label="시작 시간"
                  InputLabelProps={{
                    shrink: true,
                  }}
                />
                <Typography variant="body1">~</Typography>
                <RHFTextField
                  name="operationEndTime"
                  type="time"
                  label="종료 시간"
                  InputLabelProps={{
                    shrink: true,
                  }}
                />
              </Stack>
            </Stack>

            <Stack spacing={1.5}>
              <FormLabel component="legend" required>
                상품 이미지
              </FormLabel>
              <ProductImageUpload
                onDrop={handleFileDrop}
                onImageClick={handleImageClick}
                onRemove={handleRemoveSingle}
                onRemoveAll={handleRemoveAll}
                productImages={productImages as IProductImage[]}
              />
            </Stack>
          </Stack>
        </Card>
      </Grid>
    </>
  );

  // Transport / Pickup
  const renderTransportPickup = (
    <>
      {mdUp && !drawer && (
        <Grid md={4}>
          <Typography variant="h6" sx={{ mb: 0.5 }}>
            이동 / 픽업
          </Typography>
          <Typography variant="body2" sx={{ color: 'text.secondary' }}>
            이동수단, 픽업 유형, 픽업정보...
          </Typography>
        </Grid>
      )}

      <Grid xs={12} md={drawer ? 12 : 8}>
        <Card>
          {(!mdUp || drawer) && <CardHeader title="이동 / 픽업" />}

          <Stack spacing={3} sx={{ p: 3 }}>
            <Box
              columnGap={2}
              rowGap={3}
              display="grid"
              gridTemplateColumns={{
                xs: 'repeat(1, 1fr)',
                md: 'repeat(2, 1fr)',
              }}
            >
              <RHFRadioGroup
                required
                name="transportation"
                label="이동 수단"
                row
                options={[
                  { label: '도보', value: TransportationEnum.WALK },
                  { label: '차량', value: TransportationEnum.CAR },
                ]}
              />

              <RHFRadioGroup
                required
                name="pickupType"
                label="픽업 유형"
                row
                options={[
                  { label: '숙소 픽업', value: PicupTypeEnum.ACCOMMODATION },
                  { label: '미팅 장소 픽업', value: PicupTypeEnum.MEETING_PLACE },
                ]}
              />
            </Box>

            <RHFTextField
              required
              name="pickupInformation"
              label="픽업 정보"
              placeholder="만나는 시간, 장소에 대한 정보를 적어 주세요"
              InputLabelProps={{
                shrink: true,
              }}
            />

            <RHFTextField required name="pickupLocation" label="픽업 장소(영문 주소)" />
          </Stack>
        </Card>
      </Grid>
    </>
  );

  // Contact
  const renderContact = (
    <>
      {mdUp && !drawer && (
        <Grid md={4}>
          <Typography variant="h6" sx={{ mb: 0.5 }}>
            연락 / 문의
          </Typography>
          <Typography variant="body2" sx={{ color: 'text.secondary' }}>
            문의 형식, 고객 센터 연락처, 픽업 장소
          </Typography>
        </Grid>
      )}

      <Grid xs={12} md={drawer ? 12 : 8}>
        <Card>
          {(!mdUp || drawer) && <CardHeader title="연락 / 문의" />}

          <Stack spacing={3} sx={{ p: 3 }}>
            <RHFRadioGroup
              required
              name="inquiryType"
              label="문의 형식"
              row
              options={[
                { label: '모두 가능', value: InquiryTypeEnum.ALL },
                { label: '1:1 여행문의만 가능', value: InquiryTypeEnum.DIRECT },
                { label: '바로 결제만 가능', value: InquiryTypeEnum.INSTANT_PAYMENT },
              ]}
            />

            <RHFTextField required name="customerServiceContact" label="고객 센터 연락처" />

            <RHFTextField required name="emergencyContact" label="비상 연락망" />
          </Stack>
        </Card>
      </Grid>
    </>
  );

  // Details
  const renderDetails = (
    <>
      {mdUp && !drawer && (
        <Grid md={4}>
          <Typography variant="h6" sx={{ mb: 0.5 }}>
            상품 상세
          </Typography>
          <Typography variant="body2" sx={{ color: 'text.secondary' }}>
            공지사항, 상품 내용, 투어 코스...
          </Typography>
        </Grid>
      )}

      <Grid xs={12} md={drawer ? 12 : 8}>
        <Card>
          {(!mdUp || drawer) && <CardHeader title="상품 상세" />}

          <Stack spacing={3} sx={{ p: 3 }}>
            <RHFTextField required name="notice" label="공지 사항" multiline rows={4} />

            <RHFTextField required name="content" label="상품 내용" multiline rows={12} />

            <RHFTextField required name="tourCourse" label="투어 코스" multiline rows={12} />

            <RHFTextField required name="includedContent" label="포함 사항" multiline rows={12} />

            <RHFTextField required name="excludedContent" label="불포함 사항" multiline rows={12} />

            <RHFTextField required name="otherContent" label="기타 사항" multiline rows={12} />
          </Stack>
        </Card>
      </Grid>
    </>
  );

  // Policy
  const renderPolicy = (
    <>
      {mdUp && !drawer && (
        <Grid md={4}>
          <Typography variant="h6" sx={{ mb: 0.5 }}>
            상품 규정
          </Typography>
          <Typography variant="body2" sx={{ color: 'text.secondary' }}>
            최소 출발 인원, 결제 방식, 환불 규정
          </Typography>
        </Grid>
      )}

      <Grid xs={12} md={drawer ? 12 : 8}>
        <Card>
          {(!mdUp || drawer) && <CardHeader title="상품 상세" />}

          <Stack spacing={3} sx={{ p: 3 }}>
            <RHFTextField
              required
              name="minDepartureNumber"
              label="최소 출발 인원"
              placeholder="0"
              type="number"
              InputLabelProps={{ shrink: true }}
            />

            <RHFRadioGroup
              required
              name="paymentPlan"
              label="결제 방식"
              row
              options={[
                { label: '모두 가능', value: PaymentPlanEnum.ALL },
                { label: '전액 선결제', value: PaymentPlanEnum.FULL_PRE_PAYMENT },
                { label: '예약금만 결제', value: PaymentPlanEnum.DEPOSIT_PAYMENT },
              ]}
            />

            <RefundDetails />
          </Stack>
        </Card>
      </Grid>
    </>
  );

  return (
    <Grid container spacing={3}>
      {renderInfo}

      {renderTransportPickup}

      {renderContact}

      {renderDetails}

      {renderPolicy}
    </Grid>
  );
}
