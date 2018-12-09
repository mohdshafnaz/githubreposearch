package com.example.shaffz.mvvmgettest.view

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.shaffz.mvvmgettest.R
import com.example.shaffz.mvvmgettest.local.db.AppDatabase
import com.example.shaffz.mvvmgettest.model.MainModel
import com.example.shaffz.mvvmgettest.viewmodel.MainViewModel
import com.example.shaffz.mvvmgettest.viewmodel.MainViewModelFactory
import com.example.shaffz.mvvmgettest.viewmodel.SchedulersWrapper

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item.view.*


class MainActivity : AppCompatActivity() {

    private lateinit var mMainViewModel: MainViewModel
    private lateinit var addressAdapter: AddressAdapter
    private var myDatabase: AppDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMainViewModel =
                ViewModelProviders.of(this, MainViewModelFactory(SchedulersWrapper())).get(MainViewModel::class.java)
        mMainViewModel.mainModel = MainModel(this)
         myDatabase = AppDatabase.getInstance(applicationContext)

        loadView()
        respondToClicks()
        listenToObservables()
    }

    @SuppressLint("CheckResult", "RxLeakedSubscription", "RxSubscribeOnError")
    private fun listenToObservables() {
        mMainViewModel.itemObservable.subscribe { goToDetailActivity(it) }
        mMainViewModel.getResultListObservable().observe(this, Observer {
            hideProgressBar()
            if (it != null) {
                updateMovieList(it)
            }
        })
        mMainViewModel.getResultListErrorObservable().observe(this, Observer {
            hideProgressBar()
            showErrorMessage(it!!.message())
        })
    }

    private fun loadView() {
        setContentView(R.layout.activity_main)
        addressAdapter = AddressAdapter(this)
        main_activity_recyclerView.adapter = addressAdapter
    }

    private fun respondToClicks() {
        main_activity_button.setOnClickListener {
            main_activity_editText.clearFocus()
            showSoftKeyboard(false, this)
            showProgressBar()
            mMainViewModel.findAddress(main_activity_editText.text.toString())
        }
        addressAdapter setItemClickMethod {
            mMainViewModel.doOnItemClick(it)
        }
    }

    fun showProgressBar() {
        main_activity_progress_bar.visibility = View.VISIBLE
    }

    fun hideProgressBar() {
        main_activity_progress_bar.visibility = View.GONE
    }

    fun showErrorMessage(errorMsg: String) {
        Toast.makeText(this, "Error retrieving data: $errorMsg", Toast.LENGTH_SHORT).show()
    }

    fun updateMovieList(t: List<MainModel.ResultEntity1>) {
        addressAdapter.updateList(t)
        addressAdapter.notifyDataSetChanged()
    }

    fun goToDetailActivity(item: MainModel.ResultEntity1) {
        var bundle = Bundle()
        bundle.putString(DetailActivity.Constants.ID, item.id)
        bundle.putString(DetailActivity.Constants.NAME, item.name)
        bundle.putString(DetailActivity.Constants.DESCRIPTION, item.description)
        bundle.putString(DetailActivity.Constants.TYPE, item.owner.type)
        var intent = Intent(this, DetailActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    private fun showSoftKeyboard(showKeyboard: Boolean, activity: AppCompatActivity) {
        val inputManager = activity.getSystemService(
            Context.INPUT_METHOD_SERVICE
        ) as InputMethodManager
        if (activity.currentFocus != null) {
            inputManager.hideSoftInputFromWindow(
                activity.currentFocus!!.windowToken,
                if (showKeyboard)
                    InputMethodManager.SHOW_FORCED
                else
                    InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }

}



class AddressAdapter(private val mContext: Context) : RecyclerView.Adapter<AddressAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.item, parent, false)
        return Holder(view)
    }

    private var mList: List<MainModel.ResultEntity1> = arrayListOf()
    private lateinit var mOnClick: (position: Int) -> Unit

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.itemView.item_textView.text = mList[position].name
        holder.itemView.languages.text = mList[position].description
        holder.itemView.project_watchers.text = mList[position].owner.type
        Glide.with(mContext)
            .load(mList[position].owner.avatar_url).placeholder(R.drawable.github)
            .into((holder.itemView.ImgViewOwner))
        holder.itemView.cardView01.setOnClickListener { mOnClick(position) }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    infix fun setItemClickMethod(onClick: (position: Int) -> Unit) {
        this.mOnClick = onClick
    }

    fun updateList(list: List<MainModel.ResultEntity1>) {
        mList = list
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView)


}

