package com.petros.client.services.impl;

import com.petros.bring.annotations.Bean;
import com.petros.client.services.Service;

@Bean(name = "booblik")
public class SimpleService implements Service {

    @Override
    public String getServiceName() {
        return "simpleService";
    }
}
