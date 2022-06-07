package com.voidx.spectable.firebase

import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

interface FirebaseFirestoreProxy {

    fun retrieve(
        name: String,
        document: String,
        completion: (snapshots: List<DocumentSnapshot>?, error: Exception?) -> Unit
    )

    fun <T : Any> save(
        name: String,
        document: String,
        valueID: String?,
        value: T,
        completion: (success: Boolean, error: Exception?) -> Unit
    )

    fun delete(
        name: String,
        document: String,
        valueID: String,
        completion: (success: Boolean, error: Exception?) -> Unit
    )

    fun listenForInsertion(
        name: String,
        document: String,
        onInserted: (document: DocumentSnapshot?, error: Exception?) -> Unit,
        onEmpty: () -> Unit
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

        override fun <T : Any> save(
            name: String,
            document: String,
            valueID: String?,
            value: T,
            completion: (success: Boolean, error: Exception?) -> Unit
        ) {
            val collectionRef = firestore
                .collection("users")
                .document(document)
                .collection(name)

            val docRef = if (valueID.isNullOrBlank()) {
                collectionRef.add(value)
            } else {
                collectionRef
                    .document(valueID)
                    .set(value)
            }

            docRef
                .addOnSuccessListener {
                    completion(true, null)
                }
                .addOnFailureListener {
                    completion(false, it)
                }
        }

        override fun delete(
            name: String,
            document: String,
            valueID: String,
            completion: (success: Boolean, error: Exception?) -> Unit
        ) {
            firestore
                .collection("users")
                .document(document)
                .collection(name)
                .document(valueID)
                .delete()
                .addOnSuccessListener {
                    completion(true, null)
                }
                .addOnFailureListener {
                    completion(false, it)
                }
        }

        override fun listenForInsertion(
            name: String,
            document: String,
            onInserted: (document: DocumentSnapshot?, error: Exception?) -> Unit,
            onEmpty: () -> Unit
        ) {
            firestore
                .collection("users")
                .document(document)
                .collection(name)
                .addSnapshotListener { value, error ->
                    if (error != null) {
                        onInserted(null, error)
                        return@addSnapshotListener
                    }

                    value?.documents?.takeIf { it.isEmpty() }?.run { onEmpty.invoke() }

                    value?.documentChanges?.forEach {
                        if (it.type == DocumentChange.Type.ADDED) {
                            onInserted(it.document, null)
                        }
                    }
                }
        }
    }
}
