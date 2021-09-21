package com.example.covidapp.Dashboard;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.covidapp.R;
import com.example.covidapp.databinding.FragmentCountyNumbersBinding;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CountyNumbersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CountyNumbersFragment extends Fragment {

    private FragmentCountyNumbersBinding binding;
    Button buttonweek;
    Button buttonMonth;
    Button buttonSwitch;
    Boolean aBooleanDistAdmSwitch;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CountyNumbersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CountyNumbersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CountyNumbersFragment newInstance(String param1, String param2) {
        CountyNumbersFragment fragment = new CountyNumbersFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCountyNumbersBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //get intent data and assign to the right fields
        Intent intent = getActivity().getIntent();
        Data DistData = (Data) intent.getSerializableExtra("list");
        TextView textView_countyname = binding.textView;
        TextView textView_countyID = binding.textView2;
        TextView textView_CountyNumber = binding.textView3;
        aBooleanDistAdmSwitch = intent.getExtras().getBoolean("mode");
        buttonweek = binding.buttonweek;
        buttonMonth = binding.buttonmonth;
        buttonSwitch = binding.buttonSwitchDistAdm;
        if ( aBooleanDistAdmSwitch){
            buttonSwitch.setVisibility(View.INVISIBLE);
        }
        else buttonSwitch.setVisibility(View.VISIBLE);
        //buttonSwitch.setVisibility(View.INVISIBLE);
        //set textviews to the names according to the selected county
        textView_countyname.setText("County "+DistData.getName());
        textView_countyID.setText("ID "+DistData.getId());
        textView_CountyNumber.setText("number "+DistData.getNumber());

        //set inital view for listview
        //mode =0 --> Week
        //mode = 1 --> Month
        setViewForList(DistData,0);

        //Buttons to change view to month or week
        buttonweek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setViewForList(DistData,0);

            }
        });
        buttonMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setViewForList(DistData,1);
            }
        });
    }

    //set Listview to either Montly or weekly
    private void setViewForList(Data data, int mode) {
        ArrayAdapter<Weekly> arrayAdapter;
        ArrayList<Weekly> weeklist = data.getWeeklyDist();
        ArrayList<Monthly> monthlist = data.getMonthly();
        ListView listView = binding.listviewWeek;
        listView.setAdapter(null);
        //set listview to week
        if (mode == 0) {
            arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_2, android.R.id.text1, weeklist) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                    TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                    text1.setText("Vecka " + Integer.toString(weeklist.get(position).getWeek()));
                    text2.setText(Integer.toString(weeklist.get(position).getAmountDist()));
                    return view;
                }
            };

            listView.setAdapter(arrayAdapter);
        }
        //set listview to month
        else {
            arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_2, android.R.id.text1, monthlist) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                    TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                    text1.setText(monthlist.get(position).getMonth());
                    text2.setText(Integer.toString(monthlist.get(position).getAmount()));
                    return view;
                }
            };

            listView.setAdapter(arrayAdapter);

        }
    }
}