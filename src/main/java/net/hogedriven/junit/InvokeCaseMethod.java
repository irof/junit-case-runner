package net.hogedriven.junit;

import org.junit.internal.runners.statements.InvokeMethod;
import org.junit.runners.model.FrameworkMethod;

import java.util.concurrent.ConcurrentHashMap;

class InvokeCaseMethod extends InvokeMethod {

    // TODO 外部からの登録手段および格納場所
    private static final ConcurrentHashMap<Class<?>, Parser> parsers = new ConcurrentHashMap<>();

    static {
        parsers.put(String.class, String::new);
        parsers.put(boolean.class, Boolean::parseBoolean);
        parsers.put(byte.class, Byte::parseByte);
        // parsers.put(char.class, Character::);
        parsers.put(int.class, Integer::parseInt);
        parsers.put(long.class, Long::parseLong);
        parsers.put(float.class, Float::parseFloat);
        parsers.put(double.class, Double::parseDouble);
    }

    private final FrameworkMethod method;
    private final Object target;

    public InvokeCaseMethod(FrameworkMethod testMethod, Object target) {
        super(testMethod, target);
        this.method = testMethod;
        this.target = target;
    }

    @Override
    public void evaluate() throws Throwable {
        if (method.getAnnotation(Case.class) == null) {
            super.evaluate();
            return;
        }
        String[] args = method.getAnnotation(Case.class).value();
        Object[] params = new Object[args.length];
        Class<?>[] parameterTypes = method.getMethod().getParameterTypes();
        for (int i = 0; i < parameterTypes.length; i++) {
            params[i] = findParser(parameterTypes[i]).parse(args[i]);
        }
        method.invokeExplosively(target, params);
    }

    private Parser findParser(Class parameterType) {
        if (parameterType.isEnum()) {
            return (String arg) -> Enum.valueOf(parameterType, arg);
        }
        if (!parsers.containsKey(parameterType)) {
            throw new IllegalArgumentException("not support argument type." + parameterType);
        }
        return parsers.get(parameterType);
    }
}
