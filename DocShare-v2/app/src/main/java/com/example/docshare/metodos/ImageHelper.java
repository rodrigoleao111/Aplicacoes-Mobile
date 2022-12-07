package com.example.docshare.metodos;

import android.content.Context;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

/***
 * Métodos auxiliares no tratamento de imagens
 */
public interface ImageHelper {

    /***
     * Arredondar cantos de bitmap
     * @param bitmap - imagem a ser modificada
     * @param pixels - raio do arredondamento
     * @return - bitmap de imagem arredondada
     */
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    /***
     * Transformar bitmap em file e retornar path
     * @param bitmap - imagem recebida
     * @return - String path file criado
     */
    public static String getPathFromBitmap(Bitmap bitmap, Context context, String filename) throws IOException {

        boolean finish = false;
        StringBuilder filenameBuilder = new StringBuilder(filename);
        File file = null;

        while (!finish) {
            file = new File(context.getCacheDir(), filename);

            if (file.createNewFile()) {
                // Atribuir bitmap ao file
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
                byte[] bitmapdata = bos.toByteArray();

                FileOutputStream fos = new FileOutputStream(file);
                fos.write(bitmapdata);
                fos.flush();
                fos.close();

                finish = true;
            } else {
                // Modificar o filename
                Random r = new Random();
                char c = (char)(r.nextInt(26) + 'a');
                filenameBuilder.insert(0, c);
                filename = filenameBuilder.toString();
            }
        }


        return file.getAbsolutePath();
    }

    public static byte[] getByteArrayFromBitmap(Bitmap bitmap){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
        bitmap.recycle();
        return bos.toByteArray();
    }

    public static Uri getUriFromTumbnailBitmap(Context inContext, Bitmap inImage) {
        Bitmap OutImage = Bitmap.createScaledBitmap(inImage, inImage.getWidth()*10, inImage.getHeight()*10,true);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), OutImage, "Title", null);
        return Uri.parse(path);
    }

    public static String getRealPathFromURI(Uri uri, Context inContext) {
        Cursor cursor = inContext.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    public static Bitmap getBitmapFromUri(Uri uri, Context context) throws IOException {
        return MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);

    }
}
