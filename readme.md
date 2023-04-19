# PoC for showing error with Unsafe::getLongVolatile()

## Introduction
We are using Agrona and using `UnsafeBuffer::getLongVolatile()` caused an internal error.
Further investigation showed that the problem really is with `Unsafe::getLongVolatile()`  
and more specifically, with the `Volatile` semantics. `Unsafe::getLong()` works as expected.  
Some further testing showed that `Unsafe::getDoubleVolatile()` also fails while `getIntVolatile()`  
and `getBoolVolatile()` also works which indicates that it is a 64 bit problem.

We have reported this to Oracle bug reporting (and failed to note down the issue number given)