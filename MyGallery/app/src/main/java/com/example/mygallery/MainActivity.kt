package com.example.mygallery

import android.Manifest
import android.content.ContentUris
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {

    private val REQUEST_READ_EXTERNAL_STORAGE = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //권한 검사
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED //앱에 권한이 부여된 것인지 검사 (Manifest에 추가했는지 검사하는 것?)
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    //사용자가 권한 요청을 명시적 거부한 경우 true
                    //사용자가 권한 요청을 처음 보거나, 다시 묻지 않음 선택, 권한 허용한 경우 false
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            ) {
                var dlg = AlertDialog.Builder(this)
                dlg.setTitle("권한이 필요한 이유")
                dlg.setMessage("사진 정보를 얻기 위해서는 외부 저장소 권한이 필수로 필요합니다.")
                dlg.setPositiveButton("확인") { dialog, which ->
                    ActivityCompat.requestPermissions(
                        this@MainActivity,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        REQUEST_READ_EXTERNAL_STORAGE
                    )
                }
                dlg.setNegativeButton("취소", null)
                dlg.show()
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_READ_EXTERNAL_STORAGE
                )
            }
        } else {
            getAllPhotos()
        }
    }

    private fun getAllPhotos() {
        val fragments = mutableListOf<Fragment>()
        val query = contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null, null, null, MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC"
        )

        query?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)

                val contentUri = ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    id
                )

                fragments.add(PhotoFragment.newInstance(contentUri))
            }
        }


        /*val fragments = ArrayList<Fragment>()
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val uri =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                Log.d("MainActivity", uri)
                fragments.add(PhotoFragment.newInstance(uri))
            }
            cursor.close()
        }*/


        var viewPager: ViewPager = findViewById(R.id.viewPager)
        val adapter = MyPagerAdapter(supportFragmentManager)
        adapter.updateFragments(fragments)
        viewPager.adapter = adapter

        timer(period = 3000) {
            runOnUiThread {
                if (viewPager.currentItem < adapter.count - 1) {
                    viewPager.currentItem = viewPager.currentItem + 1
                } else {
                    viewPager.currentItem = 0
                }
            }
        }
    }
}