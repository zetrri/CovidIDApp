package com.example.covidapp.MyPage;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covidapp.Booking.Booking;
import com.example.covidapp.HealthAdmin.AvailableTimesListUserClass;
import com.example.covidapp.R;
import com.example.covidapp.UserReg.RegClass;
import com.example.covidapp.databinding.FragmentMyPageBinding;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class MyPageFragment extends Fragment {

    private FragmentMyPageBinding binding;
    int value=3;
    Booking[] Bookings = new Booking[2];
    MyPageFragment this_obj = this;

    final static int TWO_WEEKS = 1209600000;
    final static int TWENTYTHREE_DAYS = 1987200000;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference ref_vaccine;

    public MyPageFragment() {
        // Required empty public constructor
    }

    public static MyPageFragment newInstance(String param1, String param2) {
        MyPageFragment fragment = new MyPageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMyPageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Removes keyboard if up
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(getActivity().INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        //Removes keyboard if up

        //Bookings[0] = new Booking("8/2-2021", "15:30", "Gripen", "Karlstad", "Värmland", "Modena", "birch pollen", "", "Doctor spooky", "199809291111", "Karl Johan");
        //Bookings[1] = new Booking("14/7-2021", "11:00", "Nolhaga", "Alingsås", "Västra Götaland", "Modena", "Potatoe", "Potato","", "193312031234", "Peter niklas");

        //Checks if there is a new notification
        newNotification();

        //Calendar and formatting helpers
        Calendar date = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat sdfclock = new SimpleDateFormat("kk:mm");
        final long todaysdate = date.getTimeInMillis();
        long todayInMillis = date.getTimeInMillis() + TimeZone.getDefault().getOffset(date.getTimeInMillis());

        //Top menu listeners
        LinearLayout passport = binding.passport;
        LinearLayout bookappointment = binding.bookappointment;
        LinearLayout notifications = binding.notifications;

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://covidid-14222-default-rtdb.europe-west1.firebasedatabase.app/");
        FirebaseAuth firebaseAuth1 = FirebaseAuth.getInstance();
        DatabaseReference userref = database.getReference("User").child(firebaseAuth1.getUid());

        //passport knapp
        passport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getActivity().getBaseContext(), PassportMainActivity.class);
//                startActivity(intent);
                userref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        DataSnapshot dataSnapshot1= task.getResult();
                        RegClass tempregclass = dataSnapshot1.getValue(RegClass.class);
                        final long dos2 = tempregclass.getDosTwo();
                        final long dos1 = tempregclass.getDosTwo();

                        if (dos2!=0 && dos1!=0 ){
                            if (todaysdate>=(dos2+TWO_WEEKS))
                            {
                                Navigation.findNavController(view).navigate(R.id.action_nav_my_page_to_nav_passport);
                            }else{
                                new AlertDialog.Builder(getActivity())
                                        .setTitle("Vaccinpass")
                                        .setMessage("Ditt vaccinpass är tillgängligt den "+ sdf.format(dos2+TWO_WEEKS))
                                        .setPositiveButton("Tillbaka", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        })
                                        .show();

                            }
                        }
                        else {
                            new AlertDialog.Builder(getActivity())
                                    .setTitle("Vaccinpass")
                                    .setMessage("Du har inte uppfyllt kraven för att få ett vaccinpass än")
                                    .setPositiveButton("Tillbaka", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    })
                                    .show();
                        }
                    }
                });

            }
        });

        //boka tid knapp
        bookappointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getActivity().getBaseContext(), BookingMainActivity.class);
