package cz.muni.fi.pa165.currency;

import javax.inject.Named;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 *
 * @author xtrnkal
 */
@Named
@Aspect
public class MethodDurationAspect {

    @Around("execution(public * *(..))")
    public Object methodDuration(ProceedingJoinPoint joinPoint) throws Throwable {

        System.out.println("Calling method: "
                + joinPoint.getSignature().toString());

        long firstStamp = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long secondStamp = System.currentTimeMillis();

        System.out.println("Duration: "
                + (secondStamp - firstStamp) + " ms");
        return result;
    }

}
