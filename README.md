# varhandle2
A safe yet efficient implementation of atomic operations for the JVM.

This API allows to use unsafe operations (a la sun.misc.Unsafe) is a safe way
and yet be as efficient as using sun.misc.Unsafe.

The main idea is to basically let the JIT to generate exactly (or mostly) the same code
thus it will be as efficient.

By example, here is the code generated for a compareAndSet (CAS) using sun.misc.Unsafe:
```
Code:
[Entry Point]
[Constants]
  # {method} {0x00007f79679e1f40} 'compareAndSetWithUnsafe' '(Ljava/lang/String;Ljava/lang/String;)Z' in 'test/VarHandle2PerfTest'
  # this:     rsi:rsi   = 'test/VarHandle2PerfTest'
  # parm0:    rdx:rdx   = 'java/lang/String'
  # parm1:    rcx:rcx   = 'java/lang/String'
  #           [sp+0x20]  (sp of caller)
  0x00007f796d08bca0: mov    0x8(%rsi),%r10d
  0x00007f796d08bca4: shl    $0x3,%r10
  0x00007f796d08bca8: cmp    %r10,%rax
  0x00007f796d08bcab: jne    0x00007f796d045b60  ;   {runtime_call}
  0x00007f796d08bcb1: xchg   %ax,%ax
  0x00007f796d08bcb4: nopl   0x0(%rax,%rax,1)
  0x00007f796d08bcbc: xchg   %ax,%ax
[Verified Entry Point]
  0x00007f796d08bcc0: sub    $0x18,%rsp
  0x00007f796d08bcc7: mov    %rbp,0x10(%rsp)
  0x00007f796d08bccc: add    $0xc,%rsi
  0x00007f796d08bcd0: mov    %rcx,%r10
  0x00007f796d08bcd3: shr    $0x3,%r10
  0x00007f796d08bcd7: mov    %rsi,%r11
  0x00007f796d08bcda: mov    %rdx,%rax
  0x00007f796d08bcdd: shr    $0x3,%rax
  0x00007f796d08bce1: lock cmpxchg %r10d,(%rsi)
  0x00007f796d08bce6: sete   %r8b
  0x00007f796d08bcea: movzbl %r8b,%r8d
  0x00007f796d08bcee: shr    $0x9,%r11
  0x00007f796d08bcf2: mov    $0x7f7971322000,%r10
  0x00007f796d08bcfc: mov    %r12b,(%r10,%r11,1)  ;*invokevirtual compareAndSwapObject
                                                ; - test.VarHandle2PerfTest::compareAndSetWithUnsafe@9 (line 18)

  0x00007f796d08bd00: mov    %r8d,%eax
  0x00007f796d08bd03: add    $0x10,%rsp
  0x00007f796d08bd07: pop    %rbp
  0x00007f796d08bd08: test   %eax,0xa9a02f2(%rip)        # 0x00007f7977a2c000
                                                ;   {poll_return}                  
```

And the generated code for a CAS using the VarHandle2 API:
```
Code:
[Entry Point]
[Constants]
  # {method} {0x00007f76a4dc5af8} 'compareAndSetWithVarHandle' '(Ljava/lang/String;Ljava/lang/String;)Z' in 'test/VarHandle2PerfTest'
  # this:     rsi:rsi   = 'test/VarHandle2PerfTest'
  # parm0:    rdx:rdx   = 'java/lang/String'
  # parm1:    rcx:rcx   = 'java/lang/String'
  #           [sp+0x20]  (sp of caller)
  0x00007f769d096360: mov    0x8(%rsi),%r10d
  0x00007f769d096364: shl    $0x3,%r10
  0x00007f769d096368: cmp    %r10,%rax
  0x00007f769d09636b: jne    0x00007f769d0460e0  ;   {runtime_call}
  0x00007f769d096371: xchg   %ax,%ax
  0x00007f769d096374: nopl   0x0(%rax,%rax,1)
  0x00007f769d09637c: xchg   %ax,%ax
[Verified Entry Point]
  0x00007f769d096380: sub    $0x18,%rsp
  0x00007f769d096387: mov    %rbp,0x10(%rsp)
  0x00007f769d09638c: add    $0xc,%rsi
  0x00007f769d096390: mov    %rcx,%r10
  0x00007f769d096393: shr    $0x3,%r10
  0x00007f769d096397: mov    %rsi,%r11
  0x00007f769d09639a: mov    %rdx,%rax
  0x00007f769d09639d: shr    $0x3,%rax
  0x00007f769d0963a1: lock cmpxchg %r10d,(%rsi)
  0x00007f769d0963a6: sete   %r8b
  0x00007f769d0963aa: movzbl %r8b,%r8d
  0x00007f769d0963ae: shr    $0x9,%r11
  0x00007f769d0963b2: mov    $0x7f76a2ea5000,%r10
  0x00007f769d0963bc: mov    %r12b,(%r10,%r11,1)  ;*invokestatic linkToSpecial
                                                ; - java.lang.invoke.LambdaForm$DMH/804611486::invokeSpecial_LLJLL_I@18
                                                ; - java.lang.invoke.LambdaForm$BMH/100555887::reinvoke@111
                                                ; - java.lang.invoke.LambdaForm$MH/611437735::invokeExact_MT@19
                                                ; - java.lang.invoke.VarHandle2::compareAndSet@32 (line 337)
                                                ; - test.VarHandle2PerfTest::compareAndSetWithVarHandle@6 (line 14)

  0x00007f769d0963c0: mov    %r8d,%eax
  0x00007f769d0963c3: add    $0x10,%rsp
  0x00007f769d0963c7: pop    %rbp
  0x00007f769d0963c8: test   %eax,0xc4ebc32(%rip)        # 0x00007f76a9582000
                                                ;   {poll_return}
```

as you can see the code is the same :)
