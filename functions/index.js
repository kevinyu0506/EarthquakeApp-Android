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

	admin.database().ref('eqCenter').update({longitude:78, latitude:88, magnitude:3});
});

