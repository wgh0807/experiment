package test1906;

import java.util.*;

public class test1 {
    public static void main(String[] args) {
        int size = 1000000;
        int testsize = 1000;

        LinkedList linkedList=null;
        ArrayList arrayList=null;
        ArrayList defaultArrayList = null;
        int a[] = getRadomArray(testsize);
        int b[] = getRadomArray(testsize);
        Arrays.sort(b);

        long beginTime1 = System.currentTimeMillis();

        for (int i = 0; i < size; i++) {
            linkedList = new LinkedList();
        }
        linkedList = new LinkedList();

        long endTime1 = System.currentTimeMillis();

        System.out.println("创建"+size+"次LinkedList对象时间为："+(endTime1-beginTime1));

        long beginTime2 = System.currentTimeMillis();

        for (int i = 0; i < size; i++) {
            arrayList= new ArrayList(10000);
        }
        arrayList = new ArrayList(1000000);

        long endTime2 = System.currentTimeMillis();

        System.out.println("创建"+size+"次ArrayList对象时间为："+(endTime2-beginTime2));


        long beginTime3 = System.currentTimeMillis();

        for (int i = 0; i < size; i++) {
            defaultArrayList= new ArrayList(10000);
        }
        arrayList = new ArrayList();

        long endTime3 = System.currentTimeMillis();

        System.out.println("创建"+size+"次无初始值ArrayList对象时间为："+(endTime3-beginTime3));


        listTest(size, linkedList,a, b);
        listTest(size, arrayList,a, b);
        listTest(size, defaultArrayList, a, b);
    }


    public static void listTest(int size ,List list, int[] add, int del[]) {
        long beginTime = System.currentTimeMillis();

        for (int i = 0; i < size; i++) {
            list.add(i);
        }

        long insertTime = System.currentTimeMillis();

        for (int i = 0; i < add.length; i++) {
            list.get(add[i]);
        }

        long readTime = System.currentTimeMillis();

        for (int i = 0; i < add.length; i++) {
            int sub = (int) (Math.random() * list.size());
            list.set(sub, 9999);
        }


        long updateTime = System.currentTimeMillis();

        List backUpList = new LinkedList();
        backUpList.addAll(list);

        long timeNew1 = System.currentTimeMillis();

        for (int i = del.length-1; i >0 ; i--) {
            list.remove(del[i]);
        }

        long delTime = System.currentTimeMillis();

        list.clear();
        list.addAll(backUpList);

        long timeNew2 = System.currentTimeMillis();
        int list_size = list.size();
        for (int i = 0; i < add.length; i++) {
            list.remove(list_size-i-1);
        }
        int list_size2 = list.size();
        
        long delTime2 = System.currentTimeMillis();

        list.clear();
        list.addAll(backUpList);

        long timeNew3 = System.currentTimeMillis();

        for (int i = 0; i < add.length; i++) {
            list.remove((int)(list.size()/2));
        }

        long delTime3 = System.currentTimeMillis();

        System.out.println(list.getClass());
        System.out.println("增加"+size+"条数据时间为:"+(insertTime-beginTime));
        System.out.println("随机读取" + add.length + "条数据消耗时间为：" + (readTime-insertTime));
        System.out.println("随机更新"+ add.length+"条数据所消耗时间为：" + (updateTime - readTime));
        System.out.println("删除前"+del.length+"条数据的时间为：" + (delTime - timeNew1));
        System.out.println("删除最后"+ add.length+"条数据所花费的时间为("+list_size+"-"+ list_size2+"):"+(delTime2-timeNew2));
        System.out.println("删除中间"+ add.length+"条数据所花费的时间为："+(delTime3-timeNew3));

    }

    public static int[] getRadomArray(int length) {
        int a[] = new int[length];
        for (int i = 0; i < a.length; i++) {
            a[i] = (int) (Math.random() * 1000);
        }
        return a;
    }

}
