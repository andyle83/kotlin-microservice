package com.microservice.kotlin.services

import com.microservice.kotlin.models.Customer

interface CustomerService {
    fun getCustomer(id: Int): Customer?
    fun searchCustomers(nameFilter: String): List<Customer>
    fun createCustomer(customer: Customer)
    fun deleteCustomer(id: Int)
    fun updateCustomer(id: Int, customer: Customer)
}