import axiosInstance from "../api/axios"

// Get token
const getToken = () => {
  return localStorage.getItem("token")
}

// Auth headers
const getAuthConfig = () => {
  return {
    headers: {
      Authorization: `Bearer ${getToken()}`
    }
  }
}

// Dashboard summary
export const getDashboardSummary =
  async () => {

    const response =
      await axiosInstance.get(
        "/dashboard/summary",
        getAuthConfig()
      )

    return response.data
  }

// Category totals
export const getCategoryTotals =
  async () => {

    const response =
      await axiosInstance.get(
        "/dashboard/category-total",
        getAuthConfig()
      )

    return response.data
  }

// Monthly totals
export const getMonthlyTotals =
  async () => {

    const response =
      await axiosInstance.get(
        "/dashboard/monthly",
        getAuthConfig()
      )

    return response.data
  }