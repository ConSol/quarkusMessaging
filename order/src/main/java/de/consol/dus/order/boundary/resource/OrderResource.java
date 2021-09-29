package de.consol.dus.order.boundary.resource;

import de.consol.dus.order.OrderService;
import de.consol.dus.order.boundary.request.CreateOrderRequest;
import de.consol.dus.order.boundary.response.OrderResponse;
import java.net.URI;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import lombok.AllArgsConstructor;

@Path("api/orders")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@AllArgsConstructor
public class OrderResource {

  private final OrderService service;

  @POST
  public Response createOrder(CreateOrderRequest request) {
    final OrderResponse created = service.createOrder(request);
    return Response.created(URI.create(created.getUuid().toString())).entity(created).build();
  }

  @GET
  public Response getAllOrders() {
    return Response.ok(service.findAllOrders()).build();
  }
}
