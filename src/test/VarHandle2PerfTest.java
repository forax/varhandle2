package test;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle2;
import java.lang.reflect.Field;
import java.util.function.BiFunction;

import sun.misc.Unsafe;

public class VarHandle2PerfTest {
  private String value;
  
  final boolean compareAndSetWithVarHandle(String expected, String newValue) {
    return HANDLE.compareAndSet(this, expected, newValue);
  }
  
  final boolean compareAndSetWithUnsafe(String expected, String newValue) {
    return UNSAFE.compareAndSwapObject(this, OFFSET, expected, newValue);
  }
  
  private static final VarHandle2<VarHandle2PerfTest> HANDLE;
  private static final Unsafe UNSAFE;
  private static final long OFFSET;
  static {
    try {
      Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
      theUnsafe.setAccessible(true);
      UNSAFE = (Unsafe)theUnsafe.get(null);
      Field value = VarHandle2PerfTest.class.getDeclaredField("value");
      OFFSET = UNSAFE.objectFieldOffset(value);
      HANDLE = VarHandle2.findField(MethodHandles.lookup(), VarHandle2PerfTest.class, "value", String.class);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      throw new AssertionError(e);
    }
  }
  
  static void test(BiFunction<String, String, Boolean> fun) {
    for(int i=0; i < 100_000; i++) {
      fun.apply("foo", "bar");
    }
  }
  
  public static void main(String[] args) {
    VarHandle2PerfTest test = new VarHandle2PerfTest();
    test(test::compareAndSetWithVarHandle);
    test(test::compareAndSetWithUnsafe);
  }
}
