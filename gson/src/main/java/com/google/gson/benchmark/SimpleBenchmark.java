package com.google.gson.benchmark;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static java.nio.charset.StandardCharsets.UTF_8;

public class SimpleBenchmark {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();


        Staff staff = createStaffObject();

        // Java objects to String
        String json = gson.toJson(staff);

        System.out.println(json);

        List<User> usersList = new ArrayList<>();
        for (int i = 0; i < 500000; i++) {
            usersList.add(generateRandomUser(i));
        }


        String filePath = "file.json";

        // Write the User objects to the JSON file
        writeUsersToJsonFile(usersList, filePath);

        String jsonString = readJsonFromFile(filePath);
        System.out.println(jsonString.substring(0, 100));

        // Parse the JSON array into a list of User objects
        User[] usersArray = gson.fromJson(jsonString, User[].class);
        List<User> users = Arrays.asList(usersArray);

        // Access the parsed data for each user
        for (User user : users) {
            if (user.getProfile().getName().equalsIgnoreCase("test")){
                System.out.println(user);
            }
        }

        long end = System.currentTimeMillis();
        System.out.println("time: " + (end - start));

    }

    private static void writeUsersToJsonFile(List<User> users, String filePath) {
        Gson gson = new Gson();
        try (Writer writer = Files.newBufferedWriter(Paths.get(filePath), UTF_8)) {
            gson.toJson(users, writer);
            System.out.println("Users data has been written to " + filePath);
        } catch (IOException e) {
           System.err.println("Error writing JSON file: " + e.getMessage());
        }
    }


    private static String readJsonFromFile(String filePath) {
        StringBuilder jsonBuilder = new StringBuilder();
        try (BufferedReader reader =  Files.newBufferedReader(Paths.get(filePath), UTF_8)) {
            String line;
            System.out.println("Reading file using BufferedReader");
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
        return jsonBuilder.toString();
    }
    private static User generateRandomUser(int i) {

        String id = String.valueOf(i);
        String email = "abc@example.com";
        String username = "testuser";
        String name = "James Brown";
        String company = "Cpi Inc.";
        String dob = getDateOfBirth();
        String address =" Street, 15";
        double lat = 90.0;
        double lon = 180.0;
        String about = "Randomly generated user.";

        // Create and return a new User object
        Location location = new Location(lat, lon);
        Profile profile = new Profile(name, company, dob, address, location, about);
        String apiKey = "sesFSH9REE30E0E0E0DFG-GSDGSDFSDG";
        List<String> roles = new ArrayList<>();
        roles.add("owner");
        roles.add("admin");
        String createdAt = "2023-08-02T12:00:00Z";
        String updatedAt = "2023-08-02T12:00:00Z";

        return new User(id, email, username, profile, apiKey, roles, createdAt, updatedAt);
    }
    private static String getDateOfBirth() {
        int year = 1950;
        int month = 1;
        int day = 3;
        return String.format("%04d-%02d-%02d", year, month, day);
    }

    private static Staff createStaffObject() {

        Staff staff = new Staff();

        staff.setName("mkyong");
        staff.setAge(35);
        staff.setPosition(new String[]{"Founder", "CTO", "Writer"});
        Map<String, BigDecimal> salary = new HashMap<>();
        salary.put("2010", new BigDecimal(10000));
        salary.put("2012", new BigDecimal(12000));
        salary.put("2018", new BigDecimal(14000));
        staff.setSalary(salary);
        staff.setSkills(Arrays.asList("java", "python", "node", "kotlin"));

        return staff;

    }
}
