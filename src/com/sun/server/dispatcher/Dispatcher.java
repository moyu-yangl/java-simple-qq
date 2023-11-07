package com.sun.server.dispatcher;

import com.sun.domain.ResultResponse;
import com.sun.server.ServerSendAndReceive;
import com.sun.server.annotation.SystemAnnotation;
import com.sun.server.controller.UserController;
import com.sun.utils.ClassUtil;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Dispatcher {
    private static final List<Class<?>> classList;

    static {
        try {
            classList = ClassUtil.getClasses(UserController.class);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static ResultResponse dispatcher(ResultResponse rs, ServerSendAndReceive serverSendAndReceive) {
        ResultResponse response = null;
        for (Class<?> aClass : classList) {
            SystemAnnotation systemAnnotation = aClass.getAnnotation(SystemAnnotation.class);
            if (systemAnnotation != null) {
                List<Method> methods = Arrays.stream(aClass.getDeclaredMethods())
                        .filter(method -> method.isAnnotationPresent(SystemAnnotation.class)).collect(Collectors.toList());
                for (Method method : methods) {
                    SystemAnnotation annotation = method.getDeclaredAnnotation(SystemAnnotation.class);
                    if (annotation.value().equals(rs.getPath())) {
//                        System.out.println(method.getName());
                        int parameterCount = method.getParameterCount();
                        if (parameterCount == 0) {
                            try {
                                response = (ResultResponse) method.invoke(null);
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            try {
                                response = (ResultResponse) method.invoke(null, rs.getData(), serverSendAndReceive);
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
            }
        }

        return response;
    }
}
