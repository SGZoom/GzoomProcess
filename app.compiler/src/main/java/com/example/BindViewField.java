package com.example;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Name;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

/**
 * Created by xujiayunew on 2017/10/5.
 */

public class BindViewField {
    private VariableElement mVariableElement;
    private int mResId;

    BindViewField(Element element) throws IllegalArgumentException {
        if (element.getKind() != ElementKind.FIELD) {
            throw new IllegalArgumentException(String.format("Only fields can be annotated with @%s",
                    GZBindView.class.getSimpleName()));
        }
        mVariableElement = (VariableElement) element;
        //从目标文件中获取注解标记的变量
        GZBindView bindView = mVariableElement.getAnnotation(GZBindView.class);
        mResId = bindView.value();
        if (mResId < 0) {
            throw new IllegalArgumentException(
                    String.format("value() in %s for field %s is not valid !", GZBindView.class.getSimpleName(),
                            mVariableElement.getSimpleName()));
        }
    }

    /**
     * 获取变量名称
     *
     * @return
     */
    Name getFieldName() {
        return mVariableElement.getSimpleName();
    }

    /**
     * 获取变量id
     *
     * @return
     */
    int getResId() {
        return mResId;
    }

    /**
     * 获取变量类型
     *
     * @return
     */
    TypeMirror getFieldType() {
        return mVariableElement.asType();
    }
}
