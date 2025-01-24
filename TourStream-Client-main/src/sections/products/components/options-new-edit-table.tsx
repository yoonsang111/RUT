import { format } from 'date-fns';
import { useState, useCallback } from 'react';
import { useFormContext } from 'react-hook-form';

import { DatePicker } from '@mui/x-date-pickers';
import { koKR, DataGrid, GridColDef, GridToolbarContainer } from '@mui/x-data-grid';
import {
  Box,
  Card,
  Alert,
  Stack,
  Button,
  Select,
  MenuItem,
  Collapse,
  CardHeader,
  InputLabel,
  IconButton,
  FormControl,
} from '@mui/material';

import { genNumId } from 'src/utils/id';

import Iconify from 'src/components/iconify';

import { IOptionItem, ApplicationDay } from 'src/types/options';

import { ProductRegistrationSchemaType } from '../types';
import { APPLICATION_DAYS, optionsToSingleArray, ApplicationDayToLabel } from '../utils';
import {
  RenderCellPrice,
  RenderCellDelete,
  RenderCellCurrency,
  RenderEditCellName,
  RenderCellSeparator,
  RenderEditCellPrice,
  RenderCellSalesDate,
  RenderEditCellCurrency,
  RenderEditCellDatePicker,
  RenderCellApplicationDay,
  RenderCellAddSpecificDate,
} from './options-table-row';

// ----------------------------------------------------------------------

const applicationDayOptions = Object.entries(ApplicationDayToLabel).map(([value, label]) => ({
  value,
  label,
}));

// ----------------------------------------------------------------------

const columns: GridColDef[] = [
  {
    field: 'separator',
    headerName: '구분',
    maxWidth: 10,
    sortable: false,
    renderCell: (params) => <RenderCellSeparator params={params} />,
  },
  {
    field: 'name',
    headerName: '옵션명',
    flex: 1,
    minWidth: 120,
    editable: true,
    sortable: false,
    renderEditCell: (params) => <RenderEditCellName params={params} />,
  },
  {
    field: 'stockQuantity',
    headerName: '수량',
    flex: 1,
    minWidth: 100,
    editable: true,
    sortable: false,
    type: 'number',
  },
  {
    field: 'siteCurrency',
    headerName: '사이트 판매 통화',
    headerAlign: 'center',
    align: 'center',
    minWidth: 120,
    editable: true,
    sortable: false,
    renderCell: (params) => <RenderCellCurrency params={params} />,
    renderEditCell: (params) => <RenderEditCellCurrency params={params} />,
  },
  {
    field: 'sitePrice',
    headerName: '사이트 판매가',
    flex: 1,
    minWidth: 160,
    editable: true,
    sortable: false,
    type: 'number',
    renderCell: (params) => <RenderCellPrice params={params} type="SITE" />,
    renderEditCell: (params) => <RenderEditCellPrice params={params} />,
  },
  {
    field: 'platformCurrency',
    headerName: '플랫폼 판매 통화',
    headerAlign: 'center',
    align: 'center',
    minWidth: 120,
    type: 'singleSelect',
    editable: true,
    sortable: false,
    renderCell: (params) => <RenderCellCurrency params={params} />,
    renderEditCell: (params) => <RenderEditCellCurrency params={params} />,
  },
  {
    field: 'platformPrice',
    headerName: '플랫폼 판매가',
    flex: 1,
    minWidth: 160,
    editable: true,
    sortable: false,
    type: 'number',
    renderCell: (params) => <RenderCellPrice params={params} type="PLATFORM" />,
    renderEditCell: (params) => <RenderEditCellPrice params={params} />,
  },
  {
    field: 'salesStartDate',
    headerName: '판매 시작 일',
    align: 'center',
    headerAlign: 'center',
    minWidth: 140,
    editable: true,
    sortable: false,
    renderCell: (params) => <RenderCellSalesDate params={params} />,
    renderEditCell: (params) => <RenderEditCellDatePicker params={params} />,
  },
  {
    field: 'salesEndDate',
    headerName: '판매 종료 일',
    align: 'center',
    headerAlign: 'center',
    minWidth: 140,
    editable: true,
    sortable: false,
    renderCell: (params) => <RenderCellSalesDate params={params} />,
    renderEditCell: (params) => <RenderEditCellDatePicker params={params} />,
  },
  {
    field: 'applicationDay',
    headerName: '적용 요일',
    align: 'center',
    headerAlign: 'center',
    minWidth: 120,
    type: 'singleSelect',
    editable: true,
    sortable: false,
    valueOptions: applicationDayOptions,
    renderCell: (params) => <RenderCellApplicationDay params={params} />,
  },
  {
    field: 'specificdate',
    headerName: '특정일 추가',
    align: 'center',
    headerAlign: 'center',
    minWidth: 20,
    sortable: false,
    editable: false,
    renderCell: (params) => <RenderCellAddSpecificDate params={params} />,
  },
  {
    field: 'delete',
    headerName: '삭제',
    align: 'center',
    headerAlign: 'center',
    minWidth: 20,
    sortable: false,
    editable: false,
    renderCell: (params) => <RenderCellDelete params={params} />,
  },
];

// ----------------------------------------------------------------------

