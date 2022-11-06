import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.AssertionError

internal class LRUCacheTest {

    @Test
    fun testEmptyCache() {
        val cache = LRUCacheImpl<String, Int>(1)
        assertEquals(cache.size(), 0)
        assertTrue(cache[""] == null)
    }

    @Test
    fun testSimplePut() {
        val cache = LRUCacheImpl<String, Int>(2)
        cache.put("1", 1)
        cache.put("2", 2)
        cache.put("3", 3)
        assertTrue(cache["1"] == null)
        assertEquals(cache["2"], 2)
        assertEquals(cache["3"], 3)
    }

    @Test
    fun testPutExisting() {
        val cache = LRUCacheImpl<String, Int>(2)
        cache.put("1", 1)
        cache.put("1", -1)
        assertEquals(cache["1"], -1)
    }

    @Test
    fun testPutFrontPush() {
        val cache = LRUCacheImpl<String, Int>(2)
        cache.put("1", 1)
        cache.put("2", 2)
        cache.put("1", -1)
        cache.put("3", 3)
        assertEquals(cache["1"], -1)
        assertEquals(cache["2"], null)
        assertEquals(cache["3"], 3)
    }

    @Test
    fun testSimpleGet() {
        val cache = LRUCacheImpl<String, Int>(2)
        cache.put("1", 1)
        cache.put("2", 2)
        assertEquals(cache["1"], 1)
        assertEquals(cache["2"], 2)
    }

    @Test
    fun testGetFrontPush() {
        val cache = LRUCacheImpl<String, Int>(2)
        cache.put("1", 1)
        cache.put("2", 2)
        val toFront = cache["1"]
        cache.put("3", 3)
        assertEquals(toFront, 1)
        assertEquals(cache["1"], 1)
        assertEquals(cache["2"], null)
        assertEquals(cache["3"], 3)
    }

    @Test
    fun testSimpleSize() {
        val cache = LRUCacheImpl<String, Int>(2)
        cache.put("1", 1)
        assertEquals(cache.size(), 1)
        cache.put("1", -1)
        assertEquals(cache.size(), 1)
        cache.put("2", 2)
        assertEquals(cache.size(), 2)
        cache.put("3", 3)
        assertEquals(cache.size(), 2)
    }

    @Test
    fun testCapError() {
        assertThrows<AssertionError> { LRUCacheImpl<String?, Int?>(0) }
        assertThrows<AssertionError> { LRUCacheImpl<String?, Int?>(-1) }
    }

    @Test
    fun testNullErrorPut() {
        val cache = LRUCacheImpl<String?, Int?>(1)
        assertThrows<AssertionError> { cache.put(null, 1) }
        assertThrows<AssertionError> { cache.put("1", null) }
    }

    @Test
    fun testNullErrorGet() {
        val cache = LRUCacheImpl<String?, Int>(1)
        assertThrows<AssertionError> { cache[null] }
    }
}