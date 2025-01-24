import { useFormContext } from 'react-hook-form';
import React, { useRef, useState, useCallback } from 'react';

import { DatePicker } from '@mui/x-date-pickers';
import { Button, MenuItem, TextField, IconButton } from '@mui/material';
import { useGridApiContext, type GridRenderEditCellParams } from '@mui/x-data-grid';

import { genNumId } from 'src/utils/id';
import { fCurrency } from 'src/utils/format-number';
import { formatDateToString } from 'src/utils/format-date';

import Label from 'src/components/label';
import Iconify from 'src/components/iconify';
import { CustomNumericFormat } from 'src/components/custom-numeric-format';

import { IOptionItem, ApplicationDay } from 'src/types/options';

import { ProductRegistrationSchemaType } from '../types';
import { CURRENCY_LIST, ApplicationDayToLabel } from '../utils';

interface ParamsProps {
  params: GridRenderEditCellParams;
}

export function RenderCellSeparator({ params }: ParamsProps) {
  const { id, row } = params;
  const { getValues } = useFormContext<ProductRegistrationSchemaType>();

  const [separator, setSeparator] = useState('');

  const isSubOption = !row?.subOptions;

  React.useLayoutEffect(() => {
    const currentOptions = getValues('options');

    if (!isSubOption) {
      const parentIndex = currentOptions.findIndex((o) => o.id === id);
      setSeparator(`${parentIndex + 1}`);
    } else {
      currentOptions.forEach((parentOption, parentIndex) => {
        const subIndex = parentOption.subOptions?.findIndex((o) => o.id === id);
        if (subIndex !== undefined && subIndex >= 0) {
          setSeparator(`${parentIndex + 1}-${subIndex + 1}`);
        }
      });
    }
  }, [getValues, id, isSubOption]);

  if (isSubOption) return <Label variant="soft">{separator}</Label>;

  return (
    <Label variant="soft" color="primary">
      {separator}
    </Label>
  );
}

export function RenderEditCellName({ params }: ParamsProps) {
  const { row, value, id, field } = params;
  const apiRef = useGridApiContext();

  const isSubOption = !row.subOptions;

  const handleChange = useCallback(
    (e: React.ChangeEvent<HTMLInputElement>) => {
      const newValue = e.target.value;
      apiRef.current.setEditCellValue({ id, field, value: newValue });
    },
    [apiRef, field, id]
  );

  if (isSubOption) return <>{value}</>;

  return <TextField value={value} onChange={handleChange} />;
}

export function RenderCellCurrency({ params }: ParamsProps) {
  return <Label color="info">{params.value}</Label>;
}

export function RenderEditCellCurrency({ params }: ParamsProps) {
  const { row, value, id, field } = params;
  const apiRef = useGridApiContext();

  const isSubOption = !row.subOptions;

  const handleChange = useCallback(
    (e: React.ChangeEvent<HTMLInputElement>) => {
      const newValue = e.target.value;
      apiRef.current.setEditCellValue({ id, field, value: newValue });
    },
    [apiRef, field, id]
  );

  if (isSubOption) return <Label color="info">{params.value}</Label>;

  return (
    <TextField select value={value} onChange={handleChange} fullWidth>
      {CURRENCY_LIST.map((currency) => (
        <MenuItem key={currency} value={currency}>
          {currency}
        </MenuItem>
      ))}
    </TextField>
  );
}

export function RenderCellPrice({ params, type }: ParamsProps & { type: 'SITE' | 'PLATFORM' }) {
  return (
    <>
      {fCurrency(
        params.value,
        type === 'SITE' ? params.row.siteCurrency : params.row.platformCurrency
      )}
    </>
  );
}

export function RenderEditCellPrice({ params }: ParamsProps) {
  const { id, value, field, hasFocus } = params;
  const apiRef = useGridApiContext();
  const ref = useRef<HTMLInputElement>(null);

  React.useLayoutEffect(() => {
    if (hasFocus && ref.current) {
      ref.current.focus();
    }
  }, [hasFocus]);

  const handleChange = useCallback(
    (e: React.ChangeEvent<HTMLInputElement>) => {
      const newValue = e.target.value;
      apiRef.current.setEditCellValue({ id, field, value: newValue });
    },
    [apiRef, field, id]
  );

  return (
    <TextField
      // type="number"
      inputRef={ref}
      value={value}
      onChange={handleChange}
      InputProps={{
        inputComponent: CustomNumericFormat as any,
      }}
    />
  );
}

export function RenderCellSalesDate({ params }: ParamsProps) {
  return <Label>{params.value}</Label>;
}

export function RenderEditCellDatePicker({ params }: ParamsProps) {
  const { id, value, field } = params;
  const apiRef = useGridApiContext();

  const handleChange = useCallback(
    (date: Date | null) => {
      if (!date) return;
      apiRef.current.setEditCellValue({ id, field, value: formatDateToString(date) });
    },
    [apiRef, field, id]
  );

  return <DatePicker value={new Date(value)} onChange={(date) => handleChange(date)} />;
}

export function RenderCellApplicationDay({ params }: ParamsProps) {
  return <Label color="secondary">{ApplicationDayToLabel[params.value as ApplicationDay]}</Label>;
}

export function RenderCellAddSpecificDate({ params }: ParamsProps) {
  const { id, row } = params;
  const { setValue, getValues } = useFormContext();

  const isSubOption = !row?.subOptions;

  const addSpecificDate = useCallback(
    (e: React.MouseEvent) => {
      const currentOptions = getValues('options') as IOptionItem[];
      const selectedRowIndex = currentOptions.findIndex((o: IOptionItem) => o.id === id);

      const subOption: IOptionItem = {
        ...currentOptions[selectedRowIndex],
        id: genNumId(),
        subOptions: undefined,
      };

      const optionsCopy = [...currentOptions];

      if (optionsCopy[selectedRowIndex]?.subOptions) {
        optionsCopy[selectedRowIndex].subOptions?.push(subOption);
      } else {
        optionsCopy[selectedRowIndex].subOptions = [subOption];
      }

      setValue('options', currentOptions);
    },
    [getValues, setValue, id]
  );

  if (isSubOption) return null;

  return (
    <Button variant="soft" color="warning" onClick={addSpecificDate}>
      <Iconify icon="mingcute:calendar-add-line" />
    </Button>
  );
}

export function RenderCellDelete({ params }: ParamsProps) {
  const { id, row } = params;
  const { setValue, getValues } = useFormContext();

  const isSubOption = !row?.subOptions;

  const deleteOption = (e: React.MouseEvent) => {
    e.stopPropagation();

    const currentOptions = getValues('options') as IOptionItem[];
    const optionsCopy = [...currentOptions];

    if (!isSubOption) {
      setValue(
        'options',
        optionsCopy.filter((o) => o.id !== id)
      );
    } else {
      setValue(
        'options',
        optionsCopy.map((o) => ({
          ...o,
          subOptions: o.subOptions?.filter((so) => so.id !== id),
        }))
      );
    }
  };

  return (
    <IconButton color="error" onClick={deleteOption}>
      <Iconify icon="solar:trash-bin-trash-bold" />
    </IconButton>
  );
}
