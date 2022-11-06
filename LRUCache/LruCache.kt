abstract class LRUCache<K, V>(capacity: Int) {
    val capacity: Int
    var head: N<K, V>? = null
    var tail: N<K, V>? = null

    init {
        assert(capacity > 0)
        this.capacity = capacity
    }

    fun put(key: K, value: V) {
        assert(key != null)
        assert(value != null)
        val prevSize = size()
        doPut(key, value)
        assert(size() >= prevSize)
        assert(head != null)
        assert(tail != null)
        assert(head!!.value == value)
    }

    operator fun get(key: K): V? {
        assert(key != null)
        val oldSize = size()
        val res = doGet(key)
        assert(size() == oldSize)
        assert(res == null || head?.value == res)
        return res
    }

    fun size(): Int {
        val result = doSize()
        assert(result in 0..capacity)
        return result
    }

    protected abstract fun doPut(key: K, value: V)
    protected abstract fun doGet(key: K): V?
    protected abstract fun doSize(): Int

}