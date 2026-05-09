import { useState } from "react"
import { useNavigate, Link } from "react-router-dom"
import { toast } from "react-toastify"

import { registerUser } from "../../services/authService"

function RegisterPage() {

  const navigate = useNavigate()

  const [formData, setFormData] = useState({
    name: "",
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
        await registerUser(formData)

      // Save token
      localStorage.setItem(
        "token",
        response.token
      )

      toast.success("Registration successful")

      navigate("/dashboard")

    } catch (error) {

      toast.error(
        error.response?.data?.message ||
        "Registration failed"
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
          Register
        </h1>

        <input
          type="text"
          name="name"
          placeholder="Name"
          onChange={handleChange}
          className="w-full border p-2 rounded"
        />

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
          Register
        </button>

        <p className="text-center">

          Already have an account?

          <Link
            to="/login"
            className="text-blue-500 ml-1"
          >
            Login
          </Link>

        </p>

      </form>

    </div>
  )
}

export default RegisterPage