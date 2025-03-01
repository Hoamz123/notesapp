package hoamz.notesapp.ui.frag;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hoamz.notesapp.R;

public class FragmentNote extends Fragment {



    public FragmentNote() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FragmentNote newInstance(String param1, String param2) {
        FragmentNote fragment = new FragmentNote();
        Bundle args = new Bundle();
        //logic set du lieu
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           //logic get du lieu
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note, container, false);
    }


}