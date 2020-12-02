# Instructions on Integrating FireStore

## Add Dependency

In build.gradle (:app), add
``` gradle
implementation 'com.google.firebase:firebase-firestore'
```

## Import and Usage

In your respective activity

```java
import com.google.firebase.firestore.FirebaseFirestore;

FirebaseFirestore db = FirebaseFirestore.getInstance();

// Read

db.collection("Users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(ACTIVITY_TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w(ACTIVITY_TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

// Write

CollectionReference users = db.collection("Users");

Map<String, Object> data1 = new HashMap<>();
data1.put("name", "Sam");
data1.put("profile picture", "file:///data/user/");

users.document(user.getUid()).set(data1);


```


