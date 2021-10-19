package com.example.covidapp.Notifications;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;

import com.example.covidapp.Notifications.NotificationClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class NotificationContent {

    /**
     * An array of sample (placeholder) items.
     */
    public static final List<NotificationClass> notifications = new ArrayList<NotificationClass>();
    public static final List<NotificationClass> ITEMS = new ArrayList<NotificationClass>();

    /**
     * A map of sample (placeholder) items, by ID.
     */
    public static final Map<String, NotificationClass> ITEM_MAP = new HashMap<String, NotificationClass>();

    private static final int COUNT = 1;
    private int counter = 0;

    static {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://covidid-14222-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference reference = database.getReference("Notifications");
        reference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NotNull Task<DataSnapshot> task) {
                for(DataSnapshot dataSnapshot1: task.getResult().getChildren()) {
                    NotificationClass notificationClass = dataSnapshot1.getValue(NotificationClass.class);
//                    notifications.add(usergetearlist.getNotification());
                    notifications.add(notificationClass.getNotification());

                    System.out.println(notificationClass.message);
                }
            }
        });
        System.out.println(notifications.size());
//        System.out.println(notifications.get(0).message);
//        counter = 5;
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createNotificationClass(i));
        }
    }

//    public NotificationContent() {
//        FirebaseDatabase database = FirebaseDatabase.getInstance("https://covidid-14222-default-rtdb.europe-west1.firebasedatabase.app/");
//        DatabaseReference reference = database.getReference("Notifications");
//        reference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//        @Override
//        public void onComplete(@NotNull Task<DataSnapshot> task) {
//            for(DataSnapshot dataSnapshot1: task.getResult().getChildren()) {
//                NotificationClass usergetearlist = dataSnapshot1.getValue(NotificationClass.class);
//                notifications.add(usergetearlist.getNotification());
//                }
//            }
//        });
//        System.out.println(notifications.size());
//        this.counter = counter;
//    }

    private static void addItem(NotificationClass item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getUserID(), item);
    }

    private static NotificationClass createNotificationClass(int position) {
//        return new NotificationClass(String.valueOf(position), "Stuffyao " + position, makeDetails(position));
        return new NotificationClass("String userID", "String sender", "String message", "String date");
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

}

//    FirebaseDatabase database = FirebaseDatabase.getInstance("https://covidid-14222-default-rtdb.europe-west1.firebasedatabase.app/");
//    DatabaseReference reference = database.getReference("Kliniker");
//    reference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//        @Override
//        public void onComplete(@NonNull Task<DataSnapshot> task) {
//                for(DataSnapshot dataSnapshot1: task.getResult().getChildren()){
//                Clinicsclass usergetearlist = dataSnapshot1.getValue(Clinicsclass.class);
//                Clinics.add(usergetearlist.getclinic());
//            }
//        }
//    });
//
//    FirebaseDatabase database = FirebaseDatabase.getInstance("https://covidid-14222-default-rtdb.europe-west1.firebasedatabase.app/");
//    DatabaseReference reference = database.getReference(Notifications);
//    reference.get().addOnCompleteLister(new OnCompleteListener<DataSnapshot>() {
//        @Override
//        public void onComplete(@NotNull Task<DataSnapshot> task) {
//            for(DataSnapshot dataSnapshot1: task.getResult().getChildren()) {
//                NotificationsClass usergetearlist = dataSnapshot1.getValue(NotificationClass.class);
//                Notifications.add(usergetearlist.getNotification());
//            }
//        }
//    });






//    FirebaseDatabase database = FirebaseDatabase.getInstance("https://covidid-14222-default-rtdb.europe-west1.firebasedatabase.app/");
//    DatabaseReference reference = database.getReference("Notifications");
//    reference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//        @Override
//        public void onComplete(@NotNull Task<DataSnapshot> task) {
//            for(DataSnapshot dataSnapshot1: task.getResult().getChildren()) {
//            NotificationClass usergetearlist = dataSnapshot1.getValue(NotificationClass.class);
//            Notifications.add(usergetearlist.getNotification());
//            }
//        }
//    });
