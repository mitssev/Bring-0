package com.petros.client;

import com.petros.ApplicationContext;
import com.petros.bring.BeanDefinition;
import com.petros.bring.impl.ApplicationContextImpl;
import com.petros.client.services.Service;
import com.petros.client.services.impl.SimpleService;

import java.util.Map;

public class BringClient {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ApplicationContextImpl("com.petros.client");
        SimpleService simpleService = applicationContext.getBean(SimpleService.class);
        Service complexServiceByName = applicationContext.getBean("complexService", Service.class);
        Service simpleServiceByAlias = applicationContext.getBean("booblik", Service.class);
        Map<String, Service> allServiceBeans = applicationContext.getAllBeans(Service.class);

        System.out.println("client access to simpleService: " + simpleService.getServiceName());
        System.out.println("client access to service by default name: " + complexServiceByName.getServiceName());
        System.out.println("client access to service by custom alias: " + simpleServiceByAlias.getServiceName());
        System.out.printf("client access to all services: %s%n", allServiceBeans);

        try {
            applicationContext.getBean(Service.class);
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            applicationContext.getBean(BeanDefinition.class);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
