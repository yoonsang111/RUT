import Typography from '@mui/material/Typography';
import Paper, { PaperProps } from '@mui/material/Paper';

// ----------------------------------------------------------------------

interface Props extends PaperProps {
  query?: string;
}

export default function SearchNotFound({ query, sx, ...other }: Props) {
  return query ? (
    <Paper
      sx={{
        bgcolor: 'unset',
        textAlign: 'center',
        ...sx,
      }}
      {...other}
    >
      <Typography variant="h6" gutterBottom>
        결과 없음
      </Typography>

      <Typography variant="body2">
        &nbsp;
        <strong>&quot;{query}&quot;</strong>.
        <br /> 키워드에 대한 결과를 찾을 수 없습니다.
      </Typography>
    </Paper>
  ) : (
    <Typography variant="body2" sx={sx}>
      키워드를 입력해 주세요
    </Typography>
  );
}
