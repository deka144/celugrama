package com.ponc.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;

@Controller
public class MapperConfig {
    @Bean(name = "defaultMapper")
    public ModelMapper defaultMapper(){
       return new ModelMapper();
    }
}
