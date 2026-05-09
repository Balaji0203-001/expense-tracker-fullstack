import { Link, Outlet, useNavigate } from "react-router-dom"

function MainLayout() {

  const navigate = useNavigate()

  // Logout
  const handleLogout = () => {

    localStorage.removeItem("token")

    navigate("/login")
  }

  return (
    <div className="min-h-screen bg-gray-100">

      {/* Navbar */}
      <nav className="bg-black text-white px-6 py-4 flex justify-between items-center">

        {/* Logo */}
        <h1 className="text-2xl font-bold">
          Expense Tracker
        </h1>

        {/* Navigation */}
        <div className="flex gap-6 items-center">

          <Link
            to="/dashboard"
            className="hover:text-gray-300"
          >
            Dashboard
          </Link>

          <Link
            to="/expenses"
            className="hover:text-gray-300"
          >
            Expenses
          </Link>

          <button
            onClick={handleLogout}
            className="bg-red-500 px-4 py-2 rounded hover:bg-red-600"
          >
            Logout
          </button>

        </div>

      </nav>

      {/* Page Content */}
      <div className="p-6">

        <Outlet />

      </div>

    </div>
  )
}

export default MainLayout