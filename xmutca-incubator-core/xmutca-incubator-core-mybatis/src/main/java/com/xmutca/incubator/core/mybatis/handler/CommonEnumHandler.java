package com.xmutca.incubator.core.mybatis.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


/**
 * 封装通用的mybatis枚举映射处理器
 * @author Peter
 */
@MappedTypes({Enum.class})
@MappedJdbcTypes({JdbcType.VARCHAR})
public class CommonEnumHandler extends BaseTypeHandler<Enum> {

    private Map<Integer, Enum> enumMap;

    public CommonEnumHandler(Class<Enum> type) {
        enumMap = new HashMap<>();
        Enum[] constants = type.getEnumConstants();
        if (constants == null) {
            return;
        }

        for (Enum baseEnum:constants) {
            enumMap.put(baseEnum.ordinal(), baseEnum);
        }
    }

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Enum stateEnum, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i, stateEnum.ordinal());
    }

    @Override
    public Enum getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return enumMap.get(resultSet.getInt(s));
    }

    @Override
    public Enum getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return enumMap.get(resultSet.getInt(i));
    }

    @Override
    public Enum getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        int val = callableStatement.getInt(i);
        return enumMap.get(val);
    }
}