class N<K, V>(key: K, value: V) {
    var key: K
    var value: V
    var prev: N<K, V>? = null
    var next: N<K, V>? = null

    init {
        assert(key != null)
        assert(value != null)
        this.key = key
        this.value = value
    }
}