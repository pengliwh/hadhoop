

## top
可以通过top命令查看各个进程的cpu使用情况，默认按cpu使用率排序
- usage 1
```
top
1： 监控每个逻辑cpu的情况
b： 打开/关闭加亮效果
x:  打开/关闭排序列的加亮效果
shift + > | <: 向右或向左改变排序列
```

- usage 2
```
top -c  可以显示完整的子命令
top -Hp 23344
```


## jmap
- usage 1: 打印heap的概要信息，GC使用的算法，heap的配置及使用情况，可以用此来判断内存目前的使用情况以及垃圾回收情况
```
jmap -heap 2178
```

- usage 2: 打印等待回收的对象信息
```
jmap -finalizerinfo 24971
```

- usage 3: dump堆到文件,format指定输出格式，live指明是活着的对象,file指定文件名
```
jmap -dump:live,format=b,file=dump.hprof 2178
可以使用jvisualvm来分析dump文件
```


## jstack
jstack命令主要用来查看Java线程的调用堆栈的，可以用来分析线程问题（如死锁）。

- usage1： 
```
jstack -m 2178
```

- usage2：
```
jstack -l 89281 > 89281.pid
```

## jdb
The Java Debugger (JDB) is a simple command-line debugger for Java classes. The jdb command and its options call the JDB. The jdb command demonstrates the Java Platform Debugger Architecture (JDBA) and provides inspection and debugging of a local or remote Java Virtual Machine (JVM). See Java Platform Debugger Architecture (JDBA) at