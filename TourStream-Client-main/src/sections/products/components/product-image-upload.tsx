import { Theme } from '@emotion/react';
import { useDropzone } from 'react-dropzone';
import { m, AnimatePresence } from 'framer-motion';

import Box from '@mui/material/Box';
import Stack from '@mui/material/Stack';
import Button from '@mui/material/Button';
import { Chip, IconButton } from '@mui/material';
import Typography from '@mui/material/Typography';
import { alpha, SxProps } from '@mui/material/styles';

import { UploadIllustration } from 'src/assets/illustrations';

import Iconify from 'src/components/iconify';
import { varFade } from 'src/components/animate';
import { RejectionFiles } from 'src/components/upload';

import { IProductImage } from 'src/types/product';

// ----------------------------------------------------------------------

type ProductImageUploadProps = {
  error?: boolean;
  disabled?: boolean;
  sx?: SxProps<Theme>;
  placeholder?: React.ReactNode;
  helperText?: React.ReactNode;
  //
  onDrop: (acceptedFiles: File[]) => Promise<void>;
  onRemove: (productImage: IProductImage) => Promise<void>;
  onRemoveAll: () => Promise<void>;
  onImageClick: (productImage: IProductImage) => void;
  //
  productImages: IProductImage[];
};

// ----------------------------------------------------------------------

export default function ProductImageUpload({
  error,
  disabled,
  sx,
  placeholder,
  helperText,
  onRemove,
  onRemoveAll,
  onImageClick,
  productImages,
  ...other
}: ProductImageUploadProps) {
  const { getRootProps, getInputProps, isDragActive, isDragReject, fileRejections } = useDropzone({
    multiple: true,
    disabled,
    ...other,
  });

  const hasError = isDragReject || !!error;

  const renderPlaceholder = (
    <Stack spacing={3} alignItems="center" justifyContent="center" flexWrap="wrap">
      <UploadIllustration sx={{ width: 1, maxWidth: 200 }} />
      <Stack spacing={1} sx={{ textAlign: 'center' }}>
        <Typography variant="h6">파일을 드랍하거나 선택해 주세요</Typography>
        <Typography variant="body2" sx={{ color: 'text.secondary' }}>
          파일을 여기 드랍하거나 선택해서 기기내 파일
          <Box
            component="span"
            sx={{
              mx: 0.5,
              color: 'primary.main',
              textDecoration: 'underline',
            }}
          >
            탐색
          </Box>
          하기
        </Typography>
      </Stack>
    </Stack>
  );

  const renderMultiPreview = productImages.length > 0 && (
    <>
      <Box sx={{ my: 3 }}>
        <ProductImagesPreview
          productImages={productImages}
          onClick={onImageClick}
          onRemove={onRemove}
        />
      </Box>

      <Stack direction="row" justifyContent="flex-end" spacing={1.5}>
        {onRemoveAll && (
          <Button color="inherit" variant="outlined" size="small" onClick={onRemoveAll}>
            모두 제거
          </Button>
        )}
      </Stack>
    </>
  );

  return (
    <Box sx={{ width: 1, position: 'relative', ...sx }}>
      <Box
        {...getRootProps()}
        sx={{
          p: 5,
          outline: 'none',
          borderRadius: 1,
          cursor: 'pointer',
          overflow: 'hidden',
          position: 'relative',
          bgcolor: (theme) => alpha(theme.palette.grey[500], 0.08),
          border: (theme) => `1px dashed ${alpha(theme.palette.grey[500], 0.2)}`,
          transition: (theme) => theme.transitions.create(['opacity', 'padding']),
          '&:hover': {
            opacity: 0.72,
          },
          ...(isDragActive && {
            opacity: 0.72,
          }),
          ...(disabled && {
            opacity: 0.48,
            pointerEvents: 'none',
          }),
          ...(hasError && {
            color: 'error.main',
            borderColor: 'error.main',
            bgcolor: (theme) => alpha(theme.palette.error.main, 0.08),
          }),
        }}
      >
        <input {...getInputProps()} />

        {renderPlaceholder}
      </Box>

      {helperText && helperText}

      <RejectionFiles fileRejections={fileRejections} />

      {renderMultiPreview}
    </Box>
  );
}

// ----------------------------------------------------------------------

interface ProductImagesPreviewProps {
  productImages: IProductImage[];
  onClick: (productImage: IProductImage) => void;
  onRemove: (productImage: IProductImage) => Promise<void>;
  sx?: SxProps<Theme>;
}

function ProductImagesPreview({ productImages, onClick, onRemove, sx }: ProductImagesPreviewProps) {
  return (
    <AnimatePresence initial={false}>
      {productImages.map((prodImage) => (
        <Stack
          key={prodImage.productImageUrl}
          component={m.div}
          {...varFade().inUp}
          alignItems="center"
          display="inline-flex"
          justifyContent="center"
          sx={{
            m: 0.5,
            width: 130,
            height: 130,
            borderRadius: 1.25,
            overflow: 'hidden',
            position: 'relative',
            border: (theme) => `solid 1px ${alpha(theme.palette.grey[500], 0.16)}`,
            cursor: 'pointer',
            ...sx,
          }}
          onClick={() => onClick(prodImage)}
        >
          <Box
            component="img"
            src={prodImage.productImageUrl}
            sx={{
              width: 1,
              height: 1,
              flexShrink: 0,
              objectFit: 'cover',
              '&:hover': {
                opacity: 0.85,
              },
            }}
          />

          {prodImage.isRepresentative && (
            <Chip
              label="대표 이미지"
              color="primary"
              size="small"
              sx={{
                position: 'absolute',
                width: '100%',
                bottom: -3,
              }}
            />
          )}

          <IconButton
            size="small"
            onClick={(e) => {
              e.stopPropagation();
              onRemove(prodImage);
            }}
            sx={{
              p: 0.5,
              top: 4,
              right: 4,
              position: 'absolute',
              color: 'common.white',
              bgcolor: (theme) => alpha(theme.palette.grey[900], 0.48),
              '&:hover': {
                bgcolor: (theme) => alpha(theme.palette.grey[900], 0.72),
              },
            }}
          >
            <Iconify icon="mingcute:close-line" width={14} />
          </IconButton>
        </Stack>
      ))}
    </AnimatePresence>
  );
}
