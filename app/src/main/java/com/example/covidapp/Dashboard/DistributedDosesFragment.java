package com.example.covidapp.Dashboard;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.covidapp.R;
import com.example.covidapp.databinding.FragmentDistributedDosesBinding;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DistributedDosesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DistributedDosesFragment extends Fragment {

    private FragmentDistributedDosesBinding binding;

    Boolean buttonSwitchBool;
    ArrayList<Data> datalist;
    Boolean booleanReady;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DistributedDosesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DistributedDosesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DistributedDosesFragment newInstance(String param1, String param2) {
        DistributedDosesFragment fragment = new DistributedDosesFragment();
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
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
            System.out.println("WTFFFFF");
        }
//        buttonSwitchBool = getArguments().getBoolean("mode");
//        if(buttonSwitchBool) System.out.println("BOOLEAN IS TRUE");
//        else System.out.println("BOOLEAN IS FUCKEN FALSE AGEN");
        Bundle bundle = getArguments();
        if(bundle != null) {
            buttonSwitchBool = bundle.getBoolean("mode");
//            System.out.println("THIS AINT NULL YALL");
        }
        else {
            buttonSwitchBool = false;
//            Navigation.findNavController(view).navigate(R.id.action_nav_distributed_doses_to_nav_dashboard);
//            System.out.println("BUNDLE IS NULL");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDistributedDosesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        booleanReady =false;

//        Bundle bundle = getArguments();
//        if(bundle != null) {
//            buttonSwitchBool = bundle.getBoolean("mode");
//            System.out.println("THIS AINT NULL YALL");
//        }
//        else {
//            buttonSwitchBool = false;
////            Navigation.findNavController(view).navigate(R.id.action_nav_distributed_doses_to_nav_dashboard);
//            System.out.println("BUNDLE IS NULL");
//        }

//        Intent intentExtras = getActivity().getIntent();
//        buttonSwitchBool = intentExtras.getExtras().getBoolean("mode");




        //Declaration
        ListView listView;
        ArrayList<String> conties;
        ArrayList<String> months;
        ArrayAdapter<Data> arrayAdapter;
        EditText etSearch;
        listView =  binding.listview;
        etSearch = binding.edittext;

        //assign arrays to demo data
        conties = createdummydatacounties();
        months = createdummydatamonths();

        //user classes that contain weekly and monthly dose information
        ArrayList<Weekly> weeklist ;
        ArrayList<Monthly> Monthlist ;

        //filling the list with demo data
        Monthlist = createMonths(months,null,1);
        weeklist = createWeeks(null,null,null,null,1);
        datalist = createData(Monthlist, weeklist, conties);

        //setting up the listview to show conties
        arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, datalist);
        listView.setAdapter(arrayAdapter);

        //for the search to work
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                arrayAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        //when clicking on item in list, that countys data get sent to the intent
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity().getApplicationContext(),datalist.get(i).getName(),Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(getActivity().getBaseContext(), DisplayCountyNumbersActivity.class);

                Fragment fragment = new Fragment();
                Bundle bundle = new Bundle();

                bundle.putString("name", datalist.get(i).getName());
                bundle.putString("id",Integer.toString(datalist.get(i).getId()));
                bundle.putString("number", Integer.toString(datalist.get(i).getNumber()));
                bundle.putSerializable("list", datalist.get(i));
                if(!buttonSwitchBool) bundle.putBoolean("mode", true);

                fragment.setArguments(bundle);

//                intent.putExtra("name", datalist.get(i).getName());
//                intent.putExtra("id",Integer.toString(datalist.get(i).getId()));
//                intent.putExtra("number",Integer.toString(datalist.get(i).getNumber()));
//                intent.putExtra("list",datalist.get(i));
//                if ( !buttonSwitchBool) intent.putExtra("mode",true);


//                startActivity(intent);
                Navigation.findNavController(view).navigate(R.id.action_nav_distributed_doses_to_nav_county_numbers);
            }
        });
    }

    //making the final dataclass for all conties
    public ArrayList<Data> createData(ArrayList<Monthly> monthly,ArrayList<Weekly> weekly,ArrayList<String> conties){
        ArrayList<Data> listofData = new ArrayList<>();
        for(int i=0;i<conties.size();i++){
            Data data = new Data(conties.get(i),i,1,weekly,monthly);
            listofData.add(data);
        }

        return listofData;
    }
    //Reciving a list of weeks and a list of the amount on that week and create the userclass Weekly
    //mode 1 == demo mode
    public ArrayList<Weekly> createWeeks(ArrayList<Integer> weeks, ArrayList<Integer> amountDist,ArrayList<Integer> amountAdm,ArrayList<Double> perc, int mode){
        ArrayList<Weekly> listDone = new ArrayList<>();
        if (mode==1){
            for (int i=1;i<=52;i++){
                Weekly weekly = new Weekly(i,150, 200,0.56);
                listDone.add(weekly);
            }
        }
        else{
            for (int i=0;i<weeks.size();i++){
                Weekly weekly = new Weekly(weeks.get(i),amountDist.get(i),amountAdm.get(i),perc.get(i));
                listDone.add(weekly);
            }
        }


        return listDone;
    }
    //Reciving a list of months and a list of the amount on that month and create the userclass Monthly
    //mode 1 == demo mode
    public ArrayList<Monthly> createMonths(ArrayList<String> months, ArrayList<Integer> amount, int mode){
        ArrayList<Monthly> listdone = new ArrayList<>();
        if(mode==1){
            for (int i=0;i<months.size();i++){
                Monthly monthly = new Monthly(months.get(i),600);
                listdone.add(monthly);
            }
        }
        else{
            for (int i=0;i<months.size();i++){
                Monthly monthly = new Monthly(months.get(i),amount.get(i));
                listdone.add(monthly);
            }

        }
        return listdone;
    }

    //creating dummy months
    private ArrayList<String> createdummydatamonths() {
        ArrayList<String> months= new ArrayList<>();
        months.add("Year");
        months.add("January");
        months.add("February");
        months.add("March");
        months.add("April");
        months.add("May");
        months.add("June");
        months.add("August");
        months.add("September");
        months.add("October");
        months.add("November");
        months.add("December");
        return months;
    }

    //creating dummy counties
    private ArrayList<String> createdummydatacounties(){
        ArrayList<String> conties = new ArrayList<>();
        conties.add("Sverige Total");
        conties.add("Blekinge län");
        conties.add("Dalarnas län");
        conties.add("Gotlands län");
        conties.add("Gävleborgs län");
        conties.add("Hallands län");
        conties.add("Jämtlands län");
        conties.add("Jönköpings län");
        conties.add("Kalmar län");
        conties.add("Kronobergs län");
        conties.add("Norrbottens län");
        conties.add("Skåne län");
        conties.add("Stockholms län");

        conties.add("Södermanlands län");
        conties.add("Uppsala län");
        conties.add("Värmlands län");
        conties.add("Västerbottens län");
        conties.add("Västernorrlands län");
        conties.add("Västmanlands län");
        conties.add("Västra Götalands län ");
        conties.add("Örebro län");
        conties.add("Östergötlands län");
        return conties;
    }
}