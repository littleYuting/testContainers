import java.util.*;

public class testMap {
    /**
     * including hashTable , hashMap , linkedHashMap , treeMap
     * hashTable 已被弃用，相比hashMap，区别主要有二：一是线程安全(synchronize)，而是key-value为空，hashTable要求更严格，性能也更低
     * ConcurrentHashMap类（是 Java并发包 java.util.concurrent 中提供的一个线程安全且高效的 HashMap 实现, key不能为null）。
     * @param args
     */
    public static void main(String[] args){
//        testBaseMethods();
//        testHashMap();
//        testTreeMap();
//        testNull();
//        testOrder();
//        testPutPerformance();
        testTraversePerformance();
    }

    public static void testBaseMethods(){
        // just using hashMap as an example
        Map map = new HashMap();
        Map map1 = new HashMap<String, Integer>();
        Map<Integer, Integer> map2 = new HashMap<Integer, Integer>();
        //put
        map.put("a", 1);
        map.put("b", 2);
        map1.put("c", 3);
        map.put("a", 4);//map的key具有唯一性, 且对于重复的key会覆盖之前的value
        map.put("e", 5);
        map2.put(1, 1);
        System.out.println(map);//output : {a=4, b=2, e=5}
        System.out.println(map1);//output : {c=3}
        //putAll
        map1.putAll(map);
        System.out.println(map1);//output : {a=4, b=2, c=3, e=5} ##会按照hashCode排序
        map2.putAll(map);
        System.out.println(map2);//output : {1=1, a=4, b=2, e=5} ## map允许key的类型不同, 即补充的泛型类型对map没有约束作用，可不写！
        //remove
        map1.remove("c");
        System.out.println(map1);//output : {a=4, b=2, e=5}
        //get
        System.out.println(map2.get("c")); // 对于不存在的key，返回的value为null
        System.out.println(map2.get("b"));//output : 2
        //isContain
        System.out.println(map1.containsKey("c"));//output : false
        System.out.println(map2.containsValue(1));//output : true

        //traverse
        // map2 as example , output : key : 1 , value : 1; key : a , value : 4; key : b , value : 2; key : e , value : 5
        traverseOne(map2);
        traverseTwo(map2);//output : only value, no key
        //traverse-Three 一般情况下推荐使用 map.entrySet
        // 注：Set<Map.Entry<>> = map.entrySet(); 如果用set作为中间类型，需要设置容器内的数据类型，所以还是采用以下方式实现
        traverseThree(map2);

        map.clear();//清空map
        System.out.println(map.isEmpty());//isEmpty()判断map是否为空
    }

    public static void testHashMap(){
        //key为引用类型时，需重写对应类的hashCode和equals方法,否则的话，比较的是Person的物理地址
        HashMap map = new HashMap<Person, String>();
        map.put(new Person("a",1), "1001");
        map.put(new Person("b",2), "1002");
        map.put(new Person("c",3), "1003");
        map.put(new Person("d",4), "1004");
        /** output as flows if not override
         * the key is null , but its value is 1005
         * the information of person : name-c; age-3 and person.value : 1003
         * the information of person : name-b; age-2 and person.value : 1006
         * the information of person : name-b; age-2 and person.value : 1002
         * the information of person : name-a; age-1 and person.value : 1001
         * the information of person : name-d; age-4 and person.value : 1004
         */
        map.put(new Person("b", 2), "1006");
        //test1:重写hashCode 和 equals方法，可以实现重复key覆盖之前的value值，但是在 en。getKey()下输出结果形式： the key : Person@72 ; the value : 1003
        //test2:重写toString方法
        Set<Map.Entry<Person, String>> set = map.entrySet();
        /**
         * final output:
         * the key : null ; the value : 1005
         * the key : Person @ name : cage : 3 ; the value : 1003
         * the key : Person @ name : aage : 1 ; the value : 1001
         * the key : Person @ name : dage : 4 ; the value : 1004
         * the key : Person @ name : bage : 2 ; the value : 1006
         */

        Iterator<Map.Entry<Person, String>> iterator = set.iterator();
        while (iterator.hasNext()) {
            Map.Entry<Person, String> en = iterator.next();
//            if (en.getKey() != null) {
//                System.out.println("the information of person : " + "name-" + en.getKey().getName() +
//                        "; age-" + en.getKey().getAge() + " and person.value : " + en.getValue());
//            } else {
//                System.out.println("the key is null , but its value is " + en.getValue());
//            }
            System.out.println("the key : " + en.getKey() + " ; the value : " + en.getValue());
        }
    }

