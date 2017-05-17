var functions = require('firebase-functions');
var admin = require('firebase-admin');
// var geocoder = require('geocoder');
admin.initializeApp(functions.config().firebase);




const MAX_LOG_COUNT = 4;

var timearray = [];
var sixpoint = [79, 88, 76, 85, 75, 90];
var epicenter = [];
//一萬個往格
var locate = [];
for (i = 0; i < 100; i++) {
    locate[i] = [];
    for (j = 0; j < 100; j++) {
        locate[i][j] = 0;
    }
}
var magnitudearray = [];
var controlChange = 4;





// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//



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
            // admin.database().ref('eqDatas/eqData(' + eqX + ',' + eqY + ')/time').set({ time: localTime });

            // time[time.length] = localTime;
            // time.push(localTime);
            // console.log(time);

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



// exports.limitCount = functions.database.ref('/eqData/{pushId}')
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


// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

//                 var query = eqDatasRef.orderByChild('time').equalTo(localTime);
//                 return query.once("value", function(snapshot) {
//                     var updates = {};
//                     snapshot.forEach(function(child) {
//                         updates[child.key] = null;
//                     });
//                     console.log("刪除時間錯誤的");
//                     // eqDatasRef.update(updates);
//                     snapshot.ref.remove();
//                 });

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~



exports.eqDataFilter = functions.database.ref('/eqDatas/eqData(7,8)/{pushID}')
    .onWrite(event => {

        if (!event.data.exists()) {
            console.log("已清空");
            return;
        }

        var time = event.data.child('time').val();
        var detectlon = event.data.child('longitude').val();
        var detectlat = event.data.child('latitude').val();
        var magnitude = event.data.child('magnitude').val();
        var detectmag = event.data.child('magnitude').val();

        timearray.push(time);

        //  判斷是否為第一筆進來的資料
        if (timearray.length >= 2) {

            console.log("時間陣列：  " + timearray + "/ 座標陣列：  " + sixpoint + "/ 震央陣列：  " + magnitudearray);

            //  兩筆時間相差15秒以內留著跑震央、以外更新整個網格資料
            if (Date.parse(timearray[timearray.length - 1]).valueOf() - Date.parse(timearray[timearray.length - 2]).valueOf() < 15000) {

                sixpoint.push(detectlon + controlChange);
                sixpoint.push(detectlat - controlChange);
                magnitudearray.push(detectmag);
                controlChange = controlChange + 1;


                if (sixpoint.length >= 12) {
                    console.log("小於15秒/ success/  時間陣列：  " + timearray + "/ 座標陣列：  " + sixpoint + "/ 震央陣列：  " + magnitudearray);

                    //call the vertical function to Division the map
                    for (var i = 0; i < sixpoint.length; i += 2) {
                        for (var j = i + 2; j < sixpoint.length; j += 2) {
                            vertical(sixpoint[i], sixpoint[i + 1], sixpoint[j], sixpoint[j + 1]);
                        }
                    }
                    
                    // timearray.splice(0, timearray.length);
                    // magnitudearray.splice(0, magnitudearray.length);
                    // sixpoint.splice(6, sixpoint.length - 6);

                    console.log("三筆成功/ 時間陣列：  " + timearray + "/ 座標陣列：  " + sixpoint + "/ 震央陣列：  " + magnitudearray);

                    epicenters();

                } else {
                    console.log("小於15秒/ 不到6筆/   時間陣列：  " + timearray + "/ 座標陣列：  " + sixpoint + "/ 震央陣列：  " + magnitudearray);
                }

            } else {
                timearray.splice(0, timearray.length - 1);
                magnitudearray.splice(0, magnitudearray.length - 1);
                sixpoint.splice(6, sixpoint.length - 6);

                controlChange = 4;
                sixpoint.push(detectlon + controlChange);
                sixpoint.push(detectlat - controlChange);
                controlChange = controlChange + 1;


                console.log("超過15秒/ 清空eqData(7,8)/  剩餘時間陣列：  " + timearray + "/ 剩餘座標陣列：  " + sixpoint + "/ 剩餘震央陣列：  " + magnitudearray);

                // admin.database().ref('eqDatas/eqData(7,8)').once('child_added', function(snapshot) { snapshot.ref.remove();});
                // admin.database().ref('eqDatas/eqData(7,8)').remove();
                // admin.database().ref('eqDatas/eqData(7,8)').push().set({ longitude: detectlon, latitude: detectlat, magnitude: magnitude, time: time });


            }
        } else {
            sixpoint.push(detectlon + controlChange);
            sixpoint.push(detectlat - controlChange);
            magnitudearray.push(detectmag);
            controlChange = controlChange + 1;

            console.log("第一筆/ " + "時間陣列：  " + timearray + "/ 座標陣列：  " + sixpoint + "/ 震央陣列：  " + magnitudearray);
        }



    });




