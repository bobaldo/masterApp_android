package davidepatrizi.com.scadenzarioauto.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import davidepatrizi.com.scadenzarioauto.MezzoActivity;
import davidepatrizi.com.scadenzarioauto.R;

public class ChooseFragment extends Fragment implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_choose, container, false);
        ((ImageButton) layout.findViewById(R.id.btnInfo)).setOnClickListener(this);
        ((ImageButton) layout.findViewById(R.id.btnScadenze)).setOnClickListener(this);
        ((ImageButton) layout.findViewById(R.id.btnTagliandi)).setOnClickListener(this);
        return layout;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnInfo:
                ((MezzoActivity) getActivity()).showInfo();
                break;

            case R.id.btnScadenze:
                ((MezzoActivity) getActivity()).showScadenza();
                break;

            case R.id.btnTagliandi:
                ((MezzoActivity) getActivity()).showTagliando();
                break;
        }
    }
}