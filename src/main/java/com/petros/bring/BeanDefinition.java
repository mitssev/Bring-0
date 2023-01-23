package com.petros.bring;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class BeanDefinition {

    String qualifier;
    Class<?> beanClass;

}
