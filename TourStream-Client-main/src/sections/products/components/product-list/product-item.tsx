import Card from '@mui/material/Card';
import MenuItem from '@mui/material/MenuItem';
import ListItemText from '@mui/material/ListItemText';
import { Stack, IconButton, Typography } from '@mui/material';

import Image from 'src/components/image';
import Label from 'src/components/label';
import Iconify from 'src/components/iconify';
import CustomPopover, { usePopover } from 'src/components/custom-popover';

import { IProductListItem } from 'src/types/product';

import { ProductActionType } from '../../types';

// ----------------------------------------------------------------------

type Props = {
  product: IProductListItem;
  onProductActionClick: (productAction: ProductActionType) => void;
};

export default function ProductItem({ product, onProductActionClick }: Props) {
  const popover = usePopover();

  const { productImageUrl, productImageName, productName } = product;

  const renderImage = (
    <Image
      src={productImageUrl}
      alt={productImageName}
      sx={{ borderRadius: 1, height: 164, width: 1 }}
    />
  );

  const renderTexts = (
    <ListItemText
      sx={{
        p: (theme) => theme.spacing(2.5, 2.5, 2, 2.5),
      }}
      // primary={`Posted date: ${fDateTime(createdAt)}`}
      secondary={<Typography color="inherit">{productName}</Typography>}
      primaryTypographyProps={{
        typography: 'caption',
        color: 'text.disabled',
      }}
      secondaryTypographyProps={{
        mt: 1,
        noWrap: true,
        component: 'span',
        color: 'text.primary',
        typography: 'subtitle1',
      }}
    />
  );

  const renderActions = (
    <Stack direction="row" justifyContent="space-between" sx={{ p: 2 }}>
      <Label color={product.isClosed ? 'error' : 'primary'}>
        {product.isClosed ? '마감됨' : '판매중'}
      </Label>

      <IconButton onClick={popover.onOpen}>
        <Iconify icon="eva:more-vertical-fill" />
      </IconButton>
    </Stack>
  );

  return (
    <>
      <Card>
        {renderImage}

        {renderTexts}

        {renderActions}
      </Card>

      <CustomPopover
        open={popover.open}
        onClose={popover.onClose}
        arrow="right-top"
        sx={{ width: 140 }}
      >
        <MenuItem
          onClick={() => {
            popover.onClose();
            onProductActionClick({
              product,
              type: 'COPY',
            });
          }}
        >
          <Iconify icon="bxs:copy" />
          상품 복사
        </MenuItem>

        {!product.isClosed && (
          <>
            <MenuItem
              onClick={() => {
                popover.onClose();
                onProductActionClick({
                  product,
                  type: 'CONTENT_EDIT',
                });
              }}
            >
              <Iconify icon="mdi:edit" />
              내용 수정
            </MenuItem>

            <MenuItem
              onClick={() => {
                popover.onClose();
                onProductActionClick({
                  product,
                  type: 'OPTION_EDIT',
                });
              }}
            >
              <Iconify icon="mdi:table-edit" />
              옵션 수정
            </MenuItem>

            <MenuItem
              onClick={() => {
                popover.onClose();
                onProductActionClick({
                  product,
                  type: 'CLOSE',
                });
              }}
              sx={{ color: 'error.main' }}
            >
              <Iconify icon="pajamas:issue-close" />
              상품 마감
            </MenuItem>
          </>
        )}
      </CustomPopover>
    </>
  );
}
