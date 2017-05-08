var functions = require('firebase-functions');
var admin = require('firebase-admin');
// var geocoder = require('geocoder');
admin.initializeApp(functions.config().firebase);

const MAX_LOG_COUNT = 13;
time = [];

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//

// exports.helloWorld = functions.https.onRequest((request, response) => {
//     response.send("Hello from Firebase!");
// });


exports.uploadEqDatas = functions.database.ref('/eqData/{pushId}')
    .onWrite(event => {

        var collectionRef = event.data.ref.parent;
        var eqCenterRef = collectionRef.parent.child('eqCenter');
        var eqDataRef = collectionRef.parent.child('eqData');
        var eventSnapshot = event.data;

        var y = eventSnapshot.child("latitude").val();
        var x = eventSnapshot.child("longitude").val();
        var localMagnitude = eventSnapshot.child("magnitude").val();
        var localTime = eventSnapshot.child("time").val();
        var eqDataID = eventSnapshot.child("eqDataID").val();



        //(0~10,0~10)(11~20,11~20)(21~30,21~30)(31~40,31~40)
        //  0    0      1     1      2     2      3     3

        // var localX = Math.floor(x / 10);
        // var localY = Math.floor(y / 10);


        if (x % 10 != 0 && y % 10 != 0) {

            var eqX = Math.floor(x / 10);
            var eqY = Math.floor(y / 10);

            admin.database().ref('eqDatas/eqData(' + eqX + ',' + eqY + ')').push().set({ longitude: x, latitude: y, magnitude: localMagnitude, time: localTime });
            
            time[time.length] = localTime;
            // time.push(localTime);
            console.log(time);

        } else if (x % 10 == 0 && y % 10 != 0 && x != 100 && x != 0) {

            var eqX = Math.floor(x / 10);
            var eqY = Math.floor(y / 10);
            var eqXmin1 = eqX - 1;
            var eqYmin1 = eqY - 1;

            admin.database().ref('eqDatas/eqData(' + eqX + ',' + eqY + ')').push().set({ longitude: x, latitude: y, magnitude: localMagnitude, time: localTime });
            admin.database().ref('eqDatas/eqData(' + eqXmin1 + ',' + eqY + ')').push().set({ longitude: x, latitude: y, magnitude: localMagnitude, time: localTime });

        } else if (x % 10 != 0 && y % 10 == 0 && y != 100 && y != 0) {

            var eqX = Math.floor(x / 10);
            var eqY = Math.floor(y / 10);
            var eqXmin1 = eqX - 1;
            var eqYmin1 = eqY - 1;

            admin.database().ref('eqDatas/eqData(' + eqX + ',' + eqY + ')').push().set({ longitude: x, latitude: y, magnitude: localMagnitude, time: localTime });
            admin.database().ref('eqDatas/eqData(' + eqX + ',' + eqYmin1 + ')').push().set({ longitude: x, latitude: y, magnitude: localMagnitude, time: localTime });

        } else if (x % 10 == 0 && y % 10 == 0 && x != 0 && x != 100 && y != 0 && y != 100) {

            var eqX = Math.floor(x / 10);
            var eqY = Math.floor(y / 10);
            var eqXmin1 = eqX - 1;
            var eqYmin1 = eqY - 1;

            admin.database().ref('eqDatas/eqData(' + eqX + ',' + eqY + ')').push().set({ longitude: x, latitude: y, magnitude: localMagnitude, time: localTime });
            admin.database().ref('eqDatas/eqData(' + eqXmin1 + ',' + eqY + ')').push().set({ longitude: x, latitude: y, magnitude: localMagnitude, time: localTime });
            admin.database().ref('eqDatas/eqData(' + eqX + ',' + eqYmin1 + ')').push().set({ longitude: x, latitude: y, magnitude: localMagnitude, time: localTime });
            admin.database().ref('eqDatas/eqData(' + eqXmin1 + ',' + eqYmin1 + ')').push().set({ longitude: x, latitude: y, magnitude: localMagnitude, time: localTime });

        } else if (x % 10 == 0 && y % 10 != 0 && x == 100) {

            var eqX = Math.floor(x / 10);
            var eqY = Math.floor(y / 10);
            var eqXmin1 = eqX - 1;
            var eqYmin1 = eqY - 1;

            admin.database().ref('eqDatas/eqData(' + eqXmin1 + ',' + eqY + ')').push().set({ longitude: x, latitude: y, magnitude: localMagnitude, time: localTime });

        } else if (x % 10 == 0 && y % 10 != 0 && x == 0) {

            var eqX = Math.floor(x / 10);
            var eqY = Math.floor(y / 10);
            var eqXmin1 = eqX - 1;
            var eqYmin1 = eqY - 1;

            admin.database().ref('eqDatas/eqData(' + eqX + ',' + eqY + ')').push().set({ longitude: x, latitude: y, magnitude: localMagnitude, time: localTime });

        } else if (x % 10 != 0 && y % 10 == 0 && y == 100) {

            var eqX = Math.floor(x / 10);
            var eqY = Math.floor(y / 10);
            var eqXmin1 = eqX - 1;
            var eqYmin1 = eqY - 1;

            admin.database().ref('eqDatas/eqData(' + eqX + ',' + eqYmin1 + ')').push().set({ longitude: x, latitude: y, magnitude: localMagnitude, time: localTime });

        } else if (x % 10 != 0 && y % 10 == 0 && y == 0) {

            var eqX = Math.floor(x / 10);
            var eqY = Math.floor(y / 10);
            var eqXmin1 = eqX - 1;
            var eqYmin1 = eqY - 1;

            admin.database().ref('eqDatas/eqData(' + eqX + ',' + eqY + ')').push().set({ longitude: x, latitude: y, magnitude: localMagnitude, time: localTime });


        } else if (x % 10 == 0 && y % 10 == 0 && x == 100 && y != 0 && y != 100) {

            var eqX = Math.floor(x / 10);
            var eqY = Math.floor(y / 10);
            var eqXmin1 = eqX - 1;
            var eqYmin1 = eqY - 1;

            admin.database().ref('eqDatas/eqData(' + eqXmin1 + ',' + eqY + ')').push().set({ longitude: x, latitude: y, magnitude: localMagnitude, time: localTime });
            admin.database().ref('eqDatas/eqData(' + eqXmin1 + ',' + eqYmin1 + ')').push().set({ longitude: x, latitude: y, magnitude: localMagnitude, time: localTime });


        } else if (x % 10 == 0 && y % 10 == 0 && x == 0 && y != 0 && y != 100) {

            var eqX = Math.floor(x / 10);
            var eqY = Math.floor(y / 10);
            var eqXmin1 = eqX - 1;
            var eqYmin1 = eqY - 1;

            admin.database().ref('eqDatas/eqData(' + eqX + ',' + eqY + ')').push().set({ longitude: x, latitude: y, magnitude: localMagnitude, time: localTime });
            admin.database().ref('eqDatas/eqData(' + eqX + ',' + eqYmin1 + ')').push().set({ longitude: x, latitude: y, magnitude: localMagnitude, time: localTime });

        } else if (x % 10 == 0 && y % 10 == 0 && y == 100 && x != 0 && x != 100) {

            var eqX = Math.floor(x / 10);
            var eqY = Math.floor(y / 10);
            var eqXmin1 = eqX - 1;
            var eqYmin1 = eqY - 1;

            admin.database().ref('eqDatas/eqData(' + eqX + ',' + eqYmin1 + ')').push().set({ longitude: x, latitude: y, magnitude: localMagnitude, time: localTime });
            admin.database().ref('eqDatas/eqData(' + eqXmin1 + ',' + eqYmin1 + ')').push().set({ longitude: x, latitude: y, magnitude: localMagnitude, time: localTime });

        } else if (x % 10 == 0 && y % 10 == 0 && y == 0 && x != 0 && x != 100) {

            var eqX = Math.floor(x / 10);
            var eqY = Math.floor(y / 10);
            var eqXmin1 = eqX - 1;
            var eqYmin1 = eqY - 1;

            admin.database().ref('eqDatas/eqData(' + eqX + ',' + eqY + ')').push().set({ longitude: x, latitude: y, magnitude: localMagnitude, time: localTime });
            admin.database().ref('eqDatas/eqData(' + eqXmin1 + ',' + eqY + ')').push().set({ longitude: x, latitude: y, magnitude: localMagnitude, time: localTime });

        } else if (x % 10 != 0 && y % 10 == 0 && y == 0) {

            var eqX = Math.floor(x / 10);
            var eqY = Math.floor(y / 10);
            var eqXmin1 = eqX - 1;
            var eqYmin1 = eqY - 1;

            admin.database().ref('eqDatas/eqData(' + eqX + ',' + eqY + ')').push().set({ longitude: x, latitude: y, magnitude: localMagnitude, time: localTime });

        } else if (x == 100 && y == 100) {

            var eqX = Math.floor(x / 10);
            var eqY = Math.floor(y / 10);
            var eqXmin1 = eqX - 1;
            var eqYmin1 = eqY - 1;

            admin.database().ref('eqDatas/eqData(' + eqXmin1 + ',' + eqYmin1 + ')').push().set({ longitude: x, latitude: y, magnitude: localMagnitude, time: localTime });

        } else if (x == 100 && y == 0) {

            var eqX = Math.floor(x / 10);
            var eqY = Math.floor(y / 10);
            var eqXmin1 = eqX - 1;
            var eqYmin1 = eqY - 1;

            admin.database().ref('eqDatas/eqData(' + eqXmin1 + ',' + eqY + ')').push().set({ longitude: x, latitude: y, magnitude: localMagnitude, time: localTime });

        } else if (x == 0 && y == 0) {

            var eqX = Math.floor(x / 10);
            var eqY = Math.floor(y / 10);
            var eqXmin1 = eqX - 1;
            var eqYmin1 = eqY - 1;

            admin.database().ref('eqDatas/eqData(' + eqX + ',' + eqY + ')').push().set({ longitude: x, latitude: y, magnitude: localMagnitude, time: localTime });


        } else if (x == 0 && y == 100) {

            var eqX = Math.floor(x / 10);
            var eqY = Math.floor(y / 10);
            var eqXmin1 = eqX - 1;
            var eqYmin1 = eqY - 1;

            admin.database().ref('eqDatas/eqData(' + eqX + ',' + eqYmin1 + ')').push().set({ longitude: x, latitude: y, magnitude: localMagnitude, time: localTime });

        }


        var query = eqDataRef.orderByChild('eqDataID').equalTo(eqDataID);
        query.on('child_added', function(snapshot) {
            snapshot.ref.remove();
        });


        // admin.database().ref('eqCenter').update({ longitude: 78, latitude: 88, magnitude: 3, address: "台灣台北市" });
    });



