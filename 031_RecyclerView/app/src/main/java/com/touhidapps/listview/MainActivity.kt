package com.touhidapps.listview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.touhidapps.listview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val mCountries = arrayListOf<CountryModel>()
    private lateinit var adapter: CountryAdapter
    private var mPageNo: Int = 1
    private val PER_PAGE_DATA = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = CountryAdapter(mCountries)
        adapter.setItemClick {

        }
        val lm = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.layoutManager = lm
        binding.recyclerView.adapter = adapter

        binding.swipeRefreshLayout.post {
            loadData(mPageNo)
        }

        binding.swipeRefreshLayout.setOnRefreshListener {

            mPageNo = 1
            loadData(mPageNo)

        }

        binding.recyclerView.addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if(dy > 0) {

                    recyclerView.layoutManager?.let {
                        val lm = it as LinearLayoutManager
                        val totalItemCount = lm.itemCount

                        if (!binding.swipeRefreshLayout.isRefreshing &&
                            totalItemCount == lm.findLastVisibleItemPosition() + 1 &&
                            (totalItemCount % PER_PAGE_DATA) == 0
                            ) {
                            mPageNo++
                            loadData(mPageNo)
                        }

                    }

                }

            }
        })

    } // onCreate


    private fun loadData(pageNo: Int) {

        binding.swipeRefreshLayout.isRefreshing = true

        Handler().postDelayed(Runnable { // TODO API call for data

            if (pageNo == 1) {
                mCountries.clear()
            }

//            mCountries.addAll(getData(pageNo))
//            adapter.notifyDataSetChanged()

            val mData = mCountries.clone() as ArrayList<CountryModel>
            mData.addAll(getData(pageNo))
            adapter.updateItems(mData)



            binding.swipeRefreshLayout.isRefreshing = false

        }, 3000)




    }

    private fun getData(pageNo: Int): ArrayList<CountryModel> {

        return if (pageNo == 1) {
            arrayListOf(
                CountryModel(
                    id = 1,
                    countryName = "Bangladesh",
                    countryCode = "+88"
                ),
                CountryModel(
                    id = 2,
                    countryName = "Iran",
                    countryCode = "+98"
                ),
                CountryModel(
                    id = 3,
                    countryName = "Turkey",
                    countryCode = "+90"
                ),
                CountryModel(
                    id = 4,
                    countryName = "Nepal",
                    countryCode = "+977"
                ),
                CountryModel(
                    id = 5,
                    countryName = "Thailand",
                    countryCode = "+66"
                ),
                CountryModel(
                    id = 6,
                    countryName = "Myanmar",
                    countryCode = "+95"
                ),
                CountryModel(
                    id = 7,
                    countryName = "Japan",
                    countryCode = "+81"
                ),
                CountryModel(
                    id = 8,
                    countryName = "Malaysia",
                    countryCode = "+60"
                ),
                CountryModel(
                    id = 9,
                    countryName = "Korea",
                    countryCode = "+82"
                ),
                CountryModel(
                    id = 10,
                    countryName = "Sri Lanka",
                    countryCode = "+94"
                ),
            )
        } else if (pageNo == 2) {
            arrayListOf(
                CountryModel(
                    id = 11,
                    countryName = "Bangladesh - 2",
                    countryCode = "+88 - 2"
                ),
                CountryModel(
                    id = 12,
                    countryName = "Iran - 2",
                    countryCode = "+98 - 2"
                ),
                CountryModel(
                    id = 13,
                    countryName = "Turkey - 2",
                    countryCode = "+90 - 2"
                ),
                CountryModel(
                    id = 14,
                    countryName = "Nepal - 2",
                    countryCode = "+977 - 2"
                ),
                CountryModel(
                    id = 15,
                    countryName = "Thailand - 2",
                    countryCode = "+66 - 2"
                ),
                CountryModel(
                    id = 16,
                    countryName = "Myanmar - 2",
                    countryCode = "+95 - 2"
                ),
                CountryModel(
                    id = 17,
                    countryName = "Japan - 2",
                    countryCode = "+81 - 2"
                ),
                CountryModel(
                    id = 18,
                    countryName = "Malaysia - 2",
                    countryCode = "+60 - 2"
                ),
                CountryModel(
                    id = 19,
                    countryName = "Korea - 2",
                    countryCode = "+82 - 2"
                ),
                CountryModel(
                    id = 20,
                    countryName = "Sri Lanka - 2",
                    countryCode = "+94 - 2"
                ),
            )
        } else if (pageNo == 3) {
            arrayListOf(
                CountryModel(
                    id = 21,
                    countryName = "Bangladesh - 3",
                    countryCode = "+88 - 3"
                )
            )
        } else {
            arrayListOf<CountryModel>()
        }

    }


}