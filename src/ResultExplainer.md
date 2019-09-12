##Collectons ArrayList与LinkedList三种遍历方式下的性能对比
![数据规模为1000](https://github.com/littleYuting/testContainers/blob/master/pic/1.png)
![数据规模为10000](https://github.com/littleYuting/testContainers/blob/master/pic/2.png)
![数据规模为100000](https://github.com/littleYuting/testContainers/blob/master/pic/3.png)
![数据规模为1000000](https://github.com/littleYuting/testContainers/blob/master/pic/4.png)

如果是ArrayList，用三种方式遍历的速度是for>Iterator>foreach，速度级别基本一致； 
如果是LinkedList，则三种方式遍历的差距很大了,数据量大时越明显(一般是超过100000级别)，用for遍历的效率远远落后于foreach和Iterator，Iterator>foreach>>>for； 
