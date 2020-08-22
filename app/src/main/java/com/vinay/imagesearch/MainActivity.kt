package com.vinay.imagesearch

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding4.widget.textChanges
import com.vinay.imagesearch.adapters.ImagesAdapter
import com.vinay.imagesearch.models.ImageModel
import com.vinay.imagesearch.networkHelpers.ImageFetchHelper
import com.vinay.imagesearch.utils.Constants.TAG
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

/**
 * MainActivity.
 *
 * User can search for images on Imgur and results are fetched
 * and displyed as a list with title and image
 *
 * Both images and gifs are supported. ( video's are skipped and not shown)
 * @author vinay
 */
class MainActivity : AppCompatActivity() {

    var imagesAdapter: ImagesAdapter? = null
    var imagesList: ArrayList<ImageModel?> = ArrayList()
    var isLoading = false
    var currentPage = 1
    var searchQuery = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initAdapter()
        initScrollListener()

        // when user types using RxBindings changes are listened and queried
        et_search.textChanges()
            .debounce(250, TimeUnit.MILLISECONDS) // debounce for 250 milliseconds
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { it ->
                currentPage = 1
                searchQuery = it.toString()
                isLoading = false

                // Fist page loading. clearing previous query results and showing full screen loading progressbar
                imagesList.clear()
                imagesAdapter?.notifyDataSetChanged()
                pBSearch.visibility = View.VISIBLE

                ImageFetchHelper.getImageList(this, currentPage, searchQuery)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ item ->
                        imagesList.addAll(item!!)
                        imagesAdapter!!.notifyDataSetChanged()
                        // Hide full screen progressbar
                        pBSearch.visibility = View.GONE
                    },
                        { error ->
                            Toast.makeText(applicationContext, error.localizedMessage, Toast.LENGTH_SHORT).show()
                            Log.e(TAG, "loadMore: " + error.message)
                            pBSearch.visibility = View.GONE
                        },
                        { Log.i(TAG, "loadMore: Done") })
            }
    }

    private fun initAdapter() {
        imagesAdapter = ImagesAdapter(imagesList, this)
        recyclerView!!.adapter = imagesAdapter
    }

    // Scroll listener for load more action trigger
    private fun initScrollListener() {
        recyclerView!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == imagesList.size - 1) {
                        currentPage++;
                        loadMore()
                        isLoading = true
                    }
                }
            }
        })
    }

    private fun loadMore() {
        // null object is added which is handled in adapter to show loading progressbar
        imagesList.add(null)
        imagesAdapter!!.notifyItemInserted(imagesList.size - 1)

        ImageFetchHelper.getImageList(this, currentPage, searchQuery)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ item ->
                //Remove null item to hide progressbar
                imagesList.removeAt(imagesList.size - 1)
                val scrollPosition = imagesList.size
                imagesAdapter!!.notifyItemRemoved(scrollPosition)
                //add the new results to list
                imagesList.addAll(item!!)

                imagesAdapter!!.notifyDataSetChanged()
                isLoading = false
            },
                { error -> Log.e(TAG, "loadMore: " + error.message) },
                { Log.i(TAG, "loadMore: Done") })
    }

}