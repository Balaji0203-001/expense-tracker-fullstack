import { useEffect, useState } from "react"
import { toast } from "react-toastify"

import {
    getExpenses,
    addExpense,
    deleteExpense,
    updateExpense,
} from "../../services/expenseService"

function ExpensesPage() {

    // Expense list
    const [expenses, setExpenses] = useState([])

    // Editing state
    const [editingId, setEditingId] =
        useState(null)

    const [loading, setLoading] =
        useState(true)

    // Form state
    const [formData, setFormData] = useState({
        title: "",
        amount: "",
        category: "FOOD",
        description: "",
        expenseDate: "",
    })

    // Fetch expenses on page load
    useEffect(() => {
        fetchExpenses()
    }, [])

    // Fetch expenses
    const fetchExpenses = async () => {

        try {

            setLoading(true)

            const data = await getExpenses()

            setExpenses(data)

        } catch (error) {

            toast.error("Failed to fetch expenses")

        } finally {

            setLoading(false)
        }
    }

    // Handle input change
    const handleChange = (e) => {

        setFormData({
            ...formData,
            [e.target.name]: e.target.value,
        })
    }

    // Add or update expense
    const handleSubmit = async (e) => {

        e.preventDefault()

        try {

            // UPDATE
            if (editingId) {

                await updateExpense(
                    editingId,
                    formData
                )

                toast.success("Expense updated")

                setEditingId(null)
            }

            // ADD
            else {

                await addExpense(formData)

                toast.success("Expense added")
            }

            // Refresh expenses
            fetchExpenses()

            // Reset form
            setFormData({
                title: "",
                amount: "",
                category: "FOOD",
                description: "",
                expenseDate: "",
            })

        } catch (error) {

            toast.error("Operation failed")
        }
    }

    // Delete expense
    const handleDelete = async (id) => {

        try {

            await deleteExpense(id)

            toast.success("Expense deleted")

            fetchExpenses()

        } catch (error) {

            toast.error("Delete failed")
        }
    }

    // Edit expense
    const handleEdit = (expense) => {

        setEditingId(expense.id)

        setFormData({
            title: expense.title,
            amount: expense.amount,
            category: expense.category,
            description: expense.description,
            expenseDate: expense.expenseDate,
        })
    }

    return (
        <div className="max-w-5xl mx-auto p-6">

            {/* Page Title */}
            <h1 className="text-3xl font-bold mb-6">
                Expenses
            </h1>

            {/* Add Expense Form */}
            <form
                onSubmit={handleSubmit}
                className="grid grid-cols-1 md:grid-cols-2 gap-4 mb-8"
            >

                <input
                    type="text"
                    name="title"
                    placeholder="Title"
                    value={formData.title}
                    onChange={handleChange}
                    className="border p-2 rounded"
                    required
                />

                <input
                    type="number"
                    name="amount"
                    placeholder="Amount"
                    value={formData.amount}
                    onChange={handleChange}
                    className="border p-2 rounded"
                    required
                />

                <select
                    name="category"
                    value={formData.category}
                    onChange={handleChange}
                    className="border p-2 rounded"
                >
                    <option value="FOOD">FOOD</option>
                    <option value="TRAVEL">TRAVEL</option>
                    <option value="SHOPPING">SHOPPING</option>
                    <option value="BILLS">BILLS</option>
                    <option value="ENTERTAINMENT">
                        ENTERTAINMENT
                    </option>
                </select>

                <input
                    type="date"
                    name="expenseDate"
                    value={formData.expenseDate}
                    onChange={handleChange}
                    className="border p-2 rounded"
                    required
                />

                <input
                    type="text"
                    name="description"
                    placeholder="Description"
                    value={formData.description}
                    onChange={handleChange}
                    className="border p-2 rounded md:col-span-2"
                />

                <button
                    type="submit"
                    className="bg-black text-white p-2 rounded md:col-span-2"
                >
                    {editingId
                        ? "Update Expense"
                        : "Add Expense"}
                </button>

            </form>

            {/* Loading State */}
            {loading && (

                <div className="text-center py-10">

                    <p className="text-xl font-semibold">
                        Loading expenses...
                    </p>

                </div>

            )}

            {/* Expense Table */}
            <div className="overflow-x-auto">

                <table className="w-full border">

                    <thead className="bg-gray-100">

                        <tr>

                            <th className="border p-2">
                                Title
                            </th>

                            <th className="border p-2">
                                Amount
                            </th>

                            <th className="border p-2">
                                Category
                            </th>

                            <th className="border p-2">
                                Date
                            </th>

                            <th className="border p-2">
                                Actions
                            </th>

                        </tr>

                    </thead>

                    <tbody>

                        {!loading && expenses.length === 0 ? (

                            <tr>

                                <td
                                    colSpan="5"
                                    className="text-center p-6"
                                >
                                    No expenses found
                                </td>

                            </tr>

                        ) : (

                            expenses.map((expense) => (

                                <tr key={expense.id}>

                                    <td className="border p-2">
                                        {expense.title}
                                    </td>

                                    <td className="border p-2">
                                        ₹{expense.amount}
                                    </td>

                                    <td className="border p-2">
                                        {expense.category}
                                    </td>

                                    <td className="border p-2">
                                        {expense.expenseDate}
                                    </td>

                                    <td className="border p-2">

                                        <button
                                            onClick={() =>
                                                handleEdit(expense)
                                            }
                                            className="bg-blue-500 text-white px-3 py-1 rounded mr-2"
                                        >
                                            Edit
                                        </button>

                                        <button
                                            onClick={() =>
                                                handleDelete(expense.id)
                                            }
                                            className="bg-red-500 text-white px-3 py-1 rounded"
                                        >
                                            Delete
                                        </button>

                                    </td>

                                </tr>

                            ))

                        )}

                    </tbody>
                </table>

            </div>

        </div>
    )
}

export default ExpensesPage