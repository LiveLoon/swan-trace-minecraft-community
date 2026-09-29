import axios from 'axios'
import type { AxiosInstance, AxiosRequestConfig, AxiosResponse, AxiosError } from 'axios'
// 创建 axios 实例
const service: AxiosInstance = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL,
  timeout: 30000, // 请求超时 30s
  headers: {
    'Content-Type': 'application/json'
  }
})

// ---------- 请求拦截器 ----------
service.interceptors.request.use(
  (config) => {
    // 可在此添加 token
    const token = localStorage.getItem('token')
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    // 如果请求需要特殊配置（如上传文件），可在这里判断
    return config
  },
  (error: AxiosError) => {
    return Promise.reject(error)
  }
)

// ---------- 响应拦截器 ----------
service.interceptors.response.use(
  (response: AxiosResponse) => {
    // 如果后端有统一的数据结构，可在此解包
    // 例如：return response.data
    return response.data
  },
  (error: AxiosError) => {
    // 统一错误处理
    if (error.response) {
      const status = error.response.status
      switch (status) {
        case 401:
          // 未授权，清除 token 并跳转登录
          localStorage.removeItem('token')
          // 这里可以触发路由跳转，但组件内手动处理更灵活
          break
        case 403:
          console.warn('权限不足')
          break
        case 404:
          console.warn('请求资源不存在')
          break
        case 500:
          console.error('服务器内部错误')
          break
        default:
          console.error(`请求错误: ${status}`)
      }
    } else if (error.request) {
      console.error('网络异常，请检查网络连接')
    } else {
      console.error('请求配置错误:', error.message)
    }
    return Promise.reject(error)
  }
)

export default service
