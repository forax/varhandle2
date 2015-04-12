package test;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle2;
import java.lang.invoke.WrongMethodTypeException;
import java.util.Objects;
import java.lang.invoke.MethodHandles.Lookup;

public class VarHandle2Test {
  static class TestInt {
    private int i;
    
    int get() {
      return I_HANDLE.getInt(this);
    }
    int getVolatile() {
      return I_HANDLE.getVolatileInt(this);
    }
    int getAcquire() {
      return I_HANDLE.getAcquireInt(this);
    }
    
    int setThenGet(int value) {
      I_HANDLE.setInt(this, value);
      return get();
    }
    int setVolatileThenGetVolatile(int value) {
      I_HANDLE.setVolatileInt(this, value);
      return getVolatile();
    }
    int setReleaseThenGetAcquire(int value) {
      I_HANDLE.setReleaseInt(this, value);
      return getAcquire();
    }
    
    boolean compareAndSet(int expected, int newValue) {
      return I_HANDLE.compareAndSet(this, expected, newValue);
    }
    
    int getAndSetThenGet(int value) {
      I_HANDLE.getAndSet(this, value);
      return get();
    }
    int getAndAddThenGet(int value) {
      I_HANDLE.getAndAdd(this, value);
      return get();
    }
    int addAndGet(int value) {
      return I_HANDLE.addAndGet(this, value);
    }
    
    private static final VarHandle2<TestInt> I_HANDLE;
    static {
      Lookup lookup = MethodHandles.lookup();
      try {
        I_HANDLE = VarHandle2.findField(lookup, TestInt.class, "i", int.class);
      } catch (NoSuchFieldException | IllegalAccessException e) {
        throw new AssertionError(e);
      }
    }
    
    static void test() {
      TestInt test = new TestInt();
      
      assertThat(test.get(), 0);
      assertThat(test.getVolatile(), 0);
      assertThat(test.getAcquire(), 0);
      
      assertThat(test.setThenGet(1), 1);
      assertThat(test.setVolatileThenGetVolatile(2), 2);
      assertThat(test.setReleaseThenGetAcquire(3), 3);
      
      assertThat(test.compareAndSet(0, 1), false);
      assertThat(test.compareAndSet(3, 4), true);
      
      assertThat(test.getAndSetThenGet(5), 5);
      assertThat(test.getAndAddThenGet(5), 10);
      assertThat(test.addAndGet(5), 15);
    }
  }
  
  static class TestLong {
    private long l;
    
    long get() {
      return L_HANDLE.getLong(this);
    }
    long getVolatile() {
      return L_HANDLE.getVolatileLong(this);
    }
    long getAcquire() {
      return L_HANDLE.getAcquireLong(this);
    }
    
    long setThenGet(long value) {
      L_HANDLE.setLong(this, value);
      return get();
    }
    long setVolatileThenGetVolatile(long value) {
      L_HANDLE.setVolatileLong(this, value);
      return getVolatile();
    }
    long setReleaseThenGetAcquire(long value) {
      L_HANDLE.setReleaseLong(this, value);
      return getAcquire();
    }
    
    boolean compareAndSet(long expected, long newValue) {
      return L_HANDLE.compareAndSet(this, expected, newValue);
    }
    
    long getAndSetThenGet(long value) {
      L_HANDLE.getAndSet(this, value);
      return get();
    }
    long getAndAddThenGet(long value) {
      L_HANDLE.getAndAdd(this, value);
      return get();
    }
    long addAndGet(long value) {
      return L_HANDLE.addAndGet(this, value);
    }
    
    private static final VarHandle2<TestLong> L_HANDLE;
    static {
      Lookup lookup = MethodHandles.lookup();
      try {
        L_HANDLE = VarHandle2.findField(lookup, TestLong.class, "l", long.class);
      } catch (NoSuchFieldException | IllegalAccessException e) {
        throw new AssertionError(e);
      }
    }
    
