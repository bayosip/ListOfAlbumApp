package com.vertex5.jpmorganalbums.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.vertex5.jpmorganalbums.R
import com.vertex5.jpmorganalbums.databinding.FragmentAlbumListBinding
import com.vertex5.jpmorganalbums.view_model.ListViewModel


/**
 * A fragment representing a list of Items.
 */
class AlbumFragment : BaseFragment(), PopupMenu.OnMenuItemClickListener {

    private var columnCount = 1
    private lateinit var binding:FragmentAlbumListBinding
    private lateinit var viewModel: ListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_album_list, container, false)
        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentAlbumListBinding.bind(view)
        viewModel.getUnfilteredAlbumListFromDatabase()
        listener?.setupActionBar()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initialiseWidgets() {

            with(binding) {
                list.layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                val adapter= MyItemRecyclerViewAdapter()
                list.adapter = adapter
                viewModel.unfilteredAlbums.observe(viewLifecycleOwner, {
                    if (it.isEmpty()){
                        Log.d(TAG, "initialiseWidgets: ")
                        viewModel.getAlbumDataFromNetworkService()
                    }
                    adapter.setAlbumList(it)
                })
                btnFilter.setOnClickListener {
                    val popup = PopupMenu(requireContext(), it)
                    val inflater: MenuInflater = popup.getMenuInflater()
                    popup.setOnMenuItemClickListener(this@AlbumFragment)
                    inflater.inflate(R.menu.filter_menu, popup.getMenu())
                    popup.show()
                }
            }
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        return when (item.getItemId()) {
            R.id.filter_asc -> {
                viewModel.getAlbumListFromDbInSpecifiedOrder(ListViewModel.OrderType.ASC)
                true
            }
            R.id.filter_desc -> {
                viewModel.getAlbumListFromDbInSpecifiedOrder(ListViewModel.OrderType.DESC)
                true
            }
            else -> false
        }
    }

    companion object {

        private const val TAG = "AlbumFragment"
        @JvmStatic
        fun newInstance() = AlbumFragment()
    }
}