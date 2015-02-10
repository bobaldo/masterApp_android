package it.unipi.di.masterapp.favorites;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PageList extends ListFragment {

    private List<String> urls;
    private List<String> labels;

    private ArrayAdapter arrayAdapter;

    public interface OnPageSelectedListener {

        // stessi argomenti di onListItemClick()
        public void onPageSelected(ListView lv, int position);
    }

    // da chiamare quando una pagina è selezionata; tutto va delegato alla Activity
    private OnPageSelectedListener callback;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // ne siamo sicuri, altrimenti occorre controllare
        callback = (OnPageSelectedListener) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // recupera gli array
        urls = new ArrayList<String>();
        urls.addAll(Arrays.asList(getResources().getStringArray(R.array.urls)));
        labels = new ArrayList<String>();
        labels.addAll(Arrays.asList(getResources().getStringArray(R.array.labels)));

        // carica le etichette dalle risorse
        arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_activated_1, labels);
        setListAdapter(arrayAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        // chiede alla Activity di visualizzare la pagina
        callback.onPageSelected(l, position);
    }

    public void startActivityFor(int position) {
        String url = urls.get(position);
        String label = labels.get(position);
        Intent intent = new Intent(getActivity(), PageDisplayActivity.class);
        intent.putExtra("label", label);
        intent.putExtra("url", url);
        getActivity().startActivity(intent);
    }

    public String getUrl(int position) {
        return urls.get(position);
    }

    public void addPage(String label, String url) {
        labels.add(label);
        urls.add(url);
        arrayAdapter.notifyDataSetChanged();
    }
}
