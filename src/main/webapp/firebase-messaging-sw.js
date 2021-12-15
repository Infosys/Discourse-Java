importScripts('https://www.gstatic.com/firebasejs/8.3.1/firebase-app.js')
importScripts('https://www.gstatic.com/firebasejs/8.3.1/firebase-messaging.js')
const firebaseConfig = {
  apiKey: "AIzaSyDZfuEqkuLUAL17sRWALP3B6-eF1EBWP6U",
  authDomain: "forums-survey.firebaseapp.com",
  projectId: "forums-survey",
  storageBucket: "forums-survey.appspot.com",
  messagingSenderId: "550726420439",
  appId: "1:550726420439:web:89552bb9b9a257324a2d92"
};
// Initialize Firebase
firebase.initializeApp(firebaseConfig);
const messaging = firebase.messaging();
const messages = firebase.messaging();
// eslint-disable-next-line no-console
console.log('Firebase initialised');