export default function OptionsNewEditTable() {
  const { watch, setValue } = useFormContext<ProductRegistrationSchemaType>();

  const { options } = watch();

  // Option Add State
  const [salesStartDate, setSalesStartDate] = useState('');
  const [salesEndDate, setSalesEndDate] = useState('');
  const [applicationDay, setApplicationDay] = useState<ApplicationDay>('TOTAL');
  const [optionAddError, setOptionAddError] = useState('');

  const [showInfo, setShowInfo] = useState(true);

  const handleOptionAdd = () => {
    if (!salesStartDate || !salesEndDate) {
      setOptionAddError('판매 시작, 종료 날짜를 먼저 선택해 주세요');
      return;
    }
    if (!applicationDay) {
      setOptionAddError('적용일을 선택해 주세요');
      return;
    }
    if (new Date(salesStartDate) > new Date(salesEndDate)) {
      setOptionAddError('판매 사작 일은 판매 종료 일과 같거나 작아야 합니다');
      return;
    }

    setOptionAddError('');

    const newOption: IOptionItem = {
      id: genNumId(),
      name: '',
      stockQuantity: 0,
      siteCurrency: 'KRW',
      sitePrice: 0,
      platformCurrency: 'KRW',
      platformPrice: 0,
      applicationDay,
      salesStatus: 'SALE' as 'SALE',
      salesStartDate,
      salesEndDate,
      subOptions: [], // 부모 option만 SubOptions 배열을 가짐
    };

    setValue('options', [...options, newOption]);
  };

  const updateOptions = useCallback(
    (newOption: IOptionItem, oldOption: IOptionItem) => {
      const isSubOption = !newOption?.subOptions;
      const isNameUpdate = newOption.name !== oldOption.name;
      const isCurrencyUpdate =
        newOption.siteCurrency !== oldOption.siteCurrency ||
        newOption.platformCurrency !== oldOption.platformCurrency;

      let updatedOption = newOption;
      if (!isSubOption && (isNameUpdate || isCurrencyUpdate)) {
        updatedOption = {
          ...newOption,
          subOptions: newOption.subOptions?.map((so) => ({
            ...so,
            name: updatedOption.name,
            siteCurrency: updatedOption.siteCurrency,
            platformCurrency: updatedOption.platformCurrency,
          })),
        };
      }

      let updatedOptions: any;
      if (!isSubOption) {
        updatedOptions = options.map((o) => (o.id === updatedOption.id ? updatedOption : { ...o }));
      } else {
        updatedOptions = options.map((o) => ({
          ...o,
          subOptions: o.subOptions?.map((so) =>
            so.id === updatedOption.id ? { ...updatedOption } : { ...so }
          ),
        }));
      }

      setValue('options', updatedOptions);

      return updatedOption;
    },
    [options, setValue]
  );

  const renderToolBar = (
    <GridToolbarContainer>
      <Stack
        direction={{
          xs: 'column',
          sm: 'row',
        }}
        sx={{
          width: 1,
        }}
        spacing={1}
      >
        <DatePicker
          label="판매 시작 일"
          value={salesStartDate ? new Date(salesStartDate) : null}
          onChange={(d) => setSalesStartDate(format(d as Date, 'yyyy-MM-dd'))}
        />
        <DatePicker
          label="판매 종료 일"
          value={salesEndDate ? new Date(salesEndDate) : null}
          onChange={(d) => setSalesEndDate(format(d as Date, 'yyyy-MM-dd'))}
        />
        <FormControl sx={{ minWidth: 100 }}>
          <InputLabel id="application-day">적용 일</InputLabel>
          <Select
            labelId="application-day"
            label="적용 일"
            value={applicationDay}
            onChange={(e) => setApplicationDay(e.target.value as ApplicationDay)}
            sx={{
              whiteSpace: 'nowrap',
            }}
          >
            {APPLICATION_DAYS.map((ad) => (
              <MenuItem key={ad} value={ad}>
                {ApplicationDayToLabel[ad as keyof typeof ApplicationDayToLabel]}
              </MenuItem>
            ))}
          </Select>
        </FormControl>
        <Button
          variant="soft"
          color="primary"
          onClick={handleOptionAdd}
          sx={{
            whiteSpace: 'nowrap',
          }}
        >
          옵션 추가
        </Button>
      </Stack>

      <Info show={showInfo} onClose={() => setShowInfo(false)} />
    </GridToolbarContainer>
  );

  return (
    <Card>
      <CardHeader title="상품 옵션" />

      <Box sx={{ px: 2, pt: 2 }}>
        {optionAddError && <Alert severity="error">{optionAddError}</Alert>}
      </Box>

      <DataGrid
        rows={optionsToSingleArray(options as IOptionItem[])}
        columns={columns}
        sx={{ width: '100%', height: '80svh' }}
        processRowUpdate={updateOptions}
        onProcessRowUpdateError={(error) => console.error(error)}
        disableColumnMenu
        localeText={koKR.components.MuiDataGrid.defaultProps.localeText}
        slots={{
          toolbar: () => renderToolBar,
        }}
      />
    </Card>
  );
}

interface InfoProps {
  show: boolean;
  onClose: () => void;
}

function Info({ show, onClose }: InfoProps) {
  return (
    <Collapse in={show} sx={{ width: 1 }}>
      <Alert
        severity="info"
        variant="outlined"
        sx={{ width: 1, display: 'flex', alignItems: 'center' }}
        action={
          <IconButton aria-label="close" size="small" onClick={onClose}>
            <Iconify icon="material-symbols:close" width={16} />
          </IconButton>
        }
      >
        셀을 더블클릭하여 수정 할 수 있습니다
      </Alert>
    </Collapse>
  );
}
