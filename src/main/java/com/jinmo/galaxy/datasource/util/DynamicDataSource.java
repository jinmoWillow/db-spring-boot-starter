package com.jinmo.galaxy.datasource.util;

import com.jinmo.galaxy.datasource.constant.DataSourceKey;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * spring动态数据源（需要继承AbstractRoutingDataSource）
 *
 * @author jinmo
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    private Map<Object, Object> dataSources;

    public DynamicDataSource() {

        dataSources = new HashMap<>();

        super.setTargetDataSources(dataSources);

    }


    public <T extends DataSource> void addDataSource(DataSourceKey key, T data) {
        dataSources.put(key, data);
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceHolder.getDataSourceKey();
    }
}
