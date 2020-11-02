const functions = require('firebase-functions');

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//   functions.logger.info("Hello logs!", {structuredData: true});
//   response.send("Hello from Firebase!");
// });

// The Cloud Functions for Firebase SDK to create Cloud Functions and setup triggers.
const functions = require('firebase-functions');

// The Firebase Admin SDK to access Cloud Firestore.
const admin = require('firebase-admin');
admin.initializeApp();

export const removeUser = functions.firestore.document("/users/{uid}")
    .onDelete((snapshot, context) => {        
        const serviceAccount = require('path/to/serviceAccountKey.json');
        admin.initializeApp({
            credential: admin.credential.cert(serviceAccount),
            databaseURL: "https://<DATABASE_NAME>>.firebaseio.com"
        });
        return admin.auth().deleteUser(context.params.uid);
    });