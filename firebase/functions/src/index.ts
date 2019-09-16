import * as functions from 'firebase-functions';
import * as admin from 'firebase-admin';
import FieldValue = admin.firestore.FieldValue;

admin.initializeApp();

// Start writing Firebase Functions
// https://firebase.google.com/docs/functions/typescript

export const onCreate = functions.firestore.document("/users/{userID}/associated_lights/{lightID}").onCreate((created, context) => {
    const data: any = created.data();
    const userID = context.params.userID;
    const lightID = context.params.lightID;

    const newLight = {
        id: lightID,
        name: String(data.name),
        brightness: 100,
        color: "FFFFFF",
        users: FieldValue.arrayUnion(userID)
    };

    return admin.firestore().doc('lights/' + lightID)
        .update({users: FieldValue.arrayUnion(userID)})
        .catch((error) => {
            return admin.firestore().doc('lights/' + lightID).set(newLight)
        })
});
