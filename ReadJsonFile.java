import org.json.JSONObject;

public class ReadJsonFile {
    public static void main(String[] args) {
        String jsonData = """
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

        try {
            // Attempt to parse the JSON data
            JSONObject jsonObject = new JSONObject(jsonData);

            // Print the read data
            System.out.println("JSON Data:");
            System.out.println(jsonObject.toString(4));
        } catch (Exception e) {
            // Handle any JSON parsing errors
            System.out.println("Error parsing JSON: " + e.getMessage());
        }
    }
}