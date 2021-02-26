package com.example.annotation
@Target( AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY) // 要在编译时进行一些预处理操作。注解会在class文件中存在
annotation  class ARouter(val path:String,val group:String="")