package com.busanit501.boot_project;

import lombok.Cleanup;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootTest
@Log4j2
public class DataSourceTests {

    @Autowired
    private DataSource dataSource;


    //데이터소스, 디비에 연결하는 객체 생성
    @Test
    public void testConnection() throws SQLException {

        @Cleanup Connection connection = dataSource.getConnection();

        log.info("connection" + connection);
        //null이면 실패 null이 아니면 실행
        Assertions.assertNotNull(connection);
    }

}
