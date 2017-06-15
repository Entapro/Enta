package ru.otus.l21;

import java.lang.reflect.Array;

public class Main {
    public static void main(String... args) throws InterruptedException, InstantiationException, IllegalAccessException
    {
        int size = 10_000_000;

        System.gc();
        Thread.sleep(50);
        Runtime runtime = Runtime.getRuntime();
        long mem_before = runtime.totalMemory() - runtime.freeMemory();

        //1-st way, doesn't work on arrays
        GenArray<Object> a = new GenArray(Object.class, size);

       //2-nd way, works on everything
       /*
        Object[] a2 = new Object[size];
        for (int i = 0; i < size; i++) {
            a2[i] = getObject();
        }*/

        System.gc();
        Thread.sleep(50);
        long mem_after = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Object size = " + (mem_after - mem_before)/ size);

    }

    public static Object getObject()
    {
        return new int[0];
    }

    public static class GenArray<T> {

        public T[] a;

        public GenArray(Class<T> c, int count) throws InstantiationException, IllegalAccessException {
            T[] a = (T[]) Array.newInstance(c, count);
            this.a = a;

            for (int i = 0; i < count; i++) {
                a[i] = c.newInstance();
            }
        }
    }
}
