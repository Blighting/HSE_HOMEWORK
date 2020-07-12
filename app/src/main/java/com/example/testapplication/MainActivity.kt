package com.example.testapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapplication.AppData.getSkills
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.skill_item.*

class MainActivity : AppCompatActivity(), FilterDialogFragment.Callback {
    private var skills: ArrayList<Skill> = getSkills()
    private var skillsAdapter = ListDelegationAdapter(skillsAdapterDelegate())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        github_link.setOnClickListener {
            val openLink = Intent(Intent.ACTION_VIEW)
            openLink.data = Uri.parse("https://github.com/Blighting")
            startActivityForResult(openLink, 1)
        }
        dialog_fragment.setOnClickListener {
            FilterDialogFragment
                .newInstance()
                .show(supportFragmentManager, FilterDialogFragment.TAG)
        }
        skillsAdapter.items = skills
        with(skillsContainer) {
            adapter = skillsAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun skillsAdapterDelegate() =
        adapterDelegateLayoutContainer<Skill, Skill>(R.layout.skill_item) {
            bind {
                langName.text = item.lang
                exp.text = item.exp
            }
        }

    override fun sendOnSkills(skills: List<Skill>) {
        skillsAdapter.items = skills
        with(skillsContainer) {
            adapter = skillsAdapter
        }
        skillsContainer.adapter?.notifyDataSetChanged()
    }
}
