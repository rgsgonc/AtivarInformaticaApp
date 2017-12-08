package br.com.alura.ativarinformatica;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.alura.ativarinformatica.dao.ClienteDAO;
import br.com.alura.ativarinformatica.model.Cliente;

public class FormularioClientesActivity extends AppCompatActivity {
    private FormularioHelper helper;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_clientes);

        helper = new FormularioHelper (this);

        Intent intent = getIntent();
        Cliente cliente = (Cliente)  intent.getSerializableExtra("cliente");
        if (cliente !=null) {
            helper.preencheFormulario(cliente);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_formulario_ok:
                Cliente cliente = helper.pegaCliente();
                ClienteDAO dao = new ClienteDAO(this);

                if(cliente.getId() != null){
                    dao.altera(cliente);
                }else{
                    dao.insere(cliente);
                }
                dao.close();
                if(cliente.getId() != null){
                    Toast.makeText(getApplicationContext(), "Cliente " + cliente.getNome() + " alterado com sucesso!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Cliente " + cliente.getNome() + " salvo com sucesso!", Toast.LENGTH_SHORT).show();
                }

                finish();
                break;

        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cadastro_cliente, menu);
        return super.onCreateOptionsMenu(menu);

    }


}
