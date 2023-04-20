# PoC for showing error with Unsafe::getLongVolatile() on Apple Silicon

## Introduction
We are using Agrona and using `UnsafeBuffer::getLongVolatile()` caused an internal error (SIGBUS) on Apple M1 & Apple M2  
using Apple Silicon M1 and Apple Silicon M2 cpu:s.
Further investigation showed that the problem really is with `Unsafe::getLongVolatile()`  
and more specifically, with the `Volatile` semantics. `Unsafe::getLong()` works as expected.  
Some further testing showed that `Unsafe::getDoubleVolatile()` also fails while `getIntVolatile()`  
and `getBoolVolatile()` also works which indicates that it is a 64 bit problem.

We have reported this to Oracle bug reporting (and failed to note down the issue number given)

Addendum: Aleksey Shipilev has pointed out that the `UnsafeBuffer::getLongVolatile()` is unaligned which is correct. Aligning the read  
on a 8 byte boundary works.
<blockquote class="twitter-tweet"><p lang="en" dir="ltr">SIGBUS on unaligned atomic load is the valid result for many architectures, ARM64 included. Modern versions of JVM handle it a bit better, but this is still a programming error, not a JVM bug. If you are using Unsafe, you are opting into handling hardware quirks yourself.</p>&mdash; Aleksey Shipilëv (@shipilev) <a href="https://twitter.com/shipilev/status/1648705945172692994?ref_src=twsrc%5Etfw">April 19, 2023</a></blockquote> <script async src="https://platform.twitter.com/widgets.js" charset="utf-8"></script>  

However, the code in question works fine on the following JDK:s:

- ARM Temurin JDK 11
- x86-64 Temurin JDK 17
- x86-64 GraalVM JDK 17

and fails on:

- ARM Temurin JDK 17
- ARM Zulu JDK 17

The problem is presend in JDK 15 but not in JDK 13 so my take is that something in the JVM changed since 13. 

Erik Svensson, erik.svensson@nasdaq.com

Great help from [Peter Franzen](https://github.com/handmadecode/)
