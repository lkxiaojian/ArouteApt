package com.example.annotation

import java.lang.Exception
import java.lang.IllegalArgumentException
import javax.lang.model.element.Element
import javax.lang.model.util.Elements

class ARouterBean private constructor(builder: Builder) {
    enum class Type {
        ACTIVITY
    }

    private var path = builder.path
    private var group = builder.group
    private lateinit var clazz:Class<Any>
    private lateinit var type: Type
    private  var element: Element= builder.element
//    private constructor(
//        path: String,
//        group: String,
//        clazz: Class<Any>,
//        type: Type,
//        element: Element
//    ) {
//        this.path=path
//        this.group=group
//        this.clazz=clazz
//        this.type=type
//        this.element=element
//    }
   companion object inner class Builder {
        lateinit var path: String
        lateinit var group: String
        lateinit var element: Element
        fun setPath(_path: String): Builder {
            this.path = _path
            return this
        }

        fun setGroup(_group: String): Builder {
            this.group = _group
            return this
        }

        fun setElements(_element: Element): Builder {
            this.element = _element
            return this
        }

        fun build(): ARouterBean? {
            if (path.isNullOrEmpty()) {
                throw IllegalArgumentException("path is null or empty")
            }
            return ARouterBean(this)
        }
    }

}