package com.deathsdoor.chillbackmusicplayer.data.firebase

/*
//TODO store THRESHOLD in sharedpreference and then
// create workmanager to update the thing and a interface
// that gets data and then deletes it
//TODO use this and make it do something
@OptIn(DelicateCoroutinesApi::class)
class FirestoreOptimizer(private val context:Context, private val firebaseCollection: CollectionReference, private val THRESHOLD: Int = 50) {
    companion object{
        val firebaseFirestore by lazy { FirebaseFirestore.getInstance() }

        fun <T : Any> upload(data:Document<T>){
            if(data.action == DOCUMENT.CREATE) firebaseFirestore.collection("${data.path}/${data.id}").add(data.data)
            if(data.action == DOCUMENT.UPDATE) firebaseFirestore.collection(data.path).document(data.id).update(data.data)
            if(data.action == DOCUMENT.CREATE) firebaseFirestore.collection(data.path).add(data.data)
            if(data.action == DOCUMENT.CREATE) firebaseFirestore.collection(data.path).add(data.data)
        }
    }

    private val dao = AppDataBase.dataBase(context).fireStoreOptimizerDao()

    enum class DOCUMENT {
        CREATE,
        DELETE,
        UPDATE
    }

    @Entity
    data class Document<T>(
        val path:String,
        val data:T,
        val action:DOCUMENT,
        @PrimaryKey(autoGenerate = false) val id:String = UUID.randomUUID().toString()
    )


    fun <T : Any> createDocument(data:T) {
        GlobalScope.launch { dao.insert(Document(path = firebaseCollection.path,data,DOCUMENT.CREATE)) }
    }
    suspend fun <T : Any> getDocument(docID:String):Document<T>? = CoroutineHelper.onDefaultThread { dao.document(docID) }

    //TODO delete task if there in room db else delete it in the firestore
    fun deleteDocument(){

    }
}
/*
* class FirestoreHelper(val activity: Activity) {
    companion object {
        val FB_FIRESTORE = FirebaseFirestore.getInstance()
    }

    fun addDocument(collection: String, data: Map<String, Any>, onSuccess: () -> Unit, onFailure: (e: Exception) -> Unit) {
        FB_FIRESTORE.collection(collection)
            .add(data)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }

    fun getDocument(collection: String, document: String, onSuccess: (documentSnapshot: DocumentSnapshot) -> Unit, onFailure: (e: Exception) -> Unit) {
        FB_FIRESTORE.collection(collection).document(document)
            .get()
            .addOnSuccessListener { onSuccess(it) }
            .addOnFailureListener { onFailure(it) }
    }

    fun updateDocument(collection: String, document: String, data: Map<String, Any>, onSuccess: () -> Unit, onFailure: (e: Exception) -> Unit) {
        FB_FIRESTORE.collection(collection).document(document)
            .update(data)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }

    fun deleteDocument(collection: String, document: String, onSuccess: () -> Unit, onFailure: (e: Exception) -> Unit) {
        FB_FIRESTORE.collection(collection).document(document)
            .delete()
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }

    fun queryCollection(collection: String, query: Query, onSuccess: (querySnapshot: QuerySnapshot) -> Unit, onFailure: (e: Exception) -> Unit) {
        query.get()
            .addOnSuccessListener
*/