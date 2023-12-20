package com.daisy.picky.tutorial

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.daisy.picky.BaseActivity
import com.daisy.picky.R
import com.daisy.picky.databinding.ActivityTutorialBinding
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class TutorialActivity : BaseActivity() {

    private lateinit var binding: ActivityTutorialBinding

    private var curPage:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTutorialBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (isTutorial){ // initial tutorial
            Log.d("tutorialTest", isTutorial.toString())
            binding.btnExit.visibility = View.INVISIBLE
            binding.btnSkip.visibility = View.VISIBLE
        } else { // tutorial menu
            binding.btnSkip.visibility = View.INVISIBLE
            binding.btnExit.visibility = View.VISIBLE
        }

        isTutorial = false
        prefs = this.getSharedPreferences("picky", MODE_PRIVATE)
        prefs.edit().putBoolean("tutorial", isTutorial).apply()

        val vpAdapter = PagerFragmentStateAdapter(this)
        vpAdapter.addFragment(FirstFragment())
        vpAdapter.addFragment(SecondFragment())
        vpAdapter.addFragment(ThirdFragment())
        vpAdapter.addFragment(FourthFragment())
        vpAdapter.addFragment(FifthFragment())
        binding.vpTutorial.adapter = vpAdapter
        binding.indicator.setViewPager2(binding.vpTutorial)
        //binding.vpTutorial.setUserInputEnabled(false)

        binding.vpTutorial.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                Log.e("ViewPagerFragment", "Page ${position+1}")
            }
        })


        binding.vpTutorial.setCurrentItem(0, true)
        /*
        val subject: PublishSubject<Int> = PublishSubject.create()
        val mCompositeDisposable = CompositeDisposable()

        val disposable = subject.throttleFirst(1, TimeUnit.SECONDS)
            .subscribe{
                binding.btnNext.callOnClick()
                binding.btnPrev.callOnClick()
            }
        mCompositeDisposable.add(disposable)
*/
        binding.btnSkip.setOnClickListener {
            finish()
        }
        binding.btnExit.setOnClickListener {
            finish()
        }

        /*
        binding.btnNext.setOnClickListener {

            var curPage = binding.vpTutorial.currentItem
            if (curPage == 4){
                finish()
            }else {
                curPage += 1

                if (curPage == 4){
                    binding.btnNext.text = resources.getString(R.string.btn_done)
                    binding.btnNext.visibility = View.VISIBLE
                    binding.btnPrev.visibility = View.VISIBLE
                    binding.vpTutorial.setCurrentItem(curPage, true)
                } else if (curPage < 4) {
                    binding.btnNext.visibility = View.VISIBLE
                    binding.btnPrev.visibility = View.VISIBLE
                    binding.vpTutorial.setCurrentItem(curPage, true)
                }
            }



        }
        binding.btnPrev.setOnClickListener {

            var curPage = binding.vpTutorial.currentItem
            curPage -= 1

            if (curPage == 0){
                binding.btnNext.visibility = View.VISIBLE
                binding.btnPrev.visibility = View.INVISIBLE

                binding.vpTutorial.setCurrentItem(curPage, true)
            } else if (curPage > 0) {
                binding.btnNext.text =resources.getString(R.string.btn_next)
                binding.btnNext.visibility = View.VISIBLE
                binding.btnPrev.visibility = View.VISIBLE

                binding.vpTutorial.setCurrentItem(curPage, true)
            }


        }
         */

    }


}