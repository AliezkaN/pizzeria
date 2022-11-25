package com.pizzamamamia.pizzeria.controller;

import com.pizzamamamia.pizzeria.controller.dto.CustomerDto;
import com.pizzamamamia.pizzeria.controller.validation.group.OnCreate;
import com.pizzamamamia.pizzeria.controller.validation.group.OnUpdate;
import com.pizzamamamia.pizzeria.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customer")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerDto> getAllCustomers(){
    log.info("get all customers");
    return customerService.listCustomers();
    }

    @GetMapping(value = "/{email}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDto getCustomer(@PathVariable String email){
        log.info("getUser by email {}", email);
        return customerService.getCustomer(email);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public CustomerDto createUser(@RequestBody @Validated(OnCreate.class) CustomerDto customerDto){
        log.info("createUser with email {}", customerDto.getEmail());
        return customerService.createCustomer(customerDto);
    }

    @PatchMapping(value = "/{email}")
    public CustomerDto updateUser(@PathVariable String email,
                                  @RequestBody @Validated(OnUpdate.class) CustomerDto customerDto){
        log.info("updateUser with email {}", email);
        return customerService.updateCustomer(email, customerDto);
    }

    @DeleteMapping(value = "/{email}")
    public ResponseEntity<Void> deleteUser(@PathVariable String email){
        log.info("deleteCustomer with email {}", email);
        customerService.deleteCustomer(email);
        return ResponseEntity.noContent().build();
    }

}
