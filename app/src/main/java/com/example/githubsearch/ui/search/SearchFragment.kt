package com.example.githubsearch.ui.search

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.githubsearch.R
import com.example.githubsearch.databinding.FragmentSearchBinding
import com.example.githubsearch.ui.search.adapters.GithubLoadStateAdapter
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
            rvSearchResults.adapter = adapter.withLoadStateHeaderAndFooter(
                header = GithubLoadStateAdapter(adapter::retry),
                footer = GithubLoadStateAdapter(adapter::retry)
            )
//            rvSearchVariants.setHasFixedSize(true)
//            rvSearchVariants.adapter = adapter
        }

        viewModel.items.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_search, menu)

        //setup search
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { queryString ->
                    binding.rvSearchResults.scrollToPosition(0)
                    viewModel.searchItems(queryString)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}