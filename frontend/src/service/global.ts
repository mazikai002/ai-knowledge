import axios, { AxiosInstance } from "axios";

export interface HttpResp<T = undefined> {
  success: boolean;
  msg: string;
  code: string;
  content: T;
}

export const http: AxiosInstance = axios.create({
  withCredentials: true,
  baseURL: "/api",
});

http.interceptors.response.use(
  (resp) => {
    if (resp.data.code === "401") {
      window.location.href = "/login";
    }
    return resp.data;
  },
  (error) => {
    if (error.response.status === 401) {
      window.location.href = "/login";
    }
    throw error;
  },
);
