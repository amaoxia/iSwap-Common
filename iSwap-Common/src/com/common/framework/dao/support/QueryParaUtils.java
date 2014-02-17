package com.common.framework.dao.support;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.common.framework.dao.Constants;
import com.common.framework.dao.QueryPara;
import com.common.utils.common.RegexUtils;

/**
 * 对查询条件进行处理
 *@Company 北京光码软件有限公司
 *@author huwanshan
 *@version iSwap V6.0 数据交换平台
 *@date 2010-12-12 下午04:51:30
 *@Team 研发中心
 */
public class QueryParaUtils {

    /**
     * 获取查询属性
     * @param queryPara
     * @return
     */
    public static String getQueryParaPropertyStr(QueryPara queryPara) {
        String name = queryPara.getName();
        if (name.startsWith("start_")) {
            return name.substring("start_".length());
        } else if (name.startsWith("end_")) {
            return name.substring("end_".length());
        } else {
            return name;
        }
    }

    /**
     * 获取查询属性的命名参数别名
     * @param queryPara
     * @return
     */
    public static String getQueryParaNamedStr(QueryPara queryPara) {
        String name = queryPara.getName();
        if (StringUtils.contains(name, "."))
            return StringUtils.replace(name, ".", "__");
        return name;
    }

    /**
     * 获取查询参数对象的操作符串，如‘ge’返回‘>=’，默认返回‘=’
     * @param queryPara
     *            查询参数对象
     * @return
     */
    public static String getQueryParaOpStr(QueryPara queryPara) {
        String opStr = Constants.getOpStr(queryPara.getOp());
        if (StringUtils.isNotBlank(opStr))
            return opStr;
        return Constants.OP_EQ_VALUE;
    }

    /**
     * 获取查询参数对象的值符串对应的对象值，如“123”返回为int型123
     * @param queryPara
     *            查询参数对象
     * @return
     */
    public static Object getQueryParaValueObject(QueryPara queryPara) {
        return transValueByTypeAndOpStr(queryPara.getValue().trim(), queryPara.getType(), queryPara.getOp());
    }

    @SuppressWarnings("unchecked")
    public static Object[] getQueryParaListValueObject(QueryPara queryPara) {
        String valueStr = queryPara.getValue();
        String[] values = StringUtils.split(valueStr, ",");
        if (values != null && values.length > 0) {
            List l = new ArrayList();
            for (String value : values) {
                Object o = transValueByTypeAndOpStr(value, queryPara.getType(), queryPara.getOp());
                l.add(o);
            }
            return l.toArray();
        }
        return new String[] { "" };
    }
    /**
     * 转换操作符，如将“eq”转换为“=”
     * @param op
     * @return
     */
    protected static String transOpStr(String op) {
        if (op.equalsIgnoreCase(Constants.OP_EQ)) {// '='
            return Constants.OP_EQ_VALUE;
        }
        if (op.equalsIgnoreCase(Constants.OP_GE)) {// '>='
            return Constants.OP_GE_VALUE;
        }
        if (op.equalsIgnoreCase(Constants.OP_GT)) {// '>'
            return Constants.OP_GT_VALUE;
        }
        if (op.equalsIgnoreCase(Constants.OP_LE)) {// '<='
            return Constants.OP_LE_VALUE;
        }
        if (op.equalsIgnoreCase(Constants.OP_LT)) {// '<'
            return Constants.OP_LT_VALUE;
        }
        if (op.equalsIgnoreCase(Constants.OP_NE)) {// '<>'
            return Constants.OP_NE_VALUE;
        }
        if (op.equalsIgnoreCase(Constants.OP_LIKE) || op.equalsIgnoreCase(Constants.OP_LLIKE)
                        || op.equalsIgnoreCase(Constants.OP_RLIKE)) {
            return Constants.OP_LIKE;
        }
        return Constants.OP_EQ_VALUE;
    }

