import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
//создаем класс chatServer с объявлением socket и создаем обзего для всех клиентов ArrayList.
public class chatServer {
    ServerSocket serverSocket;
    ArrayList<Client> ClientsList = new ArrayList<>();

    //создаем конструктор chatServer, создаем в нем подключение к порту.
   chatServer() {
       //отрабатываем исключение socket.
       try {
           serverSocket = new ServerSocket(1234);
       } catch (IOException e) {
           e.printStackTrace();
       }
   }
    //создаем метод для рассылки сообщений  по ArrayList со всеми добавленными клиентами.
    public void sendForAllClient(String message) {
        for (Client client : ClientsList) {
            client.receiveMessage(message);
        }
    }
    //запускаем chatServer в отдельном потоке.
    public void run() {
       //запускаем бесконечный цикл.
       while (true) {
           //выводим сообщение в консоль - ожидание клиента.
           System.out.println("I'm waiting..");
            //ловим исключение на подключение к чату.
           try {
               Socket socket = serverSocket.accept();
                //объявление нового клиента в чате.
               System.out.println("Buddy is here");
                //добавляем всех клиентов в ArrayList.
               ClientsList.add(new Client(socket, this));
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
    }
}

