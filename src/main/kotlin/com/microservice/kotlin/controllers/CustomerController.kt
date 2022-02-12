package com.microservice.kotlin.controllers

import com.microservice.kotlin.exceptions.CustomerNotFoundException
import com.microservice.kotlin.models.Customer
import com.microservice.kotlin.services.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class CustomerController(val customerService: CustomerService) {
    @GetMapping("/customer/{id}")
    fun getCustomer(@PathVariable id: Int) : ResponseEntity<Customer?> {
        val customer = customerService.getCustomer(id) ?:
            throw CustomerNotFoundException("customer '$id' not found")
        return ResponseEntity(customer, HttpStatus.OK)
    }

    @GetMapping("/customers")
    fun getCustomers(@RequestParam(required = false, defaultValue = "") nameFilter: String) : ResponseEntity<List<Customer>> {
        val customers = customerService.searchCustomers(nameFilter)
        val status = if(customers.isEmpty()) HttpStatus.NOT_FOUND else HttpStatus.OK
        return ResponseEntity(customers, status)
    }

    @PostMapping("/customer")
    fun createCustomer(@RequestBody customer: Customer): ResponseEntity<Unit?> {
        customerService.createCustomer(customer)
        return ResponseEntity<Unit?>(null, HttpStatus.CREATED)
    }

    @DeleteMapping("/customer/{id}")
    fun deleteCustomer(@PathVariable id: Int): ResponseEntity<Unit?> {
        var status = HttpStatus.NOT_FOUND
        if (customerService.getCustomer(id) != null) {
            customerService.deleteCustomer(id)
            status = HttpStatus.OK
        }

        return ResponseEntity<Unit?>(null, status)
    }

    @PutMapping("/cutomer/{id}")
    fun updateCustomer(@PathVariable id: Int, @RequestBody customer: Customer): ResponseEntity<Unit?> {
        var status = HttpStatus.NOT_FOUND
        if (customerService.getCustomer(id) != null) {
            customerService.updateCustomer(id, customer)
            status = HttpStatus.ACCEPTED
        }

        return ResponseEntity<Unit?>(null, status)
    }
}