package view

// Import necessary classes and functions from Jetpack Compose and the controller
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import controller.placeOrder
import model.Pizza

// Composable function representing the Pizza Order Screen
@Composable
fun PizzaOrderScreen() {
    // Mutable state variables to track address and result
    var address by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    // List of available pizzas
    val pizzas = remember {
        listOf(
            Pizza("Margherita", 9.99),
            Pizza("Pepperoni", 11.99),
            Pizza("Vegetarian", 10.99),
            Pizza("Hawaiian", 12.99),
            Pizza("Meat Lovers", 9.99),
            Pizza("BBQ Chicken", 11.99),
            Pizza("Supreme", 10.99),
            Pizza("Mushroom", 12.99),
            Pizza("Buffalo Chicken", 10.99),
        )
    }

    // Mutable state variable to track selected pizzas
    val selectedPizzas = remember { mutableStateListOf<Pizza>() }
    var runPlaceOrder by remember { mutableStateOf(false) }

    // MaterialTheme for consistent styling
    MaterialTheme {
        // Column to arrange UI vertically
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header text
            Text(text = "Choose your pizza:", style = MaterialTheme.typography.h5)

            // LazyVerticalGrid to display pizzas in a grid layout
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(
                    start = 12.dp,
                    top = 16.dp,
                    end = 12.dp,
                    bottom = 16.dp
                ),
                content = {
                    // Iterate through the list of pizzas and display each pizza as a selection item
                    items(pizzas.size) { index ->
                        PizzaSelectionItem(pizza = pizzas[index]) { isSelected ->
                            if (isSelected) {
                                selectedPizzas.add(pizzas[index])
                            } else {
                                selectedPizzas.remove(pizzas[index])
                            }
                        }
                    }
                }
            )

            // TextField for entering address
            TextField(
                value = address,
                onValueChange = { address = it },
                modifier = Modifier.padding(16.dp),
                label = { Text("Enter your address") }
            )

            // Button to place order
            Row {
                Button(onClick = {
                    runPlaceOrder = true
                }) {
                    Text("Order")
                }
            }

            // Check if order needs to be placed
            if (runPlaceOrder) {
                runPlaceOrder = false
                result = PlaceOrder(pizzas = selectedPizzas, address = address)
            }

            // Display result if available
            if (result.isNotBlank()) {
                Row {
                    OutlinedTextField(
                        value = result,
                        readOnly = true,
                        onValueChange = {},
                        label = { Text("Result") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

// Composable function to place the order
@Composable
private fun PlaceOrder(pizzas: List<Pizza>, address: String): String {
    return placeOrder(pizzas, address)
}

// Composable function representing a single pizza selection item
@Composable
private fun PizzaSelectionItem(pizza: Pizza, onPizzaSelected: (Boolean) -> Unit) {
    // Mutable state variable to track selection state
    var selected by remember { mutableStateOf(false) }

    // Row to arrange UI horizontally
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // Checkbox to allow selection of the pizza
        Checkbox(
            checked = selected,
            onCheckedChange = {
                selected = it
                onPizzaSelected(it) // Notify the parent component of the selection change
            },
            modifier = Modifier.padding(end = 8.dp)
        )
        // Text displaying the name of the pizza
        Text(text = pizza.name)
    }
}
