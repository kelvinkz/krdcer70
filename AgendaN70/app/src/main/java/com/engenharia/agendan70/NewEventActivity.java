package com.engenharia.agendan70;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import android.text.Editable;
import android.text.TextWatcher;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NewEventActivity extends AppCompatActivity {

    private EditText campoTitulo;
    private EditText campoLocal;
    private Spinner campoTipoEvento;
    private Spinner campoRepeticao;
    private EditText campoDataInicio;
    private EditText campoParticipante;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

        campoTitulo = (EditText) findViewById(R.id.campoTitulo);
        campoLocal = (EditText) findViewById(R.id.campoLocal);
        campoTipoEvento = (Spinner) findViewById(R.id.campoTipoEvento);
        campoRepeticao = (Spinner) findViewById(R.id.campoRepeticao);
        campoParticipante = (EditText) findViewById(R.id.campoParticipante);

        campoDataInicio = (EditText) findViewById(R.id.campoData);
        campoDataInicio.addTextChangedListener(mDateEntryWatcher);

        ArrayAdapter<String> adapterTipoEvento = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, new String[]{"Festa", "Casamento", "Reuniao", "Outro"});
        campoTipoEvento.setAdapter(adapterTipoEvento);

        ArrayAdapter<String> adapterRepeticao = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, new String[]{"Sem repetição", "Diariamente", "Semanalmente", "Mensalmente", "Anualmente"});
        campoRepeticao.setAdapter(adapterRepeticao);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void gotoSalvar(View view) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        try {
            c.setTime(df.parse(campoDataInicio.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String data = df.format(c.getTime());
        if (!campoRepeticao.getSelectedItem().toString().equals("Sem repetição")) {
            for (int i = 0; i < 30; i++) {
                AcessoBanco.getInstance(this).open();
                AcessoBanco.getInstance(this).insertCompromisso(data, campoTitulo.getText().toString(), campoLocal.getText().toString(), campoParticipante.getText().toString(), campoTipoEvento.getSelectedItem().toString(), campoRepeticao.getSelectedItem().toString());
                AcessoBanco.getInstance(this).close();

                switch (campoRepeticao.getSelectedItem().toString()) {
                    case "Diariamente":
                        c.add(Calendar.DAY_OF_YEAR, 1);
                        break;
                    case "Semanalmente":
                        c.add(Calendar.DAY_OF_YEAR, 7);
                        break;
                    case "Mensalmente":
                        c.add(Calendar.MONTH, 1);
                        break;
                    case "Anualmente":
                        c.add(Calendar.YEAR, 1);
                        break;
                }
                data = df.format(c.getTime());
            }
        } else {
            AcessoBanco.getInstance(this).open();
            AcessoBanco.getInstance(this).insertCompromisso(data, campoTitulo.getText().toString(), campoLocal.getText().toString(), campoParticipante.getText().toString(), campoTipoEvento.getSelectedItem().toString(), campoRepeticao.getSelectedItem().toString());
            AcessoBanco.getInstance(this).close();
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void gotoCancelar(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private TextWatcher mDateEntryWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String working = s.toString();
            boolean isValid = true;
            if (working.length() == 2 && before == 0) {
                if (Integer.parseInt(working.substring(0,2)) < 1 || Integer.parseInt(working.substring(0,2)) > 31) {
                    isValid = false;
                } else {
                    working += "/";
                    campoDataInicio.setText(working);
                    campoDataInicio.setSelection(working.length());
                }
            } else if (working.length() == 5 && before == 0) {
                if (Integer.parseInt(working.substring(3,5)) < 1 || Integer.parseInt(working.substring(3,5)) > 12) {
                    isValid = false;
                } else {
                    working += "/";
                    campoDataInicio.setText(working);
                    campoDataInicio.setSelection(working.length());
                }
            } else if (working.length() == 10 && before == 0) {
                String enteredYear = working.substring(6);
                int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                if (Integer.parseInt(enteredYear) < currentYear) {
                    isValid = false;
                }
            } else if (working.length() != 10) {
                isValid = false;
            }

            if (!isValid) {
                campoDataInicio.setError("Enter a valid date: DD/MM/YYYY");
            } else {
                campoDataInicio.setError(null);
            }

        }

        @Override
        public void afterTextChanged(Editable s) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

    };

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "NewEvent Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.engenharia.agendan70/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "NewEvent Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.engenharia.agendan70/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
