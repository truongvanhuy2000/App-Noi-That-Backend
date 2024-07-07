package com.huy.backendnoithat.AOP;

import com.huy.backendnoithat.DTO.Event.NoiThatUpdate;
import com.huy.backendnoithat.Event.NoiThatUpdateHandler;
import com.huy.backendnoithat.Utils.JwtTokenUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@Component
@Aspect
public class DatabaseModificationAspect {
    private final NoiThatUpdateHandler noiThatUpdateHandler;
    private final JwtTokenUtil jwtTokenUtil;
    @Autowired
    public DatabaseModificationAspect(NoiThatUpdateHandler noiThatUpdateHandler, JwtTokenUtil jwtTokenUtil) {
        this.noiThatUpdateHandler = noiThatUpdateHandler;
        this.jwtTokenUtil = jwtTokenUtil;
    }
    @AfterReturning("@annotation(DBModifyEvent)")
    public void createDBUpdateEventAfter(JoinPoint joinPoint, DBModifyEvent DBModifyEvent) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Parameter[] parameters = method.getParameters();
        for (int index = 0; index < parameters.length; index++) {
            if (parameters[index].isAnnotationPresent(RequestHeader.class)) {
                Object header = joinPoint.getArgs()[index];
                if (!(header instanceof String)) {
                    throw new RuntimeException("RequestHeader must be String");
                }
                String username = jwtTokenUtil.getUsernameFromToken(JwtTokenUtil.getTokenFromHeader((String) header));
                createUpdateEvent(username, DBModifyEvent.value());
                break;
            }
        }
    }
    private void createUpdateEvent(String username, String tableName) {
        noiThatUpdateHandler.publish(username, new NoiThatUpdate(tableName, username));
    }
}
