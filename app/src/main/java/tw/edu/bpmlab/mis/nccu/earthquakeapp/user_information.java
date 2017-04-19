package tw.edu.bpmlab.mis.nccu.earthquakeapp;

//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//
///**
// * Created by willy on 2017/4/17.
// */
//
//public class user_information {
//    //    public int user_id;
//    public int magnitude;
//    public double longitude;
//    public double latitude;
//    public double acceleration;
//    protected DatabaseReference mDataBase;
//
//
//    public user_information() {
//
//
//    }
//
//    public user_information(int magnitude, double longitude, double latitude, double acceleration) {
////        this.user_id = user_id;
//        this.magnitude = magnitude;
//        this.longitude = longitude;
//        this.latitude = latitude;
//        this.acceleration = acceleration;
//        mDataBase = FirebaseDatabase.getInstance().getReference();
//
//
//    }
//
//    public void user_upload(String user_id, int magnitude, double longitude, double latitude, double acceleration) {
//        user_information user = new user_information(magnitude, longitude, latitude, acceleration);
//        mDataBase.child("user_information_upload").child(user_id).setValue(user);
//
//    }
//
//
//}
//
