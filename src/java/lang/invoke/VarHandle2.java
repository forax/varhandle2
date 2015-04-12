package java.lang.invoke;

import static java.lang.invoke.MethodHandles.dropArguments;
import static java.lang.invoke.MethodHandles.filterReturnValue;
import static java.lang.invoke.MethodHandles.foldArguments;
import static java.lang.invoke.MethodHandles.identity;
import static java.lang.invoke.MethodHandles.insertArguments;
import static java.lang.invoke.MethodHandles.reflectAs;
import static java.lang.invoke.MethodType.methodType;
import static java.util.function.Function.identity;
import static jdk.internal.org.objectweb.asm.Opcodes.ACC_FINAL;
import static jdk.internal.org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static jdk.internal.org.objectweb.asm.Opcodes.ACC_SUPER;
import static jdk.internal.org.objectweb.asm.Opcodes.V1_7;

import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.EnumMap;
import java.util.function.Function;

import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;
import sun.misc.Unsafe;

/**
 * A var handle is a typed reference to an underlying variable to safely
 * perform atomic operations on.
 */
public class VarHandle2<T> {
  private final Field field;
  private @Stable MethodHandle get;
  private @Stable MethodHandle set;
  private @Stable MethodHandle getVolatile;
  private @Stable MethodHandle setVolatile;
  private @Stable MethodHandle getAcquire;
  private @Stable MethodHandle setRelease;
  private @Stable MethodHandle compareAndSet;
  private @Stable MethodHandle getAndSet;
  private @Stable MethodHandle getAndAdd;
  private @Stable MethodHandle addAndGet;
  
  private VarHandle2(Field field) {
    this.field = field;
  }
  
  
  // Relaxed accessors

  @ForceInline public <V> V getObject(T receiver) {
    MethodHandle get = this.get;
    if (get == null) {
      get = this.get = createMH(AccessorKind.get, Object.class);
    }
    try {
      return (V)get.invokeExact(receiver);
    } catch(Throwable t) {
      throw rethrow(t);
    }
  }
  @ForceInline public int getInt(T receiver) {
    MethodHandle get = this.get;
    if (get == null) {
      get = this.get = createMH(AccessorKind.get, int.class);
    }
    try {
      return (int)get.invokeExact(receiver);
    } catch(Throwable t) {
      throw rethrow(t);
    }
  }
  @ForceInline public long getLong(T receiver) {
    MethodHandle get = this.get;
    if (get == null) {
      get = this.get = createMH(AccessorKind.get, long.class);
    }
    try {
      return (long)get.invokeExact(receiver);
    } catch(Throwable t) {
      throw rethrow(t);
    }
  }
  @ForceInline public double getDouble(T receiver) {
    MethodHandle get = this.get;
    if (get == null) {
      get = this.get = createMH(AccessorKind.get, double.class);
    }
    try {
      return (double)get.invokeExact(receiver);
    } catch(Throwable t) {
      throw rethrow(t);
    }
  }

  
  @ForceInline public void setObject(T receiver, Object value) {
    MethodHandle set = this.setVolatile;
    if (set == null) {
      set = this.setVolatile = createMH(AccessorKind.set, Object.class);
    }
    try {
      set.invokeExact(receiver, value);
    } catch(Throwable t) {
      throw rethrow(t);
    }
  }
  @ForceInline public void setInt(T receiver, int value) {
    MethodHandle set = this.setVolatile;
    if (set == null) {
      set = this.setVolatile = createMH(AccessorKind.set, int.class);
    }
    try {
      set.invokeExact(receiver, value);
    } catch(Throwable t) {
      throw rethrow(t);
    }
  }
  @ForceInline public void setLong(T receiver, long value) {
    MethodHandle set = this.setVolatile;
    if (set == null) {
      set = this.setVolatile = createMH(AccessorKind.set, long.class);
    }
    try {
      set.invokeExact(receiver, value);
    } catch(Throwable t) {
      throw rethrow(t);
    }
  }
  @ForceInline public void setDouble(T receiver, double value) {
    MethodHandle set = this.setVolatile;
    if (set == null) {
      set = this.setVolatile = createMH(AccessorKind.set, double.class);
    }
    try {
      set.invokeExact(receiver, value);
    } catch(Throwable t) {
      throw rethrow(t);
    }
  }


