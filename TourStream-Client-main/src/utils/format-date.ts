import { format } from 'date-fns';

export function formatDateToString(date: Date) {
  return format(date, 'yyyy-MM-dd');
}
