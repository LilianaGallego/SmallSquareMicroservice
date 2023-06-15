package com.pragma.powerup.smallsquaremicroservice.configuration;

import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.adapter.*;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.mappers.*;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.repositories.*;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.adapter.EmployeeHttpAdapter;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.adapter.MessangerServiceHttpAdapter;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.adapter.OwnerHttpAdapter;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.mapper.IOrderPlateResponseMapper;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.mapper.IOrderResponseMapper;
import com.pragma.powerup.smallsquaremicroservice.domain.api.ICategoryServicePort;
import com.pragma.powerup.smallsquaremicroservice.domain.api.IOrderServicePort;
import com.pragma.powerup.smallsquaremicroservice.domain.api.IPlateServicePort;
import com.pragma.powerup.smallsquaremicroservice.domain.api.IRestaurantServicePort;
import com.pragma.powerup.smallsquaremicroservice.domain.spi.*;
import com.pragma.powerup.smallsquaremicroservice.domain.usecase.CategoryUseCase;
import com.pragma.powerup.smallsquaremicroservice.domain.usecase.OrderUseCase;
import com.pragma.powerup.smallsquaremicroservice.domain.usecase.PlateUseCase;
import com.pragma.powerup.smallsquaremicroservice.domain.usecase.RestaurantUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final IRestaurantRepository restaurantRepository;
    private final IRestaurantEntityMapper restaurantEntityMapper;

    private final IRestaurantEmployeeRepository restaurantEmployeeRepository;
    private final IRestaurantEmployeeEntityMapper restaurantEmployeeEntityMapper;

    private final IPlateRepository plateRepository;
    private final IPlateEntityMapper plateEntityMapper;

    private final ICategoryEntityMapper categoryEntityMapper;
    private final ICategoryRepository categoryRepository;

    private final IOrderEntityMapper orderEntityMapper;
    private final IOrderRepository orderRepository;

    private final IOrderPlateEntityMapper orderPlateEntityMapper;
    private final IOrderPlateRepository orderPlateRepository;
    private final IOrderPlateResponseMapper orderPlateResponseMapper;
    private final IOrderResponseMapper orderResponseMapper;



    @Bean
    public IRestaurantServicePort restaurantServicePort() {
        return new RestaurantUseCase(restaurantPersistencePort(), restaurantEmployeePersistencePort(), employeePersistencePort(), restaurantRepository, ownerPersistencePort());
    }
    @Bean
    public IRestaurantPersistencePort restaurantPersistencePort() {
        return new RestaurantMysqlAdapter(restaurantRepository, restaurantEntityMapper);
    }

    @Bean
    public IEmployeePersistencePort employeePersistencePort() {
        return new EmployeeHttpAdapter();
    }
    @Bean
    public IOwnerHttpPersistencePort ownerPersistencePort() {return new OwnerHttpAdapter();}


    @Bean
    public IMessangerServicePersistencePort messengerServicePersistencePort() {
        return new MessangerServiceHttpAdapter();
    }

    @Bean
    public IRestaurantEmployeePersistencePort restaurantEmployeePersistencePort() {
        return new RestaurantEmployeeMysqlAdapter(restaurantEmployeeRepository, restaurantEmployeeEntityMapper);
    }

    @Bean
    public IPlateServicePort plateServicePort() {
        return new PlateUseCase(platePersistencePort(),plateRepository, restaurantRepository, categoryRepository);
    }
    @Bean
    public IPlatePersistencePort platePersistencePort() {
        return new PlateMysqlAdapter(plateRepository, plateEntityMapper);
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public ICategoryServicePort categoryServicePort() {
        return new CategoryUseCase(categoryPersistencePort());
    }
    @Bean
    public ICategoryPersistencePort categoryPersistencePort() {
        return new CategoryMysqlAdapter(categoryRepository, categoryEntityMapper);
    }

    @Bean
    public IOrderPersistencePort orderPersistencePort() {
        return new OrderMysqlAdapter(orderEntityMapper,orderPlateEntityMapper, orderPlateResponseMapper, orderResponseMapper,orderRepository, orderPlateRepository);
    }

    @Bean
    public IOrderServicePort orderServicePort() {
        return new OrderUseCase(orderPersistencePort(),restaurantPersistencePort(), restaurantEmployeePersistencePort(), messengerServicePersistencePort());
    }
}
