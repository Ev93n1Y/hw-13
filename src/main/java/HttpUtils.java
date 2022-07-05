import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class HttpUtils extends HttpClient {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    //Get user last comment
    public static String saveUserLastComment(String URI, int userId) throws IOException {
        //read user all posts
        String Uri = URI
                .concat("/users/")
                .concat(Integer.toString(userId))
                .concat("/posts");
        String response = getRequest(Uri);
        Posts[] posts = GSON.fromJson(response, Posts[].class);
        int lastPostId = posts[posts.length-1].getId();
        //read comments of last user post
        Uri = URI.concat("/posts/").concat(String.valueOf(lastPostId)).concat("/comments");
        response = getRequest(Uri);
        File file = new File("./src/main/resources/user-"
                .concat(Integer.toString(userId))
                .concat("-post-")
                .concat(String.valueOf(lastPostId))
                .concat("-comments.json"));
        return saveStringToFile(file, response);
    }

    //Get user open task(to do list)
    public static String getUserOpenTodos(String URI, int userId) throws IOException {
        String Uri = URI
                .concat("/users/")
                .concat(Integer.toString(userId))
                .concat("/todos");
        String response = getRequest(Uri);
        List<Todos> openTodosList = new ArrayList<>();
        Todos[] todos = GSON.fromJson(response, Todos[].class);
        for (Todos element :todos){
            if(!element.completed){
                openTodosList.add(element);
            }
        }
        return GSON.toJson(openTodosList);
    }

    private static String saveStringToFile(File file, String response) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        byte[] buffer = response.getBytes();
        fos.write(buffer,0, buffer.length);
        System.out.println("File " + file + " successfully created.");
        return response;
    }
}
