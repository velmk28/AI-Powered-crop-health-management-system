import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PrakritiChatbot {

    private static final String URL = "jdbc:mysql://localhost:3306/sentiment_db";
    private static final String USER = "root";
    private static final String PASSWORD = "mysql";
    private static String soilType;
    private static String state;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Initial Introduction
        System.out.println("Hello! I’m Prakriti, your friendly neighborhood farmer.");
        System.out.println("I’m here to help you with crop recommendations, soil health analysis, and soil treatments.");
        System.out.println("Please start by telling me which state you're farming in.");

        state = scanner.nextLine();

        // Provide soil and crop information based on state
        provideSoilInfoBasedOnState(state);

        // Prompt for what the farmer wants to grow
        System.out.println("What crop are you interested in growing?");
        String crop = scanner.nextLine();
        System.out.println("Great choice! Here are some tips for growing " + crop + " on " + soilType + " soil.");

        // Database connection for sentiment analysis on soil health
        System.out.println("Let's analyze your soil health. Please describe your soil's condition in your daily journal (e.g., 'fertile', 'compact', 'well-drained').");
        String soilCondition = scanner.nextLine();
        
        // Calculate soil health score and provide feedback
        double soilHealthScore = analyzeSoilHealth(soilCondition);
        System.out.printf("Your soil quality score is: %.2f%%\n", soilHealthScore);
        
        if (soilHealthScore < 60) {
            System.out.println("Based on your soil condition, here are some treatments and recommended medicines to improve soil quality.");
            suggestSoilImprovement(soilCondition);
        } else {
            System.out.println("Your soil is in good condition! Keep up the healthy practices.");
        }
    }

    private static void provideSoilInfoBasedOnState(String state) {
        Map<String, String[]> stateSoilInfo = new HashMap<>();
        stateSoilInfo.put("Punjab", new String[]{"Alluvial", "Wheat, Rice, Maize, Pulses, Sugarcane, Potatoes, Mangoes", "Semi-arid"});
        stateSoilInfo.put("Maharashtra", new String[]{"Black soil", "Cotton, Soybean, Sorghum, Millets, Chickpeas, Grapes, Sunflower", "Tropical"});
        stateSoilInfo.put("West Bengal", new String[]{"Laterite", "Tea, Rice, Jute, Pineapple, Coconut", "Humid"});
        stateSoilInfo.put("Tamil Nadu", new String[]{"Red soil", "Groundnut, Millets, Cotton, Sorghum, Castor", "Tropical"});
        stateSoilInfo.put("Rajasthan", new String[]{"Sandy", "Bajra, Wheat, Gram, Barley, Mustard", "Arid"});
        stateSoilInfo.put("Uttar Pradesh", new String[]{"Alluvial", "Wheat, Sugarcane, Rice, Maize, Pulses", "Subtropical"});
        stateSoilInfo.put("Gujarat", new String[]{"Black soil", "Cotton, Groundnut, Bajra, Maize, Sesame", "Semi-arid"});
        stateSoilInfo.put("Assam", new String[]{"Alluvial", "Tea, Rice, Jute, Pineapple, Orange", "Humid"});
        stateSoilInfo.put("Kerala", new String[]{"Laterite", "Rubber, Coconut, Tea, Coffee, Cocoa", "Tropical"});
        stateSoilInfo.put("Karnataka", new String[]{"Red soil", "Millets, Sugarcane, Rice, Groundnut, Sunflower", "Tropical"});
        stateSoilInfo.put("Haryana", new String[]{"Loamy", "Wheat, Rice, Mustard, Barley, Sunflower", "Semi-arid"});
        stateSoilInfo.put("Bihar", new String[]{"Alluvial", "Rice, Maize, Wheat, Pulses, Oilseeds", "Subtropical"});
        stateSoilInfo.put("Odisha", new String[]{"Coastal Alluvial", "Rice, Coconut, Cashew, Sugarcane, Jute", "Humid"});
        stateSoilInfo.put("Madhya Pradesh", new String[]{"Black soil", "Soybean, Wheat, Gram, Pulses, Cotton", "Tropical"});
        stateSoilInfo.put("Telangana", new String[]{"Red soil", "Cotton, Chilli, Tobacco, Millets, Maize", "Semi-arid"});
        stateSoilInfo.put("Andhra Pradesh", new String[]{"Red soil", "Groundnut, Sugarcane, Rice, Tobacco, Maize", "Tropical"});

        if (stateSoilInfo.containsKey(state)) {
            String[] info = stateSoilInfo.get(state);
            soilType = info[0];
            String crops = info[1];
            String climate = info[2];

            System.out.println("In " + state + ", the soil is primarily " + soilType + ".");
            System.out.println("Recommended crops for this region: " + crops + ".");
            System.out.println("The climate in this region is generally " + climate + ".");
        } else {
            System.out.println("I'm still learning about soils in your area!");
        }
    }

    private static double analyzeSoilHealth(String soilCondition) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int positiveMatches = 0;
        int totalConditions = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            // Split conditions and analyze each individually
            String[] conditions = soilCondition.toLowerCase().split(", ");
            totalConditions = conditions.length;

            for (String condition : conditions) {
                String sql = "SELECT sentiment FROM sentiments WHERE term = ?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, condition);
                resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    String sentiment = resultSet.getString("sentiment");
                    if ("good".equalsIgnoreCase(sentiment)) {
                        positiveMatches++;
                    }
                }
            }

            double healthScore = ((double) positiveMatches / totalConditions) * 100;
            return healthScore;

        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0.0;
    }

    private static void suggestSoilImprovement(String soilCondition) {
        // Basic recommendations with medicines
        switch (soilCondition.toLowerCase()) {
            case "compacted":
                System.out.println("Loosen the soil by tilling and add organic matter to improve aeration.");
                System.out.println("Medicine: Humic acid, Biochar");
                break;
            case "salinity":
                System.out.println("Consider adding gypsum and leaching salts with plenty of water.");
                System.out.println("Medicine: Gypsum, Salt-resistant fertilizers");
                break;
            case "acidic":
                System.out.println("Lime the soil to raise pH levels.");
                System.out.println("Medicine: Agricultural lime");
                break;
            case "nutrient-deficient":
                System.out.println("Add organic compost and balanced fertilizers.");
                System.out.println("Medicine: NPK (Nitrogen, Phosphorus, Potassium) fertilizer");
                break;
            case "drought-prone":
                System.out.println("Increase soil moisture retention with mulching.");
                System.out.println("Medicine: Water-retentive polymers, Mulch");
                break;
            default:
                System.out.println("Consider adding compost or organic matter for better soil structure.");
                System.out.println("General Medicine: Vermicompost, Organic manure");
                break;
        }
    }
}