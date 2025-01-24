import React from 'react';
import { useSnackbar } from 'notistack';

import { Stack, Button } from '@mui/material';

import api from 'src/utils/tourstream-api';

export default function OverViewRootPage() {
  const { enqueueSnackbar } = useSnackbar();

  return (
    <Stack spacing={1} direction="row">
      <Button
        variant="contained"
        onClick={async () => {
          try {
            const res = await api.post<{ accessToken: string }>('/access-token');
            if (res.status === 'SUCCESS') {
              console.log('토큰이 재설정 되었습니다');
              localStorage.setItem('accessToken', res.data.accessToken);
              enqueueSnackbar('토큰이 재설정 되었습니다');
            }
          } catch (error) {
            enqueueSnackbar('토큰 재발급 오류', { variant: 'error' });
          }
        }}
      >
        토큰 재발급후 token 재설정
      </Button>
      <Button
        variant="contained"
        onClick={() => {
          const accessToken = localStorage.getItem('accessToken');
          console.log(getJwtExpiration(accessToken));
        }}
      >
        토큰 만료 시간 Log
      </Button>
    </Stack>
  );
}

function getJwtExpiration(token: string | null) {
  if (!token) {
    return null;
  }

  // JWT는 세 부분으로 나뉘어 '.'으로 구분됩니다.
  const base64Url = token.split('.')[1];
  if (!base64Url) {
    return null;
  }

  // Base64 인코딩을 디코딩합니다.
  const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
  const jsonPayload = decodeURIComponent(
    atob(base64)
      .split('')
      .map((c) => `%${`00${c.charCodeAt(0).toString(16)}`.slice(-2)}`)
      .join('')
  );

  const payload = JSON.parse(jsonPayload);

  if (!payload.exp) {
    return null;
  }

  // Unix 타임스탬프를 JavaScript Date 객체로 변환합니다.
  const expirationDate = new Date(payload.exp * 1000);
  return expirationDate;
}
