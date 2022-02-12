package com.microservice.kotlin.services

import com.microservice.kotlin.models.Customer
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap

@Service
class CustomerServiceImpl : CustomerService {
    companion object {
        val initialCustomers = arrayOf(
            Customer(1, "Kotlin"),
            Customer(2, "Spring")
        )
    }

    val customers = ConcurrentHashMap(
        initialCustomers.associateBy(Customer::id)
    )

    override fun getCustomer(id: Int): Customer? = customers[id]

    override fun searchCustomers(nameFilter: String): List<Customer> {
        return customers.filter {
            it.value.name.contains(nameFilter, true)
        }.map(Map.Entry<Int, Customer>::value).toList()
    }

    override fun createCustomer(customer: Customer) {
        customers[customer.id] = customer
    }

    override fun deleteCustomer(id: Int) {
        customers.remove(id)
    }

    override fun updateCustomer(id: Int, customer: Customer) {
        deleteCustomer(id)
        createCustomer(customer)
    }
}