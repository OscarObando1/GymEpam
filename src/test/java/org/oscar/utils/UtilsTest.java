package org.oscar.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.oscar.model.Trainee;

import java.util.HashMap;
import java.util.Map;

public class UtilsTest {

    private GeneratorImp generatorImp;
    private Map<Long, Trainee> map;

    @BeforeEach
    public void setUp(){
        generatorImp = new GeneratorImp();
        map = new HashMap<>();
    }

    @Test
    public void createACorrectUsername(){
        String expected = "Oscar.Obando";
        Assertions.assertEquals(expected,generatorImp.createUser("Oscar","Obando",map));
    }

    @Test
    public void createCorrectPassword(){
        String expected = generatorImp.generatePass();
        Assertions.assertTrue(expected.length()==10);
    }
}
