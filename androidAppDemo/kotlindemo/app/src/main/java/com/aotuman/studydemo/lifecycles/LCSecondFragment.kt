package com.aotuman.studydemo.lifecycles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.aotuman.studydemo.R
import com.aotuman.studydemo.lifecycles.bean.Person
import kotlinx.android.synthetic.main.lc_fragment_second.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class LCSecondFragment : Fragment() {

    private lateinit var viewModel: PersonViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(requireActivity()).get(PersonViewModel::class.java)
        return inflater.inflate(R.layout.lc_fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribePerson()

        button_second.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    private fun subscribePerson() {
        viewModel.personValue.observe(viewLifecycleOwner,
            Observer<List<Person>> {
                textview_second.text =  "fragment2集合长度：" + it.size
            })
    }
}