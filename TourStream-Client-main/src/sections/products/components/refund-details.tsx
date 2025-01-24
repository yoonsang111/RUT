import { useFormContext } from 'react-hook-form';
import { useState, useEffect, useCallback } from 'react';

import { Chip, Stack, Alert, Button, FormLabel, TextField, Typography } from '@mui/material';

import Iconify from 'src/components/iconify';
import { CustomNumericFormat } from 'src/components/custom-numeric-format';

import { ProductRegistrationSchemaType } from '../types';

// ----------------------------------------------------------------------

type RateRefundType = {
  refundPolicy: 'RATE';
  startNumber: number;
  endNumber: number;
  value: number;
};

type AmountRefundType = {
  refundPolicy: 'RATE';
  startNumber: number;
  endNumber: number;
  value: number;
};

type RefundType = 'RATE' | 'AMOUNT';

enum InputType {
  startNumber = 'startNumber',
  endNumber = 'endNumber',
  value = 'value',
}

// ----------------------------------------------------------------------

export default function RefundDetails() {
  const {
    watch,
    setValue,
    formState: { errors },
  } = useFormContext<ProductRegistrationSchemaType>();

  const refundDetails = watch('refundDetails');

  const [rateRefunds, setRateRefunds] = useState<RateRefundType[]>(
    refundDetails.filter((r) => r.refundPolicy === 'RATE') as RateRefundType[]
  );
  const [ammountRefunds, setAmmountRefunds] = useState<AmountRefundType[]>(
    refundDetails.filter((r) => r.refundPolicy === 'AMOUNT') as AmountRefundType[]
  );

  // Update Form State
  useEffect(() => {
    setValue('refundDetails', [...rateRefunds, ...ammountRefunds]);
  }, [rateRefunds, ammountRefunds, setValue]);

  // Add
  const handleAddRefund = useCallback((refundType: RefundType) => {
    const newRefund = {
      refundPolicy: refundType,
      startNumber: 0,
      endNumber: 0,
      value: 0,
    };

    if (refundType === 'RATE') {
      setRateRefunds((state) => [...state, newRefund as RateRefundType]);
    } else {
      setAmmountRefunds((state) => [...state, newRefund as AmountRefundType]);
    }
  }, []);

  // Change
  const handleInputChange = useCallback(
    ({
      refundType,
      refundIndex,
      inputType,
      value,
    }: {
      refundType: RefundType;
      refundIndex: number;
      inputType: InputType;
      value: number;
    }) => {
      if (refundType === 'RATE') {
        setRateRefunds((state) =>
          state.map((refund, index) =>
            index === refundIndex
              ? {
                  ...refund,
                  [inputType]: value,
                }
              : {
                  ...refund,
                }
          )
        );
      } else {
        // Amount Refund
        setAmmountRefunds((state) =>
          state.map((refund, index) =>
            index === refundIndex
              ? {
                  ...refund,
                  [inputType]: value,
                }
              : {
                  ...refund,
                }
          )
        );
      }
    },
    []
  );

  // Delete
  const handleDelete = useCallback(
    ({ refundType, refundIndex }: { refundType: RefundType; refundIndex: number }) => {
      if (refundType === 'RATE') {
        setRateRefunds((state) => state.filter((_, index) => index !== refundIndex));
      } else {
        // Amount Refund
        setAmmountRefunds((state) => state.filter((_, index) => index !== refundIndex));
      }
    },
    []
  );

  return (
    <Stack spacing={2}>
      <FormLabel sx={{ typography: 'body2', color: 'text.primary' }} required>
        환불 규정
      </FormLabel>

      {errors?.refundDetails?.message && (
        <Alert severity="error" variant="outlined">
          {errors?.refundDetails?.message}
        </Alert>
      )}

      <FormLabel sx={{ typography: 'body2' }}>정률제</FormLabel>

      {rateRefunds.map((rateRefund, index) => (
        <RefundItem
          key={`Rate_Refund_${index}}`}
          refundIndex={index}
          refundItem={rateRefund}
          onChange={handleInputChange}
          onDelete={handleDelete}
        />
      ))}

      <Button
        size="small"
        color="primary"
        startIcon={<Iconify icon="mingcute:add-line" />}
        onClick={() => handleAddRefund('RATE')}
        sx={{ flexShrink: 0 }}
      >
        추가
      </Button>

      <FormLabel sx={{ typography: 'body2' }}>정액제</FormLabel>

      {ammountRefunds.map((ammountRefund, index) => (
        <RefundItem
          key={`Ammount_Refund_${index}}`}
          refundIndex={index}
          refundItem={ammountRefund}
          onChange={handleInputChange}
          onDelete={handleDelete}
        />
      ))}

      <Button
        size="small"
        color="primary"
        startIcon={<Iconify icon="mingcute:add-line" />}
        onClick={() => handleAddRefund('AMOUNT')}
        sx={{ flexShrink: 0 }}
      >
        추가
      </Button>
    </Stack>
  );
}

