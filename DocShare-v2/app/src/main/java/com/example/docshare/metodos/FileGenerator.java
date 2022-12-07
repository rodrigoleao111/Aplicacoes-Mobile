package com.example.docshare.metodos;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.docshare.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileGenerator extends AppCompatActivity {

    // Construtor vazio
    public FileGenerator(){}


    /***
     * Criar arquivo PDF com as informações inseridas pelo usuário
     * @param coletarInformacoes : bundle com as informações de texto inseridas pelo usuário
     * @param bitmap : imagem original anexada pelo usuário
     */
    public void GerarPDF(Bundle coletarInformacoes, Bitmap bitmap) throws IOException {

        // Chaves e informações
        String[] infoColaborador = {"Nome", "RG", "CPF", "Setor", "Cargo", "Telefone", "E-mail"};
        String[] infoEquipamento = {"Locação", "Equipamento", "Modelo", "ID"};
        String[] infoManutencao = {"Diagnóstico", "Solução", "Peças trocadas", "Observações"};
        String[] chavesColaborador = {"nome", "rg", "cpf", "setor", "cargo", "telefone", "email"};
        String[] chavesEquipamento = {"locacao", "equipamento", "modelo", "equipID"};
        String[] chavesManutencao = {"diagnostico", "solucao", "troca", "obs"};



        // Inicialização do pdf
        PdfDocument pdfRelatorio = new PdfDocument();
        Paint myPaint = new Paint();

        // Informações da página
        int pageWidth = 2480, pageHeight = 3508;  // Tamanho A4
        int y = 200, marginLeft = 115, center = pageWidth/2, marginRight = pageWidth-115,
                verticalSpacing = 50, imageSize = 900;
        float titleSize = 90.0f, subTitleSize = 70.0f, textSize = 40.0f, topcSize = 30.0f;
        PdfDocument.PageInfo infoRelatorio = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create();
        PdfDocument.Page pagRelatorio = pdfRelatorio.startPage(infoRelatorio);
        Canvas canvas = pagRelatorio.getCanvas();

        // Layout da página


        // Título
        myPaint.setTextAlign(Paint.Align.CENTER);
        myPaint.setTextSize(titleSize);
        myPaint.setFakeBoldText(true);
        y += 90;
        canvas.drawText("Ordem de Serviço - " + coletarInformacoes.getString("formID"), center, y, myPaint);

        // Subtítulo
        myPaint.setTextSize(subTitleSize);
        myPaint.setFakeBoldText(false);
        myPaint.setColor(Color.rgb(112, 119, 119));
        y += 90;
        canvas.drawText("Manutenção corretiva", center, y, myPaint);

        // Data de preenchimento
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        y += 90;
        myPaint.setTextSize(subTitleSize - 10.0f);
        canvas.drawText(formatter.format(date), center, y, myPaint);

        // Informações do usuário
        myPaint.setTextAlign(Paint.Align.LEFT);
        myPaint.setTextSize(topcSize);
        y += 3*verticalSpacing;
        canvas.drawText("Informações do colaborador", marginLeft, y, myPaint);

        myPaint.setTextAlign(Paint.Align.LEFT);
        myPaint.setTextSize(textSize);
        myPaint.setColor(Color.BLACK);

        y += verticalSpacing;
        for (int i = 0; i < infoColaborador.length; i++) {
            canvas.drawText(infoColaborador[i] + ":", marginLeft, y, myPaint);
            canvas.drawText(coletarInformacoes.getString(chavesColaborador[i]), marginLeft + 200, y, myPaint);
            y += verticalSpacing;
        }

        y += 3*verticalSpacing;

        // Informações do equipamento
        myPaint.setColor(Color.rgb(112, 119, 119));
        myPaint.setTextAlign(Paint.Align.LEFT);
        myPaint.setTextSize(topcSize);
        canvas.drawText("Equipamento | Ativo", marginLeft, y, myPaint);

        myPaint.setTextAlign(Paint.Align.LEFT);
        myPaint.setTextSize(textSize);
        myPaint.setColor(Color.BLACK);

        y += verticalSpacing;

        for (int i = 0; i < infoEquipamento.length; i++) {
            canvas.drawText(infoEquipamento[i] + ":", marginLeft, y, myPaint);
            canvas.drawText(coletarInformacoes.getString(chavesEquipamento[i]), marginLeft + 300, y, myPaint);
            y += verticalSpacing;
        }

        y += 3*verticalSpacing;

        // Informações de Manutenção
        myPaint.setColor(Color.rgb(112, 119, 119));
        myPaint.setTextAlign(Paint.Align.LEFT);
        myPaint.setTextSize(topcSize);
        canvas.drawText("Informações de Manutenção", marginLeft, y, myPaint);

        myPaint.setTextAlign(Paint.Align.LEFT);
        myPaint.setTextSize(textSize);
        myPaint.setColor(Color.BLACK);

        y += verticalSpacing;

        for (int i = 0; i < infoManutencao.length; i++) {
            canvas.drawText(infoManutencao[i] + ":", marginLeft, y, myPaint);
            canvas.drawText(coletarInformacoes.getString(chavesManutencao[i]), marginLeft + 300, y, myPaint);
            y += verticalSpacing;
        }

        y += 3*verticalSpacing;

        // Imagem
        if(bitmap != null){
            Rect rectImage = new Rect( marginLeft, y, marginLeft+imageSize, y+imageSize);
            canvas.drawBitmap(bitmap, null, rectImage, myPaint);
            y += 2*verticalSpacing;
        }

        // Logo In Forma
        Resources res = getResources();
        Bitmap informaLogo = BitmapFactory.decodeResource(res, R.drawable.informalogopreto);
        Rect rect = new Rect(
                marginLeft,
                pageHeight - informaLogo.getHeight() - marginLeft,
                informaLogo.getWidth() + marginLeft,
                pageHeight - marginLeft);
        canvas.drawBitmap(informaLogo, null, rect, myPaint);

        pdfRelatorio.finishPage(pagRelatorio);

        // Construção do arquivo
        String nomeArquivo = "OSManutencao_" + coletarInformacoes.getString("formID") + ".pdf";
        File file = new File(UserInfo.getUserCredentials().getString("osPath"), nomeArquivo);
        file.setWritable(true);

        try {
            FileOutputStream fos = new FileOutputStream(file);
            pdfRelatorio.writeTo(fos);
            fos.flush();
            fos.close();
            Toast.makeText(getApplicationContext(), "arquivo gerado", Toast.LENGTH_SHORT).show();
            CompartilharRelatorio(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        pdfRelatorio.close();

    }


    public void CompartilharRelatorio(File file) {

        Uri pathUri = FileProvider.getUriForFile(
                getApplicationContext(),
                "com.example.docshare.provider",
                file);

        if(file.exists()){
            Intent intentShare = new Intent(Intent.ACTION_SEND);
            intentShare.setType("application/pdf");
            intentShare.putExtra(Intent.EXTRA_STREAM, Uri.parse(pathUri.toString()));
            startActivity(Intent.createChooser(intentShare, "Compartilhar Ordem de Serviço"));
        } else {
            Toast.makeText(getApplicationContext(), "Arquivo não encontrado", Toast.LENGTH_SHORT).show();
        }
    }


}
