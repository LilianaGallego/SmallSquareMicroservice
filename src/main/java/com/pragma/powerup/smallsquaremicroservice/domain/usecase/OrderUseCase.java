package com.pragma.powerup.smallsquaremicroservice.domain.usecase;

import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.entity.OrderEntity;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.exceptions.NoDataFoundException;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.exceptions.OrderInProcessesException;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.request.TraceabilityRequestDto;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.response.OrderPlateResponseDto;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.response.OrderResponseDto;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.response.TraceabilityResponseDto;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.exceptions.PlateNoFromRestautantException;
import com.pragma.powerup.smallsquaremicroservice.configuration.security.TokenInterceptor;
import com.pragma.powerup.smallsquaremicroservice.configuration.security.exception.UserNotRoleAuthorized;
import com.pragma.powerup.smallsquaremicroservice.domain.api.IOrderServicePort;
import com.pragma.powerup.smallsquaremicroservice.domain.dtouser.RestaurantEmployee;
import com.pragma.powerup.smallsquaremicroservice.domain.dtouser.User;
import com.pragma.powerup.smallsquaremicroservice.domain.exceptions.IncorrectCodeException;
import com.pragma.powerup.smallsquaremicroservice.domain.exceptions.NotStatusInProcess;
import com.pragma.powerup.smallsquaremicroservice.domain.exceptions.OrderNotCancellException;
import com.pragma.powerup.smallsquaremicroservice.domain.exceptions.PhoneClientInvalidException;
import com.pragma.powerup.smallsquaremicroservice.domain.model.Order;
import com.pragma.powerup.smallsquaremicroservice.domain.model.OrderPlate;
import com.pragma.powerup.smallsquaremicroservice.domain.model.Restaurant;
import com.pragma.powerup.smallsquaremicroservice.domain.spi.*;
import com.pragma.powerup.smallsquaremicroservice.utilitis.StateEnum;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class OrderUseCase implements IOrderServicePort {

    private final IOrderPersistencePort orderPersistencePort;
    private final IRestaurantPersistencePort  restaurantPersistencePort;
    private final IRestaurantEmployeePersistencePort restaurantEmployeePersistencePort;
    private final IMessangerServicePersistencePort messengerServicePersistencePort;
    private final IUserHttpPersistencePort userHttpPersistencePort;
    private final ITraceabilityPersistencePort traceabilityPersistencePort;

    public static final String READY ="READY";
    public static final String EARNING= "EARNING";
    public static final String DELIVERED = "DELIVERED";
    public static final String CANCELLED = "CANCELLED";
    public static final String PREPARATION = "PREPARATION";

    String phone = "+573118688145";

    public OrderUseCase(IOrderPersistencePort orderPersistencePort, IRestaurantPersistencePort restaurantPersistencePort, IRestaurantEmployeePersistencePort restaurantEmployeePersistencePort, IMessangerServicePersistencePort messengerServicePersistencePort, IUserHttpPersistencePort userHttpPersistencePort, ITraceabilityPersistencePort traceabilityPersistencePort) {
        this.orderPersistencePort = orderPersistencePort;
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.restaurantEmployeePersistencePort = restaurantEmployeePersistencePort;
        this.messengerServicePersistencePort = messengerServicePersistencePort;
        this.userHttpPersistencePort = userHttpPersistencePort;
        this.traceabilityPersistencePort = traceabilityPersistencePort;
    }

    @Override
    public void saveOrder(Long idRestaurant, Order order) {
        Restaurant restaurant = restaurantPersistencePort.findById(idRestaurant);
        order.setIdClient(TokenInterceptor.getIdUser());
        if (orderPersistencePort.existsByIdClient(order.getIdClient())) {
            OrderEntity orderBD = orderPersistencePort.findByIdClient(order.getIdClient());
            validateState(orderBD,order,restaurant);
            TraceabilityRequestDto traceabilityRequestDto =saveTraceabilityDto(Optional.of(orderBD));

            traceabilityPersistencePort.saveTraceability(traceabilityRequestDto);
        }
        order.setDate(LocalDate.now());
        order.setRestaurant(restaurant);
        orderPersistencePort.saveOrder(order);

    }

    @Override
    public void saveOrderPlate(OrderPlate orderPlate) {

        orderPersistencePort.saveOrderPlate(orderPlate);
    }

    @Override
    public void validateState(OrderEntity orderBD, Order order, Restaurant restaurant) {
        switch (orderBD.getStateEnum()){
            case READY ,EARNING ,PREPARATION -> throw new OrderInProcessesException();
            case CANCELLED ,DELIVERED -> {
                order.setStateEnum(StateEnum.EARNING);
                order.setIdClient(TokenInterceptor.getIdUser());
                order.setDate(LocalDate.now());
                order.setRestaurant(restaurant);
                orderPersistencePort.saveOrder(order);
                TraceabilityRequestDto traceabilityRequestDto = saveTraceabilityDto(Optional.of(orderBD));

                traceabilityPersistencePort.saveTraceability(traceabilityRequestDto);
            }
            default ->throw new OrderInProcessesException();
        }

    }

    @Override
    public List<OrderResponseDto> getAllOrdersByStateEnum(StateEnum stateEnum, int page, int size) {

        if ( restaurantEmployeePersistencePort.findByIdEmployee(TokenInterceptor.getIdUser())==null){
            throw new UserNotRoleAuthorized();
        }
        RestaurantEmployee restaurantEmployee = restaurantEmployeePersistencePort.findByIdEmployee(TokenInterceptor.getIdUser());
        Long idRestaurant = restaurantEmployee.getIdRestaurant();
        return orderPersistencePort.getAllOrdersByStateEnum(stateEnum, idRestaurant,page, size);
    }

    @Override
    public List<OrderPlateResponseDto> getAllOrdersByOrder(OrderResponseDto order) {

        return orderPersistencePort.getAllOrdersByOrder(order);
    }

    @Override
    public List<OrderResponseDto> updateStatusOrder(Long idOrder, int page, int size) {
        if(orderPersistencePort.existsById(idOrder)){
            Optional<OrderEntity> order = orderPersistencePort.findById(idOrder);
            Long idEmployee = TokenInterceptor.getIdUser();
            order.get().setStateEnum(StateEnum.PREPARATION.toString());
            validateRestaurant(order.get(),idEmployee);

            return orderPersistencePort.updateStatusOrder(order.get(), StateEnum.PREPARATION, order.get().getRestaurantEntity().getId(), page, size);
        }
        throw new NoDataFoundException();

    }

    @Override
    public void validateRestaurant(OrderEntity orderBD,Long idEmployee) {


        if (!restaurantEmployeePersistencePort.findByIdEmployee(idEmployee).getIdRestaurant().equals(orderBD.getRestaurantEntity().getId())){
            throw new PlateNoFromRestautantException();
        }

        orderBD.setIdChef(idEmployee);

    }


    @Override
    public void updateOrder(Long idOrder, int codeClient) {
        if(!orderPersistencePort.existsById(idOrder)){
            throw new NoDataFoundException();
        }
        Optional<OrderEntity> order = orderPersistencePort.findById(idOrder);
        Long idEmployee = TokenInterceptor.getIdUser();
        validateRestaurant(order.get(),idEmployee);
        validateStateOrder(order.get(), codeClient);

    }

    @Override
    public void validateStateOrder(OrderEntity order, int codeClient) {

        switch (order.getStateEnum()) {
            case PREPARATION-> {
                validatePhoneClient(order);
                order.setStateEnum(StateEnum.READY.toString());
                int code = generateCode();
                order.setCode(code);
                orderPersistencePort.updateOrder(order);
                sendMessageOrder(order, code);
                TraceabilityRequestDto traceabilityRequestDto = new TraceabilityRequestDto();

                traceabilityRequestDto.setStateOld(PREPARATION);
                traceabilityRequestDto.setStateNew(READY);
                saveRecord(traceabilityRequestDto,order);
            }
            case READY->{
                order.setStateEnum(StateEnum.DELIVERED.toString());
                orderPersistencePort.updateOrder(order);
                TraceabilityRequestDto traceabilityRequestDto = new TraceabilityRequestDto();
                traceabilityRequestDto.setStateOld(READY);
                traceabilityRequestDto.setStateNew(DELIVERED);
                saveRecord(traceabilityRequestDto,order);
            }
            default ->
                throw new NotStatusInProcess();

        }

    }

    @Override
    public void validatePhoneClient(OrderEntity order) {
        User user = userHttpPersistencePort.getClient(order.getIdClient());
        if(!user.getPhone().equals(phone)){
            throw new PhoneClientInvalidException();
        }
    }

    @Override
    public void validateCodeClient(OrderEntity order, int codeClient) {
        if(order.getCode() != codeClient){
            throw  new IncorrectCodeException();
        }

        orderPersistencePort.updateOrder(order);
        sendMessageOrder(order, codeClient);

    }

    @Override
    public void sendMessageOrder(OrderEntity order, int code) {
        String message;
        switch (order.getStateEnum()){
            case READY->{
                message= "Estimado cliente su pedido con id: " + order.getId()+ "." +
                    "\nYa esta listo para reclamar con el siguiente codigo: " + code;
                messengerServicePersistencePort.sendMessageStateOrderUpdated(message);}


            case DELIVERED-> {
                message = "Estimado cliente su pedido con id: " + order.getId() + "." +
                        "\nSe entrego con exito";
                messengerServicePersistencePort.sendMessageStateOrderUpdated(message);
                TraceabilityRequestDto traceabilityRequestDto = new TraceabilityRequestDto();
                traceabilityRequestDto.setStateOld(READY);
                traceabilityRequestDto.setStateNew(DELIVERED);
                saveRecord(traceabilityRequestDto,order);
            }

            default -> throw  new NotStatusInProcess();
        }

    }
    @Override
    public int generateCode() {
        return (int) (10000 + Math.random() * 90000);
    }

    @Override
    public void cancelOrder(Long idOrder) {
        if (!orderPersistencePort.existsById(idOrder)){
            throw new NoDataFoundException();
        }
        Optional<OrderEntity> order = orderPersistencePort.findById(idOrder);
        validateStateEarning(order.get());

    }

    @Override
    public void validateStateEarning(OrderEntity order) {
        switch (order.getStateEnum()){
            case PREPARATION , READY->{
                String message = "Lo sentimos tu pedido ya esta en proceso y no puede cancelarse";
                messengerServicePersistencePort.sendMessageStateOrderUpdated(message);
                throw  new OrderNotCancellException();
            }
            case DELIVERED->{
                String message = "Lo sentimos tu pedido  ya se entrego y no puede cancelarse";
                messengerServicePersistencePort.sendMessageStateOrderUpdated(message);
                throw  new NotStatusInProcess();
            }

            case CANCELLED->{
                String message = "Lo sentimos tu pedido  ya fue cancelado con anterioridad";
                messengerServicePersistencePort.sendMessageStateOrderUpdated(message);
                throw  new NotStatusInProcess();
            }
            case EARNING->{
                order.setStateEnum(StateEnum.CANCELLED.toString());
                orderPersistencePort.updateOrder(order);
                TraceabilityRequestDto traceabilityRequestDto = new TraceabilityRequestDto();
                traceabilityRequestDto.setStateOld(EARNING);
                traceabilityRequestDto.setStateNew(CANCELLED);
                saveRecord(traceabilityRequestDto,order);
            }

            default ->
                throw  new NotStatusInProcess();

        }

    }

    @Override
    public void saveRecord(TraceabilityRequestDto traceabilityRequestDto, OrderEntity order) {

        traceabilityRequestDto.setIdOrder(order.getId());
        traceabilityRequestDto.setIdClient(order.getIdClient());
        traceabilityRequestDto.setDate(order.getDate());
        traceabilityRequestDto.setEmailEmployee(TokenInterceptor.getEmail());
        User user2 = userHttpPersistencePort.getClient(order.getIdClient());
        traceabilityRequestDto.setEmailClient(user2.getMail());
        traceabilityPersistencePort.saveTraceability(traceabilityRequestDto);
    }

    @Override
    public TraceabilityRequestDto saveTraceabilityDto(Optional<OrderEntity> orderBD) {
        TraceabilityRequestDto traceabilityRequestDto = new TraceabilityRequestDto();

        traceabilityRequestDto.setIdOrder(orderBD.get().getId());
        traceabilityRequestDto.setIdEmployee(TokenInterceptor.getIdUser());
        traceabilityRequestDto.setIdClient(orderBD.get().getIdClient());
        traceabilityRequestDto.setDate(orderBD.get().getDate());
        traceabilityRequestDto.setStateNew(EARNING);
        return traceabilityRequestDto;
    }

    @Override
    public List<TraceabilityResponseDto> getAllRecordsOrdersByClient() {
        return orderPersistencePort.getAllRecordsOrdersByClient(TokenInterceptor.getIdUser());
    }


}
