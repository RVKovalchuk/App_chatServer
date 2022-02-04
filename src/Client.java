import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
//создаем класс клиента, имплементирующий интерфейс Runnable.
public class Client implements Runnable{
    //выносим общее.
    Socket socket;
    chatServer server;
    Scanner in;
    PrintStream out;
    //создаем конструктор Клиента с агрументом на socket и server.
    public Client(Socket socket, chatServer server) {
        this.socket = socket;
        this.server = server;
        //создаем объект класса Thread и передаем ему клиента, запускаем Thread.
        new Thread(this).start();
    }
    //создаем метод для вывода сообщения.
    public void receiveMessage(String message) {
        out.println(message);
    }
    //переопределяем run() интерфейса Runnable.
    @Override
    public void run() {
        //отрабатываем исключения в Stream.
        try {
            //открываем потоки ввода и вывода.
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();
            //используем поток вывода для вывода приветствия в чат.
            out = new PrintStream(os);
            out.println("Welcome to the club Buddy.");
            //используем поток ввода для ввода клиентами текста в чат.
            in = new Scanner(is);
            String input = in.nextLine();
            //ввод/вывод работают до тех пор, пока не будет введено "bb".
            while (!input.equals("bb")) {
                //рассылаем сообщение всем клиентам.
                server.sendForAllClient(input);
                //обновляем проверямое сообщение на вводе (для сравнения с "bb").
                input = in.nextLine();
            }
            //закрываем поток.
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
