package com.davito.mimonerdero.data

import android.util.Log
import com.davito.mimonerdero.model.Income
import com.davito.mimonerdero.model.Outcome
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class IncomeOutComeRepository {

    private var db = Firebase.firestore
    private var auth : FirebaseAuth = Firebase.auth

    suspend fun saveIncome(income: Income) : ResourceRemote<String?> {
        return try {
            val uid = auth.currentUser?.uid
            val path = uid?.let { db.collection("users").document(it).collection("incomes")}
            val documentoSerie = path?.document()
            income.id = documentoSerie?.id
            income.id?.let { path?.document(it)?.set(income)?.await() }
            ResourceRemote.Success(data = uid)
        } catch (e: FirebaseFirestoreException) {
            e.localizedMessage?.let { Log.e("firebasesFirestoreEx", it) }
            ResourceRemote.Error(message = e.localizedMessage)
        } catch (e: FirebaseNetworkException) {
            e.localizedMessage?.let { Log.e("firebasesNetworkEx", it) }
            ResourceRemote.Error(message = e.localizedMessage)
        }
    }

    suspend fun loadIncomes():  ResourceRemote<QuerySnapshot?> {
        return try {
            val doref= auth.uid?.let { db.collection("users").document(it).collection("incomes")}
            val result = doref?.get()?.await()
            ResourceRemote.Success(data = result)
        } catch (e: FirebaseFirestoreException) {
            e.localizedMessage?.let { Log.e("firebasesAuthEx", it) }
            ResourceRemote.Error(message = e.localizedMessage)
        } catch (e: FirebaseNetworkException) {
            e.localizedMessage?.let { Log.e("firebasesAuthEx", it) }
            ResourceRemote.Error(message = e.localizedMessage)
        }
    }

    suspend fun saveOutcome(outcome: Outcome) : ResourceRemote<String?> {
        return try {
            val uid = auth.currentUser?.uid
            val path = uid?.let { db.collection("users").document(it).collection("outcomes")}
            val documentoSerie = path?.document()
            outcome.id = documentoSerie?.id
            outcome.id?.let { path?.document(it)?.set(outcome)?.await() }
            ResourceRemote.Success(data = uid)
        } catch (e: FirebaseFirestoreException) {
            e.localizedMessage?.let { Log.e("firebasesFirestoreEx", it) }
            ResourceRemote.Error(message = e.localizedMessage)
        } catch (e: FirebaseNetworkException) {
            e.localizedMessage?.let { Log.e("firebasesNetworkEx", it) }
            ResourceRemote.Error(message = e.localizedMessage)
        }
    }

    suspend fun loadOutcomes():  ResourceRemote<QuerySnapshot?> {
        return try {
            val doref= auth.uid?.let { db.collection("users").document(it).collection("outcomes")}
            val result = doref?.get()?.await()
            ResourceRemote.Success(data = result)
        } catch (e: FirebaseFirestoreException) {
            e.localizedMessage?.let { Log.e("firebasesAuthEx", it) }
            ResourceRemote.Error(message = e.localizedMessage)
        } catch (e: FirebaseNetworkException) {
            e.localizedMessage?.let { Log.e("firebasesAuthEx", it) }
            ResourceRemote.Error(message = e.localizedMessage)
        }
    }

}