    public static void testTreeMap(){
        //key为引用类型时，需重写自然排序或构造比较器,否则，报错：Exception in thread "main" java.lang.ClassCastException: Person cannot be cast to java.lang.Comparable
//        TreeMap map = new TreeMap<Person, String>(new PersonComparetor());
        TreeMap map = new TreeMap<Person, String>();
        map.put(new Person("a",1),"11");
        map.put(new Person("b",2),"12");
        map.put(new Person("b",3),"14");
        map.put(new Person("a",1),"14");
        Map.Entry en = null;
        Iterator iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            en = (Map.Entry) iterator.next();
            System.out.println("the key : " + en.getKey() + " ; the value : " + en.getValue());
        }
        /**
         * output
         * the key : Person @ name : b , age : 3 ; the value : 14
         * the key : Person @ name : b , age : 2 ; the value : 12
         * the key : Person @ name : a , age : 1 ; the value : 14
         */
    }

    public static void testNull(){
        Hashtable dic1 = new Hashtable();
        HashMap map2 = new HashMap();
        LinkedHashMap map3 = new LinkedHashMap();
        TreeMap map4 = new TreeMap();
        //Hashtable 不支持 key或value为null，会抛出空指针异常
//        dic1.put(null, 1);
//        dic1.put(1, null);
//        dic1.put(null, null);
        //hashMap support key-null,null-value,null-null
        map2.put(null, 1);
        map2.put(1, null);
        map2.put(null, null);
        System.out.println(map2);//output : {null=null, 1=null}
        //linkedHashMap support key-null,null-value,null-null
        map3.put(null, 1);
        map3.put(1, null);
        map3.put(null, null);
        System.out.println(map3);//output : {null=null, 1=null}
        //treeMap 不支持 key为null，会抛出空指针异常
//        map4.put(null, 1);
        map4.put(1, null);
//        map4.put(null, null);
        //补充： HashMap支持带有null的形式，那么在HashMap中不能由get()方法来判断,而应该用containsKey()方法来判断!
    }

    public static void testOrder(){
        /**
         * original order : cyt , ly, lrx , hkp
         * use map then order :
         * {hkp=0, cyt=2, lrx=4, ly=1}
         * {lrx=4, hkp=0, ly=1, cyt=2}
         * {cyt=2, ly=1, lrx=4, hkp=0}
         * {cyt=2, hkp=0, lrx=4, ly=1}
         */
        Hashtable dic1 = new Hashtable();
        Map map2 = new HashMap();
        Map map3 = new LinkedHashMap();
        TreeMap map4 = new TreeMap();
        creatMap(dic1);
        creatMap(map2);
        creatMap(map3);
        creatMap(map4);
        System.out.println(dic1);// hashTable无序
        System.out.println(map2);// hashMap 无序
        System.out.println(map3);// linkedHashMap 有序，且与put顺序一致
        System.out.println(map4);// treeMap 有序，按照一定规则重排， 与put顺序可能不一致
    }
    public static void creatMap(Map map){
        map.put("cyt", 2);
        map.put("ly", 1);
        map.put("lrx", 4);
        map.put("hkp", 0);
    }

    public static void testPutPerformance(){
        Map hashMap= new HashMap();
        Map treeMap = new TreeMap();
        Map linkedHashMap = new LinkedHashMap();
        System.out.println("————————————hashMap、treeMap 与 linkedHashMap 的 put 性能对比————————————————");
        System.out.println("对比数据规模 " + "1000");
        System.out.println("for hashMap 时间: " + putTime(hashMap, 1000));
        System.out.println("for treeMap 时间: " + putTime(treeMap, 1000));
        System.out.println("for linkedHashMap 时间: " + putTime(linkedHashMap, 1000));
        System.out.println("对比数据规模 " + "50000");
        System.out.println("for hashMap 时间: " + putTime(hashMap, 50000));
        System.out.println("for treeMap 时间: " + putTime(treeMap, 50000));
        System.out.println("for linkedHashMap 时间: " + putTime(linkedHashMap, 50000));
        System.out.println("对比数据规模 " + "1000000");
        System.out.println("for hashMap 时间: " + putTime(hashMap, 100000));
        System.out.println("for treeMap 时间: " + putTime(treeMap, 100000));
        System.out.println("for linkedHashMap 时间: " + putTime(linkedHashMap, 100000));
        System.out.println("对比数据规模 " + "10000000");
        System.out.println("for hashMap 时间: " + putTime(hashMap, 10000000));
        System.out.println("for treeMap 时间: " + putTime(treeMap, 10000000));
        System.out.println("for linkedHashMap 时间: " + putTime(linkedHashMap, 10000000));

        /**
         * output
         * ————————————hashMap、treeMap 与 linkedHashMap 的 put 性能对比————————————————
         * 对比数据规模 1000
         * for hashMap 时间: 1411258
         * for treeMap 时间: 2113467
         * for linkedHashMap 时间: 772771
         * 对比数据规模 50000
         * for hashMap 时间: 16471100
         * for treeMap 时间: 21691902
         * for linkedHashMap 时间: 6123581
         * 对比数据规模 1000000
         * for hashMap 时间: 10640464
         * for treeMap 时间: 35225874
         * for linkedHashMap 时间: 11188288
         * 对比数据规模 10000000
         * for hashMap 时间: 8965167995
         * for treeMap 时间: 13948357337
         * for linkedHashMap 时间: 3767402745
         * summary
         * put 性能对比： linkedHashMap > hashMap >  treeMap 最慢（put过程中维持树的顺序）
         */
    }

    public static long putTime(Map map,int  nums){
        Random r = new Random();
        long startTime, endTime;
        startTime = System.nanoTime();
        for (int i = 0; i < nums; i++) {
            Integer data = r.nextInt(nums)+10;
            map.put(data, data);
        }
        endTime = System.nanoTime();
        return endTime - startTime;
    }

    public static void testTraversePerformance(){
        Map map = new HashMap();
        Integer nums = 1000;
        testVaryNums(map, nums);
        nums = 50000;
        testVaryNums(map, nums);
        nums = 1000000;
        testVaryNums(map, nums);
        nums = 10000000;
        testVaryNums(map, nums);
        /**Output
         * ------在1000 规模下的 map.keySet 遍历时间 :780896
         * ------在1000 规模下的 map.values 遍历时间 :426799
         * ------在1000 规模下的 map.entrySet 遍历时间 :412687
         * ------在50000 规模下的 map.keySet 遍历时间 :3907903
         * ------在50000 规模下的 map.values 遍历时间 :2259297
         * ------在50000 规模下的 map.entrySet 遍历时间 :2429503
         * ------在1000000 规模下的 map.keySet 遍历时间 :37791372
         * ------在1000000 规模下的 map.values 遍历时间 :21066672
         * ------在1000000 规模下的 map.entrySet 遍历时间 :23875505
         * ------在10000000 规模下的 map.keySet 遍历时间 :267162403
         * ------在10000000 规模下的 map.values 遍历时间 :137588322
         * ------在10000000 规模下的 map.entrySet 遍历时间 :152286368
         * 总结： 亿级规模数据运行受阻，在千万级规模以下，普遍存在 map.values 与 map.entrySet 遍历速度相当，map.keySet 遍历速度较慢，
         * 考虑到 entrySet 能同时得到 key 和 value ，所以推荐使用 map.entrySet 遍历;
         */
    }
    public static void testVaryNums(Map map, Integer nums){
        Random r = new Random();
        for (int i = 0; i < nums; i++) {
            Integer data = r.nextInt(nums)+10;
            map.put(data, data);
        }
        Long time1 = testSingleTraverse(1, map);
        Long time2 = testSingleTraverse(2, map);
        Long time3 = testSingleTraverse(3, map);
        System.out.println("------在" + String.valueOf(nums) + " 规模下的 map.keySet 遍历时间 :" + String.valueOf(time1));
        System.out.println("------在" + String.valueOf(nums) + " 规模下的 map.values 遍历时间 :" + String.valueOf(time2));
        System.out.println("------在" + String.valueOf(nums) + " 规模下的 map.entrySet 遍历时间 :" + String.valueOf(time3));
    }
    public static Long testSingleTraverse(Integer flag, Map map){
        long startTime, endTime;
        startTime = System.nanoTime();
        switch (flag) {
            case 1:
                traverseOne(map);
                break;
            case 2:
                traverseTwo(map);
                break;
            case 3:
                traverseThree(map);
                break;
        }
        endTime = System.nanoTime();
        return endTime - startTime;
    }

    public static void traverseOne(Map map){
        Set set = map.keySet();
        Iterator it1 = set.iterator();
        while (it1.hasNext()) {
            Object key_obj = it1.next();
            Object value_obj = map.get(key_obj);
//            System.out.println("key : " + key_obj + " , value : " + value_obj);
        }
    }

    public static void traverseTwo(Map map){
        Collection collection = map.values();
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            Object value_obj = it.next();
//            System.out.println("value : " + value_obj);
        }//output : only value, no key
    }

    public static void traverseThree(Map map){
        Map.Entry en = null;
        Iterator iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            en = (Map.Entry) iterator.next();
//            System.out.println("key : " + en.getKey() + " , value : " + en.getValue());
        }
    }
}

