package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var canoperate = false
    private var decalt = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
    fun allclearact(view: View){
        binding.answ.text = ""
        binding.rest.text = ""
    }
    fun backact(view: View){
        val lenght = binding.rest.length()
        if (lenght>0){
            binding.rest.text = binding.rest.text.subSequence(0, lenght-1)
        }

    }
    fun numbact (view: View){
        if (view is Button){
            if (view.text == "."){
                if (decalt){
                    binding.rest.append(view.text)
                    decalt = false
                }
                else {
                    binding.rest.append(view.text)
                }

            }
            binding.rest.append(view.text)
            canoperate = true
        }
    }
    fun operation (view: View){
        if (view is Button && canoperate){
            binding.rest.append(view.text)
            canoperate = false
            decalt = true
        }
    }
    fun ecualstoact (view: View){
        binding.answ.text = calculateresult()
    }

    private fun calculateresult(): String {
        val  digitoperator = digitop()
        if (digitoperator.isEmpty()) return ""

        val  timesDivision = timedivisioncalculate(digitoperator)
        if (digitoperator.isEmpty()) return ""

        val result = addsubtractcalculate(timesDivision as MutableList<Any>)

        return result.toString()
    }

    private fun addsubtractcalculate(passedlist: MutableList<Any>): Float {
        var result = passedlist[0] as Float
        for (i in passedlist.indices){
            if (passedlist[i] is Char && i != passedlist.lastIndex){
                val operator= passedlist[i]
                val nextdigit = passedlist[i +1] as Float
                if (operator == '+')
                    result += nextdigit
                if (operator == '-')
                    result -= nextdigit
            }
        }
        return result

    }

    private fun timedivisioncalculate(passedlist: MutableList<Any>): Any {
    var list = passedlist
        while(list.contains('x')||list.contains('/')){
            list=caltimesdiv(list)
        }
        return list
    }

    private fun caltimesdiv(passedlist: MutableList<Any>): MutableList<Any> {
        val newList = mutableListOf<Any>()
        var restartindex = passedlist.size

        for (i in passedlist.indices){
            if(passedlist[i]is Char && i != passedlist.lastIndex && i < restartindex){
                val operator = passedlist[i]
                val  prevdigit = passedlist[i - 1] as Float
                val  nextdigit = passedlist[i + 1] as Float
                when (operator)
                {
                    'x'-> {
                        newList.add(prevdigit * nextdigit)
                        restartindex = i +1
                    }
                    '/'-> {
                        newList.add(prevdigit / nextdigit)
                        restartindex = i +1
                    }
                    else -> {
                        newList.add(prevdigit)
                        newList.add(operator)
                    }
                }
            }
            if(i > restartindex)
                newList.add(passedlist[i])
        }
        return  newList
    }

    private fun digitop(): MutableList<Any>{
        val list = mutableListOf<Any>()
        var currentdigit = ""
        for (character in binding.rest.text){
            if( character.isDigit() || character == '.')
                currentdigit += character
            else{
                list.add(currentdigit.toFloat())
                currentdigit = ""
                list.add((character))
            }
        }
        if (currentdigit != "")
            list.add(currentdigit.toFloat())
        return  list
    }

}