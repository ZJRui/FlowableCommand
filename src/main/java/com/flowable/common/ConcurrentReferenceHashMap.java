package com.flowable.common;

import com.flowable.common.utils.ObjectUtils;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class ConcurrentReferenceHashMap<K, V> extends AbstractMap<K, V> implements ConcurrentMap<K, V> {


    public static final int DEFAULT_INITIAL_CAPACITY = 16;

    public static final float DEFAULT_LOAD_FACTOR = 0.75f;

    //default_concurrency_level
    private static final int DEFAULT_CONCURRENCY_LEVEL = 16;

    private static final ReferenceType DEFAULT_REFERENCE_TYPE = ReferenceType.SOFT;

    /**
     * <<表示二进制左移，即将该数字在二进制下每一位向左移动n个，右面补充零，数值上扩大了2^n倍；65536
     */
    private static final int MAXIMUM_CONCURRENCY_LEVEL = 1 << 16;
    /**
     * 1 073 741 824
     *
     * 左移的运算规则：按二进制形式把所有的数字向左移动对应的位数，高位移出（舍弃），低位的空位补零。
     *
     * 计算过程已1<<30为例,首先把1转为二进制数字 0000 0000 0000 0000 0000 0000 0000 0001
     *
     * 然后将上面的二进制数字向左移动30位后面补0得到 0010 0000 0000 0000 0000 0000 0000 0000
     *
     * 最后将得到的二进制数字转回对应类型的十进制
     *
     */
    private static final int MAXIMUM_SEGMENT_SIZE = 1 << 30;





    protected ReferenceManager createReferenceManager(){
        return new ReferenceManager();
    }

    protected Reference<K,V>[] createReferenceArray(int size){
        return new Reference[size];
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }

    @Override
    public V getOrDefault(Object key, V defaultValue) {
        return ConcurrentMap.super.getOrDefault(key, defaultValue);
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> action) {
        ConcurrentMap.super.forEach(action);
    }

    @Override
    public boolean remove(Object key, Object value) {
        return false;
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        return false;
    }

    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        ConcurrentMap.super.replaceAll(function);
    }

    @Override
    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        return ConcurrentMap.super.computeIfAbsent(key, mappingFunction);
    }

    @Override
    public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        return ConcurrentMap.super.computeIfPresent(key, remappingFunction);
    }

    @Override
    public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        return ConcurrentMap.super.compute(key, remappingFunction);
    }

    @Override
    public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        return ConcurrentMap.super.merge(key, value, remappingFunction);
    }


    public enum ReferenceType {
        /**
         * use softReferences
         */
        SOFT,
        /**
         * Use WeakReference
         */
        WEAK
    }
    public  enum  Restructure{
        WHEN_NECESSARY,NEVER;
    }

    /**
     * A single segment used to divide the map to allow better concurrent performance.
     *
     * 用于划分映射的单个段，以获得更好的并发性能。
     *
     */
    protected final class Segment extends ReentrantLock{


        private final ReferenceManager referenceManager;
        private final int initialSize;
        /**
         * Array of references indexed using the low order bits from the hash.
         * This property should only be set along with {@code resizeThreshold}.
         * 使用来自哈希的低阶位建立索引的引用数组。此属性只能与resizeThreshold一起设置。
         */
        private volatile Reference<K,V>[] references;

        /**
         * The total number of references contained in this segment. This includes chained
         * references and references that have been garbage collected but not purged.
         * *本段所包含的参考文献总数。这包括链接
         * *已被垃圾回收但未清除的引用。
         */
        private final AtomicInteger count = new AtomicInteger();


        /**
         * The threshold when resizing of the references should occur. When {@code count}
         * exceeds this value references will be resized.
         * *调整引用大小时的阈值。当{@code数}
         * *超过此值的引用将被调整大小。
         */
        private int resizeThreshold;


        public Segment(int initialSize, int resizeThreshold) {
            this.referenceManager = createReferenceManager();
            this.initialSize = initialSize;
            this.references = createReferenceArray(this.initialSize);
            this.resizeThreshold = resizeThreshold;

        }






    }

    protected class  ReferenceManager{

        private final ReferenceQueue<Entry<K, V>> queue = new ReferenceQueue<>();

        public Reference<K,V> createReference(Entry<K,V> entry,int hash ,Reference<K,V> next){

        }
    }

    protected interface  Reference<K,V>{
        Entry<K,V> get();
        int getHash();
        Reference<K,V>getNext();
        void release();
    }

    protected  static final class Entry<K,V> implements Map.Entry<K,V>{
        private final K key;
        private volatile  V value;
        public Entry(K key, V value){
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return this.key;
        }

        @Override
        public V getValue() {
            return this.value;
        }

        @Override
        public V setValue(V value) {
            V previous = this.value;
            this.value = value;
            return previous;
        }

        @Override
        public String toString() {
            return (this.key + "=" + this.value);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry otherEntry = (Map.Entry) obj;
            return  ObjectUtils.nullSafeEquals(getKey(), otherEntry.getKey()) && ObjectUtils.nullSafeEquals(getValue(), otherEntry.getValue());
        }

        @Override
        public int hashCode() {
            return ObjectUtils.nullSafeHashCode(this.key) ^ ObjectUtils.nullSafeHashCode(this.value);
        }
    }


    private static final class SoftEntryReference<K,V> extends SoftReference<Entry<K,V>> implements Reference<K,V>{
        private final int hash;

        private final Reference<K,V> nextReference;


        public SoftEntryReference(Entry<K, V> referent,int hash, Reference<K,V>nextReference,ReferenceQueue<Entry<K,V>>queue) {

            super(referent,queue);
            this.hash=hash;
            this.nextReference = nextReference;


        }

        @Override
        public int getHash() {
            return this.hash;
        }

        @Override
        public Reference<K, V> getNext() {
            return nextReference;
        }

        @Override
        public void release() {

            enqueue();
            clear();
        }
    }
}
