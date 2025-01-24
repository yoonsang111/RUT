import { IPartnersProfile } from 'src/types/partners';

// ----------------------------------------------------------------------

export type ActionMapType<M extends { [index: string]: any }> = {
  [Key in keyof M]: M[Key] extends undefined
    ? {
        type: Key;
      }
    : {
        type: Key;
        payload: M[Key];
      };
};

export type AuthStateType = {
  status?: string;
  error?: string;
  loading: boolean;
  profile: IPartnersProfile;
};

// ----------------------------------------------------------------------

export type AuthContextType = {
  loading: boolean;
  authenticated: boolean;
  login: (email: string, password: string) => Promise<void>;
  logout: () => Promise<void>;
};
