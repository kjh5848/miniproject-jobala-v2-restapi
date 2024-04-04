package com.example.jobala._core.handler;

import com.example.jobala._core.errors.apiException.ApiException400;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;


@Aspect // AOP 등록
@Component // IoC 등록
public class MyValidationHandler {

    // Advice (부가 로직 메서드)
    // Advice가 수행될 위치 == PointCut
    @Before("@annotation(org.springframework.web.bind.annotation.PostMapping) || @annotation(org.springframework.web.bind.annotation.PutMapping)")
    // PointCut
    public void hello(JoinPoint jp) {
        Object[] args = jp.getArgs(); // Args: 파라미터 -> object를 리턴
        System.out.println("크기 : " + args.length);

        for (Object arg : args) {

            if (arg instanceof Errors) {
                Errors errors = (Errors) arg; // 에러스타입의 arg를 다운캐스팅

                if (errors.hasErrors()) {
                    for (FieldError error : errors.getFieldErrors()) {
                        System.out.println(error.getField());
                        System.out.println(error.getDefaultMessage());
                        throw new ApiException400(error.getDefaultMessage() + " : " + error.getField());
                    }
                }
            }
        }
        System.out.println("MyValidationHandler: hello____________________");
    }
}
