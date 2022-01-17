package com.jampetz.sequenceSearchGA;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class DataManager {
    public <T> T readDataFromJson(String filename, Class<T> objectClass) {
        String absolutePath = new File("").getAbsolutePath();
        try (BufferedReader reader = new BufferedReader
                (new InputStreamReader(new FileInputStream(absolutePath + filename), StandardCharsets.UTF_8))) { ///UTF-8
            return new GsonBuilder().create().fromJson(reader, objectClass);
        } catch (Exception e) {
            System.out.println("File was not found.");
            return null;
        }
    }

    public void writeToJson(String filename, Object o) {
        String absolutePath = new File("").getAbsolutePath();
        try (Writer writer = new FileWriter(absolutePath + filename)) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(o, writer);
        } catch (Exception e) {
            System.out.println("An error occurred.");
        }
    }
}