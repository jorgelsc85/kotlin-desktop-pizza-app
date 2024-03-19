package controller

// Import necessary classes and functions from Jetpack Compose
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

// Import Order and Pizza data classes, and OrderRepository class
import model.Order
import model.Pizza
import repository.OrderRepository

// Composable function to place an order for pizzas
@Composable
fun placeOrder(pizzas: List<Pizza>, address: String): String {
    // Create an instance of OrderRepository using remember to retain state across recompositions
    val orderRepository = remember { OrderRepository() }

    // Build a summary of the order including names and prices of selected pizzas
    val orderSummary = buildString {
        pizzas.forEachIndexed { _, pizza ->
            appendLine("   - ${pizza.name}(${pizza.price}CHF)")
        }
    }

    // Insert the order into the database using OrderRepository
    orderRepository.insertOrder(
        Order(
            address = address,
            orderSummary = orderSummary
        )
    )

    // Build a string representing all orders in the database
    val orders = buildString {
        orderRepository.getAllOrders().forEachIndexed { index, order ->
            appendLine("${index + 1}. ${order.address}")
            appendLine(order.orderSummary)
        }
    }

    // Return the string representation of all orders
    return orders
}
