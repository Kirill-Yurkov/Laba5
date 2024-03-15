package server.commons;

import org.apache.maven.surefire.shared.lang3.ClassUtils;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ConfigurationBuilder;

import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.*;
import java.util.Set;

public class ReflectionImplements {
    public static List<Class<?>> getImplementations(Class<?> interfaceClass) {
        List<Class<?>> implementations = new ArrayList<>();
        // Получаем список всех классов в JVM
        Class<?>[] allClasses = getAllClasses();

        for (Class<?> cls : allClasses) {
            // Проверяем, имплементирует ли класс указанный интерфейс
            if (isImplementation(cls, interfaceClass)) {
                implementations.add(cls);
            }
        }

        return implementations;
    }

    private static Class<?>[] getAllClasses() {
        Class<?>[] allClasses;
        try {
            // Получаем загрузчик текущего класса
            ClassLoader classLoader = ClassUtils.class.getClassLoader();
            // Используем Reflections для получения всех классов в JVM
            Reflections reflections = new Reflections(new ConfigurationBuilder()
                    .setScanners(new SubTypesScanner(false))
                    .addClassLoaders(classLoader)
                    .forPackages(""));
            // Получаем список всех классов
            Set<Class<?>> allClassesSet = reflections.getSubTypesOf(Object.class);
            // Преобразуем Set в массив Class
            allClasses = allClassesSet.toArray(new Class<?>[0]);
        } catch (Exception ex) {
            allClasses = new Class<?>[0];
        }
        return allClasses;
    }

    private static boolean isImplementation(Class<?> cls, Class<?> interfaceClass) {
        // Проверяем, имплементирует ли класс указанный интерфейс
        return interfaceClass.isAssignableFrom(cls) && !interfaceClass.equals(cls);
    }
}