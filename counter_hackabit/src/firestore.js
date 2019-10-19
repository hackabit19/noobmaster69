import firebase from "firebase";

const firebaseApp = firebase.initializeApp({
    apiKey: "AIzaSyAFgkV7GPVodr4xycxpzBQ_YY2R_NUvkuw",
    authDomain: "hackabit-bfd1f.firebaseapp.com",
    databaseURL: "https://hackabit-bfd1f.firebaseio.com",
    projectId: "hackabit-bfd1f",
    storageBucket: "hackabit-bfd1f.appspot.com",
    messagingSenderId: "90116635915",
    appId: "1:90116635915:web:06f43f537d8b42ade7760e",
    measurementId: "G-F9KKQWLZ8X"
});

const db = firebaseApp.firestore();

export { db };