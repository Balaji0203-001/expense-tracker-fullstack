import {
  BrowserRouter,
  Routes,
  Route,
} from "react-router-dom"

import LoginPage from "../pages/auth/LoginPage"
import RegisterPage from "../pages/auth/RegisterPage"
import DashboardPage from "../pages/dashboard/DashboardPage"
import ExpensesPage from "../pages/expenses/ExpensesPage"

import ProtectedRoute from "./ProtectedRoute"
import MainLayout from "../layouts/MainLayout"

function AppRoutes() {

  return (
    <BrowserRouter>

      <Routes>

        {/* Public Routes */}
        <Route
          path="/login"
          element={<LoginPage />}
        />

        <Route
          path="/register"
          element={<RegisterPage />}
        />

        {/* Protected Routes */}
        <Route
          element={
            <ProtectedRoute>
              <MainLayout />
            </ProtectedRoute>
          }
        >

          <Route
            path="/dashboard"
            element={<DashboardPage />}
          />

          <Route
            path="/expenses"
            element={<ExpensesPage />}
          />

        </Route>

      </Routes>

    </BrowserRouter>
  )
}

export default AppRoutes