import java.util.*;

public class testSet {
    public static void main(String[] args){
        testOrder();//三种Set的插入顺序对比
        testTreeSet();//对比TreeSet的两种排序方式，一种是针对基本数据类型，一种是针对类对象
//        testAddingTime();//三种Set的插入时间对比
    }

    public static void testOrder(){
        //三种Set的插入顺序对比
        HashSet hashSet = new HashSet();
        TreeSet treeSet = new TreeSet();
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (String data: Arrays.asList("r", "c", "a", "e", "k", "b","m", "a")
             ) {
            hashSet.add(data);
            treeSet.add(data);
            linkedHashSet.add(data);
        }
        System.out.println("the ordering of inputing array:" + Arrays.asList("r", "c", "a", "e", "k", "b","m", "a"));
        System.out.println("the ordering of hashSet:" + hashSet);
        System.out.println("the ordering of treeSet:" + treeSet);
        System.out.println("the ordering of linkedHashSet:" + linkedHashSet);
        /**总结：
         * 1）set无重复元素；
         * 2）hashSet无序，与输入顺序可能不一致
         * 3）treeSet会按照已定义的顺序规则重新排序【红黑树】
         * 4)linkedHashSet的输出顺序和输入顺序保持绝对一致
         */
    }

    public static void testTreeSet(){
        //对比TreeSet的两种排序方式，一种是针对基本数据类型，一种是针对类对象
        TreeSet treeSet = new TreeSet();
        for (int data: Arrays.asList(3,4,2,9,12,4,7)
             ) {
            treeSet.add(data);
        }
        System.out.println("————————————————————————针对基本数据类型的排序————————————————————");
        System.out.println("the ordering of inputing array"+Arrays.asList(3,4,2,9,12,4,7));
        System.out.println("the ordering of treeSet"+treeSet);
        System.out.println("————————————————————————针对引用数据类型的排序————————————————————");
//        //自然排序_Sudent类实现comparable接口，重写compareTo方法
//        TreeSet<Student> students = new TreeSet<>();
        //比较器_新建一个实现Comparator接口的类，重写compare方法
        TreeSet<Student> students = new TreeSet<>(new StudentComparetor());
        Student[] arrayStudent = {new Student("e",12), new Student("a",21),
                new Student("c",10), new Student("a",10)};
        List<Student> listStudent = Arrays.asList(arrayStudent);
        for (Student student: listStudent
             ) {
            students.add(student);
        }
        for (Student s: students
             ) {
            s.getInfo();
        }
        //总结：对于引用数据类型用比较器实现较好，不用修改原有类的结构
    }

    public static void testAddingTime(){
        Set hashSet = new HashSet();
        Set treeSet = new TreeSet();
        Set linkedHashSet = new LinkedHashSet();
        System.out.println("————————————三种Set的性能对比————————————————");
        System.out.println("对比数据规模 " + "1000");
        System.out.println("for hashSet 时间: " + addingDuration(hashSet, 1000));
        System.out.println("for treeSet 时间: " + addingDuration(treeSet, 1000));
        System.out.println("for linkedHashSet 时间: " + addingDuration(linkedHashSet, 1000));
        System.out.println("对比数据规模 " + "50000");
        System.out.println("for hashSet 时间: " + addingDuration(hashSet, 50000));
        System.out.println("for treeSet 时间: " + addingDuration(treeSet, 50000));
        System.out.println("for linkedHashSet 时间: " + addingDuration(linkedHashSet, 50000));
        System.out.println("对比数据规模 " + "1000000");
        System.out.println("for hashSet 时间: " + addingDuration(hashSet, 100000));
        System.out.println("for treeSet 时间: " + addingDuration(treeSet, 100000));
        System.out.println("for linkedHashSet 时间: " + addingDuration(linkedHashSet, 100000));
        System.out.println("对比数据规模 " + "10000000");
        System.out.println("for hashSet 时间: " + addingDuration(hashSet, 10000000));
        System.out.println("for treeSet 时间: " + addingDuration(treeSet, 10000000));
        System.out.println("for linkedHashSet 时间: " + addingDuration(linkedHashSet, 10000000));
    }

    public static long addingDuration(Set set,int  nums){
        Random r = new Random();
        long startTime, endTime;
        startTime = System.nanoTime();
        for (int i = 0; i < nums; i++) {
            set.add(r.nextInt(nums)+10);
        }
        endTime = System.nanoTime();
        return endTime - startTime;
    }
}
//class Student implements Comparable<Student>{
//    private String name;
//    private int age;
//
//    public Student(){}
//
//    public Student(String name, int age){
//        this.name = name;
//        this.age = age;
//    }
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public int getAge() {
//        return age;
//    }
//
//    public void setAge(int age) {
//        this.age = age;
//    }
//
//    public void getInfo(){
//        System.out.println("the student's name is "+this.name+", and his age is "+this.age);
//    }
//
//    @Override
//    public int compareTo(Student s){
//        int num = this.name.length() - s.name.length();//判断名字长度一致
//        int num1 = num == 0 ? this.name.compareTo(s.name) : num;//判断名字的长度和内容一致
//        int num2 = num1 == 0 ? this.age - s.age : num1;//名字一致的时候判断年龄一致
//        return num2;
//    }
//}
class Student{
    private String name;
    private int age;

    public Student(){}

    public Student(String name, int age){
        this.name = name;
        this.age = age;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void getInfo(){
        System.out.println("the student's name is "+this.name+", and his age is "+this.age);
    }
}
class StudentComparetor implements Comparator<Student>{

    @Override
    public int compare(Student s1, Student s2){
        int num = s1.getName().length() - s2.getName().length();//判断名字长度一致
        int num1 = num == 0 ? s1.getName().compareTo(s2.getName()) : num;//判断名字的长度和内容一致
        int num2 = num1 == 0 ? s1.getAge() - s2.getAge() : num1;//名字一致的时候判断年龄一致
        return num2;
    }
}
