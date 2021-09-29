package de.consol.dus.order;

import de.consol.dus.order.boundary.repository.CustomerRepository;
import de.consol.dus.order.boundary.request.CreateCustomerRequest;
import de.consol.dus.order.boundary.response.CustomerResponse;
import de.consol.dus.order.entity.CustomerEntity;
import de.consol.dus.order.entity.mapper.CustomerMapper;
import javax.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class CustomerService {

  private final CustomerRepository repository;
  private final CustomerMapper mapper;

  public CustomerResponse createNewCustomer(CreateCustomerRequest request) {
    final CustomerEntity toSave = mapper.requestToEntity(request);
    final CustomerEntity saved = repository.save(toSave);
    return mapper.entityToResponse(saved);
  }
}
