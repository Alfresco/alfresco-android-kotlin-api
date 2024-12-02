package com.alfresco.sample

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alfresco.content.models.ResultNode
import com.alfresco.sample.databinding.ActivityMainBinding
import com.alfresco.sample.databinding.ViewResultRowBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class ResultAdapter(var resultList: List<ResultNode>) :
    RecyclerView.Adapter<ResultAdapter.ResultViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ViewResultRowBinding.inflate(layoutInflater, parent, false)
        return ResultViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return resultList.count()
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        holder.bind(resultList[position])
    }

    class ResultViewHolder(private val binding: ViewResultRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(result: ResultNode?) {
            binding.result = result
            binding.executePendingBindings()
        }
    }
}

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels { MainViewModel.Factory(applicationContext) }
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ResultAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val recyclerView: RecyclerView = binding.recyclerView
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        val resultList: List<ResultNode> = emptyList()
        adapter = ResultAdapter(resultList)
        recyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()

        val acc = Account.getAccount(this)
        if (acc == null) {
            navigateToLogin()
            return
        }

        viewModel.results.observe(this) {
            adapter.resultList = it
            adapter.notifyDataSetChanged()
        }
        viewModel.resultsConfig.observe(this) {

        }

        viewModel.onSessionExpired.observe(this, ::onSessionExpired)
        viewModel.onError.observe(this, ::onError)
        viewModel.loadRecents()
    }

    private fun navigateToLogin() {
        val i = Intent(this, LoginActivity::class.java)
        startActivity(i)
        finish()
    }

    private fun onSessionExpired(expired: Boolean) {
        MaterialAlertDialogBuilder(this)
            .setTitle(resources.getString(R.string.auth_session_expired_title))
            .setMessage(resources.getString(R.string.auth_session_expired_msg))
            .setCancelable(false)
            .setPositiveButton(resources.getString(R.string.auth_session_expired_button)) { _, _ ->
                logout()
            }
            .show()
    }

    private fun onError(error: String) {
        val parentLayout: View = findViewById(android.R.id.content)
        Snackbar.make(parentLayout,
            error,
            Snackbar.LENGTH_LONG).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout -> {
                logout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun logout() {
        val intent = Intent(this, LogoutActivity::class.java)
        startActivityForResult(intent, REQUEST_CODE_LOGOUT)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_LOGOUT) {
            if (resultCode == Activity.RESULT_OK) {
                Account.delete(this)
                navigateToLogin()
            } else {
                // no-op
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    companion object {
        const val REQUEST_CODE_LOGOUT = 0
    }
}
