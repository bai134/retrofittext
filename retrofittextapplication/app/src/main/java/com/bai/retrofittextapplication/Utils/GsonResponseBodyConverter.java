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
import java.nio.charset.Charset;

import okhttp3.ResponseBody;
import retrofit2.Converter;


final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    GsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        JsonReader jsonReader = null;
        try {
            String s = value.string();
            JSONObject jsonObject = new JSONObject(s);
            //处理异常数据
//            if (jsonObject.get("error") instanceof Integer) {
//
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