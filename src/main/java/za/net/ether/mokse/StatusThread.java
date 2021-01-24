package za.net.ether.mokse;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class StatusThread extends Thread {

    public volatile static Status status;
    public int stage = -1;
    public int prevStage = 0;
    private Status newStatus;
    private String URL;

    ExecutorService executorService = Executors.newSingleThreadExecutor();

    StatusThread(String GETSTATUS_URL) {
        this.URL = GETSTATUS_URL;
    }

    public void run() {
        try {
            while (true) {
                Future<Status> futureStatus = getStatusFromEskom();
                for (int i = 0; i < 2; i++) {
                    if (futureStatus.isDone()) {
                        if ((newStatus = futureStatus.get()).getStage() > -1 ) {
                            this.status = newStatus;
                            break;
                        }
                    }
                    Thread.sleep(1000);   
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public Future<Status> getStatusFromEskom() {
        Future<Status> future = executorService.submit(() -> {
            // TODO: ERROR HANDLING
            URL url = new URL(URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            int stage = Integer.parseInt(in.readLine());
            in.close();
            connection.disconnect();
            String date = connection.getHeaderField("Date");
            return new Status(date, stage-1);
        });
        return future;
    }
}
