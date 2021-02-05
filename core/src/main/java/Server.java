import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;
import java.io.PrintWriter;

public class Server {


    public static void main(String[] args) {

        ExecutorService executorService = Executors.newCachedThreadPool();


        try {
            ServerSocket serverSocket = new ServerSocket(5050);

            while(true){

                Socket socket = serverSocket.accept();

                executorService.execute(() -> handleConnection(socket));

            }



        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private static void handleConnection(Socket socket){

        try (BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()))){

            String headerLine = input.readLine();

            String[] header = headerLine.split(" ");

            switch (header[0]){

                case "GET":
                    System.out.println("Hämta GET klass");
                break;

                case "HEAD":
                    System.out.println("Hämta HEAD metod");
                break;

                case "POST":
                    System.out.println("Hämta POST metod");
                break;

                default:
                    System.out.println("404");
            }


            System.out.println(header[0]);



            while(true){
                headerLine = input.readLine();

                if(headerLine.isEmpty()){
                    break;
                }

                var output = new PrintWriter(socket.getOutputStream());
                String page = """
                        <html>
                        <head><title></title>
                        </head>
                        <body>
                        <h1>Hej</h1>
                        <div><form id="form" name="form" method="post" action=" ">
                               Förnamn:<br>
                               <input type="text" name="fnamn" id="fnamn" /> <br>
                               Efternamn:<br>
                               <input type="text" name="enamn" id="enamn" /> <br>
                               E-postadress:<br>
                               <input type="text" name="epost" id="epost" /> <br>
                               Meddelande:<br>
                               <textarea name="meddelande" id="meddelande" cols="45" rows="5"></textarea> <br>
                               <input type="submit" name="skicka" id="skicka" value="Skicka meddelandet" />
                               </form></div>
                        </body>
                        </html>""";

                output.println("HTTP/1.1 200 OK");
                output.println("Content-Length:" + page.getBytes().length);
                output.println("Content-Type:text/html");
                output.println("");

                output.print(page);
                output.flush();
            }




        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
