package com.petros.client.services.impl;

import com.petros.bring.annotations.Bean;
import com.petros.client.services.Service;

@Bean
public class ComplexService implements Service {
    @Override
    public String getServiceName() {
        return "complexService";
    }
}
