package com.nuoxin.virtual.rep.api.utils;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 集合工具类-判断 List ,Set, Map, Array 是否为空,是否不为空.
 * @author xiekaiyu
 */
public final class CollectionsUtil {

    private CollectionsUtil(){}

    /**
     * 判断 List 为空
     * @param l List
     * @return  List 为 null 或元素个数为0时返回 true,否则返回 false.
     */
    public static final boolean isEmptyList(List<?> l) {
        return (l == null) || (l.isEmpty());
    }

    /**
     * 判断 List 不为空
     * @param l List
     * @return List 不为 null 且元素个数大于 0 时返回 true,否则返回 false.
     */
    public static final boolean isNotEmptyList(List<?> l) {
        return (l != null) && (!l.isEmpty());
    }

    /**
     * 判断 Map 为空
     * @param m Map
     * @return Map 为 null 或元素个数为0时返回 true,否则返回 false.
     */
    public static final boolean isEmptyMap(Map<?, ?> m) {
        return (m == null) || (m.isEmpty());
    }

    /**
     * 判断 Map 不为空
     * @param m Map
     * @return  Map 不为 null 且元素个数大于 0 时返回 true,否则返回 false.
     */
    public static final boolean isNotEmptyMap(Map<?, ?> m) {
        return (m != null) && (!m.isEmpty());
    }

    /**
     * 判断 Set 为空
     * @param s Set
     * @return  Set 为 null 或元素个数为 0 时返回 true,否则返回 false.
     */
    public static final boolean isEmptySet(Set<?> s) {
        return (s == null) || (s.isEmpty());
    }

    /**
     * 判断 Set 不为空
     * @param s Set
     * @return  Set  不为 null 且元素个数大于 0 时返回 true,否则返回 false.
     */
    public static final boolean isNotEmptySet(Set<?> s) {
        return (s != null) && (!s.isEmpty());
    }

    /**
     * 判断 Array 为空
     * @param arr Object[]
     * @return  Object[]  为 null 或元素个数小于 1 时返回 true,否则返回 false.
     */
    public static final boolean isEmptyArray(Object[] arr) {
        return (arr == null) || (arr.length < 1);
    }

    /**
     * 判断 Array 不为空
     * @param arr Object[]
     * @return  Object[]  不为 null 且元素个数大于 0 时返回 true,否则返回 false.
     */
    public static final boolean isNotEmptyArray(Object[] arr) {
        return (arr != null) && (arr.length > 0);
    }
}

