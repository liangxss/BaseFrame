package com.owen.base.frame;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author ZhouSuQiang
 * @email zhousuqiang@126.com
 * @date 2019-08-26
 */
class FrameUtils {

    static Type getRealType(Object object, int index) {
        Type genericSuperclass = object.getClass().getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            ParameterizedType types = (ParameterizedType) genericSuperclass;
            Type[] actualTypeArguments = types.getActualTypeArguments();
            if (index < actualTypeArguments.length) {
                return types.getActualTypeArguments()[index];
            }
        }
        return null;
    }
}
