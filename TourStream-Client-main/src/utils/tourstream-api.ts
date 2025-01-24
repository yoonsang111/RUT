import axios, { AxiosRequestConfig } from 'axios';

// ----------------------------------------------------------------------

export type ApiResponseType<T> = {
  data: T;
  status: 'SUCCESS' | 'FAIL';
  message: string;
};

interface IAuthRefresh {
  accessToken: string;
  tokenType: string;
  expiresIn: number;
}

// ----------------------------------------------------------------------

// TourStream Api
class Api {
  private axiosInstance = axios.create({
    baseURL: 'https://api.tourstream.co.kr',
    withCredentials: true,
  });

  private static instance: Api;

  constructor() {
    // Request Interceptor
    this.axiosInstance.interceptors.request.use(
      (config) => {
        const accessToken = sessionStorage.getItem('accessToken');

        if (accessToken) {
          config.headers.Authorization = `Bearer ${accessToken}`;
        }

        return config;
      },
      (error) => Promise.reject(error)
    );

    // Response Interceptor
    this.axiosInstance.interceptors.response.use(
      (res) => res,
      async (error) => {
        // 401
        const prevRequest = error?.config;

        if (error?.response?.status === 403 && !prevRequest?.sent) {
          try {
            prevRequest.sent = true;
            const accessToken = await this.refreshToken();
            prevRequest.headers.Authorization = `Bearer ${accessToken}`;
            sessionStorage.setItem('accessToken', accessToken);
            return await this.axiosInstance(prevRequest);
          } catch (err) {
            return Promise.reject(error?.message || 'Something went wrong');
          }
        }

        // Other Errors
        return Promise.reject(error?.response?.data || 'Something went wrong');
      }
    );
  }

  private async refreshToken() {
    const { data } = await this.get<IAuthRefresh>(apiEndpoints.accessToken);
    return data.accessToken;
  }

  public static getInstance() {
    if (!Api.instance) {
      Api.instance = new Api();
    }
    return Api.instance;
  }

  public async get<T>(url: string, config?: any) {
    const response = await this.axiosInstance.get<ApiResponseType<T>>(url, config);
    return response.data;
  }

  public async post<T>(url: string, data?: any, config?: any) {
    const response = await this.axiosInstance.post<ApiResponseType<T>>(url, data, config);
    return response.data;
  }

  public async put<T>(url: string, data?: any, config?: any) {
    const response = await this.axiosInstance.put<ApiResponseType<T>>(url, data, config);
    return response.data;
  }

  public async delete<T>(url: string, config?: any) {
    const response = await this.axiosInstance.delete<ApiResponseType<T>>(url, config);
    return response.data;
  }
}

const api = Api.getInstance();

export default api;

// ----------------------------------------------------------------------

export const fetcher = async (args: string | [string, AxiosRequestConfig]) => {
  const [url, config] = Array.isArray(args) ? args : [args];

  const res = await api.get(url, { ...config });

  return res.data;
};

// ----------------------------------------------------------------------

// TourStream api의 모든 endpoint
export const apiEndpoints = {
  accessToken: '/access-token',
  auth: {
    login: '/auth/login',
    logout: '/auth/logout',
  },
  partners: {
    profile: '/partners/profile',
  },
  products: {
    root: '/products',
    productId: (productId: number) => `/products/${productId}`,
    files: {
      root: '/products/files',
      upload: '/products/files/upload',
    },
  },
  options: {
    root: '/options',
  },
};
