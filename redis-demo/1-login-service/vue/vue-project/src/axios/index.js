import axios from 'axios'

const instance = axios.create({
  baseURL: 'http://127.0.0.1:8081',
  timeout: 5000
})

// 配置请求拦截器
instance.interceptors.request.use(
  config => {
    // 在发送请求之前做些什么
    // config.headers.Authorization = 'Bearer ' + localStorage.getItem('token')
    config.headers.Authorization = localStorage.getItem('token')
    console.log("在发送请求之前做些什么")
    return config
  },
  error => {
    // 对请求错误做些什么
    console.log("对请求错误做些什么")
    return Promise.reject(error)
  }
)

// 配置响应拦截器
instance.interceptors.response.use(
  response => {
    // 对响应数据做点什么
    console.log("对响应数据做点什么")
    return response.data
  },
  error => {
    // 对响应错误做点什么
    if (error.response) {
      switch (error.response.status) {
        case 401:
          // 未授权访问
          setTimeout(() => {
            window.location.href = "/login"
          }, 200);
          break
        case 404:
          // 请求的资源不存在
          break
        case 500:
          // 服务器内部错误
          break
      }
    }
    return Promise.reject(error)
  }
)

export default instance