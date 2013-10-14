package net.hogedriven.junit;

import org.junit.runners.model.FrameworkMethod;

import java.lang.annotation.Annotation;
import java.util.Arrays;

class CaseFrameworkMethod extends FrameworkMethod {

    private FrameworkMethod method;
    private Case caseAnnotation;

    public CaseFrameworkMethod(FrameworkMethod method, Case with) {
        super(method.getMethod());
        this.method = method;
        this.caseAnnotation = with;
    }

    @Override
    public String getName() {
        return super.getName() + "_" + Arrays.toString(caseAnnotation.value());
    }

    @Override
    public <T extends Annotation> T getAnnotation(Class<T> annotationType) {
        if (annotationType == Case.class) {
            return (T) caseAnnotation;
        }
        return super.getAnnotation(annotationType);
    }
}