  // Volatile accessors

  @ForceInline public <V> V getVolatileObject(T receiver) {
    MethodHandle getVolatile = this.getVolatile;
    if (getVolatile == null) {
      getVolatile = this.getVolatile = createMH(AccessorKind.getVolatile, Object.class);
    }
    try {
      return (V)getVolatile.invokeExact(receiver);
    } catch(Throwable t) {
      throw rethrow(t);
    }
  }
  @ForceInline public int getVolatileInt(T receiver) {
    MethodHandle getVolatile = this.getVolatile;
    if (getVolatile == null) {
      getVolatile = this.getVolatile = createMH(AccessorKind.getVolatile, int.class);
    }
    try {
      return (int)getVolatile.invokeExact(receiver);
    } catch(Throwable t) {
      throw rethrow(t);
    }
  }
  @ForceInline public long getVolatileLong(T receiver) {
    MethodHandle getVolatile = this.getVolatile;
    if (getVolatile == null) {
      getVolatile = this.getVolatile = createMH(AccessorKind.getVolatile, long.class);
    }
    try {
      return (long)getVolatile.invokeExact(receiver);
    } catch(Throwable t) {
      throw rethrow(t);
    }
  }
  @ForceInline public double getVolatileDouble(T receiver) {
    MethodHandle getVolatile = this.getVolatile;
    if (getVolatile == null) {
      getVolatile = this.getVolatile = createMH(AccessorKind.getVolatile, double.class);
    }
    try {
      return (double)getVolatile.invokeExact(receiver);
    } catch(Throwable t) {
      throw rethrow(t);
    }
  }

  @ForceInline public void setVolatileObject(T receiver, Object value) {
    MethodHandle setVolatile = this.setVolatile;
    if (setVolatile == null) {
      setVolatile = this.setVolatile = createMH(AccessorKind.setVolatile, Object.class);
    }
    try {
      setVolatile.invokeExact(receiver, value);
    } catch(Throwable t) {
      throw rethrow(t);
    }
  }
  @ForceInline public void setVolatileInt(T receiver, int value) {
    MethodHandle setVolatile = this.setVolatile;
    if (setVolatile == null) {
      setVolatile = this.setVolatile = createMH(AccessorKind.setVolatile, int.class);
    }
    try {
      setVolatile.invokeExact(receiver, value);
    } catch(Throwable t) {
      throw rethrow(t);
    }
  }
  @ForceInline public void setVolatileLong(T receiver, long value) {
    MethodHandle setVolatile = this.setVolatile;
    if (setVolatile == null) {
      setVolatile = this.setVolatile = createMH(AccessorKind.setVolatile, long.class);
    }
    try {
      setVolatile.invokeExact(receiver, value);
    } catch(Throwable t) {
      throw rethrow(t);
    }
  }
  @ForceInline public void setVolatileDouble(T receiver, double value) {
    MethodHandle setVolatile = this.setVolatile;
    if (setVolatile == null) {
      setVolatile = this.setVolatile = createMH(AccessorKind.setVolatile, double.class);
    }
    try {
      setVolatile.invokeExact(receiver, value);
    } catch(Throwable t) {
      throw rethrow(t);
    }
  }


  // Lazy accessors

