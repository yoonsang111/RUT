import { Helmet } from 'react-helmet-async';

import { NotFoundView } from 'src/sections/error';

// ----------------------------------------------------------------------

export default function NotFoundPage() {
  return (
    <>
      <Helmet>
        <title> 404 페이지를 찾을 수 없습니다!</title>
      </Helmet>

      <NotFoundView />
    </>
  );
}
