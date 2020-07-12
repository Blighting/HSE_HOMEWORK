package com.example.testapplication

object AppData {
    public var allFilter = Filter(100, 'y')
    fun getSkills(): ArrayList<Skill> =
        arrayListOf(
            Skill("Kotlin", 5, 'd'),
            Skill("Java", 2, 'y'),
            Skill("C++", 1, 'y'),
            Skill("Python", 1, 'y')
        )

    fun getFilters(): ArrayList<Filter> =
        arrayListOf(
            Filter(0, 'd', selectAll = true),
            Filter(6, 'd'),
            Filter(1, 'y'),
            Filter(2, 'y')
        )
}