  @ForceInline public <V> V getAcquireObject(T receiver) {
    MethodHandle getAcquire = this.getAcquire;
    if (getAcquire == null) {
      getAcquire = this.getAcquire = createMH(AccessorKind.getAcquire, Object.class);
    }
    try {
      return (V)getAcquire.invokeExact(receiver);
    } catch(Throwable t) {
      throw rethrow(t);
    }
  }
  @ForceInline public int getAcquireInt(T receiver) {
    MethodHandle getAcquire = this.getAcquire;
    if (getAcquire == null) {
      getAcquire = this.getAcquire = createMH(AccessorKind.getAcquire, int.class);
    }
    try {
      return (int)getAcquire.invokeExact(receiver);
    } catch(Throwable t) {
      throw rethrow(t);
    }
  }
  @ForceInline public long getAcquireLong(T receiver) {
    MethodHandle getAcquire = this.getAcquire;
    if (getAcquire == null) {
      getAcquire = this.getAcquire = createMH(AccessorKind.getAcquire, long.class);
    }
    try {
      return (long)getAcquire.invokeExact(receiver);
    } catch(Throwable t) {
      throw rethrow(t);
    }
  }
  @ForceInline public double getAcquireDouble(T receiver) {
    MethodHandle getAcquire = this.getAcquire;
    if (getAcquire == null) {
      getAcquire = this.getAcquire = createMH(AccessorKind.getAcquire, double.class);
    }
    try {
      return (double)getAcquire.invokeExact(receiver);
    } catch(Throwable t) {
      throw rethrow(t);
    }
  }

  @ForceInline public void setReleaseObject(T receiver, Object value) {
    MethodHandle setRelease = this.setRelease;
    if (setRelease == null) {
      setRelease = this.setRelease = createMH(AccessorKind.setRelease, Object.class);
    }
    try {
      setRelease.invokeExact(receiver, value);
    } catch(Throwable t) {
      throw rethrow(t);
    }
  }
  @ForceInline public void setReleaseInt(T receiver, int value) {
    MethodHandle setRelease = this.setRelease;
    if (setRelease == null) {
      setRelease = this.setRelease = createMH(AccessorKind.setRelease, int.class);
    }
    try {
      setRelease.invokeExact(receiver, value);
    } catch(Throwable t) {
      throw rethrow(t);
    }
  }
  @ForceInline public void setReleaseLong(T receiver, long value) {
    MethodHandle setRelease = this.setRelease;
    if (setRelease == null) {
      setRelease = this.setRelease = createMH(AccessorKind.setRelease, long.class);
    }
    try {
      setRelease.invokeExact(receiver, value);
    } catch(Throwable t) {
      throw rethrow(t);
    }
  }
  @ForceInline public void setReleaseDouble(T receiver, double value) {
    MethodHandle setRelease = this.setRelease;
    if (setRelease == null) {
      setRelease = this.setRelease = createMH(AccessorKind.setRelease, double.class);
    }
    try {
      setRelease.invokeExact(receiver, value);
    } catch(Throwable t) {
      throw rethrow(t);
    }
  }


  // Compare and set accessor

  @ForceInline public <V> boolean compareAndSet(T receiver, V expected, V newValue) {
    MethodHandle compareAndSet = this.compareAndSet;
    if (compareAndSet == null) {
      compareAndSet = this.compareAndSet = createMH(AccessorKind.compareAndSet, Object.class);
    }
    try {
      return (boolean)compareAndSet.invokeExact(receiver, (Object)expected, (Object)newValue);
    } catch(Throwable t) {
      throw rethrow(t);
    }
  }
  
