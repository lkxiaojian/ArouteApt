package com.example.compiler

import com.example.annotation.ARouter
import com.google.auto.service.AutoService
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeSpec
import java.io.IOException
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.Modifier
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements
import javax.lang.model.util.Types
import javax.tools.Diagnostic


@AutoService(Processor::class)
@SupportedAnnotationTypes("com.example.annotation.ARouter")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedOptions("content")
class ARouterProcessor : AbstractProcessor() {
    private var elementUtils: Elements? = null
    private var typeUtils: Types? = null
    private var messager: Messager? = null
    private var filer: Filer? = null

    //
    //初始化
    override fun init(processingEnv: ProcessingEnvironment?) {
        super.init(processingEnv)
        elementUtils = processingEnv?.elementUtils
        typeUtils = processingEnv?.typeUtils
        messager = processingEnv?.messager
        val content = processingEnv?.options?.get("content")
        filer = processingEnv?.filer
        messager?.printMessage(Diagnostic.Kind.NOTE, content)

    }


//    override fun getSupportedAnnotationTypes(): MutableSet<String> {
//        return mutableSetOf(ARouter::class.java.name)
//    }
//    override fun getSupportedSourceVersion(): SourceVersion {
//        return SourceVersion.RELEASE_8
//    }

    override fun process(
        annotations: MutableSet<out TypeElement>?,
        roundEnv: RoundEnvironment?
    ): Boolean {
        if (annotations.isNullOrEmpty()) {
            return false
        }
        val elementsAnnotatedWith = roundEnv?.getElementsAnnotatedWith(ARouter::class.java)
        elementsAnnotatedWith?.forEach {


            //一行一行的写入
//            // 通过类节点获取包节点（全路径：com.example.xxx）
//            val packageName = elementUtils?.getPackageOf(it)?.qualifiedName
//            // 获取简单类名
//            val className = it.simpleName
//            messager?.printMessage(Diagnostic.Kind.NOTE, "被注解的类有：$className \r\n")
//            // 最终想生成的类文件名
//            val finalClassName = "$className$\$ARouter"
//            try {
//                val createSourceFile = filer?.createSourceFile("$packageName.$finalClassName")
//                val writer = createSourceFile?.openWriter()
//                // 设置包名
//                writer?.write("package $packageName;\n")
//                writer?.write("public class $finalClassName {\n")
//                writer?.write("public static Class<?> findTargetClass(String path) {\n")
//                // 获取类之上@ARouter注解的path值
//                val aRouter: ARouter = it.getAnnotation(ARouter::class.java)
//                writer?.write(
//                    """
//                    if (path.equals("${aRouter.path}")) {
//
//                    """.trimIndent()
//                )
//                writer?.write("return $className.class;\n}\n")
//                writer?.write("return null;\n")
//                writer?.write("}\n}")
//                // 最后结束别忘了
//                writer?.close()
//            } catch (e: IOException) {
//                messager?.printMessage(Diagnostic.Kind.NOTE, "error：${e.message}")
//                e.printStackTrace()
//            }

            //使用javaPot
            try {
                // 通过类节点获取包节点（全路径：com.example.xxx）
                val packageName = elementUtils?.getPackageOf(it)?.qualifiedName.toString()
                // 获取简单类名
                val className = it.simpleName.toString()
                val arouter = it.getAnnotation(ARouter::class.java)
                val finalClassName = "$className$\$ARouter"


//            package com.example.aroute;
//            public class MainActivity$$ARouter {
//            public static Class<?> findTargetClass(String path) {
//                if (path.equals("main/MainActivity")) {
//                    return MainActivity.class;
//        }
//            return null;
//        }
//        }

                messager?.printMessage(Diagnostic.Kind.NOTE, "被注解的类有：$className \r\n")
                val method = MethodSpec.methodBuilder("findTargetClass")
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .returns(Class::class.java)
                    .addParameter(String::class.java, "path")
                    .addStatement(
                        "return path.equals(\$S) ? \$T.class : null ",
                        arouter.path,
                        ClassName.get(it as TypeElement)
                    )
                    .build()
                val type = TypeSpec.classBuilder(finalClassName)
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .addMethod(method)
                    .build()
                val javaFile = JavaFile.builder(packageName, type)
                    .build()
                javaFile.writeTo(filer)
            } catch (e: IOException) {
                messager?.printMessage(Diagnostic.Kind.NOTE, "error：${e.message.toString()} ")
            }


        }

        return true
    }

}