package com.huy.backendnoithat.configuration;

import com.blazebit.persistence.Criteria;
import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.spi.CriteriaBuilderConfiguration;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BlazeBitPersistenceConfiguration {
    @Bean
    public CriteriaBuilderFactory createCriteriaBuilderFactory(EntityManagerFactory entityManagerFactory) {
        CriteriaBuilderConfiguration config = Criteria.getDefault();
        return config.createCriteriaBuilderFactory(entityManagerFactory);
    }
}