  @ForceInline public boolean compareAndSet(T receiver, int expected, int newValue) {
    MethodHandle compareAndSet = this.compareAndSet;
    if (compareAndSet == null) {
      compareAndSet = this.compareAndSet = createMH(AccessorKind.compareAndSet, int.class);
    }
    try {
      return (boolean)compareAndSet.invokeExact(receiver, expected, newValue);
    } catch(Throwable t) {
      throw rethrow(t);
    }
  }
  @ForceInline public boolean compareAndSet(T receiver, long expected, long newValue) {
    MethodHandle compareAndSet = this.compareAndSet;
    if (compareAndSet == null) {
      compareAndSet = this.compareAndSet = createMH(AccessorKind.compareAndSet, long.class);
    }
    try {
      return (boolean)compareAndSet.invokeExact(receiver, expected, newValue);
    } catch(Throwable t) {
      throw rethrow(t);
    }
  }
  @ForceInline public boolean compareAndSet(T receiver, double expected, double newValue) {
    MethodHandle compareAndSet = this.compareAndSet;
    if (compareAndSet == null) {
      compareAndSet = this.compareAndSet = createMH(AccessorKind.compareAndSet, double.class);
    }
    try {
      return (boolean)compareAndSet.invokeExact(receiver, expected, newValue);
    } catch(Throwable t) {
      throw rethrow(t);
    }
  }

  @ForceInline public <V> V getAndSet(T receiver, V value) {
    MethodHandle getAndSet = this.getAndSet;
    if (getAndSet == null) {
      getAndSet = this.getAndSet = createMH(AccessorKind.getAndSet, Object.class);
    }
    try {
      return (V)getAndSet.invokeExact(receiver, value);
    } catch(Throwable t) {
      throw rethrow(t);
    }
  }
  @ForceInline public int getAndSet(T receiver, int value) {
    MethodHandle getAndSet = this.getAndSet;
    if (getAndSet == null) {
      getAndSet = this.getAndSet = createMH(AccessorKind.getAndSet, int.class);
    }
    try {
      return (int)getAndSet.invokeExact(receiver, value);
    } catch(Throwable t) {
      throw rethrow(t);
    }
  }
  @ForceInline public long getAndSet(T receiver, long value) {
    MethodHandle getAndSet = this.getAndSet;
    if (getAndSet == null) {
      getAndSet = this.getAndSet = createMH(AccessorKind.getAndSet, long.class);
    }
    try {
      return (long)getAndSet.invokeExact(receiver, value);
    } catch(Throwable t) {
      throw rethrow(t);
    }
  }
  @ForceInline public double getAndSet(T receiver, double value) {
    MethodHandle getAndSet = this.getAndSet;
    if (getAndSet == null) {
      getAndSet = this.getAndSet = createMH(AccessorKind.getAndSet, double.class);
    }
    try {
      return (double)getAndSet.invokeExact(receiver, value);
    } catch(Throwable t) {
      throw rethrow(t);
    }
  }


  // Primitive adders

  @ForceInline public int getAndAdd(T receiver, int value) {
    MethodHandle getAndAdd = this.getAndAdd;
    if (getAndAdd == null) {
      getAndAdd = this.getAndAdd = createMH(AccessorKind.getAndAdd, int.class);
    }
    try {
      return (int)getAndAdd.invokeExact(receiver, value);
    } catch(Throwable t) {
      throw rethrow(t);
    }
  }
  @ForceInline public long getAndAdd(T receiver, long value) {
    MethodHandle getAndAdd = this.getAndAdd;
    if (getAndAdd == null) {
      getAndAdd = this.getAndAdd = createMH(AccessorKind.getAndAdd, long.class);
    }
    try {
      return (long)getAndAdd.invokeExact(receiver, value);
    } catch(Throwable t) {
      throw rethrow(t);
    }
  }
  @ForceInline public double getAndAdd(T receiver, double value) {
    MethodHandle getAndAdd = this.getAndAdd;
    if (getAndAdd == null) {
      getAndAdd = this.getAndAdd = createMH(AccessorKind.getAndAdd, double.class);
    }
    try {
      return (double)getAndAdd.invokeExact(receiver, value);
    } catch(Throwable t) {
      throw rethrow(t);
    }
  }

