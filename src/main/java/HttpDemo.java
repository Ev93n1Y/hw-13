/*
+Задание 1#
 Программа должна содержать методы для реализации следующего функционала:
  + создание нового объекта в https://jsonplaceholder.typicode.com/users.
    Возможно, вы не увидите обновлений на сайте. Метод создания работает правильно,
    если в ответ на JSON с объектом вернулся такой же JSON, но с полем id со значением на 1 больше,
    чем самый большой id на сайте.

  + обновление объекта в https://jsonplaceholder.typicode.com/users.
    Возможно, обновления не будут видны на сайте напрямую. Будем считать, что метод работает правильно,
    если в ответ на запрос придет обновленный JSON (он должен быть точно таким же, какой вы отправляли).

  + удаление объекта из https://jsonplaceholder.typicode.com/users. Здесь будем считать корректным
    результат - статус из группы 2хх в ответ на запрос.

  + получение информации обо всех пользователях https://jsonplaceholder.typicode.com/users

  + получение информации о пользователе с определенным id https://jsonplaceholder.typicode.com/users/{id}

  + получение информации о пользователе с опредленным username
    https://jsonplaceholder.typicode.com/users?username={username}

 Задание 2#
    Дополните программу методом, который будет выводить все комментарии к последнему
    посту определенного пользователя и записывать их в файл.
    https://jsonplaceholder.typicode.com/users/1/posts Последним будем считать пост с наибольшим id.
    https://jsonplaceholder.typicode.com/posts/10/comments
    Файл должен называться "user-X-post-Y-comments.json", где Х - номер пользователя, Y - номер поста.

 Задание 3#
    Дополните программу методом, который будет выводить все открытые задачи для пользователя Х.
    https://jsonplaceholder.typicode.com/users/1/todos.
    Открытыми считаются все задачи, у которых completed = false.
*/

import java.io.*;

public class HttpDemo {
    private static final String URI = "https://jsonplaceholder.typicode.com/";
    private static final String USERS_URN = "users/";
    private static final String RESOURSE_PATH = "./src/main/resources/";
    private static final String USERS_JSON_PATH = RESOURSE_PATH.concat("users.json");

    public static void main(String[] args) {
        doFirstTask();
        doSecondTask();
        doThirdTask();
    }

    public static void doFirstTask() {
        try {
            //Create new user
            System.out.println(HttpClient.postRequest(URI.concat(USERS_URN), USERS_JSON_PATH));

            //Update user id=3
            System.out.println(HttpClient.putRequest(URI
                    .concat(USERS_URN)
                    .concat(Integer.toString(3)), USERS_JSON_PATH));

            //Delete user id=2
            System.out.println("User deleted = "
                    + (HttpClient.deleteRequest(URI
                    .concat(USERS_URN)
                    .concat(Integer.toString(2)))));

            //Get all users info
            System.out.println(HttpClient.getRequest(URI.concat(USERS_URN)));

            //Get user info by id=9
            System.out.println(HttpClient.getUserById(URI, USERS_URN, 9));

            //get user info by username="Samantha"
            System.out.println(HttpClient.getUserByUserName(URI, USERS_URN, "Samantha"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void doSecondTask() {
        try {
            //Prints user last post all comments & save to json file
            System.out.println(HttpUtils.saveUserLastComment(URI, 2));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void doThirdTask() {
        try {
            //prints user all open todos
            System.out.println(HttpUtils.getUserOpenTodos(URI, 3));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


