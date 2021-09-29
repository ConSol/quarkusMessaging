package de.consol.customer;

import de.consol.customer.boundary.repository.CustomerRepository;
import de.consol.customer.boundary.request.CreateCustomerRequest;
import de.consol.customer.boundary.response.CustomerResponse;
import de.consol.customer.boundary.response.OrderResponse;
import de.consol.customer.entity.CustomerEntity;
import de.consol.customer.entity.mapper.CustomerMapper;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class CustomerService {

  private final CustomerRepository repository;
  private final CustomerMapper mapper;
  private final OrderService orderService;

  public CustomerResponse createNewCustomer(CreateCustomerRequest request) {
    final CustomerEntity toSave = mapper.requestToEntity(request, Instant.now());
    final CustomerEntity saved = repository.save(toSave);
    return mapper.entityToResponse(saved);
  }

  public List<CustomerResponse> getAllCustomers() {
    return repository.findAll().stream()
        .map(mapper::entityToResponse)
        .collect(Collectors.toList());
  }

  public Optional<CustomerResponse> getCustomerByEmail(String email) {
    return repository.findByEmail(email)
        .map(mapper::entityToResponse);
  }

  public List<OrderResponse> getOrdersByCustomerEmail(String email) {
    return orderService.getOrdersByCustomerEmail(email);
  }
}
