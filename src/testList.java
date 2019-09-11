import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class testList {

    public static void main(String[] args){
        List arrayList = new ArrayList();
        List linkedList = new LinkedList();
        int times = 1000000;
        for (int i = 0; i < times; i++) {
            arrayList.add(String.valueOf(i));
            linkedList.add(String.valueOf(i));
        }
        System.out.println("——————arrayList三种遍历方式对比 " + "& 数据规模" + String.valueOf(times) + "——————");
        System.out.println("for arraylist 时间: " + testFor(arrayList));
        System.out.println("iteator arraylist 时间: " + testIterator(arrayList));
        System.out.println("foreach arraylist 时间: " + testForeach(arrayList));
        System.out.println("——————linkedList三种遍历方式对比" + "& 数据规模" + String.valueOf(times) + "——————");
        System.out.println("for arraylist 时间: " + testFor(linkedList));
        System.out.println("iteator arraylist 时间: " + testIterator(linkedList));
        System.out.println("foreach arraylist 时间: " + testForeach(linkedList));
    }

    public static long testFor(List<String> list){
        long startTime = 0L, endTime = 0L;
        startTime = System.nanoTime();
        String str;
        for (int i = 0; i < list.size(); i++) {
            str = list.get(i);
        }
        endTime = System.nanoTime();
        return endTime - startTime;
    }

    public static long testIterator(List<String> list){
        long startTime = 0L, endTime = 0L;
        startTime = System.nanoTime();
        String str;
        Iterator<String> iter = list.iterator();
        while(iter.hasNext()){
            str = iter.next();
        }
        endTime = System.nanoTime();
        return endTime - startTime;
    }

    public static long testForeach(List<String> list){
        long startTime = 0L, endTime = 0L;
        startTime = System.nanoTime();
        String str;
        for (Object obj:list
             ) {
            str = (String)obj;
        }
        endTime = System.nanoTime();
        return endTime - startTime;
    }
}
