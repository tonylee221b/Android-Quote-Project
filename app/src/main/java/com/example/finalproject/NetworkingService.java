package com.example.finalproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NetworkingService {
    private final String quoteURL = "https://api.quotable.io/random";
    private final String imgMainURL = "https://picsum.photos/seed/";
    private final String imgRandomStr1 = "map";
    private final String imgRandomStr2 = "final";
    private final String imgSize = "/400?blur=1";

    private HttpURLConnection httpURLConnection = null;

    public static ExecutorService networkExecutorService = Executors.newFixedThreadPool(4);
    public static Handler networkHandler = new Handler(Looper.getMainLooper());

    interface NetworkingListener {
        void quoteListener(String json);
        void imgListener(Bitmap img, String imgURL);
    }

    public NetworkingListener listener;

    public void getQuote() {
        networkExecutorService.execute(new Runnable() {
            @Override
            public void run() {

                try {
                    String jsonStr = "";
                    URL urlObj = new URL(quoteURL);

                    httpURLConnection = (HttpURLConnection)urlObj.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setRequestProperty("Content-Type", "application/json");

                    InputStream in = httpURLConnection.getInputStream();
                    InputStreamReader reader = new InputStreamReader(in);

                    int data = 0;
                    while ((data = reader.read()) != -1) {
                        char cur = (char)data;
                        jsonStr += cur;
                    }

                    final String finalJsonStr = jsonStr;
                    networkHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.quoteListener(finalJsonStr);
                        }
                    });
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                    networkExecutorService.shutdown();
                } finally {
                    httpURLConnection.disconnect();
                }
            }
        });
    }

    public void getImage() {
        Random rand = new Random();
        rand.setSeed(System.currentTimeMillis());

        String imgURL = imgMainURL + imgRandomStr1 + (rand.nextInt(1000)) + imgRandomStr2 + (rand.nextInt(100)) + imgSize;

        networkExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL urlObj = new URL(imgURL);

                    httpURLConnection = (HttpURLConnection)urlObj.openConnection();
                    InputStream urlIn = new BufferedInputStream(urlObj.openStream());

                    Bitmap bitmap = BitmapFactory.decodeStream(urlIn);

                    networkHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.imgListener(bitmap, imgURL);
                        }
                    });
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                    networkExecutorService.shutdown();
                } finally {
                    httpURLConnection.disconnect();
                }
            }
        });
    }

    public void getImageSelected(String url) {
        networkExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL urlObj = new URL(url);

                    httpURLConnection = (HttpURLConnection)urlObj.openConnection();
                    InputStream urlIn = new BufferedInputStream(urlObj.openStream());

                    Bitmap bitmap = BitmapFactory.decodeStream(urlIn);

                    networkHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.imgListener(bitmap, url);
                        }
                    });

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                    networkExecutorService.shutdown();
                } finally {
                    httpURLConnection.disconnect();
                }
            }
        });
    }
}
