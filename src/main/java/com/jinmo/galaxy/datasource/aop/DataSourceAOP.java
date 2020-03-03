package com.jinmo.galaxy.datasource.aop;

import com.jinmo.galaxy.datasource.annotation.DataSource;
import com.jinmo.galaxy.datasource.constant.DataSourceKey;
import com.jinmo.galaxy.datasource.util.DataSourceHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;

/**
 * 切换数据源advice
 *
 * @author jinmo
 */
@Slf4j
@Aspect
@Order(value = -1)
public class DataSourceAOP {
    /**
     * @param dataSource 数据源
     * @param point
     */
    @Before(value = "@annotation(dataSource)")
    public void changeDataSource(JoinPoint point, DataSource dataSource) {
        /**
         * 获取数据源的名字
         * */
        String dataSourceId = dataSource.name();
        try {
            DataSourceKey dataSourceKey = DataSourceKey.valueOf(dataSourceId);
            DataSourceHolder.setDataSourceKey(dataSourceKey);
        } catch (Exception e) {
            log.error("数据源[{}]不存在，使用默认数据源 > {}", dataSource.name(), point.getSignature());
        }
    }

    /**
     * 恢复数据源
     * @param joinPoint
     * @param dataSource 数据源
     * */
    @After(value = "@annotation(dataSource)")
    public void restoreDataSource(JoinPoint joinPoint , DataSource dataSource){
        log.debug("Revert DataSource : {transIdo} > {}", dataSource.name(), joinPoint.getSignature());
        DataSourceHolder.clearDataSourceKey();
    }
}
