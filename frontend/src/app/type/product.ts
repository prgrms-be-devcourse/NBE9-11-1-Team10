export interface Product {
  id: number;
  name: string;
  price: number;
  stock: number;
}

export interface ApiResponse<T> {
  success: boolean;
  data: T;
  error?: {
    code: string;
    message: string;
  };
}