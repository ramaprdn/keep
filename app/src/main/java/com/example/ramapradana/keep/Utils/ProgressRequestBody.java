package com.example.ramapradana.keep.Utils;

import android.os.Looper;
import android.support.annotation.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Handler;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

public class ProgressRequestBody extends RequestBody {
    private File file;
    private UploadCallbackListener uploadCallbackListener;
    private static final int DEFAULT_BUFFER_SIZE = 4096;

    public ProgressRequestBody(File file, UploadCallbackListener uploadCallbackListener) {
        this.file = file;
        this.uploadCallbackListener = uploadCallbackListener;
    }

    @Nullable
    @Override
    public MediaType contentType() {
        return MediaType.parse("*/*");
    }

    @Override
    public long contentLength() throws IOException {
        return file.length();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        long fileLength = file.length();
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        FileInputStream in = new FileInputStream(file);
        long uploaded = 0;
        try{
            int read;
            android.os.Handler handler = new android.os.Handler(Looper.getMainLooper());
            while((read = in.read(buffer)) != -1){
                handler.post(new ProgressUpdater(uploaded, fileLength));
                uploaded += read;
                sink.write(buffer, 0, read);
            }
        }finally {
            in.close();
        }
    }

    private class ProgressUpdater implements Runnable {
        private long uploaded;
        private long fileLength;

        public ProgressUpdater(long uploaded, long fileLength) {
            this.fileLength = fileLength;
            this.uploaded = uploaded;
        }

        @Override
        public void run() {
            uploadCallbackListener.onProgressUpdate((int) (100 * uploaded / fileLength));
        }
    }
}
