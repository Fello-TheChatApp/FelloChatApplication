'use strict'

const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);

export.sendNotification= functions.database.ref('/notifications/{user_id}/notification_id').onWrite(event =>{
    const user_id=event.params.user_id;
    const notification=event.params.notification;
    
    console.log('The User Id is: ', user_id);
    
    if(!event.data.val()){
        
        return console.log('A Notification has been deleted from the database: ', notification_id);        
    }
    
    const payload= {
        
      notification:{
          title:"Friend Request",
          body:"You've recieved a new Friend Request",
          icon:"default"
      }    
    };
    
    return admin.messaging().sendToDevice();  
    
    console.log('This was the notification Feature');
    
});