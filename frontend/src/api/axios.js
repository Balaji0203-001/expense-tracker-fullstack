import axios from "axios"

const axiosInstance = axios.create({
  baseURL: "https://expense-tracker-fullstack-app-1n25.onrender.com/api",
})

export default axiosInstance