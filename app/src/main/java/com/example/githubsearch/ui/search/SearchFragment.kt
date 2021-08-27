package com.example.githubsearch.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.githubsearch.R
import com.example.githubsearch.databinding.FragmentSearchBinding
import com.example.githubsearch.ui.search.adapters.GithubUserAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private var _binding: FragmentSearchBinding? = null
    private val binding  get() = _binding!!

    private val viewModel by viewModels<SearchViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = GithubUserAdapter()

        binding.run {
            rvSearchResults.setHasFixedSize(true)
            rvSearchResults.adapter = adapter
//            rvSearchVariants.setHasFixedSize(true)
//            rvSearchVariants.adapter = adapter
        }

        viewModel.items.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}