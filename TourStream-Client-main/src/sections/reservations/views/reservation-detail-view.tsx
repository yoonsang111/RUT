import React from 'react';

import { Container } from '@mui/system';
import {
  Card,
  Table,
  TableRow,
  TableBody,
  TableCell,
  TableHead,
  TableContainer,
} from '@mui/material';

import { paths } from 'src/routes/paths';

import { useTable } from 'src/components/table';
import Scrollbar from 'src/components/scrollbar';
import { useSettingsContext } from 'src/components/settings';
import CustomBreadcrumbs from 'src/components/custom-breadcrumbs/custom-breadcrumbs';

// ----------------------------------------------------------------------

export default function ReservationDetailView() {
  const settings = useSettingsContext();

  const table = useTable({ defaultOrderBy: 'orderNumber' });

  return (
    <Container maxWidth={settings.themeStretch ? false : 'lg'}>
      <CustomBreadcrumbs
        heading="예약 상세 내역 조회"
        links={[
          {
            name: '전체 개요',
            href: paths.dashboard.root,
          },
          {
            name: '예약 상세 내역 조회',
          },
        ]}
        sx={{
          mb: { xs: 3, md: 5 },
        }}
      />

      <Card>
        <TableContainer sx={{ position: 'relative', overflow: 'unset' }}>
          <Scrollbar>
            <Table size={table.dense ? 'small' : 'medium'} sx={{ minWidth: 960 }}>
              <TableHead>
                <TableRow>
                  <TableCell>123</TableCell>
                </TableRow>
              </TableHead>
            </Table>
            <TableBody>
              <TableRow>
                <TableCell>11</TableCell>
              </TableRow>
            </TableBody>
          </Scrollbar>
        </TableContainer>
      </Card>
    </Container>
  );
}
