package com.example.thegerman.simpletodo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class Edit extends android.support.v4.app.DialogFragment{

    Button btnSave;
    EditText edtItem;
    MonConnecteur monConnecteur;
    public static int pos;
    public static String contenu;
    FragmentManager fm;
    MainActivity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.editdialog, null);
        btnSave = (Button) rootView.findViewById(R.id.btnSave);
        edtItem = (EditText) rootView.findViewById(R.id.edtItem);
        fm = getFragmentManager();
        mainActivity = (MainActivity) getActivity();

        try{
            edtItem.setText(contenu);
        }catch (Exception e){

        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.onEditItem(pos, edtItem.getText().toString());
                dismiss();
            }
        });

        return rootView;
    }

    public static Edit newInstance(int position, String cont){
        Edit e = new Edit();
        e.setData(position, cont);
        return e;
    }

    public  void setData(int i, String c){
        pos = i;
        contenu  = c;
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof MonConnecteur){
            monConnecteur = (MonConnecteur) activity; // appel de la classe interface MonConnecteur: Transfert de donnees Fragment/Activite
        }
    }


}