class Person implements Comparable<Person>{
    private String name;
    private Integer age;

    Person() {}

    Person(String name, Integer age){
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public int hashCode(){
        return this.name.hashCode() + age * 5;
    }

    @Override
    public boolean equals(Object obj){
        if (obj instanceof Person) {
            Person p = (Person)obj;
            return this.name == p.getName() && this.age == p.getAge();
        } else {
            return false;
        }
    }

    @Override
    public  String toString(){
        return "Person @ name : " + this.name + " , age : " + this.age;
    }

    @Override
    public int compareTo(Person p){
        int num = p.getName().length() - this.getName().length();//判断名字长度一致
        int num1 = num == 0 ? p.getName().compareTo(this.getName()) : num;//判断名字的长度和内容一致
        int num2 = num1 == 0 ? p.getAge() - this.getAge() : num1;//名字一致的时候判断年龄一致
        return num2;
    }
}
//comparator for treeMap test
class PersonComparetor implements Comparator<Person>{
    @Override
    public int compare(Person p1, Person p2){
        int num = p1.getName().length() - p2.getName().length();//判断名字长度一致
        int num1 = num == 0 ? p1.getName().compareTo(p2.getName()) : num;//判断名字的长度和内容一致
        int num2 = num1 == 0 ? p1.getAge() - p2.getAge() : num1;//名字一致的时候判断年龄一致
        return num2;
    }
}