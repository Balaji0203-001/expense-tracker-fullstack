import { useEffect, useState } from "react"
import { toast } from "react-toastify"

import {
    getDashboardSummary,
    getCategoryTotals,
    getMonthlyTotals,
} from "../../services/dashboardService"

import {
    PieChart,
    Pie,
    Cell,
    Tooltip,
    Legend,
    ResponsiveContainer,
    BarChart,
    Bar,
    XAxis,
    YAxis,
    CartesianGrid,
} from "recharts"

const COLORS = [
    "#0088FE",
    "#00C49F",
    "#FFBB28",
    "#FF8042",
    "#A855F7",
]

function DashboardPage() {

    // Summary state
    const [summary, setSummary] = useState({
        totalExpenses: 0,
        highestExpense: 0,
        totalTransactions: 0,
    })

    // Category chart data
    const [categoryData, setCategoryData] =
        useState([])

    // Monthly chart data
    const [monthlyData, setMonthlyData] =
        useState([])

    const [loading, setLoading] =
        useState(true)

    // Fetch dashboard data
    useEffect(() => {

        fetchDashboardData()

    }, [])

    // Fetch all dashboard APIs
    const fetchDashboardData = async () => {

        try {

            setLoading(true)

            // API calls
            const summaryResponse =
                await getDashboardSummary()

            const categoryResponse =
                await getCategoryTotals()

            const monthlyResponse =
                await getMonthlyTotals()

            // Store data
            setSummary(summaryResponse)

            setCategoryData(categoryResponse)

            setMonthlyData(monthlyResponse)

        } catch (error) {

            toast.error(
                "Failed to load dashboard"
            )

        } finally {

            setLoading(false)
        }
    }

    if (loading) {

        return (

            <div className="text-center py-20">

                <h1 className="text-3xl font-bold">
                    Loading Dashboard...
                </h1>

            </div>
        )
    }

    return (
        <div className="max-w-7xl mx-auto p-6">

            {/* Page Title */}
            <h1 className="text-3xl font-bold mb-8">
                Dashboard
            </h1>

            {/* Summary Cards */}
            <div className="grid grid-cols-1 md:grid-cols-3 gap-6">

                {/* Total Expenses */}
                <div className="bg-white shadow-lg rounded-lg p-6">

                    <h2 className="text-gray-500 text-sm">
                        Total Expenses
                    </h2>

                    <p className="text-3xl font-bold mt-2">
                        ₹{summary.totalExpenses}
                    </p>

                </div>

                {/* Highest Expense */}
                <div className="bg-white shadow-lg rounded-lg p-6">

                    <h2 className="text-gray-500 text-sm">
                        Highest Expense
                    </h2>

                    <p className="text-3xl font-bold mt-2">
                        ₹{summary.highestExpense}
                    </p>

                </div>

                {/* Transactions */}
                <div className="bg-white shadow-lg rounded-lg p-6">

                    <h2 className="text-gray-500 text-sm">
                        Transactions
                    </h2>

                    <p className="text-3xl font-bold mt-2">
                        {summary.totalTransactions}
                    </p>

                </div>

            </div>

            {/* Charts */}
            <div className="grid grid-cols-1 lg:grid-cols-2 gap-8 mt-10">

                {/* Pie Chart */}
                <div className="bg-white shadow-lg rounded-lg p-6">

                    <h2 className="text-2xl font-bold mb-6">
                        Category Analytics
                    </h2>

                    <div className="h-80">

                        <ResponsiveContainer width="100%" height="100%">

                            <PieChart>

                                <Pie
                                    data={categoryData}
                                    dataKey="totalAmount"
                                    nameKey="category"
                                    cx="50%"
                                    cy="50%"
                                    outerRadius={100}
                                    label
                                >

                                    {categoryData.map(
                                        (entry, index) => (

                                            <Cell
                                                key={index}
                                                fill={
                                                    COLORS[
                                                    index % COLORS.length
                                                    ]
                                                }
                                            />

                                        )
                                    )}

                                </Pie>

                                <Tooltip />

                                <Legend />

                            </PieChart>

                        </ResponsiveContainer>

                    </div>

                </div>

                {/* Bar Chart */}
                <div className="bg-white shadow-lg rounded-lg p-6">

                    <h2 className="text-2xl font-bold mb-6">
                        Monthly Expenses
                    </h2>

                    <div className="h-80">

                        <ResponsiveContainer width="100%" height="100%">

                            <BarChart data={monthlyData}>

                                <CartesianGrid strokeDasharray="3 3" />

                                <XAxis dataKey="month" />

                                <YAxis />

                                <Tooltip />

                                <Legend />

                                <Bar
                                    dataKey="totalAmount"
                                    fill="#8884d8"
                                />

                            </BarChart>

                        </ResponsiveContainer>

                    </div>

                </div>

            </div>

        </div>
    )
}

export default DashboardPage