    static void test() {
      TestLong test = new TestLong();
      
      assertThat(test.get(), 0L);
      assertThat(test.getVolatile(), 0L);
      assertThat(test.getAcquire(), 0L);
      
      assertThat(test.setThenGet(1), 1L);
      assertThat(test.setVolatileThenGetVolatile(2), 2L);
      assertThat(test.setReleaseThenGetAcquire(3), 3L);
      
      assertThat(test.compareAndSet(0, 1), false);
      assertThat(test.compareAndSet(3, 4), true);
      
      assertThat(test.getAndSetThenGet(5), 5L);
      assertThat(test.getAndAddThenGet(5), 10L);
      assertThat(test.addAndGet(5), 15L);
    }
  }
  
  static class TestString {
    private String s;
    
    String get() {
      return S_HANDLE.getObject(this);
    }
    String getVolatile() {
      return S_HANDLE.getVolatileObject(this);
    }
    String getAcquire() {
      return S_HANDLE.getAcquireObject(this);
    }
    
    String setThenGet(String value) {
      S_HANDLE.setObject(this, value);
      return get();
    }
    String setVolatileThenGetVolatile(String value) {
      S_HANDLE.setVolatileObject(this, value);
      return getVolatile();
    }
    String setReleaseThenGetAcquire(String value) {
      S_HANDLE.setReleaseObject(this, value);
      return getAcquire();
    }
    
    boolean compareAndSet(String expected, String newValue) {
      return S_HANDLE.compareAndSet(this, expected, newValue);
    }
    
    String getAndSetThenGet(String value) {
      S_HANDLE.getAndSet(this, value);
      return get();
    }
    
    private static final VarHandle2<TestString> S_HANDLE;
    static {
      Lookup lookup = MethodHandles.lookup();
      try {
        S_HANDLE = VarHandle2.findField(lookup, TestString.class, "s", String.class);
      } catch (NoSuchFieldException | IllegalAccessException e) {
        throw new AssertionError(e);
      }
    }
    
    static void test() {
      TestString test = new TestString();
      
      assertThat(test.get(), null);
      assertThat(test.getVolatile(), null);
      assertThat(test.getAcquire(), null);
      
      assertThat(test.setThenGet("foo"), "foo");
      assertThat(test.setVolatileThenGetVolatile("bar"), "bar");
      assertThat(test.setReleaseThenGetAcquire("baz"), "baz");
      
      assertThat(test.compareAndSet("foo", "fuzz"), false);
      assertThat(test.compareAndSet("baz", "fuzz"), true);
      
      assertThat(test.getAndSetThenGet("wizz"), "wizz");
    }
    
    
    void badSet(Object value) {
      S_HANDLE.setObject(this, value);
    }
    void badSetVolatile(Object value) {
      S_HANDLE.setVolatileObject(this, value);
    }
    void badSetRelease(Object value) {
      S_HANDLE.setReleaseObject(this, value);
    }
    boolean badCompareAndSet(Object expected, Object newValue) {
      return S_HANDLE.compareAndSet(this, expected, newValue);
    }
    
    static void test2() {
      TestString test = new TestString();
      
      expectException(ClassCastException.class, () -> test.badSet(new Object()));
      expectException(ClassCastException.class, () -> test.badSetVolatile(new Object()));
      expectException(ClassCastException.class, () -> test.badSetRelease(new Object()));
      
      expectException(ClassCastException.class, () -> test.badCompareAndSet(null, new Object()));
      expectException(ClassCastException.class, () -> test.badCompareAndSet(new Object(), null));
    }
  }
  
  static void assertThat(Object expected, Object value) {
    if (!Objects.equals(expected, value)) {
      throw new AssertionError();
    }
  }
  
  static void expectException(Class<? extends Throwable> type, Runnable runnable) {
    try {
      runnable.run();
      throw new AssertionError("exception " + type.getSimpleName() + " not raised !");
    } catch(Throwable t) {
      if (!(type.isInstance(t))) {
        throw new AssertionError("wrong exception, " + t.getClass().getSimpleName() + " raised instead !", t);
      }
    }
  }
  
  public static void main(String[] args) {
    TestInt.test();
    TestLong.test();
    TestString.test();
    TestString.test2();
    
    System.out.println("done !");
  }
}
