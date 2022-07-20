package com.tanglx.wechat.common.util;


import java.util.*;

/**
 * @Describe
 * @Author tanglingxiao
 * @Date 2022-05-26
 */
public class StringUtil {

    /**
     * 判断是否为空，为空则返回true
     * 为空的条件：null、""、"null"
     *
     * @param obj
     * @return
     */
    public static boolean isBlank(Object obj) {
        if (obj == null) {
            return true;
        }
        String str = obj.toString().trim();
        if ("".equals(str) || "null".equalsIgnoreCase(str)) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否不为空，不为空则返回true
     * 为空的条件：null、""、"null"
     *
     * @param obj
     * @return
     */
    public static boolean isNotBlank(Object obj) {
        return !isBlank(obj);
    }

    /**
     * 获取换行符
     *
     * @return
     */
    public static String getNewLine() {
        return System.getProperty("line.separator");
    }

    /**
     * 获取uuid字符串
     */
    public static String UUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 去除前后空格，若obj为null返回""空字串
     *
     * @param obj
     * @return
     */
    public static String trim(Object obj) {
        if (obj == null) {
            return "";
        }
        return obj.toString().trim();
    }

    public static int getRandomByTwentyEight() {
        int max = 2800;
        int min = 280;
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        return s;
    }


    public static int[] getRandomByListSize(List<String> list) {
        int max = list.size();
        int min = 0;
        int[] s = new int[5];
        if (max > 10) {
            for (int x = 0; x < 5; x++) {
                Random random = new Random();
                s[x] = random.nextInt(max) % (max - min + 1) + min;
            }

        } else {
            if (max > 5 && max < 10) {
                max = max - 5;
                Random random = new Random();
                int s0 = random.nextInt(max) % (max - min + 1) + min;
                int i = 0;
                for (int x = s0; x < (s0 + 5); x++) {
                    s[i] = x;
                    i++;
                }
            } else {
                for (int x = 0; x < max; x++) {
                    s[x] = x;
                }
            }
        }

        return s;
    }


    /**
     * 任务编号
     *
     * @return
     */
    public static String getTaskId() {
        long code = System.currentTimeMillis();
        int max = 9999;
        int min = 1000;
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        String id = String.valueOf(code) + s;
        return id;
    }

    /**
     * 手动拼接get请求的参数
     *
     * @param url
     * @param params
     * @return
     */

    public static String attachHttpGetParams(String url, HashMap<String, String> params) {
        Iterator<String> keys = params.keySet().iterator();
        Iterator<String> values = params.values().iterator();
        StringBuilder stringBuffer = new StringBuilder();
        stringBuffer.append("?");

        for (int i = 0; i < params.size(); i++) {
            stringBuffer.append(keys.next()).append("=").append(values.next());
            if (i != params.size() - 1) {
                stringBuffer.append("&");
            }
        }
        return url + stringBuffer.toString();
    }

    /**
     * 手动拼接单个参数
     *
     * @param url
     * @param name
     * @param value
     * @return
     */
    public static String attachHttpGetParam(String url, String name, String value) {
        return url + "?" + name + "=" + value;
    }

    /**
     * 切分字符串
     *
     * @param str       被切分的字符串
     * @param separator 分隔符字符
     * @return 切分后的集合
     */
    public static String[] splitToArray(String str, char separator) {
        List<String> result = split(str, separator);
        return result.toArray(new String[result.size()]);
    }

    /**
     * 切分字符串<br>
     * a#b#c -> [a,b,c] <br>
     * a##b#c -> [a,"",b,c]
     *
     * @param str       被切分的字符串
     * @param separator 分隔符字符
     * @return 切分后的集合
     */
    public static List<String> split(String str, char separator) {
        return split(str, separator, 0);
    }

    /**
     * 切分字符串
     *
     * @param str       被切分的字符串
     * @param separator 分隔符字符
     * @param limit     限制分片数
     * @return 切分后的集合
     */
    public static String[] splitToArray(String str, char separator, int limit) {
        List<String> result = split(str, separator, limit);
        return result.toArray(new String[result.size()]);
    }

    /**
     * 切分字符串
     *
     * @param str       被切分的字符串
     * @param separator 分隔符字符
     * @param limit     限制分片数
     * @return 切分后的集合
     */
    public static List<String> split(String str, char separator, int limit) {
        if (str == null) {
            return null;
        }
        List<String> list = new ArrayList<>(limit == 0 ? 16 : limit);
        if (limit == 1) {
            list.add(str);
            return list;
        }

        // 未结束切分的标志
        boolean isNotEnd = true;
        int strLen = str.length();
        StringBuilder sb = new StringBuilder(strLen);
        for (int i = 0; i < strLen; i++) {
            char c = str.charAt(i);
            if (isNotEnd && c == separator) {
                list.add(sb.toString());
                // 清空StringBuilder
                sb.delete(0, sb.length());

                // 当达到切分上限-1的量时，将所剩字符全部作为最后一个串
                if (limit != 0 && list.size() == limit - 1) {
                    isNotEnd = false;
                }
            } else {
                sb.append(c);
            }
        }
        // 加入尾串
        list.add(sb.toString());
        return list;
    }

    /**
     * 切分字符串<br>
     *
     * @param str       被切分的字符串
     * @param delimiter 分隔符
     * @return 字符串
     */
    public static String[] split(String str, String delimiter) {
        if (str == null) {
            return null;
        }
        if (str.trim().length() == 0) {
            return new String[]{str};
        }

        // del length
        int dellen = delimiter.length();
        // one more for the lastPage
        int maxparts = (str.length() / dellen) + 2;
        int[] positions = new int[maxparts];

        int i, j = 0;
        int count = 0;
        positions[0] = -dellen;
        while ((i = str.indexOf(delimiter, j)) != -1) {
            count++;
            positions[count] = i;
            j = i + dellen;
        }
        count++;
        positions[count] = str.length();

        String[] result = new String[count];

        for (i = 0; i < count; i++) {
            result[i] = str.substring(positions[i] + dellen, positions[i + 1]);
        }
        return result;
    }

    /**
     * 拆分字符串
     * 根据给定长度，将给定字符串截取为多个部分
     *
     * @param str 字符串
     * @param len 每一个小节的长度
     * @return 截取后的字符串数组
     */
    public static String[] split(String str, int len) {
        int partCount = str.length() / len;
        int lastPartCount = str.length() % len;
        int fixPart = 0;
        if (lastPartCount != 0) {
            fixPart = 1;
        }
        final String[] strs = new String[partCount + fixPart];
        for (int i = 0; i < partCount + fixPart; i++) {
            if (i == partCount + fixPart - 1 && lastPartCount != 0) {
                strs[i] = str.substring(i * len, i * len + lastPartCount);
            } else {
                strs[i] = str.substring(i * len, i * len + len);
            }
        }
        return strs;
    }
}
