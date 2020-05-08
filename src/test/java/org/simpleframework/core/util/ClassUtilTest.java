package org.simpleframework.core.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.desktop.SystemSleepEvent;
import java.util.Set;

public class ClassUtilTest {
    @DisplayName(value = "extractPackageClassTest")
    @Test
    public void extractPackageClassTest(){
        Set<Class<?>>classSet = ClassUtil.extractPackageClass("com.touch.entity");
        System.out.println(classSet);
        Assertions.assertEquals(4,classSet.size());
    }
}
