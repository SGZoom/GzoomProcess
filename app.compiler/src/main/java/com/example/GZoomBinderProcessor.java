package com.example;

import com.google.auto.service.AutoService;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Logger;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

/**
 * Created by xujiayunew on 2017/10/5.
 */

//google提供的注册处理器的库，这样可以免去使用 javax.annotation.processing.Processor进行注册
@AutoService(Processor.class)
public class GZoomBinderProcessor extends AbstractProcessor {



    private Filer mFiler; //文件相关的辅助类
    private Elements mElementUtils; //元素相关的辅助类
    private Messager mMessager; //日志相关的辅助类
    private Map<String, AnnotatedClass> mAnnotatedClassMap;
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        //filter用来创建新的源文件、class文件以及辅助文件
        mFiler = processingEnv.getFiler();
        //elements中包含着操作element的工具方法
        mElementUtils = processingEnv.getElementUtils();
        //用来报告错误、警告以及其他提示信息
        mMessager = processingEnv.getMessager();
        mAnnotatedClassMap = new TreeMap<>();
        //processingZEnvirment中还有操作TYPE mirror的
        //processingEnv.getTypeUtils();
    }

    /**每一个注解处理器类都必须有一个空的构造函数。然而，这里有一个特殊的init()方法，
     * 它会被注解处理工具调用，并输入ProcessingEnviroment参数。
     * ProcessingEnviroment提供很多有用的工具类Elements,Types和Filer
     *
     * 这相当于每个处理器的主函数main()。 在这里写扫描、评估和处理注解的代码，以及生成Java文件。
     * 输入参数RoundEnviroment，可以让查询出包含特定注解的被注解元素
     *
     * */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        //RoundEnvironment
        //可以返回包含指定注解类型的元素的集合
        mAnnotatedClassMap.clear();
        try {
            //增加方法，处理点击注解
            processBindView(roundEnv);
            processClickBindMethod(roundEnv);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            error(e.getMessage());
        }
        for (AnnotatedClass annotatedClass : mAnnotatedClassMap.values()) {
            try {
                //输出中间文件
                annotatedClass.generateFile().writeTo(mFiler);
            } catch (IOException e) {
                error("Generate file failed, reason: %s", e.getMessage());
            }
        }
        return true;
    }

    /**处理点击事件绑定*/
    private void processClickBindMethod(RoundEnvironment roundEnv) {
        for(Element element : roundEnv.getElementsAnnotatedWith(GZClickView.class))
        {
            //获取对应的生成类
            AnnotatedClass annotatedClass = getAnnotatedClass(element);
          //生成我们的目标注解模型，方便后期文件输出
            ClickViewFIled clickFile = new ClickViewFIled(element);
           annotatedClass.addClickField(clickFile);
        }

    }

    private void processBindView(RoundEnvironment roundEnv) throws IllegalArgumentException {
        //作者在这里有点省略了，其实这里annotations中是包含我们的注解类型的，但是你可以看看
        //下面的支持类型函数，只是放进去了一个GZBindview,所以这里也没有遍历的必要了
        for (Element element : roundEnv.getElementsAnnotatedWith(GZBindView.class)) {
            AnnotatedClass annotatedClass = getAnnotatedClass(element);
            BindViewField bindViewField = new BindViewField(element);
            annotatedClass.addField(bindViewField);
        }
    }
    /**获取注解所在文件对应的生成类*/
    private AnnotatedClass getAnnotatedClass(Element element) {
        //typeElement表示类或者接口元素
        TypeElement typeElement = (TypeElement) element.getEnclosingElement();
        String fullName = typeElement.getQualifiedName().toString();
        //这里其实就是变相获得了注解的类名（完全限定名称，这里是这么说的）
        AnnotatedClass annotatedClass = mAnnotatedClassMap.get(fullName);
        // Map<String, AnnotatedClass>
        if (annotatedClass == null) {
            annotatedClass = new AnnotatedClass(typeElement, mElementUtils);
            mAnnotatedClassMap.put(fullName, annotatedClass);
        }
        return annotatedClass;
    }

    private void error(String msg, Object... args) {
        mMessager.printMessage(Diagnostic.Kind.ERROR, String.format(msg, args));
    }


    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }


    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(GZBindView.class.getCanonicalName());
        types.add(GZClickView.class.getCanonicalName());
        return types;
    }
}