//                startActivity(intent);
//                Navigation.findNavController(view).navigate(R.id.action_nav_my_page_to_nav_booking);
                userref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        DataSnapshot dataSnapshot1= task.getResult();
                        RegClass tempregclass = dataSnapshot1.getValue(RegClass.class);
                        String temp = tempregclass.getPersnr();
                        final String temp1 = temp.substring(0,8);
                        final long dos2 = tempregclass.getDosTwo();
                        final long dos1 = tempregclass.getDosOne();
                        final long currentTime = Calendar.getInstance().getTimeInMillis();

                        DatabaseReference reference = database.getReference("Minbookingage");
                        reference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                DataSnapshot dataSnapshot2= task.getResult();
                                long minage = (dataSnapshot2.getValue(long.class)) + 10000; //kollade från 0101 året som är valt -> ingen född efter 0101 det valda året kan vaccinera sig

                                if(dos2 == 1) {
                                    tempregclass.setDosTwo(0);
                                    userref.removeValue();
                                    userref.setValue(tempregclass);
                                }
                                else if(dos1 == 1) {
                                    tempregclass.setDosOne(0);
                                    userref.removeValue();
                                    userref.setValue(tempregclass);
                                }


                                if(dos2!=0) {
                                    new AlertDialog.Builder(getActivity())
                                    .setTitle("Boka tid")
                                    .setMessage("Du har redan tagit eller bokat tid för dos 1 och dos 2")
                                    .setPositiveButton("Tillbaka", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    })
                                    .show();
                                }else if (dos1 + TWENTYTHREE_DAYS > currentTime) {
                                    new AlertDialog.Builder(getActivity())
                                    .setTitle("Boka tid")
                                    .setMessage("Du måste vänta 23 dagar efter du tagit dos 1 innan du boka dos 2")
                                    .setPositiveButton("Tillbaka", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    })
                                    .show();
                                }else if (Long.parseLong(temp1)<minage){
                                    Navigation.findNavController(view).navigate(R.id.action_nav_my_page_to_nav_booking);
                                    Log.d("testtest","fårboka");
                                }else {Log.d("testtest","fårejboka");
                                    new AlertDialog.Builder(getActivity())
                                            .setTitle("Boka tid")
                                            .setMessage("Din åldersgrupp får inte boka tid för vaccin ännu")
                                            .setPositiveButton("Tillbaka", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {

                                                }
                                            }).show();}
                            }
                        });
                    }
                });
            }
        });

        //notifications knapp
        firebaseAuth = FirebaseAuth.getInstance();
        notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_nav_my_page_to_nav_notifications);
            }
        });

        LinearLayout container = binding.containerbookings;

        // lägger till kort för tider som är passerade
        userref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                DataSnapshot dataSnapshot1= task.getResult();
                RegClass tempregclass = dataSnapshot1.getValue(RegClass.class);
                final long dos2 = tempregclass.getDosTwo();
                final long dos1 = tempregclass.getDosOne();

                //om dos 1 är tagen
                if(dos1 > 0 && todayInMillis > dos1){
                    CardView new_card = new CardView(getActivity());
                    LinearLayout linear_layout1 = new LinearLayout(getActivity());
                    linear_layout1.setOrientation(LinearLayout.VERTICAL);
                    TextView date_time = new TextView(getActivity());

                    date_time.setText(" Dos 1 togs " + sdf.format(dos1) +" "+sdfclock.format(dos1));
                    date_time.setTextSize(20);
                    date_time.setBackgroundColor(0xFF6200EE);
                    date_time.setTextColor(Color.WHITE);
                    linear_layout1.addView(date_time);
                    linear_layout1.setPadding(0, 0, 0, 16);

                    new_card.addView(linear_layout1);
                    container.addView(new_card);
                }
                //om dos 2 är tagen
                if(dos2 > 0 && todayInMillis > dos2){
                    CardView new_card = new CardView(getActivity());
                    LinearLayout linear_layout1 = new LinearLayout(getActivity());
                    linear_layout1.setOrientation(LinearLayout.VERTICAL);
                    TextView date_time = new TextView(getActivity());

                    date_time.setText(" Dos 2 togs " + sdf.format(dos2) +" "+sdfclock.format(dos2));
                    date_time.setTextSize(20);
                    date_time.setBackgroundColor(0xFF6200EE);
                    date_time.setTextColor(Color.WHITE);
                    linear_layout1.addView(date_time);
                    linear_layout1.setPadding(0, 0, 0, 16);

                    new_card.addView(linear_layout1);
                    container.addView(new_card);
                }
            }
        });

        //Getting bookings from firebase
        if ( firebaseAuth1.getCurrentUser() == null){
            Navigation.findNavController(view).navigate(R.id.nav_login);
            return;
        }
        DatabaseReference ref = database.getReference("BookedTimes");
        ArrayList<AvailableTimesListUserClass> availableTimesListUserClasses =new ArrayList<>();
        ref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                for(DataSnapshot dataSnapshot1: task.getResult().getChildren()){
                    //getting all appointments booked by the user
                    AvailableTimesListUserClass availableTimesListUserClass = dataSnapshot1.getValue(AvailableTimesListUserClass.class);
                    if(todayInMillis > availableTimesListUserClass.getTimestamp())
                        ref.child(dataSnapshot1.getKey()).removeValue(); // tar bort tider som är passerade från databasen
                    else if (availableTimesListUserClass.getBookedBy().equals(firebaseAuth1.getUid())){
                        Log.d("FoundOne",availableTimesListUserClass.getId());
                        availableTimesListUserClasses.add(availableTimesListUserClass);
                    }
                }

                //Making a card for each appointment
                for(int i = 0; i < availableTimesListUserClasses.size(); i++){

                    date.setTimeInMillis(availableTimesListUserClasses.get(i).getTimestamp());
                    Date date1 = date.getTime();

                    CardView new_card = new CardView(getActivity());
                    new_card.setId(i+1);
                    LinearLayout linear_layout1 = new LinearLayout(getActivity());
                    linear_layout1.setOrientation(LinearLayout.VERTICAL);
                    TextView date_time = new TextView(getActivity());

                    //date_time.setText(String.valueOf(date1.getDay())+"/"+String.valueOf(date1.getMonth())+"-"+date1.getYear());
                    date_time.setText(" " + sdf.format(date1) +" "+sdfclock.format(date1));
                    date_time.setTextSize(20);
                    date_time.setBackgroundColor(0xFF6200EE);
                    date_time.setTextColor(Color.WHITE);
                    linear_layout1.addView(date_time);

                    TextView clinic_text = new TextView(getActivity());
                    clinic_text.setTextSize(15);
                    clinic_text.setText( (String) ("Mottagning: " + availableTimesListUserClasses.get(i).getClinic() + " " + availableTimesListUserClasses.get(i).getCity()));
                    linear_layout1.addView(clinic_text);

                    TextView vaccin_text = new TextView(getActivity());
                    vaccin_text.setTextSize(15);
                    vaccin_text.setText( (String) ("Vaccin: " + availableTimesListUserClasses.get(i).getVaccine())); //ska vara vaccine
                    linear_layout1.addView(vaccin_text);

                    TextView test_text = new TextView(getActivity());
                    test_text.setTextSize(15);
                    test_text.setText(availableTimesListUserClasses.get(i).getId());
                    test_text.setVisibility(TextView.GONE);
                    linear_layout1.addView(test_text);

                    TextView test_text2 = new TextView(getActivity());
                    test_text2.setTextSize(15);
                    test_text2.setText(String.valueOf(i));
                    test_text2.setVisibility(TextView.GONE);
                    linear_layout1.addView(test_text2);

                    TextView test_text3 = new TextView(getActivity());
                    test_text3.setTextSize(15);
                    test_text3.setText(availableTimesListUserClasses.get(i).getTimestamp().toString());
                    test_text3.setVisibility(TextView.GONE);
                    linear_layout1.addView(test_text3);

                    TextView test_text4 = new TextView(getActivity());
                    test_text4.setTextSize(15);
                    test_text4.setText(availableTimesListUserClasses.get(i).getVaccine());
                    test_text4.setVisibility(TextView.GONE);
                    linear_layout1.addView(test_text4);

                    LinearLayout linear_layout2 = new LinearLayout(getActivity());
                    linear_layout2.setOrientation(LinearLayout.HORIZONTAL);
                    linear_layout2.setPadding(0,0,0,16);
                    linear_layout2.setGravity(Gravity.RIGHT);


                    // Avboka en tid knapp
                    Button buttonAvboka = new Button(getActivity());
                    buttonAvboka.setText("Avboka");
                    linear_layout2.addView(buttonAvboka);
                    buttonAvboka.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            new AlertDialog.Builder(getActivity())
                                    .setTitle("Avboka")
                                    .setMessage("Datum: "+date_time.getText()+ "\n" + clinic_text.getText() + "\n\nÄr du säker på att du vill avboka denna tid?")
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        //gets vaccine count
                                        public void onClick(DialogInterface dialog, int which) {

                                            long tocheck = Long.parseLong(test_text3.getText().toString());
                                            FirebaseDatabase database = FirebaseDatabase.getInstance("https://covidid-14222-default-rtdb.europe-west1.firebasedatabase.app/");
                                            DatabaseReference userref = database.getReference("User").child(firebaseAuth.getCurrentUser().getUid());
                                            userref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DataSnapshot> task) {
                                                    DataSnapshot dataSnapshot2= task.getResult();
                                                    RegClass user = dataSnapshot2.getValue(RegClass.class);
                                                    //if the booking is a dose 1 booking
                                                    if (user.getDosOne()==tocheck){
                                                        //if the user also have a dose 2 booking
                                                        if (user.getDosTwo()!=0){
                                                            new AlertDialog.Builder(getActivity())
                                                                    .setTitle("Avboka")
                                                                    .setMessage("Du kan inte avboka dos1 innan dos 2 är avbokad")
                                                                    .setPositiveButton("Tillbaka", new DialogInterface.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                                            return;
                                                                        }
                                                                    }).show();

                                                        }
                                                        //no dose 2, cancelation is accepted
                                                        else{
                                                            deletefromuser(1);
                                                            new_card.removeAllViews();
                                                            gettimeback(availableTimesListUserClasses.get(Integer.parseInt(test_text2.getText().toString())));
                                                            deleteCard(test_text.getText().toString());
                                                            get_vaccineback(test_text4.getText().toString());


                                                        }
                                                        System.out.println("Dos is"+"1");
                                                    }
                                                    else if (user.getDosTwo()==tocheck){
                                                        deletefromuser(2);
                                                        new_card.removeAllViews();
                                                        gettimeback(availableTimesListUserClasses.get(Integer.parseInt(test_text2.getText().toString())));
                                                        deleteCard(test_text.getText().toString());
                                                        get_vaccineback(test_text4.getText().toString());
                                                        System.out.println("Dos is"+"2");
                                                    }
                                                    else System.out.println("Dos is"+"Error");

                                                }
                                            });

                                            //

                                        }
                                    })
                                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            //

                                        }
                                    })
                                    .show();

                        }
                    });

                    // Omboka en tid knapp
                    Button buttonOmboka = new Button(getActivity());
                    buttonOmboka.setText("Omboka");
                    linear_layout2.addView(buttonOmboka);
                    buttonOmboka.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            new AlertDialog.Builder(getActivity())
                                    .setTitle("Omboka")
                                    .setMessage("Datum: "+date_time.getText()+ "\n" + clinic_text.getText() + "\n\nÄr du säker på att du vill omboka denna tid?")
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            long tocheck = Long.parseLong(test_text3.getText().toString());
                                            FirebaseDatabase database = FirebaseDatabase.getInstance("https://covidid-14222-default-rtdb.europe-west1.firebasedatabase.app/");
                                            DatabaseReference userref = database.getReference("User").child(firebaseAuth.getCurrentUser().getUid());
                                            userref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DataSnapshot> task) {
                                                    DataSnapshot dataSnapshot2= task.getResult();
                                                    RegClass user = dataSnapshot2.getValue(RegClass.class);
                                                    //if the booking is a dose 1 booking
                                                    if (user.getDosOne()==tocheck){
                                                        //if the user also have a dose 2 booking
                                                        if (user.getDosTwo()!=0){
                                                            new AlertDialog.Builder(getActivity())
                                                                    .setTitle("Avboka")
                                                                    .setMessage("Du kan inte avboka dos1 innan dos 2 är avbokad")
                                                                    .setPositiveButton("Tillbaka", new DialogInterface.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                                            return;
                                                                        }
                                                                    }).show();

                                                        }
                                                        //no dose 2, cancelation is accepted
                                                        else{
                                                            deletefromuser(1);
                                                            new_card.removeAllViews();
                                                            gettimeback(availableTimesListUserClasses.get(Integer.parseInt(test_text2.getText().toString())));
                                                            deleteCard(test_text.getText().toString());
                                                            get_vaccineback(test_text4.getText().toString());
                                                            View view2 = getView();
                                                            Navigation.findNavController(view2).navigate(R.id.action_nav_my_page_to_nav_booking);

                                                        }
                                                        System.out.println("Dos is"+"1");
                                                    }
                                                    else if (user.getDosTwo()==tocheck){
                                                        deletefromuser(2);
                                                        new_card.removeAllViews();
                                                        gettimeback(availableTimesListUserClasses.get(Integer.parseInt(test_text2.getText().toString())));
                                                        deleteCard(test_text.getText().toString());
                                                        get_vaccineback(test_text4.getText().toString());
                                                        View view2 = getView();
                                                        Navigation.findNavController(view2).navigate(R.id.action_nav_my_page_to_nav_booking);

                                                        System.out.println("Dos is"+"2");
                                                    }
                                                    else System.out.println("Dos is"+"Error");

                                                }
                                            });


