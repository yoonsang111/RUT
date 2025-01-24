'use client';

import { useNavigate } from 'react-router';
import { useMemo, useState, useEffect, useCallback } from 'react';

import { paths } from 'src/routes/paths';

import api, { apiEndpoints } from 'src/utils/tourstream-api';

import { AuthContext } from './auth-context';

// ----------------------------------------------------------------------

interface IAuthLogin {
  accessToken: string;
  tokenType: string;
  expiresIn: number;
}

interface IPartnerProfile {
  partnerId: number;
  name: string;
  phoneNumber: string;
  email: string;
  corporateRegistrationNumber: string;
  customerServiceContact: string;
}

// ----------------------------------------------------------------------

type Props = {
  children: React.ReactNode;
};

export function AuthProvider({ children }: Props) {
  const [loading, setLoading] = useState(true);
  const [authenticated, setAuthenticated] = useState(false);
  const [authError, setAuthError] = useState('');

  const navigate = useNavigate();

  const initialize = useCallback(async () => {
    try {
      const res = await api.get<IPartnerProfile>(apiEndpoints.partners.profile);

      const { status } = res;

      if (status === 'SUCCESS') {
        setAuthenticated(true);
      } else {
        setAuthenticated(false);
      }
    } catch (error) {
      console.error(error);
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    initialize();
  }, [initialize]);

  // LOGIN
  const login = useCallback(async (email: string, password: string) => {
    try {
      const data = {
        email,
        password,
      };

      const res = await api.post<IAuthLogin>(apiEndpoints.auth.login, data);

      const { accessToken } = res.data;

      if (!accessToken) throw new Error('AccessToken을 가져올 수 없습니다');

      sessionStorage.setItem('accessToken', accessToken);

      setAuthenticated(true);
    } catch (error) {
      setAuthError(`로그인 에러 발생: ${error.message}`);
    }
  }, []);

  // LOGOUT
  const logout = useCallback(async () => {
    try {
      await api.post(apiEndpoints.auth.logout);
    } catch (error) {
      console.error(error);
    } finally {
      sessionStorage.removeItem('accessToken');
      setAuthenticated(false);
      navigate(paths.auth.login, { replace: true });
    }
  }, [navigate]);

  // ----------------------------------------------------------------------

  const memoizedValue = useMemo(
    () => ({
      loading,
      authenticated,
      error: authError,
      //
      login,
      logout,
    }),
    [login, logout, authError, authenticated, loading]
  );

  return <AuthContext.Provider value={memoizedValue}>{children}</AuthContext.Provider>;
}
