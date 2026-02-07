package com.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Component;

public class AopApp {
	public static void main(String[] args) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AopAppConfig.class);
		Student std = context.getBean("student", Student.class);
		Employee emp = context.getBean("employee", Employee.class);
		std.study();
		System.out.println("-----------------");
		emp.studySomething();
		System.out.println("-----------------");
		emp.studySomething("Ram");
		System.out.println("-----------------");		
		int res = std.calcy(100,50);
		System.out.println("Result :"+res);
		
		context.close();
	}
}


class MyException extends RuntimeException{
	private static final long serialVersionUID = -1597272905368200517L;

	public MyException() {
		super();
	}

	public MyException(String message) {
		super(message);
	}
}



@Aspect
@Component
class Greet {
	@Around("execution(public int calcy(int, int))")
	public Object calcyAdvice(ProceedingJoinPoint jp) {
		Object[] objArgs = jp.getArgs();
		int n1 = Integer.parseInt(objArgs[0]+"");
		int n2 = Integer.parseInt(objArgs[1]+"");
		objArgs[0] = 10;
		objArgs[1] = 0;
		Object res = null;
		try {
			res = jp.proceed();		//takes by default input
//			res = jp.proceed(objArgs);
		} catch (Throwable e) {
			e.getMessage();
		}
		return res;
	}
	
	@AfterThrowing(pointcut="execution(public int calcy(int, int))", throwing = "ex")
	public void exec(MyException ex) {
		System.out.println("Exception Thrown - "+ex.getMessage());
	}
	
	
	

	@Before("execution(public * study*(..)) || execution(public int calcy(int, int))")
	public void welcome(JoinPoint j) {
		System.out.println("Welcome "+(j.getArgs().length>0?j.getArgs()[0]:""));
	}
	
	@Before("myPointCut()")
	public void morning() {
		System.out.println("Good Morning");
	}
	
	@After("myPointCut()")
	public void thankyou() {
		System.out.println("Thank You");
	}
	
	@Pointcut("execution(public * study*())")
	public void myPointCut() {}
	
	
//	@Before("execution(void com.aop.*.*(..))")
//	public void welcome() {
//		System.out.println("Welcome");
//	}
	
}



@Component
class Student {
	
	public void study() {
		System.out.println("Student Studying");
	}
	
	public int calcy(int n1, int n2) {
		System.out.println("N1-"+n1+", N2-"+n2);
		try {
			int res = n1/n2;
			return res;
		} catch (Exception e) {
			throw new MyException("Invalid input");
		}
	}
}

@Component
class Employee {
	public int studySomething() {
		System.out.println("Employee Studying");
		return 0;
	}
	
	public String studySomething(String str) {
		System.out.println("Employee Studying "+str);
		return null;
	}
}



@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = "com.aop")
class AopAppConfig {
}