package br.com.postech.senderorder.sevenfoodorderapi.application.api.resources;

import br.com.postech.senderorder.sevenfoodorderapi.application.api.dto.request.OrderRequest;
import br.com.postech.senderorder.sevenfoodorderapi.application.api.dto.request.ProductRequest;
import br.com.postech.senderorder.sevenfoodorderapi.application.api.dto.response.OrderResponse;
import br.com.postech.senderorder.sevenfoodorderapi.application.api.mappper.OrderApiMapper;
import br.com.postech.senderorder.sevenfoodorderapi.application.api.mappper.ProductApiMapper;
import br.com.postech.senderorder.sevenfoodorderapi.commons.util.RestUtils;
import br.com.postech.senderorder.sevenfoodorderapi.core.domain.StatusPedido;
import br.com.postech.senderorder.sevenfoodorderapi.core.entities.Order;
import br.com.postech.senderorder.sevenfoodorderapi.core.entities.OrderCreation;
import br.com.postech.senderorder.sevenfoodorderapi.core.entities.Product;
import br.com.postech.senderorder.sevenfoodorderapi.core.ports.in.order.*;
import br.com.postech.senderorder.sevenfoodorderapi.core.ports.in.product.CreateProductPort;
import br.com.postech.senderorder.sevenfoodorderapi.core.service.ProductService;
import ch.qos.logback.core.net.server.Client;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/v1/orders")
@Data
@AllArgsConstructor(onConstructor = @__(@Autowired))
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
public class OrderResources {

    private final CreateOrderPort createOrderPort;
    private final DeleteOrderPort deleteOrderPort;
    private final FindByIdOrderPort findByIdOrderPort;
    private final FindOrdersPort findOrdersPort;
    private final UpdateOrderPort updateOrderPort;
    private final OrderApiMapper orderApiMapper;
    private final ProductApiMapper productApiMapper;

    @Operation(summary = "Create a new Order", tags = {"orders", "post"})
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {
                    @Content(schema = @Schema(implementation = OrderResources.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<OrderResponse> save(@Valid @RequestBody OrderRequest request) {
        log.info("Chegada: {}", request);

        List<ProductRequest> products = request.getProducts();
        List<Product> productList = productApiMapper.map(products);
        String clientId = request.getClientId();

        OrderCreation orderCreation = new OrderCreation(clientId, productList);
        Order saved = createOrderPort.save(orderCreation);

        OrderResponse orderResponse = orderApiMapper.fromEntity(saved);
        if (orderResponse == null) {
            return ResponseEntity.ok().build();
        }
        URI location = RestUtils.getUri(orderResponse.getId());

        return ResponseEntity.created(location).body(orderResponse);
    }

    @Operation(summary = "Update a Order by Id", tags = {"orders", "put"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = OrderResources.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())})})
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<OrderResponse> update(@PathVariable("id") Long id, @Valid @RequestBody OrderRequest request) {
        var order = orderApiMapper.fromRequest(request);
        Order updated = updateOrderPort.update(id, order);

        OrderResponse orderResponse = orderApiMapper.fromEntity(updated);
        if (orderResponse == null) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.ok(orderResponse);
    }

    @Operation(summary = "Retrieve all Order", tags = {"orders", "get", "filter"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = OrderResources.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "204", description = "There are no Associations", content = {
                    @Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<OrderResponse>> findAll() {
        List<Order> orderList = findOrdersPort.findAll();
        List<OrderResponse> orderResponse = orderApiMapper.mapResponse(orderList);

        /*orderResponse = orderResponse.stream()
                .filter(o -> ALLOWED_STATUS.contains((o.getStatusPedido())))
                .collect(Collectors.toList());

        orderResponse.sort(new OrderResponse());*/

        return orderResponse.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(orderResponse);
    }

    @Operation(
            summary = "Retrieve a Order by Id",
            description = "Get a Order object by specifying its id. The response is Association object with id, title, description and published status.",
            tags = {"orders", "get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = OrderResources.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<OrderResponse> findOne(@PathVariable("id") Long id) {
        Order orderSaved = findByIdOrderPort.findById(id);
        if (orderSaved != null) {
            OrderResponse orderResponse = orderApiMapper.fromEntity(orderSaved);
            return ResponseEntity.ok(orderResponse);
        }

        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Retrieve a Order by Id",
            description = "Get a Order object by specifying its id. The response is Association object with id, title, description and published status.",
            tags = {"orders", "get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = OrderResources.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping("/code/{code}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<OrderResponse> findByOrder(@PathVariable("code") String code) {
        Order orderSaved = findByIdOrderPort.findByCode(code);
        if (orderSaved != null) {
            OrderResponse orderResponse = orderApiMapper.fromEntity(orderSaved);
            return ResponseEntity.ok(orderResponse);
        }

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete a Order by Id", tags = {"ordertrus", "delete"})
    @ApiResponses({@ApiResponse(responseCode = "204", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> remove(@PathVariable("id") Long id) {
        boolean removed = deleteOrderPort.remove(id);
        return removed ? ResponseEntity.ok("Dados deletados!") : ResponseEntity.notFound().build();
    }

    /**
     * This is an error handler specific to this controller. It overrides the global error handler.
     * In a real-world application, this particular combination of error handlers is almost
     * certainly undesirable: the global error handler returns a complex JSON object, while the
     * local error handler returns a string.
     *
     * @param ex
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        var result = "There's a validation error in the order.";
        var errors = ex.getBindingResult().getAllErrors();
        var details = errors.stream().map(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            return "\n" + fieldName + ":" + errorMessage;
        }).collect(Collectors.joining());
        return result;
    }

    private static final List<StatusPedido> ALLOWED_STATUS = Arrays.asList(
            StatusPedido.PRONTO,
            StatusPedido.EM_PREPARACAO,
            StatusPedido.RECEBIDO
    );
}