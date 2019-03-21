package wolox.training.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import wolox.training.models.Book;

public class OpenLibraryService {

    private static String url = "https://openlibrary.org/api";
    private static String getMethod = "GET";

    public OpenLibraryService() {

    }

    private String getExternalApi(String externalApi) throws IOException {
        URL urlApi = new URL(externalApi);
        HttpURLConnection connection = (HttpURLConnection) urlApi.openConnection();
        connection.setRequestMethod(OpenLibraryService.getMethod);
        StringBuffer response = new StringBuffer();
        String input;

        BufferedReader buffer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        while ((input = buffer.readLine()) != null) {
            response.append(input);
        }
        buffer.close();
        return response.toString();
    }

    public Book bookInfo(String isbn) {
        try {
            String response = getExternalApi(
                OpenLibraryService.url + "/books?bibkeys=ISBN:" + isbn + "&format=json&jscmd=data");
            Book bookParsed = buildBookFromJson(response, isbn);
            return bookParsed;
        } catch (Exception e) {
            return null;
        }
    }

    private Book buildBookFromJson(String response, String isbn) throws JSONException {
        JSONObject jsonRaw = new JSONObject(response);

        if (!jsonRaw.has("ISBN:" + isbn))
            return null;

        JSONObject bookData = jsonRaw.getJSONObject("ISBN:" + isbn);
        Book book = new Book();

        book.setIsbn(isbn);
        book.setTitle(bookData.getString("title"));
        book.setSubtitle(bookData.getString("subtitle"));
        book.setYear(bookData.getString("publish_date"));
        book.setPages(bookData.getInt("number_of_pages"));
        book.setPublisher(getContentArray(bookData, "publishers"));
        book.setAuthor(getContentArray(bookData, "authors"));
        JSONObject images = bookData.getJSONObject("cover");
        book.setImage(images.getString("small"));
        return book;
    }

    private String getContentArray(JSONObject bookData, String type) throws JSONException {
        String content = "";
        JSONArray dataJson = bookData.getJSONArray(type);
        for (int i = 0; i < dataJson.length(); i++) {
            content += ((JSONObject)dataJson.get(i)).getString("name") + ".";
        }
        return content;
    }
}
