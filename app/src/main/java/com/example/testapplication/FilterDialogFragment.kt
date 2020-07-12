package com.example.testapplication

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapplication.AppData.allFilter
import com.example.testapplication.AppData.getFilters
import com.example.testapplication.AppData.getSkills
import com.google.android.material.checkbox.MaterialCheckBox
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import kotlinx.android.synthetic.main.filter_item.*
import kotlinx.android.synthetic.main.filter_item.view.*
import kotlinx.android.synthetic.main.fragment_filter.*
import kotlin.math.max

class FilterDialogFragment : DialogFragment() {
    companion object {
        const val TAG = "DialogFragment"

        fun newInstance(): DialogFragment =
            FilterDialogFragment()
    }

    var filters: ArrayList<Filter> = getFilters()
    var checkBoxes: ArrayList<MaterialCheckBox> = arrayListOf()
    var listener: Callback? = null
    var skills: ArrayList<Skill> = getSkills()
    private var filterAdapter = ListDelegationAdapter(filterAdapterDelegate())
    private var currentFilterRule: Int = 0
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_filter, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.ThemeOverlay_AppTheme_Dialog_Fullscreen)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        floatingActionButton.setOnClickListener {
            if (currentFilterRule != null) {
                listener!!.sendOnSkills(skills.filter { it.time <= currentFilterRule })
                dismiss()
            }
        }
        filterAdapter.items = getFilters()
        with(filtersHolder) {
            adapter = filterAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun filterAdapterDelegate() =
        adapterDelegateLayoutContainer<Filter, Filter>(R.layout.filter_item) {
            checkBoxes.add(itemView.filter as MaterialCheckBox)
            bind {
                if (item.time != 0) {
                    filter.text = item.label
                } else {
                    filter.text = "All"
                }
            }
            filter.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    if (item.time != 0) {
                        currentFilterRule = max(currentFilterRule, item.time)
                        var allChecked = true
                        checkBoxes.forEach { if (!it.isChecked) allChecked = false }
                        if (allChecked) checkBoxes[0].isChecked = true
                    } else {
                        currentFilterRule = allFilter.time
                        checkBoxes.forEach { it.isChecked = true }
                    }
                } else {
                    checkBoxes[0].isChecked = false
                    if (currentFilterRule == allFilter.time || currentFilterRule == item.time) {
                        var mostFilter: Int = 0
                        for (i in 0 until checkBoxes.size) {
                            if (checkBoxes[i].isChecked) mostFilter = i
                        }
                        currentFilterRule = filters[mostFilter].time
                    }
                }
            }
        }

    public interface Callback {
        fun sendOnSkills(skills: List<Skill>)
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        listener = context as Callback
    }
}
