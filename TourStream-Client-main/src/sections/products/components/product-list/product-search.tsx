import { memo, useState, useCallback } from 'react';

import { InputAdornment } from '@mui/material';
import TextField from '@mui/material/TextField';

import Iconify from 'src/components/iconify';

// ----------------------------------------------------------------------

type Props = {
  onSubmit: (query: string) => void;
};

function ProductSearch({ onSubmit }: Props) {
  const [query, setQuery] = useState('');

  const handleInputChange = useCallback((e: React.ChangeEvent<HTMLInputElement>) => {
    setQuery(e.target.value);
  }, []);

  const handleKeyUp = (event: React.KeyboardEvent<HTMLInputElement>) => {
    if (event.key === 'Enter') {
      onSubmit(query);
    }
  };

  return (
    <TextField
      placeholder="상품명 필터"
      onKeyUp={handleKeyUp}
      value={query}
      onChange={handleInputChange}
      InputProps={{
        startAdornment: (
          <InputAdornment position="start">
            <Iconify icon="eva:search-fill" sx={{ ml: 1, color: 'text.disabled' }} />
          </InputAdornment>
        ),
      }}
    />
  );
}

export default memo(ProductSearch);
