import { forwardRef } from 'react';

import { Typography, TypographyProps } from '@mui/material';

const TourStream = forwardRef<HTMLSpanElement, TypographyProps>(({ sx, ...other }, ref) => (
  <Typography
    ref={ref}
    sx={{
      ...sx,
      fontFamily: 'Sansita Swashed, sans-serif',
      background: 'linear-gradient(to bottom right, #000000, #9c9c9c)',
      WebkitBackgroundClip: 'text',
      WebkitTextFillColor: 'transparent',
      display: 'inline-block',
    }}
    {...other}
  >
    TourStream
  </Typography>
));

export default TourStream;
