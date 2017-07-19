const functions = require('firebase-functions');
const admin = require('firebase-admin');
// var geocoder = require('geocoder');
admin.initializeApp(functions.config().firebase);

var epicenter = [];
var epicenterarea = [];
var information = [];
var index = 0;
//一萬個往格
var locate = [];
for (i = 0; i < 100; i++) {
    locate[i] = [];
    for (j = 0; j < 100; j++) {
        locate[i][j] = 0;
    }
}

exports.eqDataFilter = functions.database.ref('/eqData/{pushId}')
    .onWrite(event => {

        var time = event.data.child('time').val();
        var x = event.data.child('x').val();
        var y = event.data.child('y').val();
        var detectmag = event.data.child('magnitude').val();


        console.log('搖進前information陣列： ' + information);
        information.push([x, y, time, detectmag]);
        console.log('搖進後information陣列： ' + information);


        if (information.length >= 2){
        if (Date.parse(information[information.length-1][2]).valueOf() - Date.parse(information[information.length-2][2]).valueOf() > 15000){

            information.splice(0, information.length-1);
            console.log('與前一筆資料相差大於15秒，剩餘陣列： ' + information);
            
        }}


        if (information.length >= 3) {

            console.log('information陣列長度大於3，檢查ab');

            //check the distance of A to B whether in 4 grid or not
            if (Math.pow(information[index][0] - information[index + 1][0], 2) + Math.pow(information[index][1] - information[index + 1][1], 2) >= 8 &&
                Math.pow(information[index][0] - information[index + 1][0], 2) + Math.pow(information[index][1] - information[index + 1][1], 2) < 72) {

                console.log('ab成功，檢查c');

                //check the distance of B to C whether in 4 grid or not
                if (Math.pow(information[index][0] - information[index + 2][0], 2) + Math.pow(information[index][1] - information[index + 2][1], 2) > Math.pow(information[index][0] - information[index + 1][0], 2) + Math.pow(information[index][1] - information[index + 1][1], 2) &&
                    Math.pow(information[index][0] - information[index + 2][0], 2) + Math.pow(information[index][1] - information[index + 2][1], 2) < 128) {

                    console.log('abc成功，跑timecheck');
                    timecheck();

                } else if (information.length >= 4) {

                    console.log('abc失敗，檢查d');

                    //確認ab 與 d 是否有關
                    if (Math.pow(information[index][0] - information[index + 3][0], 2) + Math.pow(information[index][1] - information[index + 3][1], 2) > Math.pow(information[index][0] - information[index + 1][0], 2) + Math.pow(information[index][1] - information[index + 1][1], 2) &&
                        Math.pow(information[index][0] - information[index + 3][0], 2) + Math.pow(information[index][1] - information[index + 3][1], 2) < 128) {

                        information.splice(2, 1);
                        console.log('abd成功，跑timecheck');
                        console.log('information陣列刪c留abd, 剩餘陣列： ' + information);
                        timecheck();

                    } else if (
                        //確認 c 與d是否有關
                        Math.pow(information[index + 2][0] - information[index + 3][0], 2) + Math.pow(information[index + 2][1] - information[index + 3][1], 2) >= 8 &&
                        Math.pow(information[index + 2][0] - information[index + 3][0], 2) + Math.pow(information[index + 2][1] - information[index + 3][1], 2) < 72) {

                        information.splice(0, 2);
                        console.log('abd失敗，cd成功');
                        console.log('information陣列刪ab留cd, 剩餘陣列： ' + information);
                        
                    }else {

                        information.splice(2, 2);
                        console.log('abd失敗，cd失敗，留下ab');
                        console.log('information陣列刪cd留ab, 剩餘陣列： ' + information);

                    }
                }

                //確認 b與c是否有關
            } else if (Math.pow(information[index + 1][0] - information[index + 2][0], 2) + Math.pow(information[index + 1][1] - information[index + 2][1], 2) >= 8 &&
                Math.pow(information[index + 1][0] - information[index + 2][0], 2) + Math.pow(information[index + 1][1] - information[index + 2][1], 2) < 72) {

                information.splice(0, 1);
                console.log('ab失敗，bc成功');
                console.log('information陣列刪a留bc, 剩餘陣列： ' + information);

            } else {

                information.splice(0, 2);
                console.log('ab失敗，bc失敗');
                console.log('information陣列刪ab留c, 剩餘陣列： ' + information);

            }
        }


    });

