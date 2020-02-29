package com.ivzb.chicks.data.links

import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.ivzb.chicks.model.Link
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Feed data source backed by items in a FireStore collection.
 */
class RemoteLinkDataSource @Inject constructor(
    val firestore: FirebaseFirestore
) : LinkDataSource {

    override fun getLinks(): List<Link> {
        val task = firestore
            .collection(COLLECTION_FEED)
            .get()

        val snapshot = Tasks.await(task, 20, TimeUnit.SECONDS)

        return snapshot.documents.map { parseSnapshot(it) }
            .sortedWith(compareByDescending { it.timestamp })
    }

    private fun parseSnapshot(snapshot: DocumentSnapshot) = Link(
        url = snapshot[URL] as? String ?: "",
        title = snapshot[TITLE] as? String ?: "",
        imageUrl = snapshot[IMAGE_URL] as? String,
        timestamp = snapshot[TIMESTAMP] as? Long ?: 0
    )

    companion object {
        /**
         * Firestore constants.
         */
        private const val COLLECTION_FEED = "feed"
        private const val COLLECTION_TOP = "top"

        private const val URL = "url"
        private const val TITLE = "title"
        private const val IMAGE_URL = "imageUrl"
        private const val TIMESTAMP = "timeStamp"
    }
}
