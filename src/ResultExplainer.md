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
