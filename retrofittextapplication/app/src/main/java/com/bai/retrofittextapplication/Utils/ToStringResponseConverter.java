package com.bai.retrofittextapplication.Utils;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

import okhttp3.ResponseBody;
import retrofit2.Converter;

public class ToStringResponseConverter<T> implements Converter<ResponseBody, T> {

    private final Gson gson;
    private final TypeAdapter<T> adapter;
    private final Type type;

    ToStringResponseConverter(Gson gson, TypeAdapter<T> adapter, Type type) {
        this.gson = gson;
        this.adapter = adapter;
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {

        JsonReader jsonReader = null;
        try {
            String s = value.string();
            JSONObject jsonObject = new JSONObject(s);
            //对异常数据进行处理
//            if (type.equals(TestBean.class)) {
//
//                return gson.fromJson(String.valueOf(jsonObject), type);
//            }
            InputStream inputStream = new ByteArrayInputStream(jsonObject.toString().getBytes());
            Reader reader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            jsonReader = gson.newJsonReader(reader);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (jsonReader == null)
            jsonReader = gson.newJsonReader(value.charStream());
        try {
            T result = adapter.read(jsonReader);
            if (jsonReader.peek() != JsonToken.END_DOCUMENT) {
                throw new JsonIOException("JSON document was not fully consumed.");
            }
            return result;
        } finally {
            value.close();
        }

    }

}
