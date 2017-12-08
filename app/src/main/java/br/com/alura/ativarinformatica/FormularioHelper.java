package br.com.alura.ativarinformatica;

import android.widget.EditText;

import java.io.Serializable;

import br.com.alura.ativarinformatica.model.Cliente;


/**
 * Created by rafael on 04/12/17.
 */

public class FormularioHelper implements Serializable{
    private EditText campoNome;
    private EditText campoEndereco;
    private EditText campoTelefone;
    private EditText campoSite;
    private Cliente cliente;

    public FormularioHelper(FormularioClientesActivity activity) {
        campoNome = activity.findViewById(R.id.cadastrar_cliente_nome);
        campoEndereco = activity.findViewById(R.id.cadastrar_cliente_endereco);
        campoTelefone = activity.findViewById(R.id.cadastrar_cliente_telefone);
        campoSite = activity.findViewById(R.id.cadastrar_cliente_site);
        cliente = new Cliente();
    }

    public Cliente pegaCliente()   {
        //Cliente cliente = new Cliente ();
        cliente.setNome(campoNome.getText().toString());
        cliente.setEndereco(campoEndereco.getText().toString());
        cliente.setTelefone(campoTelefone.getText().toString());
        cliente.setSite(campoSite.getText().toString());
        return cliente;
    }

    public void preencheFormulario(Cliente cliente) {
        campoNome.setText(cliente.getNome());
        campoEndereco.setText(cliente.getEndereco());
        campoTelefone.setText(cliente.getTelefone());
        campoSite.setText(cliente.getSite());
        this.cliente = cliente;
    }
}
