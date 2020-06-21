package com.aotuman.studydemo.lifecycles

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aotuman.studydemo.lifecycles.bean.Person

class PersonViewModel : ViewModel() {
    var personValue = MutableLiveData<MutableList<Person>>()

    fun print(): String {
        var result = StringBuffer()
        personValue.value?.forEach {
            result.append(it.name).append(it.sex).append("\n")
        }
        return result.toString()
    }
}