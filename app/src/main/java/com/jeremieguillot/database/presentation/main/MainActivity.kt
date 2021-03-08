package com.jeremieguillot.database.presentation.main

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.jeremieguillot.database.R
import com.jeremieguillot.database.domain.model.ShoppingItem
import com.jeremieguillot.database.presentation.main.recyclier.ShoppingAdapterListener
import com.jeremieguillot.database.presentation.main.recyclier.ShoppingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ShoppingAdapterListener {

    private val viewModel: MainViewModel by viewModels()
    private val adapter = ShoppingAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpRecyclerView()
        setUpEditTextListener()
        setUpAddButton()
        populateRecycler()
    }

    private fun setUpEditTextListener() {
        val edittextFilter: EditText = findViewById(R.id.et_filter)
        edittextFilter.doOnTextChanged { text, _, _, count ->

            viewModel.filterList(text.toString())
        }
    }

    private fun setUpRecyclerView() {
        val recyclerView: RecyclerView = findViewById(R.id.recyclerview)
        recyclerView.adapter = adapter
    }

    private fun populateRecycler() {
        viewModel.getAllShoppingItems()
        viewModel.items.observe(this, {
            adapter.setData(it)
        })
    }

    private fun setUpAddButton() {
        val btnAdd: Button = findViewById(R.id.btn_add)
        btnAdd.setOnClickListener {
            showDialog(it.context)
        }
    }

    private fun showDialog(context: Context) {
        val messageBoxView = LayoutInflater.from(context).inflate(R.layout.dialog, null)
        val etName: EditText = messageBoxView.findViewById(R.id.et_name)
        val etQuantity: EditText = messageBoxView.findViewById(R.id.et_quantity)

        val dialogBuilder = AlertDialog.Builder(context).apply {
            setView(messageBoxView)
            setPositiveButton(android.R.string.ok) { dialog, _ ->
                val name = etName.text.toString()
                val quantity = etQuantity.text.toString().toInt()
                viewModel.addItem(name, quantity)
                //close dialog
                dialog.dismiss()
            }
            setNegativeButton(android.R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }
        }

        dialogBuilder.show()
    }

    private fun deleteDialog(context: Context, item: ShoppingItem) {
        AlertDialog.Builder(context).apply {
            setTitle(R.string.delete)
            setMessage(R.string.delete_item)
            setPositiveButton(android.R.string.ok) { dialog, _ ->
                viewModel.delete(item)
                dialog.dismiss()
            }
            setNegativeButton(android.R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }
            show()
        }
    }

    override fun onShoppingItemClicked(item: ShoppingItem) {
        deleteDialog(this, item)
    }
}