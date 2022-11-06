class LRUCacheImpl<K, V>(capacity: Int) : LRUCache<K, V>(capacity) {
    private val data: MutableMap<K, N<K, V>> = HashMap()
    override fun doPut(key: K, value: V) {
        assert(key != null)
        assert(value != null)
        val n = data[key]
        n?.let { remove(it) }
        add(N(key, value))
    }

    override fun doGet(key: K): V? {
        assert(key != null)
        val n = data[key] ?: return null
        remove(n)
        add(N(n.key, n.value))
        return n.value
    }

    override fun doSize(): Int {
        var res = 0
        var cur: N<*, *>? = head
        while (cur != null) {
            res++
            cur = cur.prev
        }
        return res
    }

    private fun add(new: N<K, V>) {
        if (size() == capacity) {
            if (capacity == 0) {
                return
            }
            remove(tail!!)
        }
        if (head != null) {
            head!!.next = new
            new.prev = head
        }
        head = new
        if (tail == null) {
            tail = new
        }
        data[new.key] = new
    }

    private fun remove(n: N<K, V>) {
        data.remove(n.key)
        if (n.prev != null) {
            n.prev!!.next = n.next
        } else {
            tail = n.next
        }
        if (n.next != null) {
            n.next!!.prev = n.prev
        } else {
            head = n.prev
        }
    }
}