//                                                new_card.removeAllViews();
//                                                gettimeback(availableTimesListUserClasses.get(Integer.parseInt(test_text2.getText().toString())));
//                                                deleteCard(test_text.getText().toString());
//                                                View view2 = getView();
//                                                Navigation.findNavController(view2).navigate(R.id.action_nav_my_page_to_nav_booking);


                                        }
                                    })
                                    .setNegativeButton(android.R.string.no, null)
                                    .show();
                        }
                    });

                    linear_layout1.addView(linear_layout2);
                    new_card.addView(linear_layout1);
                    container.addView(new_card);
                }
            }

        });

    }

    private void deletefromuser(int dos){

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://covidid-14222-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference userref = database.getReference("User").child(firebaseAuth.getCurrentUser().getUid());
        userref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                DataSnapshot dataSnapshot2= task.getResult();
                RegClass user = dataSnapshot2.getValue(RegClass.class);
                userref.removeValue();
                if (dos==1){
                    user.setDosOne(0);
                }else if (dos==2){
                    user.setDosTwo(0);
                }else{
                    userref.setValue(user);
                    return;
                }
                userref.setValue(user);

            }
        });

    }

    private void deleteCard(String id)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://covidid-14222-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference ref = database.getReference("BookedTimes");
        ref.child(id).removeValue();
    }
    private void gettimeback(AvailableTimesListUserClass item)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://covidid-14222-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference ref = database.getReference("AvailableTimes");
        item.setAvailable(true);
        item.setBookedBy("");
        item.setAllergies("");
        item.setComments("");
        item.setVaccine("");
        item.setMedication("");
        item.setApproved(false);
        ref.child(item.getId()).setValue(item);
    }
    private void get_vaccineback(String vaccine)
    {

        ref_vaccine = FirebaseDatabase.getInstance().getReference().child(vaccine);

    
        ref_vaccine.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long countVaccine = (long) snapshot.getValue();
                Log.d("Vaccine amount", String.valueOf(countVaccine));
                ref_vaccine.setValue(countVaccine+1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }

    //Checks if there is a new notification and changes the color of the image
    private void newNotification() {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://covidid-14222-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference reference = database.getReference("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        reference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                DataSnapshot dataSnapshot1 = task.getResult();
                RegClass regClass = dataSnapshot1.getValue(RegClass.class);
                regClass.getDosOne();
                Calendar calendar = Calendar.getInstance();
                final long dos1 = regClass.getDosOne();
                final long dos2 = regClass.getDosTwo();
                if (dos1 == 1 || dos2 == 1 || dos1 + TWENTYTHREE_DAYS <= calendar.getTimeInMillis() && dos1 != 0 && dos2 == 0) {
                    binding.imageView7.setColorFilter(Color.argb(255, 255, 0, 0));
                }
            }
        });
    }

    private void createCard(){}
}
