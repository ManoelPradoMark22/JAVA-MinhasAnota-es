package minhasanotacoes.cursoandroid.com.minhasanotacoes;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends Activity {

    private EditText texto;
    private ImageView botaoSalvar;
    //boa pratica criar uma constante para o nome do arquivo, pq assim se quisermos editar o nome, editamos aqui, em um lugar apenas
    private static final String NOME_ARQUIVO = "arquivo_anotacao.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        texto = (EditText) findViewById(R.id.textoId);
        botaoSalvar = (ImageView) findViewById(R.id.botaoSalvarId);

        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String textoDigitado = texto.getText().toString();
                gravarNoArquivo(textoDigitado);
                Toast.makeText(MainActivity.this, "Anotaçao salva com sucesso!", Toast.LENGTH_SHORT).show();
            }
        });
        //Recuperar o que foi gravado
        if (lerArquivo()!=null)
        {
            texto.setText(lerArquivo());
        }

    }

    private void gravarNoArquivo(String texto){
        try{//ele tenta executar o que esta dentro do try
            //faz a gravação do arquivo
            //no construtor da classe passamos o arquivo que nos iremos utilizar para ser gravado
            //MODE_PRIVATE indica que apenas nossa app pode ler o arquivo
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(NOME_ARQUIVO, Context.MODE_PRIVATE));
            outputStreamWriter.write(texto);
            outputStreamWriter.close();

        }catch (IOException e){ //IOEXCEPTION é uma exceçao que é lançada sempre que vc tenta fazer uma operação de input/output
            //ou seja, inserir ou ler arquivos
            //caso ele nao execute, vai para o catch e lança a excecão
            Log.v("MainActivity", e.toString());
        }
    }

    //nao é void, pq retorna algo
    private String lerArquivo (){
        String resultado = "";

        try{
            //abrir o arquivo
            InputStream arquivo = openFileInput(NOME_ARQUIVO);
            if(arquivo!=null){
                //ler o arquivo
                //passamos o arquivo como parâmetro do construtor do InputStreamReader()
                InputStreamReader inputStreamReader = new InputStreamReader(arquivo);

                //Gerar um Buffer do arquivo que já foi lido inputStreamReader
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                //recuperar os textos dos arquivos
                //readLine lê linha por linha do arquivo e podemos recuperar as linhas já salvas
                //enquanto tiver texto no arquivo ele continua fazendo a leitura, quando nao tem mais ele retorna null
                String linhaArquivo = "";
                //linhaArquivo recebe apenas as linhas que tem texto
                while ( (linhaArquivo = bufferedReader.readLine() )!=null){
                    //vai gravando, linha por linha!
                    resultado += linhaArquivo;
                }
                arquivo.close();
            }

        }catch (IOException e){
            Log.v("MainActivity", e.toString());
        }
        return resultado;
    }
}