  @ForceInline public int addAndGet(T receiver, int value) {
    MethodHandle addAndGet = this.addAndGet;
    if (addAndGet == null) {
      addAndGet = this.addAndGet = createMH(AccessorKind.addAndGet, int.class);
    }
    try {
      return (int)addAndGet.invokeExact(receiver, value);
    } catch(Throwable t) {
      throw rethrow(t);
    }
  }
  @ForceInline public long addAndGet(T receiver, long value) {
    MethodHandle addAndGet = this.addAndGet;
    if (addAndGet == null) {
      addAndGet = this.addAndGet = createMH(AccessorKind.addAndGet, long.class);
    }
    try {
      return (long)addAndGet.invokeExact(receiver, value);
    } catch(Throwable t) {
      throw rethrow(t);
    }
  }
  @ForceInline public double addAndGet(T receiver, double value) {
    MethodHandle addAndGet = this.addAndGet;
    if (addAndGet == null) {
      addAndGet = this.addAndGet = createMH(AccessorKind.addAndGet, double.class);
    }
    try {
      return (double)addAndGet.invokeExact(receiver, value);
    } catch(Throwable t) {
      throw rethrow(t);
    }
  }
  
  
  
  // ---

  private Error rethrow(Throwable t) {
    if (t instanceof RuntimeException) {
      throw (RuntimeException)t;
    }
    if (t instanceof Error) {
      throw (Error)t;
    }
    throw new AssertionError(t);
  }
  
  @DontInline
  private MethodHandle createMH(AccessorKind kind, Class<?> declaredType) {
    // check if the right overload is used !
    Class<?> fieldType = field.getType();
    if ((fieldType.isPrimitive() && fieldType != declaredType) ||
        (!fieldType.isPrimitive() && declaredType != Object.class)) {
      throw new IllegalStateException("field declared type and method type mismatch " + fieldType.getSimpleName() + " " + declaredType.getSimpleName());
    }

    return STRATEGY_MAP.get(kind).create(field, declaredType);
  }
  
  /**
   * Produces a var handle giving access to a field.
   *
   * Access checking is performed immediately on behalf of the lookup
   * class.
   *
   * @param refc the receiver class declaring the field
   * @param name the field's name
   * @param type the field's type
   * @return a var handle which can access values from the field
   * @throws NoSuchFieldException if the field does not exist
   * @throws IllegalAccessException if the field is not visible from the lookup object
   * @throws NullPointerException if any argument is null
   */
  public static <T> VarHandle2<T> findField(Lookup lookup, Class<T> refc, String name, Class<?> type) throws NoSuchFieldException, IllegalAccessException {
    MethodHandle getter = lookup.findGetter(refc, name, type);
    Field field = reflectAs(Field.class, getter);

    if (Modifier.isStatic(field.getModifiers())) {
      throw new UnsupportedOperationException("NYI");
    }

    return new VarHandle2<>(field);
  }

  enum AccessorKind {
    get, set, getVolatile, setVolatile, getAcquire, setRelease,
    compareAndSet, getAndSet, getAndAdd, addAndGet
  }

  private static MethodHandle findInUnsafe(Unsafe unsafe, Lookup lookup, String name, MethodType methodType, Function<MethodHandle, MethodHandle> postOp) {
    // assert methodType.parameterType(0) == Object.class;
    MethodHandle target;
    try {
      target = lookup.findVirtual(Unsafe.class, name, methodType);
    } catch (NoSuchMethodException | IllegalAccessException e) {
      throw new AssertionError(e);
    }
    return foldArguments(postOp.apply(target.bindTo(unsafe)), NULLCHECK);
  }

  private static MethodHandle[] findOverloadsInUnsafe(Unsafe unsafe, Lookup lookup, String nameObject, String nameInt, String nameLong,
      Function<Class<?>, MethodType> types, Function<MethodHandle, MethodHandle> postOp) {
    MethodHandle mhObject = (nameObject == null)? null: findInUnsafe(unsafe, lookup, nameObject, types.apply(Object.class), postOp);
    MethodHandle mhInt = findInUnsafe(unsafe, lookup, nameInt, types.apply(int.class), postOp);
    MethodHandle mhLong = findInUnsafe(unsafe, lookup, nameLong, types.apply(long.class), postOp);

    // FIXME use parameter/return value transformation for double !
    return new MethodHandle[] { mhObject, mhInt, mhLong, null };
  }

