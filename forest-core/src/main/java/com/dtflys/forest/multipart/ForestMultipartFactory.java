package com.dtflys.forest.multipart;

import com.dtflys.forest.exceptions.ForestRuntimeException;
import com.dtflys.forest.mapping.MappingTemplate;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class ForestMultipartFactory<T> {

    private static Map<Class, Class> multipartTypeMap = new HashMap<>();

    private final Class<T> paramType;

    protected ForestMultipartFactory(Class<T> paramType,
                                     int index,
                                     MappingTemplate nameTemplate,
                                     MappingTemplate fileNameTemplate,
                                     MappingTemplate contentTypeTemplate) {
        this.paramType = paramType;
        this.index = index;
        this.nameTemplate = nameTemplate;
        this.fileNameTemplate = fileNameTemplate;
        this.contentTypeTemplate = contentTypeTemplate;
    }

    public static <P, M> void registerFactory(Class<P> paramType, Class<M> multipartType) {
        multipartTypeMap.put(paramType, multipartType);
    }


    public static <P> ForestMultipartFactory<P> createFactory(
            Class<P> paramType,
            int index,
            MappingTemplate nameTemplate,
            MappingTemplate fileNameTemplate,
            MappingTemplate contentTypeTemplate) {
        if (multipartTypeMap.containsKey(paramType)) {
            return new ForestMultipartFactory<>(paramType, index, nameTemplate, fileNameTemplate, contentTypeTemplate);
        }
        throw new ForestRuntimeException("[Forest] Can not find Multipart factory of type \"" + paramType.getName() + "\"");
    }

    static {
        registerFactory(File.class, FileMultipart.class);
        registerFactory(String.class, FilePathMultipart.class);
        registerFactory(InputStream.class, InputStreamMultipart.class);
        registerFactory(byte[].class, ByteArrayMultipart.class);
    }

    private final int index;

    private final MappingTemplate nameTemplate;

    private final MappingTemplate fileNameTemplate;

    private final MappingTemplate contentTypeTemplate;

    public int getIndex() {
        return index;
    }

    public MappingTemplate getNameTemplate() {
        return nameTemplate;
    }

    public MappingTemplate getFileNameTemplate() {
        return fileNameTemplate;
    }

    public MappingTemplate getContentTypeTemplate() {
        return contentTypeTemplate;
    }

    public <M extends ForestMultipart<T>> M create(String name, String fileName, T data, String contentType) {
        Class<M> multipartType = multipartTypeMap.get(paramType);
        try {
            M multipart = multipartType.newInstance();

        } catch (InstantiationException e) {
            throw new ForestRuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new ForestRuntimeException(e);
        }
        return null;
    }


}
