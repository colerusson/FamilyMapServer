package helpers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import model.Event;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

// This class uses tree parser to parse the JSON file into lists of each data type
// Then is also has methods to use those lists to generate random events, names, and locations, and such

public class FamilyTree {
    private List<String> femaleNames = new ArrayList<>();
    private List<String> maleNames = new ArrayList<>();
    private List<String> lastNames = new ArrayList<>();
    private List<Float> latitudes = new ArrayList<>();
    private List<Float> longitudes = new ArrayList<>();
    private List<String> countries = new ArrayList<>();
    private List<String> cities = new ArrayList<>();

    public void generateFemaleNames(File file) throws Exception {
        femaleNames.clear();
        try (FileReader fileReader = new FileReader(file)) {

            JsonParser jsonParser = new JsonParser();

            JsonObject rootObj = (JsonObject)jsonParser.parse(fileReader);

            JsonArray nameArray = (JsonArray)rootObj.get("data");

            for(int i = 0; i < nameArray.size(); ++i) {
                femaleNames.add(nameArray.get(i).toString());
            }
        }
    }

    public void generateMaleNames(File file) throws Exception {
        maleNames.clear();
        try (FileReader fileReader = new FileReader(file)) {

            JsonParser jsonParser = new JsonParser();

            JsonObject rootObj = (JsonObject)jsonParser.parse(fileReader);

            JsonArray nameArray = (JsonArray)rootObj.get("data");
            for(int i = 0; i < nameArray.size(); ++i) {
                maleNames.add(nameArray.get(i).toString());
            }
        }
    }

    public void generateLastNames(File file) throws Exception {
        lastNames.clear();
        try (FileReader fileReader = new FileReader(file)) {

            JsonParser jsonParser = new JsonParser();

            JsonObject rootObj = (JsonObject)jsonParser.parse(fileReader);

            JsonArray nameArray = (JsonArray)rootObj.get("data");
            for(int i = 0; i < nameArray.size(); ++i) {
                lastNames.add(nameArray.get(i).toString());
            }
        }
    }

    // generate a random location based on JSON file data
    public void generateLocations(File file) throws Exception {
        countries.clear();
        cities.clear();
        latitudes.clear();
        longitudes.clear();
        try (FileReader fileReader = new FileReader(file)) {

            JsonParser jsonParser = new JsonParser();

            JsonObject rootObj = (JsonObject)jsonParser.parse(fileReader);

            JsonArray nameArray = (JsonArray)rootObj.get("data");
            for (int i = 0; i < nameArray.size(); ++i) {
                JsonObject locationObj = (JsonObject)nameArray.get(i);

                JsonPrimitive country = (JsonPrimitive)locationObj.get("country");
                JsonPrimitive city = (JsonPrimitive)locationObj.get("city");
                JsonPrimitive latitude = (JsonPrimitive)locationObj.get("latitude");
                JsonPrimitive longitude = (JsonPrimitive)locationObj.get("longitude");

                String countryString = country.getAsString();
                String cityString = city.getAsString();
                Float latVal = latitude.getAsFloat();
                Float longVal = longitude.getAsFloat();

                countries.add(countryString);
                cities.add(cityString);
                latitudes.add(latVal);
                longitudes.add(longVal);
            }
        }
    }

    // generate a random event based on JSON file data
    public Event generateEvent(int year, String username, String personID, String eventType) {
        Event event = new Event();
        event.setEventID(UUID.randomUUID().toString());
        int locationIndex = new Random().nextInt(countries.size());
        event.setCountry(countries.get(locationIndex));
        event.setCity(cities.get(locationIndex));
        event.setLatitude(latitudes.get(locationIndex));
        event.setLongitude(longitudes.get(locationIndex));
        event.setPersonID(personID);
        event.setAssociatedUsername(username);
        event.setYear(year);
        event.setEventType(eventType);

        return event;
    }

    // generate a random name based on JSON file data
    public String getName(String nameType) {
        String name = null;
        if (nameType.equals("f")) {
            int nameIndex = new Random().nextInt(femaleNames.size());
            name = femaleNames.get(nameIndex);
        }
        else if (nameType.equals("m")) {
            int nameIndex = new Random().nextInt(maleNames.size());
            name = maleNames.get(nameIndex);
        }
        else if (nameType.equals("l")) {
            int nameIndex = new Random().nextInt(lastNames.size());
            name = lastNames.get(nameIndex);
        }

        return name;
    }

}
