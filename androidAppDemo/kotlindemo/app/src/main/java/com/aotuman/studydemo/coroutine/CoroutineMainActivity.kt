package com.aotuman.studydemo.coroutine

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.aotuman.studydemo.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.util.*
import kotlin.coroutines.CoroutineContext


@ObsoleteCoroutinesApi
val BG = newSingleThreadContext("Background")

class CoroutineMainActivity : AppCompatActivity(), CoroutineScope {

    lateinit var job: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        job = Job()

        // 设置 start 参数为 LAZY，这样 Coroutine 不会自动安排执行，当点击 FAB 的时候，调用 job.start 后才会执行
        job = launch(start = CoroutineStart.LAZY) {
            Log.d("jbjb", "launch coroutine : ${Thread.currentThread().name}")
            background()
            tv_name_value.setText(Date().toLocaleString())
        }

        btn_skip.setOnClickListener {
//            loadDataFromUI()
            // 启动 job 这个 Coroutine 实例
            job.start()
        }
    }

    suspend fun background() {
        withContext(BG) {
            Thread.sleep(3000)
            Log.d("jbjb", "background() called ${Thread.currentThread().name}")
        }
    }



    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onDestroy() {
        super.onDestroy()
        // 当 Activity 销毁的时候取消该 Scope 管理的 job。
        // 这样在该 Scope 内创建的子 Coroutine 都会被自动的取消。
        job.cancel()
    }

    /*
     * 注意 coroutine builder 的 scope， 如果 activity 被销毁了或者该函数内创建的 Coroutine
     * 抛出异常了，则所有子 Coroutines 都会被自动取消。不需要手工去取消。
     */
    private fun loadDataFromUI() = launch { // <- 自动继承当前 activity 的 scope context，所以在 UI 线程执行
        val ioData = async(Dispatchers.IO) { // <- launch scope 的扩展函数，指定了 IO dispatcher，所以在 IO 线程运行
            // 在这里执行阻塞的 I/O 耗时操作
            Log.e("jbjb","thread:"+Thread.currentThread().name)
            Thread.sleep(5000)
            return@async "5秒后成功获取结果"
        }
        // 和上面的并非 I/O 同时执行的其他操作
        val data = ioData.await() // 等待阻塞 I/O 操作的返回结果
        draw(data) // 在 UI 线程显示执行的结果
    }

    private fun draw(data: String) {
        tv_name_value.setText(data)
    }

}