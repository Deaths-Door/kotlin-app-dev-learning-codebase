package com.example.calculator30

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.example.calculator30.databinding.ActivityMainBinding
import com.example.calculator30.databinding.ActivityMainBinding.inflate
import com.example.calculator30.fragments.input.InputKeyPad
import com.example.calculator30.fragments.pages.Calculator
import com.example.calculator30.fragments.pages.Convertor
import com.example.calculator30.fragments.pages.Notes
import com.example.calculator30.viewmodel.HistoryViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_calculator.*
import kotlinx.android.synthetic.main.fragment_convertor.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener,ViewPager.OnPageChangeListener {
    private lateinit var binding: ActivityMainBinding
    private val vm by lazy{
        ViewModelProvider(this)[HistoryViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
2
        binding = inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        //view pager
        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        with(viewPagerAdapter) {
            addFragment(Calculator(),"Calculator")
            addFragment(Convertor(),"Convertor")
            addFragment(Notes(),"Notes")
        }
        binding.viewPager.adapter = viewPagerAdapter

        //setting up it with viewpager
        binding.bottomNav.setOnNavigationItemSelectedListener(this)
        binding.viewPager.addOnPageChangeListener(this)

        //default values
        vm.changeValue(0,History("",""))
        vm.changeValue(1,History("",""))

        //any changes in written data
        vm.writtenNums.observe(this){
            when(binding.viewPager.currentItem){
                //change texts in full app
                0 -> {
                    currentEquation.text = vm.writtenNums.value?.a?.s1
                    currentEquationAnsHint.text = vm.writtenNums.value?.a?.s2
                }
                1 ->{
                    tv1.text = vm.writtenNums.value?.b?.s1
                    tv2.text = vm.writtenNums.value?.b?.s2
                }
            }
        }

        //get all notes
        //GlobalScope.async {
        vm.getAllNotes()
        //})
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.page_1 -> {
                binding.viewPager.currentItem = 0
                defaultValue1()
            }
            R.id.page_2 ->{
                binding.viewPager.currentItem = 1
               defaultValue2()
            }
            R.id.page_3 ->{
                binding.viewPager.currentItem = 2
            }
        }
        return true
    }
    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
    override fun onPageScrollStateChanged(state: Int) {}
    override fun onPageSelected(position: Int) {
        when(position){
            0 -> {
                binding.bottomNav.menu.findItem(R.id.page_1).isChecked = true
                defaultValue1()
            }
            1 -> {
                binding.bottomNav.menu.findItem(R.id.page_2).isChecked = true
                defaultValue2()
            }
            2 ->{
                binding.bottomNav.menu.findItem(R.id.page_3).isChecked = true
            }
        }
    }
    //default values or reput text when fragment changed
    fun defaultValue1(){
        currentEquation.text = vm.writtenNums.value?.a?.s1
        currentEquationAnsHint.text = vm.writtenNums.value?.a?.s2
    }
    fun defaultValue2(){
        //default values
        tv1.text = vm.writtenNums.value?.b?.s1
        tv2.text = vm.writtenNums.value?.b?.s2
    }
    //numeric keypad pressed
    fun pressed(view : View){
        var s1 = ""
        var s2 = ""
        when(binding.viewPager.currentItem){
            0 ->{
                s1 = InputKeyPad(vm.writtenNums.value!!.a.s1).keypressed(view)
                s2 = InputKeyPad("").solve(vm.writtenNums.value!!.a.s1)
                vm.changeValue(0, History(s1,s2))
            }
        }
    }
}