// exports.limitCount = functions.database.ref('/eqDatas/eqData(7,8)/{pushId}')
//     .onWrite(event => {

//         const parentRef = event.data.ref.parent;
//         return parentRef.once('value').then(snapshot => {
//             if (snapshot.numChildren() >= MAX_LOG_COUNT) {
//                 let childCount = 0;
//                 const updates = {};
//                 snapshot.forEach(function(child) {
//                     if (++childCount <= snapshot.numChildren() - MAX_LOG_COUNT) {
//                         updates[child.key] = null;
//                     }
//                 });
//                 // Update the parent. This effectively removes the extra children.
//                 return parentRef.update(updates);
//             }
//         });

//     });


// exports.eqDataFilter = functions.database.ref('/eqDatas/eqData(7,8)/{pushId}')
//     .onWrite(event => {

//         var eventSnapshot = event.data;

//         var y = eventSnapshot.child("latitude").val();
//         var x = eventSnapshot.child("longitude").val();
//         var localMagnitude = eventSnapshot.child("magnitude").val();
//         var localTime = eventSnapshot.child("time").val();
//         var eqDataID = eventSnapshot.child("eqDataID").val();

//         if (event.data.previous.exists()) {

//             var eventPreviousSnapshot = event.data.previous;
//             var previousMag = eventPreviousSnapshot.child("magnitude").val();

