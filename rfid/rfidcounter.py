import RPi.GPIO as GPIO
import sys
import os
import time
import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore
os.environ["GOOGLE_APPLICATION_CREDENTIALS"] = "hackabit.json"

cred = credentials.ApplicationDefault()
firebase_admin.initialize_app(cred, {
  'projectId': 'hackabit-bfd1f',
})
db = firestore.client()

sys.path.append('/home/pi/MFRC522-python')
from mfrc522 import SimpleMFRC522

reader = SimpleMFRC522()

doc_ref = db.collection(u'counter').document(u'1')

print("Hold a tag near the reader")

while(1):
	try: 
		id, text = reader.read()
	 	print(id)
		print(text)
		doc_ref.set({u'id': id})

	finally:
    		GPIO.cleanup()
		time.sleep(1)
