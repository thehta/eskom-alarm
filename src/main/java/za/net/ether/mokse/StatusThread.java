package za.net.ether.mokse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class StatusThread extends Thread {

    public int stage = -1;
    public int prevStage = 0;
    private Status newStatus;
    private String URL;
    public Status status;

    ExecutorService executorService = Executors.newSingleThreadExecutor();

    StatusThread(String GETSTATUS_URL) {
        this.URL = GETSTATUS_URL;
        System.out.println("Starting app thread");
    }

    public void run() {
        while (true) {
            futureHandler(getStatusFromEskom());
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }

    public Status getStatus() {
        return this.status;
    }

    public void futureHandler(Future<Status> newStatus) {
        try {
            if (newStatus.get().getStage() > 0) {
                status = newStatus.get();
            } else {
                // do nothing
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public Future<Status> getStatusFromEskom() {
        Future<Status> future = executorService.submit(() -> {
            URL url = new URL("https://loadshedding.eskom.co.za/LoadShedding/GetStatus");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int status = connection.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer content = new StringBuffer();
            String inputLine = in.readLine();
            in.close();
            connection.disconnect();
            int stage = Integer.parseInt(inputLine);

            StringBuilder fullResponseBuilder = new StringBuilder();
            String date = connection.getHeaderField("Date");
            return new Status(date, stage);
        });
        return future;
    }

    public int getStage() {
        if (prevStage != stage) {
            return prevStage;
        }
        return stage;
    }
}