//Division the map
function vertical(x1, y1, x2, y2) {
    for (var x = 0; x < 100; x++) {
        for (var y = 0; y < 100; y++) {
            if (Math.pow(x1 - x, 2) + Math.pow(y1 - y, 2) > Math.pow(x2 - x, 2) + Math.pow(y2 - y, 2)) {
                locate[x][y] = 1;
            }
        }
    }


}



function epicenters() {
    var k = 0;
    for (var x = 0; x < 100; x++) {
        for (var y = 0; y < 100; y++) {
            if (locate[x][y] == 0) {
                k += 1;
                epicenter.push([x, y]);
                // epicenter.push(y);

            }
        }
    }


    if (k >= 50) {
        // add the new point
        console.log("k >= 50/  k = " + k);
    } else {
        if (k % 2 == 0) {

            epicenter.push([(epicenter[k / 2][0] + epicenter[k / 2 - 1][0]) / 2, (epicenter[k / 2][1] + epicenter[k / 2 - 1][1]) / 2]);
            updatefirebase(epicenter[epicenter.length - 1]);

            // return epicenter[epicenter.length-1];
            console.log("偶數/ k = " + k);

        } else {

            updatefirebase(epicenter[k / 2 - 0.5]);

            // return epicenter[k/2-0.5];
            console.log("雞數/ k = " + k);
        }
    }
    // return epicenter;
}


function updatefirebase(par_epicenter) {
    // var lat = (epicenters()[0] - 1) * 0.02 + 120.01;
    // var lon = (epicenters()[1] - 1) * 0.04 + 21.52;

    var lon = (par_epicenter[0] - 1) * 0.02 + 120.01;
    var lat = (par_epicenter[1] - 1) * 0.04 + 21.52;

    var address = "";

    // geocoder.reverseGeocode(lat, lon, function(err, data) {

    // do something with data 
    //show all the data
    // console.log(data);
    //show the target information
    //     address = data.results[4].formatted_address;
    //     console.log(data.results[4].formatted_address);
    // }, { language: 'zh-TW' });

    var averagemag = 0;
    var sum = 0;
    for (var i = 0; i < magnitudearray.length; i++) {
        var sum = sum + magnitudearray[i];
    }
    averagemag = sum / magnitudearray.length;

    console.log("end/ 震度平均= " + averagemag + "/ 發生時間: " + timearray );

    var update = admin.database().ref('eqCenter');
    update.update({
        "longitude": lon,
        "latitude": lat,
        "time": timearray[0],
        "magnitude": Math.ceil(averagemag)
    });

    timearray.splice(0, timearray.length);
    magnitudearray.splice(0, magnitudearray.length);
    sixpoint.splice(6, sixpoint.length - 6);
    controlChange = 4;

}




// exports.pushNotification = functions.database.ref('/eqCenter').onWrite(event => {

//     console.log('Push notification event triggered');
//     // console.log('霸脫啦' + time);


//     //  Grab the current value of what was written to the Realtime Database.

//     var eventSnapshot = event.data;

//     var y = eventSnapshot.child("latitude").val();
//     var x = eventSnapshot.child("longitude").val();
//     var centerMagnitude = eventSnapshot.child("magnitude").val();

//     const payload = {
//         notification: {
//             title: 'App Name',
//             body: "New message",
//             sound: "default"
//                 // },
//                 // data: {
//                 //     title: x.toString(),
//                 //     message: y.message
//         }
//     };

//     const options = {
//         priority: "high",
//         timeToLive: 60 * 60 * 24 //24 hours
//     };

//     return admin.messaging().sendToTopic("notifications", payload, options);
// });





// geocoder.reverseGeocode(33.7489, -84.3789, function(err, data) {

//     // do something with data 
//     //show all the data
//     // console.log(data);
//     //show the target information
//     console.log(data.results[4].formatted_address);

// }, { language: 'zh-TW' });