//             if(localMagnitude > previousMag){
//                 eventSnapshot.ref.remove();
//             }

//         }


//     });


exports.eqDataFilter2 = functions.database.ref('/eqDatas/eqData(7,8)/{pushId}')
    .onWrite(event => {

        var eventSnapshot = event.data;

        var y = eventSnapshot.child("latitude").val();
        var x = eventSnapshot.child("longitude").val();
        var localMagnitude = eventSnapshot.child("magnitude").val();
        var localTime = eventSnapshot.child("time").val();
        var eqDataID = eventSnapshot.child("eqDataID").val();

        // if (event.data.previous.exists()) {

        //     var eventPreviousSnapshot = event.data.previous;
        //     var previousMag = eventPreviousSnapshot.child("magnitude").val();

        //     if(localMagnitude > previousMag){
        //         eventSnapshot.ref.remove();
        //     }

        // }

        console.log("霸脫" + time);

        if (Date.parse(localTime).valueOf() - Date.parse(time[time.length-1]) <5000){
            eventSnapshot.ref.remove();
        }


    });



exports.pushNotification = functions.database.ref('/eqCenter').onWrite(event => {

    console.log('Push notification event triggered');
    console.log('霸脫啦' + time);


    //  Grab the current value of what was written to the Realtime Database.

    var eventSnapshot = event.data;

    var y = eventSnapshot.child("latitude").val();
    var x = eventSnapshot.child("longitude").val();
    var centerMagnitude = eventSnapshot.child("magnitude").val();

    const payload = {
        notification: {
            title: 'App Name',
            body: "New message",
            sound: "default"
                // },
                // data: {
                //     title: x.toString(),
                //     message: y.message
        }
    };

    const options = {
        priority: "high",
        timeToLive: 60 * 60 * 24 //24 hours
    };

    return admin.messaging().sendToTopic("notifications", payload, options);
});





// geocoder.reverseGeocode(33.7489, -84.3789, function(err, data) {

//     // do something with data 
//     //show all the data
//     // console.log(data);
//     //show the target information
//     console.log(data.results[4].formatted_address);

// }, { language: 'zh-TW' });
