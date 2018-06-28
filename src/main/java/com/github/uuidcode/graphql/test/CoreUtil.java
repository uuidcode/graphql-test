package com.github.uuidcode.graphql.test;

import java.io.File;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoreUtil {
    protected static Logger logger = LoggerFactory.getLogger(CoreUtil.class);

    public static String getContentFromResource(String name) {
        return getContent(new File(CoreUtil.class.getClassLoader().getResource(name).getFile()));
    }

    public static String getContent(File file) {
        try {
            return FileUtils.readFileToString(file, Charset.defaultCharset());
        } catch (Exception e) {
            logger.error("error", e);
        }

        return "";
    }

    public static String toJson(Object object) {
        return GsonHttpMessageConverter.gson.toJson(object);
    }

    public static boolean isEmpty(String value) {
        return value == null || value.trim().length() == 0;
    }

    public static boolean isNotEmpty(String value) {
        return !isEmpty(value);
    }
}
