package com.maemresen.tcw.sb.remote.docker.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StringHelper {

    public String randomString(String prefix) {
        return String.format("%s_%s", prefix, System.currentTimeMillis());
    }
}
