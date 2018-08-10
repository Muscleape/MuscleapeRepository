# HashMap深复制

> 我用的Map中存储的都是String，不是基本数据类型的，这个有很大影响；
> putAll()和clone()对基本数据类型的数据是没有问题的，但是引用类型还是不行；

## 使用场景：

有一个基础的Map，在另外的一个循环结构里，需要遍历这个Map，每次循环只修改其中部分值，但是在下一次的循环中需要将Map的值还原，所以，需要每次循环之前，将Map复制一份，循环中直接修改副本Map就好；

## 尝试的办法：

### 1、=赋值

新建一个Map，然后使用=直接赋值,这样只是复制了sourceMap的引用，和sourceMap仍使用同一个内存区域，所以，在修改newMap的时候，sourceMap的值同样会发生变化。

```java
Map<String,String> newMap = sourceMap;
```

> 上述的办法不行，使用Map本身提供的方法，网上大都说putAll()和clone()方法就是深拷贝，但是实际使用后，发现我的对象还是被改变了；这里就是开头说到的，这两个方法只能修改基本数据类型的，如果是引用类型，这两个方法还是浅拷贝；

### 2、putAll()

创建一个新的Map结构，使用putAll()方法把原先的Map添加到新的Map中，但是发现修改了副本的Map之后，原先的Map中数据也被修改了；

### 3、clone()

Map自带了一个clone()方法，但是，它的源码中注释说明了也只是一种浅复制(shallow copy)：

```java
/**
     * Returns a shallow copy of this <tt>HashMap</tt> instance: the keys and
     * values themselves are not cloned.
     *
     * @return a shallow copy of this map
     */
    @SuppressWarnings("unchecked")
    @Override
    public Object clone() {
        HashMap<K,V> result;
        try {
            result = (HashMap<K,V>)super.clone();
        } catch (CloneNotSupportedException e) {
            // this shouldn't happen, since we are Cloneable
            throw new InternalError(e);
        }
        result.reinitialize();
        result.putMapEntries(this, false);
        return result;
    }
```

### 4、 自定义clone方法

```java
/**
     * 对象深度复制(对象必须是实现了Serializable接口)
     *
     * @param obj
     * @return T
     * @author Muscleape
     * @date 2018/8/10 14:39
     */
    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T clone(T obj) {
        T clonedObj = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            oos.close();

            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            clonedObj = (T) ois.readObject();
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clonedObj;
    }
```