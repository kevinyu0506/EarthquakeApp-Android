var functions = require('firebase-functions');
var admin = require('firebase-admin');
// var geocoder = require('geocoder');
admin.initializeApp(functions.config().firebase);

var epicenter = [];
var epicenterarea = [];
var information = [];
//一萬個往格
var locate = [];
for (i = 0; i < 100; i++) {
    locate[i] = [];
    for (j = 0; j < 100; j++) {
        locate[i][j] = 0;
    }
}


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



exports.eqDataFilter = functions.database.ref('/eqDatas/eqData(7,8)/pushId')
    .onWrite(event => {

        var time = event.data.child('time').val();
        var detectlon = event.data.child('longitude').val();
        var detectlat = event.data.child('latitude').val();
        var detectmag = event.data.child('magnitude').val();


        
        information.push([detectlon+k,detectlat+k,time,detectmag]);
        console.log(information);
        k=k+1;


        


        if (information.length >= 3) {

            //check the distance of A to B whether in 4 grid or not
            if (Math.pow(information[index][0] - information[index+1][0], 2) + Math.pow(information[index][1] - information[index+1][1], 2) >= 1 && 
                Math.pow(information[index][0] - information[index+1][0], 2) + Math.pow(information[index][1] - information[index+1][1], 2) < 8 ){

                //check the distance of B to C whether in 4 grid or not
                if (Math.pow(information[index][0] - information[index+2][0], 2) + Math.pow(information[index][1] - information[index+2][1], 2) > Math.pow(information[index][0] - information[index+1][0], 2) + Math.pow(information[index][1] - information[index+1][1], 2)&& 
                    Math.pow(information[index][0] - information[index+2][0], 2) + Math.pow(information[index][1] - information[index+2][1], 2) < 32) {
                    timecheck();
                    console.log('a');

                }else if (information.length >= 4) {
                    //確認ab 與 d 是否有關
                    if (Math.pow(information[index][0] - information[index+3][0], 2) + Math.pow(information[index][1] - information[index+3][1], 2) > Math.pow(information[index][0] - information[index+1][0], 2) + Math.pow(information[index][1] - information[index+1][1], 2) && 
                        Math.pow(information[index][0] - information[index+3][0], 2) + Math.pow(information[index][1] - information[index+3][1], 2) < 32) {
                        information.splice(2,1);
                        timecheck();
                        console.log('b');
                    }else if(
                        //確認 c 與d是否有關
                        Math.pow(information[index+2][0] - information[index+3][0], 2) + Math.pow(information[index+2][1] - information[index+3][1], 2) >= 1 && 
                        Math.pow(information[index+2][0] - information[index+3][0], 2) + Math.pow(information[index+2][1] - information[index+3][1], 2) < 8){
                            information.splice(0,2);
                            console.log('c');
                    }
                }

                //確認 b與c是否有關
            }else if(Math.pow(information[index+1][0] - information[index+2][0], 2) + Math.pow(information[index+1][1] - information[index+2][1], 2) >= 1 && 
                    Math.pow(information[index+1][0] - information[index+2][0], 2) + Math.pow(information[index+1][1] - information[index+2][1], 2) < 8){
                information.splice(0, 1);
                console.log('d');
            }else {
                information.splice(0, 1);
            }
        }



        function timecheck(){

            //相差30秒
            if (Date.parse(information[0][3]).valueOf() - Date.parse(information[2][3]).valueOf() < 30000) {

                //在這將我們假設的座標在這植入
                information.push([79,88],[76,85],[75,90]);

                console.log('time correct');

                if (information.length >=6) {
                    for (var i = 0; i < information.length; i++) {
                        for (var j = i+1; j < information.length; j++) {
                            vertical(information[i][0], information[i][1], information[j+1][0], information[j+1][1]); 
                        }
                    }
                    epicenter();
                }else{
                    console.log('information.length < 6');
                }

            }else{

                // 漣漪性對的，時間錯的
                if (Date.parse(information[1][3]).valueOf() - Date.parse(information[2][3]).valueOf() > 30000) {
                    information.splice(0, 2);
                    //砍掉1、2，留下3
                    console.log('第二與第三筆資料超過30秒');
                }else{
                    information.splice(0, 1);
                    //砍掉1，留下2、3
                    console.log('第一與第二筆資料超過30秒');
                }
            }
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
                epicenterarea.push([x, y]);
                // epicenter.push(y);

            }
        }
    }


    if (k >= 50) {

        //待補

        // add the new point
        console.log("k >= 50/  k = " + k);
    } else {
        if (k % 2 == 0) {

            epicenter.push([(epicenterarea[k / 2][0] + epicenterarea[k / 2 - 1][0]) / 2, (epicenterarea[k / 2][1] + epicenterarea[k / 2 - 1][1]) / 2]);
            updatefirebase(epicenter[0]);

            // return epicenter[epicenter.length-1];
            console.log("偶數/ k = " + k);

        } else {

            epicenter.push(epicenterarea[k / 2 - 0.5]);
            updatefirebase(epicenter[0]);

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


    var averagemag = 0;
    var sum = 0;
    for (var i = 0; i < information.length-3; i++) {
        var sum = sum + information[i][3];
    }
    averagemag = sum / information.length-3;

    console.log("end/ 震度平均= " + averagemag + "/ 發生時間: " + timearray );


     var update = admin.database().ref('eqCenter');
    update.update({
        "longitude": lon,
        "latitude": lat,
        "time": timearray[0],
        "magnitude": Math.ceil(averagemag)
    });

    information.splice(0, information.length);
}



// Sends a notifications to all users when a new message is posted.
exports.sendNotifications = functions.database.ref('/eqCenter').onWrite(event => {
    const snapshot = event.data;

    // Only send a notification when a new message has been created.
    // if (snapshot.previous.val()) {
    //     return;
    // }

    var centerTime = snapshot.child('time').val();
    var centerLongitude = snapshot.child('longitude').val();
    var centerLatitude = snapshot.child('latitude').val();
    var centerMagnitude = event.data.child('magnitude').val();

    // Notification details.
    const payload = {
        notification: {
            title: `${centerMagnitude}級地震警報`,
            body: `發生時間${centerTime}`,
            sound: "default",
            viberate: "true",
        }
    };

    token = "fwFNuIiWbd4:APA91bF6MyCxU_a8nmRp9kTBOriUp5nayZkwoRgv9LlaKe3b2rbVuNa4MKqDUllp1WgXcnXkvIlglp27QKf1MdX9GY4xZ1U4cRVMJPNi0gNQnSnA_HV8S6vDuY1nuuA5CGfLbKdMxjjR";
    console.log("token: " + token);
    return admin.messaging().sendToDevice(token, payload);



});
