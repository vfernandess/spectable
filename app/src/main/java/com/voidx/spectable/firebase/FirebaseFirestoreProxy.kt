package com.voidx.spectable.firebase

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

interface FirebaseFirestoreProxy {

    fun retrieve(
        name: String,
        document: String,
        completion: (snapshots: List<DocumentSnapshot>?, error: Exception?) -> Unit
    )

    fun <T> save(
        name: String,
        document: String,
        value: T,
        completion: (success: Boolean, error: Exception?) -> Unit
    )

    class Impl(
        private val firestore: FirebaseFirestore
    ) : FirebaseFirestoreProxy {

        override fun retrieve(
            name: String,
            document: String,
            completion: (snapshots: List<DocumentSnapshot>?, error: Exception?) -> Unit
        ) {
            firestore
                .collection("users")
                .document(document)
                .collection(name)
                .get()
                .addOnSuccessListener {
                    completion(it.documents, null)
                }
                .addOnFailureListener {
                    completion(null, it)
                }
        }

        override fun <T> save(
            name: String,
            document: String,
            value: T,
            completion: (success: Boolean, error: Exception?) -> Unit
        ) {
            firestore
                .collection("users")
                .document(document)
                .collection(name)
                .add(value as Any)
                .addOnSuccessListener {
                    completion(true, null)
                }
                .addOnFailureListener {
                    completion(false, it)
                }
        }
    }
}
