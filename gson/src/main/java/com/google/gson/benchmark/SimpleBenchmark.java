package com.google.gson.benchmark;

import com.google.gson.*;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static java.nio.charset.StandardCharsets.UTF_8;

public class SimpleBenchmark {
    private static final String TOP_COUNTRIES_FILE = "files/countries.json";
    private static final String EMPLOYEE_FILE = "files/employee.json";
    private static final String USER_FILE = "files/user.json";
    private static final int TOP_COUNT = 20;

    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        simpleAnalyzer();
        userAnalyzer();
        employeeAnalyzer();
        countriesAnalyzer();
        employeeSalariesSum();
        employeeSalariesSumWithStr();
        long end = System.currentTimeMillis();
        System.out.println("time: " + (end - start));

    }

    private static void simpleAnalyzer(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Staff staff = createStaffObject();

        // Java objects to String
        String json = gson.toJson(staff);
        System.out.println(json);
    }

    private static void userAnalyzer(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        List<User> usersList = new ArrayList<>();
        for (int i = 0; i < 500000; i++) {
            usersList.add(generateRandomUser(i));
        }


        String filePath = USER_FILE;

        // Write the User objects to the JSON file
        writeUsersToJsonFile(usersList, filePath);

        String jsonString = readJsonFromFile(filePath);
        System.out.println(jsonString.substring(0, 100));

        // Parse the JSON array into a list of User objects
        User[] usersArray = gson.fromJson(jsonString, User[].class);
        List<User> users = Arrays.asList(usersArray);

        // Access the parsed data for each user
        for (User user : users) {
            if (user.getProfile().getName().equalsIgnoreCase("test")) {
                System.out.println(user);
            }
        }

    }
    private static void employeeAnalyzer() {
        //parse 70mb json file and use counter and sum
        for (int i = 0; i < 5; i++) {
            String fileName = EMPLOYEE_FILE; // Change to your JSON file name
            int count = countSalariesOver100000(fileName);
            System.out.println("----------------------------------------");
            System.out.println("Number of salaries over $100,000: " + count);
        }
    }

    private static void countriesAnalyzer() {
        String fileName = TOP_COUNTRIES_FILE; // Change to your JSON file name
        int topCount = TOP_COUNT;
        for (int i = 0; i < 5; i++) {
            List<Country> topCountries = getTopPopulationCountries(fileName, topCount);

            System.out.println("Top " + topCount + " Countries by Population:");
            for (Country country : topCountries) {
                System.out.println(country.getCountryName() + ": " + country.getPopulation());
            }
        }
    }

    public static void employeeSalariesSum() {
        String fileName = EMPLOYEE_FILE; // Change to your JSON file name
        for (int i = 0; i < 5; i++) {
            long totalSalary = calculateTotalSalary(fileName);
            System.out.println("Total sum of all salaries: $" + totalSalary);
        }
    }

    public static void employeeSalariesSumWithStr() {
        String fileName = EMPLOYEE_FILE; // Change to your JSON file name
        for (int i = 0; i < 10; i++) {
            long totalSalary = calculateTotalSalaryWithStr(fileName);
            System.out.println("Total sum of all salaries: $" + totalSalary);
        }
    }


    private static long calculateTotalSalary(String fileName) {
        long totalSalary = 0;

        try (Reader fileReader = Files.newBufferedReader(Paths.get(fileName), UTF_8)) {
            JsonArray jsonArray = JsonParser.parseReader(fileReader).getAsJsonArray();

            for (JsonElement jsonElement : jsonArray) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                int salary = jsonObject.get("salary").getAsInt();
                totalSalary += salary;
            }
        } catch (IOException e) {
            System.err.println("Error reading JSON file: " + e.getMessage());
        }

        return totalSalary;
    }

    private static long calculateTotalSalaryWithStr(String fileName) {
        int totalSalary = 0;

        try {
            StringBuilder jsonString = new StringBuilder();
            Reader fileReader = Files.newBufferedReader(Paths.get(fileName), UTF_8);
            int character;

            while ((character = fileReader.read()) != -1) {
                jsonString.append((char) character);
            }

            JsonArray jsonArray = JsonParser.parseString(jsonString.toString()).getAsJsonArray();

            for (JsonElement jsonElement : jsonArray) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                int salary = jsonObject.get("salary").getAsInt();
                totalSalary += salary;
            }

            fileReader.close();
        } catch (IOException e) {
            System.err.println("Error reading JSON file: " + e.getMessage());
        }

        return totalSalary;
    }
    private static List<Country> getTopPopulationCountries(String fileName, int topCount) {
        List<Country> countryList = new ArrayList<>();
        JsonArray jsonArray;

        try (Reader fileReader = Files.newBufferedReader(Paths.get(fileName), UTF_8)) {
            jsonArray = JsonParser.parseReader(fileReader).getAsJsonArray();

            for (JsonElement jsonElement : jsonArray) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                String countryName = jsonObject.get("countryName").getAsString();
                int population = jsonObject.get("population").getAsInt();
                countryList.add(new Country(countryName, population));
            }
        } catch (IOException e) {
            System.err.println("Error reading JSON file: " + e.getMessage());
        }

        Collections.sort(countryList, new Comparator<Country>() {
            @Override
            public int compare(Country c1, Country c2) {
                return Integer.compare(c2.getPopulation(), c1.getPopulation());
            }
        });

        return countryList.subList(0, Math.min(topCount, countryList.size()));
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
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath), UTF_8)) {
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
        String address = " Street, 15";
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

    private static int countSalariesOver100000(String fileName) {
        int count = 0;

        try (Reader fileReader = Files.newBufferedReader(Paths.get(fileName), UTF_8)) {
            JsonArray jsonArray = JsonParser.parseReader(fileReader).getAsJsonArray();

            for (JsonElement jsonElement : jsonArray) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                int salary = jsonObject.get("salary").getAsInt();
                if (salary > 100000) {
                    count++;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading JSON file: " + e.getMessage());
        }

        return count;
    }
}
