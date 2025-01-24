import React, { useState, useEffect } from 'react';

import { Alert, Collapse, IconButton } from '@mui/material';

import Iconify from 'src/components/iconify';

// ----------------------------------------------------------------------

interface OptionsErrorAlertProps {
  optionsErrors: any;
}

export default function OptionsErrorAlert({ optionsErrors }: OptionsErrorAlertProps) {
  const [show, setShow] = useState(true);

  useEffect(() => {
    setShow(true);
  }, [optionsErrors]);

  if (!optionsErrors) return null;

  let errorMessage = '';

  optionsErrors.forEach((optionError: any, index: number) => {
    if (!optionError) return;

    errorMessage += `구분 "${index + 1}" 오류:\n`;

    Object.entries(optionError).forEach(([key, value]: any) => {
      if (key !== 'subOptions') {
        errorMessage += `${value?.message}\n`;
      }
    });

    errorMessage += '\n';

    // 하위 옵션 오류 메세지 출력
    if (optionError?.subOptions) {
      optionError?.subOptions.forEach((subOptionError: any, subOptionIndex: number) => {
        errorMessage += `구분 "${index + 1}-${subOptionIndex + 1}" 오류:\n`;

        Object.values(subOptionError).forEach((errorValue: any) => {
          errorMessage += `${errorValue?.message}\n`;
        });

        errorMessage += '\n';
      });
    }
  });

  return (
    <Collapse in={show}>
      <Alert
        severity="error"
        style={{ whiteSpace: 'pre-wrap' }}
        variant="outlined"
        action={
          <IconButton aria-label="close" size="small" onClick={() => setShow(false)}>
            <Iconify icon="material-symbols:close" width={16} />
          </IconButton>
        }
      >
        {errorMessage}
      </Alert>
    </Collapse>
  );
}
