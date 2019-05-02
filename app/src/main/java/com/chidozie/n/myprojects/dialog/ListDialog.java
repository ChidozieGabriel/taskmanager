package com.chidozie.n.myprojects.dialog;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import static android.app.Activity.RESULT_OK;
import static com.chidozie.n.myprojects.util.Constants.EXTRA_CATEGORY;

/**
 * Created by Chidozie on 10/7/2018.
 */

public class ListDialog extends AppCompatDialogFragment {

    private static final String KEY_TEXTS = "key_texts";

    public static ListDialog newInstance(String[] texts) {

        Bundle args = new Bundle();
        args.putStringArray(KEY_TEXTS, texts);

        ListDialog fragment = new ListDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String[] texts = getArguments().getStringArray(KEY_TEXTS);
        assert texts != null;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
            android.R.layout.simple_list_item_1, texts);
        ListView listView = new ListView(getActivity());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                sendResult(position);
                ListDialog.this.dismiss();
            }
        });

        return new AlertDialog.Builder(getActivity())
            .setView(listView)
            .create();
    }

    private void sendResult(int category) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_CATEGORY, category);
        getTargetFragment()
            .onActivityResult(getTargetRequestCode(), RESULT_OK, intent);
    }

}
