var functions = require('firebase-functions');
var admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
exports.helloWorld = functions.https.onRequest((request, response) => {
    response.send("Hello from Firebase!");
});


exports.uploadEqCenter = functions.database.ref('/eqData/{pushId}')
    .onWrite(event => {

        var collectionRef = event.data.ref.parent;
        var eqCenterRef = collectionRef.parent.child('eqCenter');
        var eventSnapshot = event.data;

        var localLatitude = eventSnapshot.child("latitude").val();
        var localLongitude = eventSnapshot.child("longitude").val();
        var localMagnitude = eventSnapshot.child("magnitude").val();
        var localTime = eventSnapshot.child("time").val();


        // var geocoder = new google.maps.Geocoder();
        // // google.maps.LatLng 物件
        // var coord = new google.maps.LatLng(23.5, 121);

        // // 傳入 latLng 資訊至 geocoder.geocode
        // geocoder.geocode({'latLng': coord }, function(results, status) {
        //   if (status === google.maps.GeocoderStatus.OK) {
        //     // 如果有資料就會回傳
        //     if (results) {
        //        address = results[4].formatted_address;
        //        admin.database().ref('eqCenter').update({address : address});
        //        }
        //    }
        //    // 經緯度資訊錯誤
        //     else {
        //     alert("Reverse Geocoding failed because: " + status);
        //       }
        // });




        //(0~10,0~10)(11~20,11~20)(21~30,21~30)(31~40,31~40)
        //  0    0      1     1      2     2      3     3

        var localX = Math.floor(localLongitude/10);
        var localY = Math.floor(localLatitude/10);


        admin.database().ref('eqCenter').update({ longitude: 78, latitude: 88, magnitude: 3, address: "台灣台北市" });
        // admin.database().ref('eqData('+localX+','+localY+')').push().set({longitude: localLongitude, latitude: localLatitude, magnitude: localMagnitude, time: localTime});
    });

















