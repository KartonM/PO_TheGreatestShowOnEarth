package agh.cs.project;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class JsonWorldParametersParser {
    JSONParser parser;
    public JsonWorldParametersParser() {
        parser = new JSONParser();
    }

    public JSONObject parse(String fileName){
        try (FileReader reader = new FileReader(fileName))
        {
            var obj =(JSONObject) parser.parse(reader);
            return obj;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
