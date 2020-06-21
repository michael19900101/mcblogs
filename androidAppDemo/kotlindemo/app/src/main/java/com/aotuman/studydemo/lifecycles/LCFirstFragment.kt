package com.aotuman.studydemo.lifecycles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.aotuman.studydemo.R
import com.aotuman.studydemo.lifecycles.bean.Person
import kotlinx.android.synthetic.main.lc_fragment_first.*


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class LCFirstFragment : Fragment() {

    private lateinit var viewModel: PersonViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(requireActivity()).get(PersonViewModel::class.java)
        return inflater.inflate(R.layout.lc_fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribePerson()
        button_first.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    private fun subscribePerson() {
        // 注意：这里不能用requireActivity(),因为如果fragment1不可见的时候，还会回掉回来
        viewModel.personValue.observe(viewLifecycleOwner,
            Observer<List<Person>> {
                textview_first.text =  "fragment1集合长度：" + it.size
            })
    }
}