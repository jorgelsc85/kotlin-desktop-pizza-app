package repository

// Import necessary classes and functions
import model.Order
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.Connection

// Define the Orders table representing orders in the database
object Orders : Table() {
    private val id: Column<Int> = integer("id").autoIncrement()
    val address: Column<String> = varchar("address", 200)
    val orderSummary: Column<String> = varchar("pizzas", 500)
    override val primaryKey = PrimaryKey(id, name = "order_id")
}

// Repository class to interact with the Orders table
class OrderRepository {

    // Initialize the database connection and create the Orders table
    init {
        Database.connect("jdbc:sqlite:file:src/main/resources/order.db", "org.sqlite.JDBC")
        TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE
        transaction {
            SchemaUtils.create(Orders)
        }
    }

    // Retrieve all orders from the database
    fun getAllOrders(): List<Order> {
        return transaction {
            Orders.selectAll().map { toOrder(it) }
        }
    }

    // Insert an order into the database
    fun insertOrder(order: Order){
        transaction {
            Orders.insertIgnore {
                it[address] = order.address
                it[orderSummary] = order.orderSummary
            }
        }
    }

    // Convert a database row to an Order object
    private fun toOrder(row: ResultRow): Order =
        Order(
            address = row[Orders.address],
            orderSummary = row[Orders.orderSummary]
        )
}

