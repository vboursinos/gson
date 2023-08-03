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
        for (int i = 0; i < 50000; i++) {
            usersList.add(generateRandomUser());
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
    private static User generateRandomUser() {
        Random random = new Random();

        // Generate random values for User properties
        String id = Integer.toHexString(random.nextInt());
        String email = randomString(10) + "@example.com";
        String username = randomString(8);
        String name = randomString(10) + " " + randomString(10);
        String company = randomString(10) + " Inc.";
        String dob = randomDateOfBirth(random);
        String address = randomString(10) + " Street, " + randomString(8);
        double lat = random.nextDouble() * 180.0 - 90.0;
        double lon = random.nextDouble() * 360.0 - 180.0;
        String about = "Randomly generated user.";

        // Create and return a new User object
        Location location = new Location(lat, lon);
        Profile profile = new Profile(name, company, dob, address, location, about);
        String apiKey = java.util.UUID.randomUUID().toString();
        List<String> roles = new ArrayList<>();
        roles.add("owner");
        roles.add("admin");
        String createdAt = "2023-08-02T12:00:00Z";
        String updatedAt = "2023-08-02T12:00:00Z";

        return new User(id, email, username, profile, apiKey, roles, createdAt, updatedAt);
    }

    // Helper method to generate random strings
    private static String randomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }
        return sb.toString();
    }

    // Helper method to generate random date of birth
    private static String randomDateOfBirth(Random random) {
        int year = random.nextInt(50) + 1950; // Random year between 1950 and 1999
        int month = random.nextInt(12) + 1; // Random month between 1 and 12
        int day = random.nextInt(28) + 1; // Random day between 1 and 28
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
