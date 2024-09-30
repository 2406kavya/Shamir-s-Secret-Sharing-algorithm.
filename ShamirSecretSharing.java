import org.json.JSONObject;
import org.json.JSONArray;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class ShamirSecretSharing {
    public static void main(String[] args) {
        // Read the test cases from the JSON files
        String testCase1 = readJsonFile("test_case_1.json");
        String testCase2 = readJsonFile("test_case_2.json");

        // Process the test cases
        processTestCase(testCase1, "Test Case 1");
        processTestCase(testCase2, "Test Case 2");
    }

    public static String readJsonFile(String filename) {
        // Read the JSON file
        String json = "";
        try {
            Scanner scanner = new Scanner(new File(filename));
            while (scanner.hasNextLine()) {
                json += scanner.nextLine();
            }
            scanner.close();
        } catch (Exception e) {
            System.out.println("Error reading JSON file: " + e.getMessage());
        }
        return json;
    }

    public static void processTestCase(String json, String testName) {
        // Parse the JSON data
        JSONObject jsonObject = new JSONObject(json);
        int n = jsonObject.getJSONObject("keys").getInt("n");
        int k = jsonObject.getJSONObject("keys").getInt("k");

        // Decode the y-values
        List<Point> points = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            JSONObject pointJson = jsonObject.getJSONObject(String.valueOf(i));
            int x = i;
            int y = decodeValue(pointJson.getString("value"), pointJson.getInt("base"));
            points.add(new Point(x, y));
        }

        // Find the secret c
        int c = findSecret(points, k);

        // Print the result
        System.out.println(testName + ":");
        System.out.println("Secret: " + c);

        // Find the wrong points on the curve (if any)
        List<Point> wrongPoints = findWrongPoints(points, c);
        if (wrongPoints.size() > 0) {
            System.out.println("Wrong points:");
            for (Point point : wrongPoints) {
                System.out.println(point);
            }
        }
        System.out.println();
    }

    public static int decodeValue(String value, int base) {
        // Decode the value from the given base
        int decodedValue = 0;
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            int digit = Character.isDigit(c) ? c - '0' : c - 'A' + 10;
            decodedValue = decodedValue * base + digit;
        }
        return decodedValue;
    }

    public static int findSecret(List<Point> points, int k) {
        // Find the secret c using Lagrange interpolation
        int c = 0;
        for (Point point : points) {
            int numerator = 1;
            int denominator = 1;
            for (Point otherPoint : points) {
                if (point != otherPoint) {
                    numerator *= (otherPoint.x - point.x);
                    denominator *= (point.x - otherPoint.x);
                }
            }
            c += point.y * numerator / denominator;
        }
        return c;
    }

    public static List<Point> findWrongPoints(List<Point> points, int c) {
        // Find the wrong points on the curve (if any)
        List<Point> wrongPoints = new ArrayList<>();
        for (Point point : points) {
            if (point.y != evaluatePolynomial(point.x, c, points.size() - 1)) {
                wrongPoints.add(point);
            }
        }
        return wrongPoints;
    }

    public static int evaluatePolynomial(int x, int c, int degree) {
        // Evaluate the polynomial at the given point
        int result = c;
        for (int i = 1; i <= degree; i++) {
            result += x * i;
        }
        return result;
    }

    public static class Point {
        public int x;
        public int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "(" + x + ", " + y + ")";
        }
    }
}