package com.example.compiler

import com.example.annotation.ARouter
import com.google.auto.service.AutoService
import java.io.IOException
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements
import javax.lang.model.util.Types
import javax.tools.Diagnostic
@AutoService(Processor::class)
@SupportedAnnotationTypes ("com.example.annotation.ARouter")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedOptions("content")
class ARouterProcessor : AbstractProcessor() {
    private var elementUtils: Elements? = null
    private var typeUtils: Types? = null
    private var messager: Messager? = null
    private var filer: Filer? = null

    //初始化
    override fun init(processingEnv: ProcessingEnvironment?) {
        super.init(processingEnv)
        elementUtils = processingEnv?.elementUtils
        typeUtils = processingEnv?.typeUtils
        messager = processingEnv?.messager
        val content = processingEnv?.options?.get("content")
        messager?.printMessage(Diagnostic.Kind.NOTE, content)
        filer = processingEnv?.filer
    }
    override fun process(
        annotations: MutableSet<out TypeElement>?,
        roundEnv: RoundEnvironment?
    ): Boolean {
        if (annotations.isNullOrEmpty()) {
            return false
        }
        val elementsAnnotatedWith = roundEnv?.getElementsAnnotatedWith(ARouter::class.java)
        elementsAnnotatedWith?.forEach {
            // 通过类节点获取包节点（全路径：com.example.xxx）
            val packageName = elementUtils?.getPackageOf(it)?.qualifiedName
            // 获取简单类名
            val className = it.simpleName
            messager?.printMessage(Diagnostic.Kind.NOTE, "被注解的类有：$className")
            // 最终想生成的类文件名
            val finalClassName = "$className$\$ARouter"
            try {
                val createSourceFile = filer?.createSourceFile("$packageName . $finalClassName")
                val writer = createSourceFile?.openWriter()
                // 设置包名
                writer?.write("package $packageName;\n")
                writer?.write("public class $finalClassName {\n")
                writer?.write("public static Class<?> findTargetClass(String path) {\n")
                // 获取类之上@ARouter注解的path值
                val aRouter: ARouter = it.getAnnotation(ARouter::class.java)
                writer?.write(
                    """
                    if (path.equals("${aRouter.path}")) {
                    
                    """.trimIndent()
                )
                writer?.write("return $className.class;\n}\n")
                writer?.write("return null;\n")
                writer?.write("}\n}")
                // 最后结束别忘了
                writer?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }

        return true
    }

}