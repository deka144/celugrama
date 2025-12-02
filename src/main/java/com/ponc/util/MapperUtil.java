package com.ponc.util;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MapperUtil {

    private final ApplicationContext applicationContext;

    public <S,T> List<T> mapList(List<S> source,Class<T> targetClass, String...  mapperQualifier){
        ModelMapper modelMapper= getModelMapper(mapperQualifier);
        return source.stream().map(element->modelMapper.map(element,targetClass)).toList();
    }

    public <S,T> T map(S source, Class<T> targetClass, String... mapperQuialifier){
        ModelMapper modelMapper= getModelMapper(mapperQuialifier);
        return modelMapper.map(source,targetClass);
    }

    private ModelMapper getModelMapper(String... mapperQuialifier){
        if(mapperQuialifier.length==0||mapperQuialifier[0]==null||mapperQuialifier[0].isEmpty()){
            return applicationContext.getBean("defaultMapper", ModelMapper.class);
        }else{
            return applicationContext.getBean(mapperQuialifier[0], ModelMapper.class);
        }
    }
}
