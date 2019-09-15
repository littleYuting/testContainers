## 1. Collectons ArrayList与LinkedList三种遍历方式下的性能对比
![数据规模为1000](https://github.com/littleYuting/testContainers/blob/master/pic/1.png)
![数据规模为10000](https://github.com/littleYuting/testContainers/blob/master/pic/2.png)
![数据规模为100000](https://github.com/littleYuting/testContainers/blob/master/pic/3.png)
![数据规模为1000000](https://github.com/littleYuting/testContainers/blob/master/pic/4.png)

1.1 结论 
  - 如果是ArrayList，用三种方式遍历的速度是for>Iterator>foreach，速度级别基本一致；
  - 如果是LinkedList，则三种方式遍历的差距很大了,数据量大时越明显(一般是超过100000级别)，用for遍历的效率远远落后于foreach和Iterator，Iterator>foreach>>>for；

1.2 分析 
  - foreach和Iterator基本效率相当，Iterator稍快: foreach基于Iterator实现
  - ArrayList的遍历中for比Iterator快，而LinkedList中却是Iterator远快于for：ArrayList是基于索引(index)的数组，LinkedList是一个双向循环带头节点的链表，另ArrayList实现了RandomAccess接口，支持快速随机访问，详见<https://blog.csdn.net/yeyazhishang/article/details/83244800>

1.3 使用技巧

    - ArrayList一般使用for或者foreach；
    - LinkedList一般使用foreach或者Iterator(尤其数据量较大时)；
    - collections集合一般使用foreach，特殊情况除外：
        - 过滤：遍历过程需对集合某元素进行删除等，使用显式的迭代器，以便可以调用它的remove方法。
        - 转换：遍历过程需对集合元素进行修改；
        - 平行迭代：并行地遍历多个集合，就需要显式的控制迭代器或者索引变量，以便所有迭代器或者索引变量都可以得到同步前移。

## 2. Collectons HashSet、TreeSet和LinkedHashSet 的插入顺序和插入性能对比
![插入顺序对比](https://github.com/littleYuting/testContainers/blob/master/pic/5.png)

2.1 结果
- Set无重复元素
- HashSet随机插入，treeSet按定义顺序插入，LinkedList按构造顺序插入。
    
![TreeSet在两种数据类型下的排序](https://github.com/littleYuting/testContainers/blob/master/pic/6.png)

2.2 结果
- 对于基本数据类型，会按ASCALL码进行排序；
- 对于引用数据类型，有两种方法：
    - 自然排序法：引用类实现Comparable接口，重写compareTo方法；
    - 比较器：新建一个实现Comparator接口的类，重写compare方法；
    - 重写方法，需针对引用类的属性自定义比较规则；
- 对于引用数据类型，建议采用比较器，不改变原有类的结构。

![三种set插入性能对比](https://github.com/littleYuting/testContainers/blob/master/pic/7.png)

2.3 结论 
  - hashSet和linkedHashSet在百万级及以下规模时，效率基本一致，linkedHashSet的时间消耗主要是创建双向循环链表
  - treeSet插入性能差的主要原因是在插入过程中构造一棵红黑树。
  
## 3. Map- HashTable、HashMap、linkedHashMap 和 TreeMap 对比

3.1 Map 的一般方法
- init：map 允许key类型不同，定义时无需泛型约束；
- put：key具有唯一性，且重复的key会覆盖之前的value；
- putAll：合并两个map，且合并过程中put顺序改变；
- containsKey & containsValue：判断key或value是否存在，返回boolean值；
- get：hashMap当key为不存在时，返回value为空，一般在采用此方法时结合containXX先做判断；
- remove：依据key删除map中的该元素；
- clear：清空map；
- isEmpty：判断map是否为空；
- map的三种遍历方式：
    - map.keySet(Set)
    - map.values(Collection)
    - map.entrySet(Set)
    - [keySet与entrySet的区别](https://blog.csdn.net/qq_35643562/article/details/81435822)

3.2 针对引用类型的 hashMap 唯一性
- override hashCode 方法
- override equals 方法
- override toString 方法

3.3 针对引用类型的 TreeMap 唯一性
- （同TreeSet）自然排序法，引用类实现 comparable 接口，重写 compareTo 方法；
- （同TreeSet）比较器，构造对应类的 comparator 类，在 init 时声明；
- 否则，异常报错报错：Exception in thread "main" java.lang.ClassCastException: Person cannot be cast to java.lang.Comparable；

3.4 key or value 为 null
- Hashtable 不支持 key或value为null，会抛出空指针异常 compareTo 方法；
- hashMap & linkedHashMap support key-null,null-value,null-null；
- treeMap 不支持 key为null，会抛出空指针异常；

3.5 插入顺序对比
- hashTable & hashMap 无序；
- linkedHashMap 有序，且与put顺序一致；
- treeMap 有序，按照一定规则重排，与put顺序可能不一致；

3.6 插入性能对比

- hashMap、treeMap 与 linkedHashMap 的 put 性能对比
         
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

    - put 性能对比： linkedHashMap > hashMap >  treeMap （put过程中维持树的顺序）

