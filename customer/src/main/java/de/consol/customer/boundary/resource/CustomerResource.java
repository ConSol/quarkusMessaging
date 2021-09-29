package de.consol.customer.boundary.resource;

import de.consol.customer.boundary.request.CreateCustomerRequest;
import de.consol.customer.boundary.response.CustomerResponse;
import de.consol.customer.CustomerService;
import java.net.URI;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import lombok.AllArgsConstructor;

@Path("/api/customers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@AllArgsConstructor
public class CustomerResource {
  private final CustomerService customerService;

  @POST
  public Response createCustomer(@Valid CreateCustomerRequest request) {
    final CustomerResponse created = customerService.createNewCustomer(request);
    return Response.created(URI.create(created.getEmail())).entity(created).build();
  }

  @GET
  public Response getAllCustomers() {
    return Response.ok(customerService.getAllCustomers()).build();
  }

  @GET
  @Path("{email}")
  public Response getCustomerByEmail(@PathParam("email") @Valid @NotNull @Email String email) {
    return customerService.getCustomerByEmail(email)
        .map(customer -> Response.ok(customer).build())
        .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
  }

  @GET
  @Path("{email}/orders")
  public Response getOrdersByCustomerEmail(
      @PathParam("email") @Valid @NotNull @Email String email) {
    return Response.ok(customerService.getOrdersByCustomerEmail(email)).build();
  }

}
