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

// Get all expenses
export const getExpenses = async () => {

  const response = await axiosInstance.get(
    "/expenses",
    getAuthConfig()
  )

  return response.data
}

// Add expense
export const addExpense = async (data) => {

  const response = await axiosInstance.post(
    "/expenses",
    data,
    getAuthConfig()
  )

  return response.data
}

// Delete expense
export const deleteExpense = async (id) => {

  const response = await axiosInstance.delete(
    `/expenses/${id}`,
    getAuthConfig()
  )

  return response.data
}

// Update expense
export const updateExpense = async (
  id,
  data
) => {

  const response = await axiosInstance.put(
    `/expenses/${id}`,
    data,
    getAuthConfig()
  )

  return response.data
}