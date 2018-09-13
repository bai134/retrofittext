package com.bai.retrofittextapplication.Utils;

import com.bai.retrofittextapplication.bean.TestBean;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;


public class StringConverterFactory extends Converter.Factory {

    public static StringConverterFactory create() {
        return create(new Gson());
    }

    public static StringConverterFactory create(Gson gson) {
        return new StringConverterFactory(gson);
    }

    private Gson gson;

    private StringConverterFactory(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        this.gson = gson;
    }
    private static StringConverterFactory instace = new StringConverterFactory();

    private StringConverterFactory() {
        super();
    }


    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));

        if(type.equals(TestBean.class))
            return new ToStringResponseConverter(gson,adapter,type);

        return new GsonResponseBodyConverter<>(gson, adapter);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type,
                                                          Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new GsonRequestBodyConverter<>(gson, adapter);
    }


}