  private static int getOverloadOffset(Class<?> type) {
    if (type == int.class) {
      return 1;
    }
    if (type == long.class) {
      return 2;
    }
    if (type == double.class) {
      return 3;
    }
    return 0;
  }

  interface MHFactory {
    MethodHandle create(Field field, Class<?> fieldSigType);
  }

  private static MHFactory factoryFor(Unsafe unsafe, Lookup lookup, String nameObject, String nameInt, String nameLong,
      Function<Class<?>, MethodType> sigFactory, Function<MethodHandle, MethodHandle> postOp) {
    MethodHandle[] mhs = findOverloadsInUnsafe(unsafe, lookup, nameObject, nameInt, nameLong, sigFactory, postOp);
    return (field, declaredType) -> {
      long offset = unsafe.objectFieldOffset(field);
      MethodHandle overload = mhs[getOverloadOffset(declaredType)];
      //MethodHandle target = overload.asType(sigFactory.apply(field.getType()).changeParameterType(0, field.getDeclaringClass()));
      MethodHandle target = checkTypes(overload, sigFactory.apply(field.getType()).changeParameterType(0, field.getDeclaringClass()));
      MethodHandle erased = target.asType(sigFactory.apply(declaredType));
      return insertArguments(erased, 1, offset);
    };
  }

  // <HACK>
  private static MethodHandle checkTypes(MethodHandle mh, MethodType type) {
    MethodType mhType = mh.type();
    int length = mhType.parameterCount();
    MethodHandle[] filters = new MethodHandle[length];
    for(int i = 0; i < length; i++) {
      Class<?> checkClass = type.parameterType(i);
      if (mhType.parameterType(i) != checkClass) {
        filters[i] = CHEAP_CHECK_CASTER.get(checkClass);
      }
    }
    return MethodHandles.filterArguments(mh, 0, filters);
  }
  
  private static final ClassValue<MethodHandle> CHEAP_CHECK_CASTER = new ClassValue<MethodHandle>() {
    @Override
    protected MethodHandle computeValue(Class<?> type) {
      ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES|ClassWriter.COMPUTE_MAXS);
      //int index = writer.newConst("java/lang/invoke/Fake");
      writer.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC|Opcodes.ACC_SUPER|Opcodes.ACC_FINAL, "java.lang.invoke.CheapCheckCaster", null, "java/lang/Object", null);
      MethodVisitor mv = writer.visitMethod(Opcodes.ACC_PUBLIC|Opcodes.ACC_STATIC, "checkcast", "(Ljava/lang/Object;)Ljava/lang/Object;", null, null);
      mv.visitCode();
      mv.visitVarInsn(Opcodes.ALOAD, 0);
      mv.visitTypeInsn(Opcodes.CHECKCAST, /*"java/lang/invoke/Fake"*/ type.getName().replace('.', '/'));
      mv.visitInsn(Opcodes.ARETURN);
      mv.visitMaxs(-1, -1);
      mv.visitEnd();
      int constantPoolLength = writer.newConst("<<LAST_CONSTANT>>");
      writer.visitEnd();
      
