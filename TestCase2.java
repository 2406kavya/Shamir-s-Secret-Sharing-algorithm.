
public static void TestCase2() {
    public static void main(String[] args) {
        TestCase2();
    }
    // Define the test case 2 data
    String json = """
        {
            "keys": {
                "n": 9,
                "k": 6
            },
            "1": {
                "base": "10",
                "value": "28735619723837"
            },
            "2": {
                "base": "16",
                "value": "1A228867F0CA"
            },
            "3": {
                "base": "12",
                "value": "32811A4AA0B7B"
            },
            "4": {
                "base": "11",
                "value": "917978721331A"
            },
            "5": {
                "base": "16",
                "value": "1A22886782E1"
            },
            "6": {
                "base": "10",
                "value": "28735619654702"
            },
            "7": {
                "base": "14",
                "value": "71AB5070CC4B"
            },
            "8": {
                "base": "9",
                "value": "122662581541670"
            },
            "9": {
                "base": "8",
                "value": "642121030037605"
            }
        }
        """;

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
    System.out.println("Test Case 2:");
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