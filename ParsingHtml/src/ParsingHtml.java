import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ParsingHtml {

    public static void main(String[] args) {
        String link = "https://people.sc.fsu.edu/~jburkardt/data/csv/csv.html";
        List<Thread> hilos = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(link).get();
            Elements enlaces = doc.select("a[href$=.csv]");
            for (Element enlace : enlaces) {
                String urlCSV = enlace.attr("abs:href");
                String nombreCSV = enlace.text();
                Thread hilo = new Thread(() -> {
                    try {
                        URL urlArchivo = new URL(urlCSV);
                        BufferedReader lector = new BufferedReader(new InputStreamReader(urlArchivo.openStream()));
                        int lineas = 0;
                        while (lector.readLine() != null) {
                            lineas++; }
                        System.out.println("Archivo: " + nombreCSV + ", Líneas: " + lineas);
                        lector.close();
                    } catch (IOException e) {
                        e.printStackTrace(); }});
                hilos.add(hilo);
                hilo.start();
            } for (Thread hilo : hilos) {
                try {
                    hilo.join();
                } catch (InterruptedException exc) {
                    exc.printStackTrace();
                }}}
        catch (IOException exc) {
            exc.printStackTrace();
        }}
}