    /**
     * 根据值的类型和操作符转换字符值为对象值
     * @param valueStr
     *            字符值
     * @param typeStr
     *            值的类型
     * @param opStr
     *            操作符
     * @return
     */
    protected static Object transValueByTypeAndOpStr(String valueStr, String typeStr, String opStr) {
        if (typeStr.equalsIgnoreCase(Constants.TYPE_BOOLEAN))
            return Constants.getBooleanObject(valueStr);
        if (typeStr.equalsIgnoreCase(Constants.TYPE_DOUBLE))
            return Double.valueOf(valueStr);
        if (typeStr.equalsIgnoreCase(Constants.TYPE_FLOAT))
            return Float.valueOf(valueStr);
        if (typeStr.equalsIgnoreCase(Constants.TYPE_INTEGER))
            return Integer.valueOf(valueStr);
        if (typeStr.equalsIgnoreCase(Constants.TYPE_LONG))
            return Long.valueOf(valueStr);
        if (typeStr.equalsIgnoreCase(Constants.TYPE_DATE)) {// DATAT
            try {
                return parseDate(valueStr, "yyyy-MM-dd");
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        }
        if (typeStr.equalsIgnoreCase(Constants.TYPE_DATETIME)) {// DATATIME
            try {
                return parseDate(valueStr, "yyyy-MM-dd hh:mm:ss");
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        }
        if (typeStr.equalsIgnoreCase(Constants.TYPE_STRING)) {
            if (opStr.equalsIgnoreCase(Constants.OP_LIKE))
                return "%" + valueStr + "%";
            if (opStr.equalsIgnoreCase(Constants.OP_LLIKE))
                return valueStr + "%";
            if (opStr.equalsIgnoreCase(Constants.OP_RLIKE))
                return "%" + valueStr;
        }
        return valueStr;
    }

    private static Date parseDate(String value, String patten) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat(patten);
        return df.parse(value);
    }

    /**
     * 查询参数的合法性校验方法
     * @param queryPara
     *            查询参数
     * @return
     */
    public static boolean validateQueryPara(QueryPara queryPara) {
        //如果是in查询，反回true;
        if(queryPara.getOp().equalsIgnoreCase(Constants.OP_IN)){
            return true;
        }
        if (validTypeAndValue(queryPara) && validTypeAndOp(queryPara))
            return true;
        return false;
    }

    /**
     * 验证类型是否为合适的查询参数类型
     * @param para
     *            查询参数
     * @return
     */
    protected static boolean validTypeAndValue(QueryPara para) {
        String type = Constants.getTypeStr(para.getType());
        if (type.equalsIgnoreCase(Constants.TYPE_BOOLEAN)) {
            if (RegexUtils.isBoolean(para.getValue()))
                return true;
            return false;
        } else if (type.equalsIgnoreCase(Constants.TYPE_DATE)) {
            if (RegexUtils.isDate(para.getValue()))
                return true;
            return false;
        } else if (type.equalsIgnoreCase(Constants.TYPE_DOUBLE)) {
            if (RegexUtils.isFloat(para.getValue()))
                return true;
            return false;
        } else if (type.equalsIgnoreCase(Constants.TYPE_FLOAT)) {
            if (RegexUtils.isFloat(para.getValue()))
                return true;
            return false;
        } else if (type.equalsIgnoreCase(Constants.TYPE_INTEGER)) {
            if (RegexUtils.isInteger(para.getValue()))
                return true;
            return false;
        } else if (type.equalsIgnoreCase(Constants.TYPE_LONG)) {
            if (RegexUtils.isInteger(para.getValue()))
                return true;
            return false;
        } else if (type.equalsIgnoreCase(Constants.TYPE_STRING)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 类型和他所允许的操作是否一致
     * @param para
     * @return
     */
    protected static boolean validTypeAndOp(QueryPara para) {
        String type = Constants.getTypeStr(para.getType());
        if (type.equalsIgnoreCase(Constants.TYPE_BOOLEAN)) {// boolean
            String[] good_ops = new String[] { Constants.OP_EQ, Constants.OP_NE };
            return opInOps(para.getOp(), good_ops);
        } else if (type.equalsIgnoreCase(Constants.TYPE_DATE)// 其它
                        || type.equalsIgnoreCase(Constants.TYPE_DATETIME)
                        || type.equalsIgnoreCase(Constants.TYPE_DOUBLE)
                        || type.equalsIgnoreCase(Constants.TYPE_FLOAT)
                        || type.equalsIgnoreCase(Constants.TYPE_INTEGER) || type.equalsIgnoreCase(Constants.TYPE_LONG)) {
            return opNotBeLike(para);
        } else if (type.equalsIgnoreCase(Constants.TYPE_STRING)) {// string
            String[] good_ops = new String[] { Constants.OP_EQ, Constants.OP_NE, Constants.OP_LIKE, Constants.OP_LLIKE,
                    Constants.OP_RLIKE, Constants.OP_IN };
            return opInOps(para.getOp(), good_ops);
        } else {
            return false;
        }
    }

    private static boolean opNotBeLike(QueryPara para) {
        if (para.getOp().equalsIgnoreCase(Constants.OP_LIKE) || para.getOp().equalsIgnoreCase(Constants.OP_RLIKE)
                        || para.getOp().equalsIgnoreCase(Constants.OP_LLIKE))
            return false;
        return true;
    }

    private static boolean opInOps(String op, String[] good_ops) {
        for (String good_op : good_ops) {
            if (op.equalsIgnoreCase(good_op))
                return true;
        }
        return false;
    }

    /**
     * 获取条件字符串
     * @param queryPara
     * @return
     */
    public static String getConditionStr(QueryPara queryPara) {
        if (QueryParaUtils.validateQueryPara(queryPara)) {
            StringBuilder sb = new StringBuilder("");
            String str = queryPara.getName();
            String arg = "";
            if (str.endsWith("_date")) {
                arg = str.substring(0, str.indexOf("_"));
                queryPara.setName(arg);
                sb.append(QueryParaUtils.getQueryParaPropertyStr(queryPara))// 属性
                                .append(" ").append(QueryParaUtils.getQueryParaOpStr(queryPara));// 操作符
                arg += "_date";
                queryPara.setName(arg);
                if (queryPara.getOp().equalsIgnoreCase(Constants.OP_IN)) {
                    sb.append(" :(").append(QueryParaUtils.getQueryParaNamedStr(queryPara)).append(")");// 标识
                } else {
                    sb.append(" :").append(QueryParaUtils.getQueryParaNamedStr(queryPara));// 标识
                }
            } else {
                sb.append(QueryParaUtils.getQueryParaPropertyStr(queryPara))// 属性
                                .append(" ").append(QueryParaUtils.getQueryParaOpStr(queryPara));// 操作符
                if (queryPara.getOp().equalsIgnoreCase(Constants.OP_IN)) {
                    sb.append(" (:").append(QueryParaUtils.getQueryParaNamedStr(queryPara)).append(")");// 标识
                } else {
                    sb.append(" :").append(QueryParaUtils.getQueryParaNamedStr(queryPara));// 标识
                }
            }
            return sb.toString();
        } else {
            return "";
        }
    }
}
