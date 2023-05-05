package com.pragma.powerup.usermicroservice.configuration;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.adapter.PersonMysqlAdapter;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.adapter.RoleMysqlAdapter;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.adapter.UserMysqlAdapter;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.mappers.IPersonEntityMapper;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.mappers.IRoleEntityMapper;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.mappers.IUserEntityMapper;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories.IPersonRepository;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories.IRoleRepository;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories.IUserRepository;
import com.pragma.powerup.usermicroservice.domain.api.IPersonServicePort;
import com.pragma.powerup.usermicroservice.domain.api.IRoleServicePort;
import com.pragma.powerup.usermicroservice.domain.api.IUserServicePort;
import com.pragma.powerup.usermicroservice.domain.spi.IPersonPersistencePort;
import com.pragma.powerup.usermicroservice.domain.spi.IRolePersistencePort;
import com.pragma.powerup.usermicroservice.domain.spi.IUserPersistencePort;
import com.pragma.powerup.usermicroservice.domain.usecase.PersonUseCase;
import com.pragma.powerup.usermicroservice.domain.usecase.RoleUseCase;
import com.pragma.powerup.usermicroservice.domain.usecase.UserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final IRoleRepository roleRepository;
    private final IPersonRepository personRepository;
    private final IUserRepository userRepository;
    private final IRoleEntityMapper roleEntityMapper;
    private final IPersonEntityMapper personEntityMapper;
    private final IUserEntityMapper userEntityMapper;
    private final PasswordEncoder passwordEncoder;
    @Bean
    public IRoleServicePort roleServicePort() {
        return new RoleUseCase(rolePersistencePort());
    }
    @Bean
    public IRolePersistencePort rolePersistencePort() {
        return new RoleMysqlAdapter(roleRepository, roleEntityMapper);
    }
    @Bean
    public IPersonServicePort personServicePort() {
        return new PersonUseCase(personPersistencePort());
    }
    @Bean
    public IPersonPersistencePort personPersistencePort() {
        return new PersonMysqlAdapter(personRepository, personEntityMapper, passwordEncoder);
    }
    @Bean
    public IUserServicePort userServicePort() {
        return new UserUseCase(userPersistencePort());
    }
    @Bean
    public IUserPersistencePort userPersistencePort() {
        return new UserMysqlAdapter(userRepository, personRepository, roleRepository, userEntityMapper);
    }
}
