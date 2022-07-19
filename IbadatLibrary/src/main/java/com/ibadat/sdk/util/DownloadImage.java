package com.ibadat.sdk.util;

import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.ibadat.sdk.activities.HomeIbadatSdkActivity;

import java.io.File;

public class DownloadImage {
    public static void StartDownload(final Context context, final String ContentName, String DownloadedPhysicalFile, String requestType) {
        String ext = ".jpg";
        if (requestType.equals("wp")) {
            ext = ".jpg";
        } else if (requestType.equals("an")) {
            ext = ".gif";
        } else if (requestType.equals("vd")) {
            ext = ".mp4";
        } else if (requestType.equals("bft")) {
            ext = ".mp3";
        } else if (requestType.equals("tt")) {
            ext = ".wav";
        }

        File file = new File(Environment.getExternalStorageDirectory()
                + "/Ibadat");

        if (!file.exists()) {
            file.mkdirs();
        }
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(DownloadedPhysicalFile.replaceAll(" ", "%20")));
        request.setDescription(ContentName + " start to download");
        request.setTitle(ContentName);
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setAllowedOverRoaming(false);
        request.setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                ContentName + ext);
        request.setVisibleInDownloadsUi(true);
        final DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

        final long downloadId = manager.enqueue(request);
        final String finalExt = ext;
        new Thread(() -> {
            boolean downloading = true;
            while (downloading) {
                DownloadManager.Query q = new DownloadManager.Query();
                q.setFilterById(downloadId);

                Cursor cursor = manager.query(q);
                cursor.moveToFirst();
                int bytes_downloaded = cursor.getInt(cursor
                        .getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                int bytes_total = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));

                if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                    downloading = false;
                }
                final int dl_progress = (int) ((double) bytes_downloaded / (double) bytes_total * 100f);
                try {
                    ((HomeIbadatSdkActivity) context).runOnUiThread(() -> {
                        if (dl_progress == 100) {
                            try {
                                String downloadedType = "";
                                if (finalExt.equals(".jpg")) {
                                    downloadedType = "WP";
                                } else if (finalExt.equals(".gif")) {
                                    downloadedType = "AN";
                                } else if (finalExt.equals(".mp4")) {
                                    downloadedType = "VD";
                                } else if (finalExt.equals(".mp3")) {
                                    downloadedType = "BFT";
                                } else if (finalExt.equals(".wav")) {
                                    downloadedType = "TT";
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(context, "Download has been completed", Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d("Download Status", statusMessage(cursor));
                cursor.close();
            }
        }).start();
    }

    private static String statusMessage(Cursor c) {
        String msg = "???";
        switch (c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS))) {
            case DownloadManager.STATUS_FAILED:
                msg = "Download failed!";
                break;
            case DownloadManager.STATUS_PAUSED:
                msg = "Download paused!";
                break;
            case DownloadManager.STATUS_PENDING:
                msg = "Download pending!";
                break;
            case DownloadManager.STATUS_RUNNING:
                msg = "Download in progress!";
                break;
            case DownloadManager.STATUS_SUCCESSFUL:
                msg = "Download complete!";
                break;
            default:
                msg = "Download is nowhere in sight";
                break;
        }
        return (msg);
    }
}
