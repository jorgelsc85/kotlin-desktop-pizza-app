// Import necessary classes and functions from Jetpack Compose
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

// Import the PizzaOrderScreen, assuming it's a Composable function that represents the UI of the pizza ordering screen
import view.PizzaOrderScreen

// Define the main function of the application
fun main() = application {
    // Create a new window for the application
    Window(
        // Set the action to be performed when the window is requested to close (exit the application)
        onCloseRequest = ::exitApplication,
        // Set the title of the window
        title = "School App"
    ) {
        // Display the PizzaOrderScreen Composable function inside the window
        PizzaOrderScreen()
    }
}
