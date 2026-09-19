package com.ponc.config;


import com.ponc.dto.ApiResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;


// Envuelve automaticamente TODAS las respuestas exitosas en ApiResponse,
// sin que tengas que tocar cada controller. Spring la ejecuta justo
// antes de convertir el resultado a JSON.
//
// basePackages limita esto a tus propios controllers, para no tocar
// respuestas de librerias externas (ej. el JSON que genera Swagger).
// Verificado en tu proyecto: tus @RestController (MenuController,
// UserController, etc.) estan en com.ponc.controller.
@RestControllerAdvice(basePackages = "com.ponc.controller")
public class ApiResponseAdvice implements ResponseBodyAdvice<Object> {

    // Spring llama a supports() por cada respuesta, para preguntar si
    // esta clase quiere intervenir. Decimos que si a todo, EXCEPTO
    // cuando el controller ya devolvio un ApiResponse el mismo — para
    // no envolver un ApiResponse dentro de otro ApiResponse.
    @Override
    public boolean supports(MethodParameter returnType,
                            Class<? extends HttpMessageConverter<?>> converterType) {
        return !ApiResponse.class.isAssignableFrom(
                returnType.getParameterType());
    }

    // Aca ocurre el envoltorio real: toma lo que el controller iba a
    // devolver (la lista de MenuDTO, por ejemplo) y lo mete dentro de
    // un ApiResponse.ok(...) antes de que se convierta a JSON.
    @Override
    public Object beforeBodyWrite(
            Object body,
            MethodParameter returnType,
            MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType,
            ServerHttpRequest request,
            ServerHttpResponse response) {
        return ApiResponse.ok(body);
    }

}

