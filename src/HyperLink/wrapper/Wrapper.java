package HyperLink.wrapper;

import HyperLink.HyperLink;
import HyperLink.annotations.WrapperClass;

import java.lang.reflect.*;
import java.util.*;

public class Wrapper {
    protected final Object object;

    public Wrapper(final Object object) {
        if (object instanceof Wrapper) {
            this.object = ((Wrapper) object).getObject();
            return;
        }
        this.object = object;
    }

    public static Class<?> allocateClass(Class<? extends Wrapper> wrapper) {
        try {
            WrapperClass wrapperClass = wrapper.getAnnotation(WrapperClass.class);

            if (wrapperClass == null)
                throw new RuntimeException(wrapper.getName() + " is not annotated with @WrapperClass");

            return Class.forName(wrapperClass.value());
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean equals(final Object object) {
        if (object == null || this.object == null) {
            return false;
        }
        if (object instanceof Wrapper) {
            return this.object.equals(((Wrapper) object).object);
        }
        return super.equals(object);
    }

    protected static Constructor<?> allocateConstructor(Class<? extends Wrapper> wrapper, Class<? extends Wrapper>... args) {
        try {
            WrapperClass wrapperClass = wrapper.getAnnotation(WrapperClass.class);

            if (wrapperClass == null)
                throw new RuntimeException(wrapper.getName() + " is not annotated with @WrapperClass");

            List<Class<?>> arg = new ArrayList<>();

            for (Class<? extends Wrapper> a : args) {
                arg.add(allocateClass(a));
            }

            return Class.forName(wrapperClass.value()).getConstructor(arg.toArray(new Class[]{}));
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    private static final Map<String, Constructor<?>> constructorCache = new HashMap<>();

    protected static Object createObject(Constructor<?> constructor, Object... args) {
        try {
            return constructor.newInstance(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected static Object createObject(Class<? extends Wrapper> wrapper, Object... args) {
        try {
            WrapperClass wrapperClass = wrapper.getAnnotation(WrapperClass.class);

            if (wrapperClass == null)
                throw new RuntimeException(wrapper.getName() + " is not annotated with @WrapperClass");

            Class<?> clazz = Class.forName(wrapperClass.value());

            Class<?>[] argTypes = new Class<?>[args.length];
            for (int i = 0; i < args.length; i++) {
                if (args[i] == null) {
                    throw new IllegalArgumentException("Arguments " + i + " cannot be null");
                }
                argTypes[i] = args[i].getClass();
            }

            String constructorKey = clazz.getName() + Arrays.hashCode(argTypes);
            Constructor<?> constructor = constructorCache.get(constructorKey);

            if (constructor == null) {
                try {
                    constructor = clazz.getConstructor(argTypes);
                    constructorCache.put(constructorKey, constructor);
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException("Constructor not found", e);
                }
            }

            try {
                return constructor.newInstance(args);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException("Object creation failed", e);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object getObject() {
        return object;
    }

    protected static Object invokeStatic(Class<? extends Wrapper> wrapper, String name, Object... args) {
        try {
            WrapperClass wrapperClass = wrapper.getAnnotation(WrapperClass.class);

            if (wrapperClass == null)
                throw new RuntimeException(wrapper.getName() + " is not annotated with @WrapperClass");

            String methodName = wrapperClass.value().replace(".", "/") + "/" + name;
            for (Method method : allocateClass(wrapper).getDeclaredMethods()) {
                if (method.getParameters().length == args.length && Modifier.isStatic(method.getModifiers()) && method.getName().equals(HyperLink.getInstance().getMapping().getMethodName(methodName.split("/")[methodName.split("/").length - 1]))) {
                    method.setAccessible(true);
                    return method.invoke(null, args);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected static Object getStatic(Class<? extends Wrapper> wrapper, String name) {
        try {
            WrapperClass wrapperClass = wrapper.getAnnotation(WrapperClass.class);

            if (wrapperClass == null)
                throw new RuntimeException(wrapper.getName() + " is not annotated with @WrapperClass");

            String fieldName = wrapperClass.value().replace(".", "/") + "/" + name;
            Field field = allocateClass(wrapper).getDeclaredField(HyperLink.getInstance().getMapping().getFieldName(fieldName));
            field.setAccessible(true);
            return field.get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected Object invoke(String name, Object... args) {
        try {
            WrapperClass wrapperClass = this.getClass().getAnnotation(WrapperClass.class);

            if (wrapperClass == null)
                throw new RuntimeException(this.getClass().getName() + " is not annotated with @WrapperClass");

            String methodName = wrapperClass.value().replace(".", "/") + "/" + name;
            for (Method method : object.getClass().getDeclaredMethods()) {
                if (method.getName().equals(HyperLink.getInstance().getMapping().getMethodName(methodName.split("/")[methodName.split("/").length - 1]))) {
                    method.setAccessible(true);
                    return method.invoke(object, args);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean is(Class<? extends Wrapper> wrapper) {
        if (object == null) return false;
        try {
            Class<?> cla = allocateClass(wrapper);
            return cla.isAssignableFrom(object.getClass());
        } catch (Exception e) {
            return false;
        }
    }

    protected Object invoke(Class<? extends Wrapper> wrapper, String name, Object... args) {
        try {
            WrapperClass wrapperClass = wrapper.getAnnotation(WrapperClass.class);

            if (wrapperClass == null)
                throw new RuntimeException(wrapper.getName() + " is not annotated with @WrapperClass");

            String methodName = wrapperClass.value().replace(".", "/") + "/" + name;
            for (Method method : allocateClass(wrapper).getDeclaredMethods()) {
                if (method.getName().equals(HyperLink.getInstance().getMapping().getMethodName(methodName.split("/")[methodName.split("/").length - 1]))) {
                    method.setAccessible(true);
                    return method.invoke(object, args);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected Object getFieldValue(Class<? extends Wrapper> wrapper, String name) {
        try {
            WrapperClass wrapperClass = wrapper.getAnnotation(WrapperClass.class);

            if (wrapperClass == null)
                throw new RuntimeException(wrapper.getName() + " is not annotated with @WrapperClass");

            String fieldName = wrapperClass.value().replace(".", "/") + "/" + name;
            Field field = allocateClass(wrapper).getDeclaredField(HyperLink.getInstance().getMapping().getFieldName(fieldName));
            field.setAccessible(true);
            return field.get(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected Object getFieldValue(String name) {
        try {
            WrapperClass wrapperClass = this.getClass().getAnnotation(WrapperClass.class);

            if (wrapperClass == null)
                throw new RuntimeException(this.getClass().getName() + " is not annotated with @WrapperClass");

            String fieldName = wrapperClass.value().replace(".", "/") + "/" + name;
            Field field = object.getClass().getDeclaredField(HyperLink.getInstance().getMapping().getFieldName(fieldName));
            field.setAccessible(true);
            return field.get(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void setFieldValue(Class<? extends Wrapper> wrapper, String name, Object value) {
        try {
            WrapperClass wrapperClass = wrapper.getAnnotation(WrapperClass.class);

            if (wrapperClass == null)
                throw new RuntimeException(wrapper.getName() + " is not annotated with @WrapperClass");

            String fieldName = wrapperClass.value().replace(".", "/") + "/" + name;
            Field field = allocateClass(wrapper).getDeclaredField(HyperLink.getInstance().getMapping().getFieldName(fieldName));
            field.setAccessible(true);
            field.set(object, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void setFieldValue(String name, Object value) {
        try {
            WrapperClass wrapperClass = this.getClass().getAnnotation(WrapperClass.class);
            String fieldName = wrapperClass.value().replace(".", "/") + "/" + name;
            Field field = object.getClass().getDeclaredField(HyperLink.getInstance().getMapping().getFieldName(fieldName));
            field.setAccessible(true);
            field.set(object, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isNull() {
        return this.object == null;
    }

    public boolean nonNull() {
        return this.object != null;
    }

    public boolean isInstance(final Class<?> clazz) {
        return clazz != null && clazz.isInstance(this.object);
    }
}
