package com.lhsj.police.core.sql;

import com.lhsj.police.core.collection.ReMaps;
import com.google.common.base.CaseFormat;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import java.util.Map;

public class ReSqls {

    /**
     * 将hashmap拼接为sql语句
     */
    public static <T> String mapToSql(T bean, Map<String, Object> result) {
        Preconditions.checkNotNull(bean, "bean is null");
        Preconditions.checkArgument(!ReMaps.isEmpty(result), "result is null or size is 0");

        String tableName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, bean.getClass().getSimpleName());
        StringBuilder sql = new StringBuilder();
        StringBuilder sqlValues = new StringBuilder();

        sql.append("INSERT INTO ");
        sql.append(tableName).append(" ");
        sql.append("(");

        sqlValues.append("VALUES (");

        for (String key : result.keySet()) {
            Object ret = result.get(key);
            if (ret == null) {
                continue;
            }
            if (ret instanceof String) {
                if (Strings.isNullOrEmpty((String) ret)) {
                    continue;
                }
                sql.append(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, key)).append(",");
                sqlValues.append("'").append(ret).append("'").append(",");
            } else {
                sql.append(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, key)).append(",");
                sqlValues.append(ret).append(",");
            }
        }

        sql = sql.deleteCharAt(sql.length() - 1);
        sqlValues = sqlValues.deleteCharAt(sqlValues.length() - 1);

        sql.append(") ");
        sqlValues.append(")");

        return sql.append(sqlValues).append(";").toString();
    }

}
