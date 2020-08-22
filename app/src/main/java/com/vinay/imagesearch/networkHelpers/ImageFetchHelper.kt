package com.vinay.imagesearch.networkHelpers

import android.content.Context
import android.util.Log
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.vinay.imagesearch.models.ImageModel
import com.vinay.imagesearch.utils.Constants
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import java.net.URLEncoder
import java.util.*

/**
 * ImageFetchHelper.
 *
 * to make getImageList static it is defined in a object rather than a class
 *
 * Volley library to used to make get query
 * and Imgur Api is used for fetching image results
 *
 * @author vinay
 */

object ImageFetchHelper {

    fun getImageList(
        context: Context,
        currentPage: Int,
        query: String?
    ): Observable<ArrayList<ImageModel?>?> {

        //Replace white spaces
        val searchQuery = URLEncoder.encode(query, "UTF-8").replace("+", "%20")

        val imagesList: ArrayList<ImageModel?> = ArrayList()
        return Observable.create { subscriber: ObservableEmitter<ArrayList<ImageModel?>?>? ->
            //Imgur Api get endpoint
            val url = "${Constants.IMGUR_API}/$currentPage?q=$searchQuery"
            val stringRequest = object : StringRequest(
                Method.GET, url,
                Response.Listener { response ->
                    val res = Gson().fromJson(response.toString(), Map::class.java)
                    val data = (res["data"] as ArrayList<Map<String, Any>>)
                    Log.i(Constants.TAG, data.size.toString())
                    // looping on each result for images
                    for (u in data) {
                        val images = u["images"] as ArrayList<Map<String, Any?>>?
                        if (images != null) {
                            for (img in images) {
                                // Considering only images. vomiting out videos/gifs
                                if (img["type"].toString().startsWith("image")) {
                                    val imgdata = ImageModel(
                                        img["id"]?.toString(),
                                        img["link"]?.toString(),
                                        u["title"]?.toString()
                                    )
                                    imagesList.add(imgdata)
                                }
                            }
                        }
                    }
                    subscriber?.onNext(imagesList);
                    subscriber?.onComplete();
                    Log.i(Constants.TAG, "count: " + imagesList.size)
                },
                Response.ErrorListener { error ->
                    subscriber?.onError(error);
                    Log.e(Constants.TAG, error.toString())
                }) {
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val params: MutableMap<String, String> = HashMap()
                    params["Authorization"] = Constants.ACCESS_TOKEN
                    params["User-Agent"] = "My Little App"
                    return params
                }
            }
            val queue = Volley.newRequestQueue(context)
            queue.add(stringRequest)
        }
    }
}