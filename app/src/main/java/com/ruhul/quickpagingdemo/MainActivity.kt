package com.ruhul.quickpagingdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ruhul.quickpagingdemo.databinding.ActivityMainBinding
import com.ruhul.quickpagingdemo.paging.QuoteLoadAdapter
import com.ruhul.quickpagingdemo.paging.QuotePagingAdapter
import com.ruhul.quickpagingdemo.viewmodels.QuoteViewModel
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalPagingApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var quoteViewModel: QuoteViewModel
    private lateinit var adapter: QuotePagingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        quoteViewModel = ViewModelProvider(this)[QuoteViewModel::class.java]

        adapter = QuotePagingAdapter()

        binding.RV.layoutManager = LinearLayoutManager(this)
        binding.RV.setHasFixedSize(true)
        binding.RV.adapter = adapter.withLoadStateHeaderAndFooter(
            QuoteLoadAdapter(),
            QuoteLoadAdapter()
        )

        quoteViewModel.list.observe(this, Observer {
            adapter.submitData(lifecycle, it)
        })
    }
}