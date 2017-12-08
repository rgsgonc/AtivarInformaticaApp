package br.com.alura.ativarinformatica;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Browser;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import br.com.alura.ativarinformatica.dao.ClienteDAO;
import br.com.alura.ativarinformatica.model.Cliente;

public class ListaClientesActivity extends AppCompatActivity {

    private ListView listaClientes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_clientes);

        listaClientes = findViewById(R.id.lista_clientes);

        listaClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cliente cliente = (Cliente) listaClientes.getItemAtPosition(position);
                Intent intentVaiProCadastroCliente = new Intent(getApplicationContext(), FormularioClientesActivity.class); startActivity(intentVaiProCadastroCliente);
                intentVaiProCadastroCliente.putExtra("cliente",cliente);
                startActivity(intentVaiProCadastroCliente);
            }
        });

        Button novoCliente = findViewById (R.id.btn_add_lista_clientes);
        novoCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vaiParaCadastroCliente = new Intent(getApplicationContext(), FormularioClientesActivity.class);
                startActivity(vaiParaCadastroCliente);
            }
        });
        registerForContextMenu(listaClientes);
    }

    private void carregaLista() {
        ClienteDAO dao = new ClienteDAO(this);
        List<Cliente> clientes = dao.buscaClientes();
        dao.close();

        ListView listaClientes = findViewById(R.id.lista_clientes);
        ArrayAdapter<Cliente> adapter = new ArrayAdapter<Cliente>(this,  android.R.layout.simple_list_item_1, clientes);
        listaClientes.setAdapter(adapter);
    }


    @Override
    protected void onResume() {
        super.onResume();
        carregaLista();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo)  {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Cliente cliente = (Cliente) listaClientes.getItemAtPosition(info.position);

        //Ligar
        MenuItem itemLigar = menu.add("Ligar");
        itemLigar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ListaClientesActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 123);
                }else{
                    Intent intentLigar = new Intent(Intent.ACTION_CALL);
                    intentLigar.setData(Uri.parse("tel:" + cliente.getTelefone()));
                    startActivity(intentLigar);
                }
                return false;
            }
        });

        //Menu SMS
        MenuItem itemSMS = menu.add("Enviar SMS");
        Intent intentSMS = new Intent(Intent.ACTION_VIEW) ;
        intentSMS.setData(Uri.parse("sms:" + cliente.getTelefone()));
        itemSMS.setIntent(intentSMS);

        //Menu Endereco
        MenuItem itemMapa = menu.add("Visualizar no mapa");
        Intent intentMapa = new Intent(Intent.ACTION_VIEW);
        intentMapa.setData(Uri.parse("geo:0,0?q=" + cliente.getEndereco()));
        itemMapa.setIntent(intentMapa);

        //Menu site
        MenuItem itemSite = menu.add("Visitar site");
        Intent intentSite = new Intent(Intent.ACTION_VIEW);
        String site = cliente.getSite();
        if(!site.startsWith("http://")){
            site = "http://" + site;
        }
        intentSite.setData(Uri.parse(site));
        itemSite.setIntent(intentSite);

        //Menu deletar
        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                ClienteDAO dao = new ClienteDAO(getApplicationContext());
                dao.deleta(cliente);
                dao.close();
                carregaLista();

                return false;
            }
        });
    }


}


