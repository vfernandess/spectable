package com.voidx.spectable.firebase

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

interface FirebaseFirestoreProxy {

    fun retrieve(
        name: String,
        document: String,
        completion: ((snapshot: DocumentSnapshot?, error: Exception?) -> Unit)
    )

    class Impl(
        private val firestore: FirebaseFirestore
    ) : FirebaseFirestoreProxy {

        override fun retrieve(
            name: String,
            document: String,
            completion: (result: DocumentSnapshot?, error: Exception?) -> Unit
        ) {
            firestore
                .collection(name)
                .document(document)
                .get()
                .addOnSuccessListener {
                    completion(it, null)
                }
                .addOnFailureListener {
                    completion(null, it)
                }
        }
    }
}
