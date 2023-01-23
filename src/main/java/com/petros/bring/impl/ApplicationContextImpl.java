package com.petros.bring.impl;

import com.petros.ApplicationContext;
import com.petros.bring.BeanDefinition;
import com.petros.bring.annotations.Bean;
import com.petros.bring.errors.NoSuchBeanException;
import com.petros.bring.errors.NoUniqueBeanException;
import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.reflections.scanners.Scanners.TypesAnnotated;


public class ApplicationContextImpl implements ApplicationContext {


    Map<BeanDefinition, Object> bringContext;

    public ApplicationContextImpl(String rootPackage) {
        Set<BeanDefinition> beanDefinitions = getBeanDefinitions(rootPackage);
        createContext(beanDefinitions);
    }

    private Set<BeanDefinition> getBeanDefinitions(String rootPackage) {
        Reflections reflections = new Reflections(rootPackage);
        return reflections.get(TypesAnnotated.with(Bean.class).asClass())
                .stream()
                .map(clazz -> new BeanDefinition(resolveBeanName(clazz), clazz))
                .collect(Collectors.toSet());
    }

    private String resolveBeanName(Class<?> clazz) {
        String name = clazz.getAnnotation(Bean.class).name();
        return StringUtils.isEmpty(name) ? StringUtils.uncapitalize(clazz.getSimpleName()) : name;
    }

    private void createContext(Set<BeanDefinition> beanDefinitions) {
        bringContext = beanDefinitions.stream()
                .collect(Collectors.toMap(Function.identity(), value -> createClassInstance(value.getBeanClass())));
    }

    private Object createClassInstance(Class<?> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> clazz) {
        List<Object> beans = bringContext.entrySet().stream()
                .filter(entry -> clazz.isAssignableFrom(entry.getKey().getBeanClass()))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
        validateGetBeans(beans);
        return (T) beans.get(0);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getBean(String name, Class<T> clazz) {
        List<Object> beans = bringContext.entrySet().stream()
                .filter(entry -> clazz.isAssignableFrom(entry.getKey().getBeanClass()))
                .filter(entry -> entry.getKey().getQualifier().equals(name))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
        validateGetBeans(beans);
        return (T) beans.get(0);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Map<String, T> getAllBeans(Class<T> beanType) {
        return bringContext.entrySet().stream()
                .collect(Collectors.toMap(entity -> entity.getKey().getQualifier(), entity -> (T) entity.getValue()));
    }

    private void validateGetBeans(List<Object> beans) {
        if (beans.size() > 1) {
            throw new NoUniqueBeanException(beans.stream().map(obj -> obj.getClass().getSimpleName()).collect(Collectors.joining(", ")));
        }
        if (beans.size() == 0) {
            throw new NoSuchBeanException();
        }
    }

}