interface RefundItemProps {
  refundIndex: number;
  refundItem: RateRefundType | AmountRefundType;
  onChange: (props: {
    refundType: RefundType;
    refundIndex: number;
    inputType: InputType;
    value: number;
  }) => void;
  onDelete: (props: { refundType: RefundType; refundIndex: number }) => void;
}

function RefundItem({ refundIndex, refundItem, onChange, onDelete }: RefundItemProps) {
  return (
    <Stack direction={{ xs: 'column', sm: 'row' }} spacing={1.5}>
      <Chip label={refundIndex + 1} variant="soft" />

      <Stack direction="row" alignItems="center" spacing={1.5}>
        <TextField
          size="small"
          label="시작 일"
          type="number"
          value={refundItem.startNumber || ''}
          onChange={(e) =>
            onChange({
              refundIndex,
              inputType: InputType.startNumber,
              refundType: refundItem.refundPolicy,
              value: +e.target.value,
            })
          }
          InputLabelProps={{ shrink: true, required: false }}
          sx={{ flex: 1 }}
          required
        />

        <Typography variant="body2">~</Typography>

        <TextField
          size="small"
          label="종료 일"
          type="number"
          value={refundItem.endNumber || ''}
          onChange={(e) =>
            onChange({
              refundIndex,
              inputType: InputType.endNumber,
              refundType: refundItem.refundPolicy,
              value: +e.target.value,
            })
          }
          InputLabelProps={{ shrink: true, required: false }}
          sx={{ flex: 1 }}
          required
        />

        <Typography variant="body2">일 전</Typography>
      </Stack>

      <Stack direction="row" spacing={1.5}>
        <TextField
          size="small"
          label={refundItem.refundPolicy === 'RATE' ? '%' : '₩'}
          value={refundItem.refundPolicy === 'RATE' ? refundItem.value * 100 : refundItem.value}
          type={refundItem.refundPolicy === 'RATE' ? 'number' : 'text'}
          onChange={(e) =>
            onChange({
              refundIndex,
              inputType: InputType.value,
              refundType: refundItem.refundPolicy,
              value:
                refundItem.refundPolicy === 'RATE'
                  ? Number((+e.target.value / 100).toFixed(3))
                  : +e.target.value,
            })
          }
          InputLabelProps={{ shrink: true, required: false }}
          InputProps={{
            inputComponent: CustomNumericFormat as any,
          }}
          sx={{ flex: 1, minWidth: '100px' }}
          required
        />

        <Button
          onClick={() => onDelete({ refundIndex, refundType: refundItem.refundPolicy })}
          sx={{ color: 'text.secondary' }}
        >
          <Iconify icon="solar:trash-bin-trash-bold" />
        </Button>
      </Stack>
    </Stack>
  );
}
