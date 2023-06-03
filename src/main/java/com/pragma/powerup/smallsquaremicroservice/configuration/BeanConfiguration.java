package com.pragma.powerup.smallsquaremicroservice.configuration;

import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.adapter.CategoryMysqlAdapter;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.adapter.PlateMysqlAdapter;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.adapter.RestaurantEmployeeMysqlAdapter;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.mappers.ICategoryEntityMapper;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.mappers.IPlateEntityMapper;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.mappers.IRestaurantEmployeeEntityMapper;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.mappers.IRestaurantEntityMapper;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.repositories.ICategoryRepository;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.repositories.IPlateRepository;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.repositories.IRestaurantEmployeeRepository;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.adapter.EmployeeHttpAdapter;
import com.pragma.powerup.smallsquaremicroservice.domain.api.ICategoryServicePort;
import com.pragma.powerup.smallsquaremicroservice.domain.api.IPlateServicePort;
import com.pragma.powerup.smallsquaremicroservice.domain.api.IRestaurantServicePort;
import com.pragma.powerup.smallsquaremicroservice.domain.spi.*;
import com.pragma.powerup.smallsquaremicroservice.domain.usecase.CategoryUseCase;
import com.pragma.powerup.smallsquaremicroservice.domain.usecase.PlateUseCase;
import com.pragma.powerup.smallsquaremicroservice.domain.usecase.RestaurantUseCase;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.adapter.RestaurantMysqlAdapter;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.repositories.IRestaurantRepository;
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


    @Bean
    public IRestaurantServicePort restaurantServicePort() {
        return new RestaurantUseCase(restaurantPersistencePort(), restaurantEmployeePersistencePort(), employeePersistencePort(), restaurantRepository);
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
}