      Object[] patches = new Object[constantPoolLength];
      //patches[index] = type.getName().replace('.', '/');
      Class<?> clazz = Unsafe.getUnsafe().defineAnonymousClass(type, writer.toByteArray(), patches);
      try {
        return Lookup.IMPL_LOOKUP.findStatic(clazz, "checkcast", methodType(Object.class, Object.class));
      } catch (NoSuchMethodException | IllegalAccessException e) {
        throw new AssertionError(e);
      }
    }
  };
  // </HACK>
  
  private static final MethodHandle NULLCHECK, LOADFENCE;
  static final EnumMap<AccessorKind, MHFactory> STRATEGY_MAP;
  static {
    Lookup lookup = Lookup.IMPL_LOOKUP;

    MethodHandle[] sums;
    try {
      NULLCHECK = Lookup.IMPL_LOOKUP.findVirtual(Object.class, "getClass", methodType(Class.class)).asType(methodType(void.class, Object.class));
      LOADFENCE = Lookup.IMPL_LOOKUP.findVirtual(Unsafe.class, "loadFence", methodType(void.class));
      sums = new MethodHandle[] { null,
          Lookup.IMPL_LOOKUP.findStatic(Integer.class, "sum", methodType(int.class, int.class, int.class)),
          Lookup.IMPL_LOOKUP.findStatic(Long.class, "sum", methodType(long.class, long.class, long.class)),
          Lookup.IMPL_LOOKUP.findStatic(Double.class, "sum", methodType(double.class, double.class, double.class))
      };
    } catch (NoSuchMethodException | IllegalAccessException e) {
      throw new AssertionError(e);
    }

    Unsafe unsafe = Unsafe.getUnsafe();
    EnumMap<AccessorKind, MHFactory> stategyMap = new EnumMap<>(AccessorKind.class);
    stategyMap.put(AccessorKind.get, factoryFor(unsafe, lookup, "getObject", "getInt", "getLong",
        type -> methodType(type, Object.class, long.class), identity()));
    stategyMap.put(AccessorKind.set, factoryFor(unsafe, lookup, "putObject", "putInt", "putLong",
        type -> methodType(void.class, Object.class, long.class, type), identity()));
    
    stategyMap.put(AccessorKind.getVolatile, factoryFor(unsafe, lookup, "getObjectVolatile", "getIntVolatile", "getLongVolatile",
        type -> methodType(type, Object.class, long.class), identity()));
    stategyMap.put(AccessorKind.setVolatile, factoryFor(unsafe, lookup, "putObjectVolatile", "putIntVolatile", "putLongVolatile",
        type -> methodType(void.class, Object.class, long.class, type), identity()));

    MethodHandle loadFence = LOADFENCE.bindTo(unsafe);
    // TODO, share method handles already created when processing AccessorKind.getVolatile
    stategyMap.put(AccessorKind.getAcquire, factoryFor(unsafe, lookup, "getObjectVolatile", "getIntVolatile", "getLongVolatile",
        type -> methodType(type, Object.class, long.class),
        mh -> filterReturnValue(mh, foldArguments(identity(mh.type().returnType()), loadFence))));
    stategyMap.put(AccessorKind.setRelease, factoryFor(unsafe, lookup, "putOrderedObject", "putOrderedInt", "putOrderedLong",
        type -> methodType(void.class, Object.class, long.class, type), identity()));

    stategyMap.put(AccessorKind.compareAndSet, factoryFor(unsafe, lookup, "compareAndSwapObject", "compareAndSwapInt", "compareAndSwapLong",
        type -> methodType(boolean.class, Object.class, long.class, type, type), identity()));
    stategyMap.put(AccessorKind.getAndSet, factoryFor(unsafe, lookup, "getAndSetObject", "getAndSetInt", "getAndSetLong",
        type -> methodType(type, Object.class, long.class, type), identity()));

    stategyMap.put(AccessorKind.getAndAdd, factoryFor(unsafe, lookup, null, "getAndAddInt", "getAndAddLong",
        type -> methodType(type, Object.class, long.class, type), identity()));
    // TODO, share method handles already created when processing AccessorKind.getAndAddThrowable
    stategyMap.put(AccessorKind.addAndGet, factoryFor(unsafe, lookup, null, "getAndAddInt", "getAndAddLong",
        type -> methodType(type, Object.class, long.class, type),
        mh -> foldArguments(dropArguments(sums[getOverloadOffset(mh.type().returnType())], 1, Object.class, long.class), mh)));

    STRATEGY_MAP = stategyMap;
  }
}