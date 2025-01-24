import React, { memo, useState } from 'react';

import { Alert, Collapse, IconButton } from '@mui/material';

import Iconify from 'src/components/iconify';

const OptionsTableInfo = memo(() => {
  const [show, setShow] = useState(true);

  return (
    <Collapse in={show} sx={{ width: 1 }}>
      <Alert
        severity="info"
        variant="outlined"
        sx={{ width: 1, display: 'flex', alignItems: 'center' }}
        action={
          <IconButton aria-label="close" size="small" onClick={() => setShow(false)}>
            <Iconify icon="material-symbols:close" width={16} />
          </IconButton>
        }
      >
        셀을 더블클릭하여 수정 할 수 있습니다
      </Alert>
    </Collapse>
  );
});

export default OptionsTableInfo;
