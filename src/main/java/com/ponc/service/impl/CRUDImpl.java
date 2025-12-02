package com.ponc.service.impl;

import com.ponc.exception.ModelNotFoundException;
import com.ponc.repo.IGenericRepo;
import com.ponc.service.ICRUD;

import java.lang.reflect.Method;
import java.util.List;

public abstract class CRUDImpl<T,ID> implements ICRUD<T,ID> {

    protected abstract IGenericRepo<T,ID> getRepo();


    @Override
    public T save(T t) throws Exception {
        return getRepo().save(t);
    }

    @Override
    public T update(T t, ID id) throws Exception {
        //validar si existe el id
        getRepo().findById(id).orElseThrow(()-> new ModelNotFoundException("ID NOT FOUND :"+id));

        //java reflecion
        Class<?> clazz=t.getClass();
        String className=clazz.getSimpleName();

        //setId
        String methodName="setId"+className;
        Method setIdMethod=clazz.getMethod(methodName,id.getClass());
        setIdMethod.invoke(t,id);
        return getRepo().save(t);
    }

    @Override
    public List<T> findAll() throws Exception {
        return getRepo().findAll();
    }

    @Override
    public T findById(ID id) throws Exception {
        return getRepo().findById(id).orElseThrow(()-> new ModelNotFoundException("ID NOT FOUND :"+id));
    }

    @Override
    public void deleteById(ID id) throws Exception {
        getRepo().findById(id).orElseThrow(()-> new ModelNotFoundException("ID NOT FOUND :"+id));
        getRepo().deleteById(id);
    }
}
