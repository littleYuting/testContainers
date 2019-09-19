# 1. 概要
## 1.1 List、Set 与 Map 的关联  

## 1.2 List、Set 与 Map 的区别  
| ==Containers== | ArrayList | LinkedList | HashSet | LinkedHashSet | TreeSet  | HashMap | LinkedHashMap | TreeMap |
| :----: | :----:| :----:| :----: | :----: |  :----:| :----: | :----: |:----: | 
| ==DataStructure== | Object数组 | 双向链表 | HashMap  | LinkedHashMap  | 红黑树 | 数组+链表 | 拉链式散列结构 + 双向链表 | 红黑树 |
## 1.3 选用说明 
- 只需存放元素值（ArrayList）
- 只需存放元素值 & 保证元素唯一（HashSet）& 排序（TreeSet）
- 根据键值获取到元素值（HashMap）& 排序（TreeMap）& 线程安全（ConcurrentHashMap）
# 2. Collection
## 2.1 List
一般性：  
一般方法：
### 2.1.1 ArrayList  
### 2.1.2 LinkedList  
### 2.1.3 对比 ArrayList 与 LinkedList
- 线程安全性：ArrayList 和 LinkedList 均非同步，不保证线程安全；
- 插入和删除是否受元素位置的影响：ArrayList 受， LinkedList不受（与存储结构有关）；
- 三种遍历速度对比
    - ArrayList的遍历速度 for > Iterator > foreach，级别基本一致，推荐使用 for；
    - LinkedList的遍历速度 Iterator > foreach >>> for；级别相差很大,且数据量越大越明显(尤其是超过100000级别)，一般用 foreach 或 Iterator；
    - 现象与分析：
        - ArrayList的遍历中for比Iterator快，而LinkedList中却是Iterator远快于for；
        - ArrayList是基于索引(index)的数组，LinkedList是一个双向循环带头节点的链表，另ArrayList实现了RandomAccess接口，支持快速随机访问；
    - 补充：RandomAccess接口 无具体实现，作为一个标志说明实现该接口的类具有随机访问功能；
### 2.1.4 小结
- collections集合一般使用foreach，对于ArrayList推荐用for，特殊情况下利用显示迭代器Iterator：1）遍历过程需对集合某元素进行删除、修改等；2）并行地遍历多集合。
- foreach和Iterator基本效率相当，Iterator稍快: foreach基于Iterator实现
## 2.2 Set
一般性： key 具有唯一性，map 可存储不同类型 key   
一般方法：
### 2.2.1 HashSet  
### 2.2.2 LinkedHashSet
### 2.2.3 TreeSet
### 2.2.4 对比 HashSet、LinkedHashSet 与 TreeSet
- Set 无重复元素
    - HashSet 引用类型下保证唯一性的方法： 
    - TreeSet 引用类型保证唯一性的方法：
        - 自然排序法：引用类实现Comparable接口，重写compareTo方法；
        - 比较器（推荐）：新建一个实现Comparator接口的类，重写compare方法；
- 插入顺序对比：HashSet随机插入，treeSet按定义顺序插入，LinkedList按构造顺序插入。
- 插入性能对比：HashSet和LinkedHashSet在百万级及以下规模时，效率基本一致，LinkedHashSet的时间消耗主要是创建双向循环链表；
TreeSet插入性能普遍更差的主要原因是在插入过程中构造一棵红黑树。

# 3. Map
一般性：  
一般方法：init、put、putAll、containsXX、get、remove、clear、isEmpty
三种遍历方法：map.keySet(Set)、map.values(Collection)、（推荐）==map.entrySet(Set)==
## 2.1 HashMap
## 2.2 LinkedHashMap
## 2.3 TreeMap
## 2.4 对比 HashMap、LinkedHashMap和TreeMap
- Map 无重复元素
    - HashMap 引用类型下保证唯一性的方法：Override hashCode & equals 方法； 
    - TreeMap 引用类型下保证唯一性的方法：自然排序法、比较器（同 TreeSet）；
- key or value 为 null
    - Hashtable 不支持 key或value为null，会抛出空指针异常 compareTo 方法；
    - hashMap & linkedHashMap support key-null,null-value,null-null；
    - treeMap 不支持 key为null，会抛出空指针异常；
- 插入顺序对比
    - hashTable & hashMap 无序；
    - linkedHashMap 有序，且与put顺序一致；
    - treeMap 不支持 key为null，会抛出空指针异常；
- 插入性能对比： linkedHashMap > hashMap > treeMap （put过程中维持树的顺序）
- 遍历性能对比
## 4.1 对比 Map 与 Set 
## 4.2 容器与线程安全
## 4.3 经典面试题整理
- ArrayList 问题整理
    - ArrayList 与 Vector 区别：Vector类的所有方法都是同步
    - ArrayList 的扩容机制：https://github.com/Snailclimb/JavaGuide/blob/master/docs/java/collection/ArrayList-Grow.md
- HashMap 问题整理
    - HashMap 和 Hashtable 的区别
        - https://blog.csdn.net/luojishan1/article/details/81952147
https://blog.csdn.net/weixin_34166847/article/details/91883797
https://blog.csdn.net/j080624/article/details/52815693
        - 线程是否安全： HashMap 是非线程安全的，HashTable 是线程安全的；
效率： HashMap 要比 HashTable 效率高一点

    - 对比 JDK 1.8 前后的 HashMap的底层实现
    - HashMap 的长度为什么是2的幂次方
    - 多线程相关
        - HashMap 多线程操作导致死循环问题
        - ConcurrentHashMap 和 Hashtable 的区别
## 4.4 参考文献
- https://github.com/Snailclimb/JavaGuide/blob/master/docs/java/collection/Java%E9%9B%86%E5%90%88%E6%A1%86%E6%9E%B6%E5%B8%B8%E8%A7%81%E9%9D%A2%E8%AF%95%E9%A2%98.md

