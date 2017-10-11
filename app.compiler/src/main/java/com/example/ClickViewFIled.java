package com.example;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;

/**
 * Created by xujiayunew on 2017/10/7.
 */

public class ClickViewFIled {
    /**方法元素*/
   private ExecutableElement executableElement;
    /**控件id*/
    private int resId ;
    /**绑定方法名*/
    private  String methodName ;
   public ClickViewFIled(Element element)
    {
        //只支持方法注解
        if(element.getKind()!= ElementKind.METHOD)
        {
            throw new IllegalArgumentException(String.format("Only method can be annotated with @%s",
                    GZClickView.class.getSimpleName()));
        }
       //转化成方法元素
        executableElement = (ExecutableElement) element;
        //获取注解对象整体
        GZClickView gzClickView = executableElement.getAnnotation(GZClickView.class);
        //获取id
        resId = gzClickView.value();
        if (resId<0){
            throw new IllegalArgumentException(
                    String.format("value() in %s for field %s is not valid !", GZBindView.class.getSimpleName(),
                            executableElement.getSimpleName()));
        }
         methodName = executableElement.getSimpleName().toString();


    }

    public ExecutableElement getExecutableElement() {
        return executableElement;
    }

    public int getResId() {
        return resId;
    }

    public String getMethodName() {
        return methodName;
    }
}
