package com.jlindemann.science.activities

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jlindemann.science.R
import com.jlindemann.science.adapter.DictionaryAdapter
import com.jlindemann.science.adapter.ElectrodeAdapter
import com.jlindemann.science.model.Dictionary
import com.jlindemann.science.model.DictionaryModel
import com.jlindemann.science.model.Series
import com.jlindemann.science.model.SeriesModel
import com.jlindemann.science.preferences.ThemePreference
import com.jlindemann.science.utils.Utils
import kotlinx.android.synthetic.main.activity_dictionary.*
import kotlinx.android.synthetic.main.activity_dictionary.search_btn
import kotlinx.android.synthetic.main.activity_dictionary.title_box
import kotlinx.android.synthetic.main.activity_electrode.*
import java.util.*
import kotlin.collections.ArrayList


class ElectrodeActivity : BaseActivity(), ElectrodeAdapter.OnSeriesClickListener {
    private var seriesList = ArrayList<Series>()
    var mAdapter = ElectrodeAdapter(seriesList, this, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils.gestureSetup(window)

        val themePreference = ThemePreference(this)
        val themePrefValue = themePreference.getValue()

        if (themePrefValue == 100) {
            when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                Configuration.UI_MODE_NIGHT_NO -> { setTheme(R.style.AppTheme) }
                Configuration.UI_MODE_NIGHT_YES -> { setTheme(R.style.AppThemeDark) }
            }
        }
        if (themePrefValue == 0) { setTheme(R.style.AppTheme) }
        if (themePrefValue == 1) { setTheme(R.style.AppThemeDark) }
        setContentView(R.layout.activity_electrode) //REMEMBER: Never move any function calls above this

        recyclerView()
        clickSearch()

        view_ele.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        back_btn.setOnClickListener {
            this.onBackPressed()
        }
    }

    override fun onApplySystemInsets(top: Int, bottom: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val params = e_view.layoutParams as ViewGroup.MarginLayoutParams
            params.topMargin = top + resources.getDimensionPixelSize(R.dimen.title_bar)
            e_view.layoutParams = params

            val params2 = common_title_back_elo.layoutParams as ViewGroup.LayoutParams
            params2.height = top + resources.getDimensionPixelSize(R.dimen.title_bar)
            common_title_back_elo.layoutParams = params2
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            val params = e_view.layoutParams as ViewGroup.MarginLayoutParams
            params.topMargin += top
            e_view.layoutParams = params

            val params2 = common_title_back_elo.layoutParams as ViewGroup.LayoutParams
            params2.height += top
            common_title_back_elo.layoutParams = params2
        }
    }

    private fun recyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.e_view)
        val series = ArrayList<Series>()

        SeriesModel.getList(series)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        val adapter = ElectrodeAdapter(series, this, this)
        recyclerView.adapter = adapter


        adapter.notifyDataSetChanged()

        edit_ele.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {}
            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ){}

            override fun afterTextChanged(s: Editable) {
                filter(s.toString(), series, recyclerView)
            }
        })
    }

    private fun filter(text: String, list: ArrayList<Series>, recyclerView: RecyclerView) {
        val filteredList: ArrayList<Series> = ArrayList()
        for (item in list) {
            if (item.name.toLowerCase(Locale.ROOT).contains(text.toLowerCase(Locale.ROOT))) {
                filteredList.add(item)
            }
        }
        mAdapter.filterList(filteredList)
        mAdapter.notifyDataSetChanged()
        recyclerView.adapter = ElectrodeAdapter(filteredList, this, this)
    }

    private fun clickSearch() {
        search_btn.setOnClickListener {
            Utils.fadeInAnim(search_bar_ele, 150)

            val delayOpen = Handler()
            delayOpen.postDelayed({
                Utils.fadeOutAnim(title_box, 150)
            }, 151)

            edit_ele.requestFocus()
            val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(edit_ele, InputMethodManager.SHOW_IMPLICIT)
        }
        close_ele_search.setOnClickListener {
            Utils.fadeOutAnim(search_bar_ele, 150)

            val delayClose = Handler()
            delayClose.postDelayed({
                Utils.fadeInAnim(title_box, 150)
            }, 151)

            val view = this.currentFocus
            if (view != null) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }
    }

    override fun seriesClickListener(item: Series, position: Int) {
        TODO("Not yet implemented")
    }


}



