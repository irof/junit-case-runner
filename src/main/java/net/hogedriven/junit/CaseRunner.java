package net.hogedriven.junit;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import java.util.ArrayList;
import java.util.List;

public class CaseRunner extends BlockJUnit4ClassRunner {

    public CaseRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }

    protected List<FrameworkMethod> computeTestMethods() {
        ArrayList<FrameworkMethod> list = new ArrayList<>();
        list.addAll(super.computeTestMethods());

        List<FrameworkMethod> tests = getTestClass().getAnnotatedMethods(Tests.class);
        for (FrameworkMethod method : tests) {
            for (Case with : method.getAnnotation(Tests.class).value()) {
                list.add(new CaseFrameworkMethod(method, with));
            }
        }

        return list;
    }

    @Override
    protected Statement methodInvoker(final FrameworkMethod method, final Object test) {
        return new InvokeCaseMethod(method, test);
    }

    @Override
    protected void validateTestMethods(List<Throwable> errors) {
        // TODO そのうち実装する
        return;
    }

}
