import { useState } from "react"
import { useNavigate, Link } from "react-router-dom"
import { toast } from "react-toastify"

import { loginUser } from "../../services/authService"

function LoginPage() {

  const navigate = useNavigate()

  const [formData, setFormData] = useState({
    email: "",
    password: "",
  })

  // Handle input change
  const handleChange = (e) => {

    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    })
  }

  // Handle form submit
  const handleSubmit = async (e) => {

    e.preventDefault()

    try {

      const response =
        await loginUser(formData)

      // Save token
      localStorage.setItem(
        "token",
        response.token
      )

      toast.success("Login successful")

      navigate("/dashboard")

    } catch (error) {

      toast.error(
        error.response?.data?.message ||
        "Login failed"
      )
    }
  }

  return (
    <div className="min-h-screen flex items-center justify-center">

      <form
        onSubmit={handleSubmit}
        className="w-full max-w-md p-6 shadow-lg rounded-lg space-y-4"
      >

        <h1 className="text-2xl font-bold text-center">
          Login
        </h1>

        <input
          type="email"
          name="email"
          placeholder="Email"
          onChange={handleChange}
          className="w-full border p-2 rounded"
        />

        <input
          type="password"
          name="password"
          placeholder="Password"
          onChange={handleChange}
          className="w-full border p-2 rounded"
        />

        <button
          type="submit"
          className="w-full bg-black text-white p-2 rounded"
        >
          Login
        </button>

        <p className="text-center">

          Don't have an account?

          <Link
            to="/register"
            className="text-blue-500 ml-1"
          >
            Register
          </Link>

        </p>

      </form>

    </div>
  )
}

export default LoginPage