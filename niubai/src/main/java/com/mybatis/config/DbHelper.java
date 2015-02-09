package com.mybatis.config;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class DbHelper {
    private static DbHelper dbHelper=null;
    private static SqlSessionFactory sqlSessionFactory=null;
    private DbHelper(){
        String resource = "mybatis-config.xml";
        Reader reader;
        try {
            reader = Resources.getResourceAsReader(resource);
            sqlSessionFactory =new SqlSessionFactoryBuilder().build(reader);
            //sqlSessionFactory.getConfiguration().addMapper(GamePaymentTypePriceDao.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public SqlSession getSqlSession(){
        return sqlSessionFactory.openSession();
    }
    public static DbHelper getInstance(){
        if(dbHelper==null)
            dbHelper = new DbHelper();
        return dbHelper;
    }
}