function timecheck() {

    console.log('timecheck start');

    //相差30秒
    if (Date.parse(information[2][2]).valueOf() - Date.parse(information[0][2]).valueOf() < 30000) {

        //在這將我們假設的座標在這植入
        information.push([82, 83], [78, 81], [83, 92]);

        console.log('成功，第一與第三筆資料小於30秒，跑震央');

        if (information.length >= 6) {
            console.log('information陣列： '+ information);
            for (var i = 0; i < information.length; i++) {
                for (var j = i + 1; j < information.length; j++) {
                    vertical(information[i][0], information[i][1], information[j][0], information[j][1]);
                }
            }
            epicenters();
        } else {
            console.log('information.length < 6');
        }

    } else {

        // 漣漪性對的，時間錯的
        if (Date.parse(information[2][2]).valueOf() - Date.parse(information[1][2]).valueOf() > 15000) {
            information.splice(0, 2);
            //砍掉1、2，留下3
            console.log('失敗，第二與第三筆資料超過15秒');
            console.log('information陣列刪ab留c, 剩餘陣列： ' + information);
        } else {
            information.splice(0, 1);
            //砍掉1，留下2、3
            console.log('失敗，第一與第二筆資料超過15秒');
            console.log('information陣列刪a留bc, 剩餘陣列： ' + information);
        }
    }
}







//Division the map
function vertical(x1, y1, x2, y2) {
    for (var x = 0; x < 100; x++) {
        for (var y = 0; y < 100; y++) {
            if ((Math.pow(x1 - x, 2) + Math.pow(y1 - y, 2)) > (Math.pow(x2 - x, 2) + Math.pow(y2 - y, 2))) {
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

        // 待補....

        // add the new point
        console.log("k >= 50/  k = " + k);
    } else {
        console.log("epicenterarea: " + epicenterarea);
        if (k % 2 == 0) {

            epicenter.push([((epicenterarea[k / 2][0] + epicenterarea[k / 2 - 1][0]) / 2), ((epicenterarea[k / 2][1] + epicenterarea[k / 2 - 1][1]) / 2)]);
            console.log("偶數/ k = " + k);
            console.log("epicenter " + epicenter);
            updatefirebase(epicenter[0]);

            // return epicenter[epicenter.length-1];
            

        } else {

            epicenter.push(epicenterarea[k / 2 - 0.5]);
            console.log("奇數/ k = " + k);
            console.log("epicenter " + epicenter);
            updatefirebase(epicenter[0]);

            // return epicenter[k/2-0.5];
            
        }
    }
    // return epicenter[0];
}

function updatefirebase(par_epicenter) {
    // var lat = (epicenters()[0] - 1) * 0.02 + 120.01;
    // var lon = (epicenters()[1] - 1) * 0.04 + 21.52;

    var lon = (par_epicenter[0]) * 0.02 + 120.01;
    var lat = (par_epicenter[1]) * 0.04 + 21.52;

    var address = "";


    var averagemag = 0;
    var sum = 0;
    for (var i = 0; i < (information.length - 3); i++) {
        var sum = sum + information[i][3];
    }
    averagemag = sum / (information.length - 3);


    var update = admin.database().ref('eqCenter');
    update.update({
        "longitude": lon,
        "latitude": lat,
        "time": information[0][2],
        "magnitude": Math.ceil(averagemag)
    });


    console.log("更新震央/ 震度平均= " + averagemag + "/ 發生時間: " + information[0][2]);
    information.splice(0, information.length);

    console.log('information陣列清空, 剩餘陣列： ' + information);


}



// Sends a notifications to all users when a new message is posted.
exports.sendNotifications = functions.database.ref('/eqCenter/{pushId}').onWrite(event => {
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
