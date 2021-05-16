package com.india.rentzgo

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.india.rentzgo.ui.fragments.postFragments.PropertyTypeFragment
import com.india.rentzgo.ui.fragments.postFragments.individualRooms.IndividualRoomFragment
import java.io.ByteArrayOutputStream


class MainActivityTest : AppCompatActivity() {
    val IMAGE_CAPTURE_CODE = 1001
    val PERMISSION_CODE: Int = 1000
    lateinit var imageUri: Uri
    private var dotCount = 0
    private var myList = arrayListOf<Int>()
    val REQUEST_TAKE_PHOTO = 1
    lateinit var currentPhotoPath: String
    lateinit var imageClickButton: ImageView
    lateinit var imageAdapter: ImageAdapter
    lateinit var pager: ViewPager
    private var linearLayoutList = arrayOfNulls<ImageView>(0)
    private var myImages = ArrayList<Bitmap>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_test)

        myList.add(R.raw.photo_uploading)
//        setAdapter()
        imageAdapter = ImageAdapter
        pager = findViewById(R.id.viewPage)
        pager.adapter = imageAdapter
        val layoutInflater = this.layoutInflater
        val view = layoutInflater.inflate(R.layout.layout_image_click, null)
        ImageAdapter.thisList = ArrayList()
        imageAdapter.addView(view, 0)
        imageAdapter.notifyDataSetChanged()

        setIndicator()
        imageClickButton = findViewById(R.id.camera)

        val nextButton = findViewById<ImageView>(R.id.next)

        nextButton.setOnClickListener {
            val uid = FirebaseAuth.getInstance().currentUser.uid
            val filePath = FirebaseStorage.getInstance().getReference().child("Images").child(uid)
            val list = imageAdapter.thisList

            if(list.size != 1) {
                for (item in 1..(list.size - 1)) {
                    val view = list.get(item)
                    val imageView = view.findViewById<ImageView>(R.id.imageMine)
                    val baos = ByteArrayOutputStream()
                    imageView.invalidate()
                    val drawable = imageView.drawable as BitmapDrawable
                    val bitmap = drawable.bitmap
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos)
                    val array = baos.toByteArray()
                    val uploadTask = filePath.child(item.toString()).putBytes(array)
                }
            }
            val postActivityIntent = Intent(this, PostActivity::class.java)
            postActivityIntent.putExtra("IndividualRoom", "1")
            startActivity(postActivityIntent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        imageClickButton.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED || checkSelfPermission(
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) == PackageManager.PERMISSION_DENIED
                ) {
                    requestPermissions(
                        arrayOf(
                            Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ), PERMISSION_CODE
                    )
                } else {
                    openCamera()
                }
            } else {
                openCamera()
            }
        }
    }

    private fun setIndicator() {
        var linearLayout = findViewById<LinearLayout>(R.id.linearLayout)
        linearLayout.removeAllViews()

        dotCount = imageAdapter.count
        linearLayoutList = arrayOfNulls<ImageView>(dotCount)
        var size1 = linearLayoutList.size

        Log.i("size of the array", dotCount.toString())


        for (item in 0..dotCount - 1) {
            linearLayoutList[item] = ImageView(this)
            linearLayoutList[item]?.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.checkbox_blank_circle_outline
                )
            )
            var params =
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                } else {
                    TODO("VERSION.SDK_INT < KITKAT")
                }
            params.setMargins(4, 0, 4, 0)

            linearLayout.addView(linearLayoutList[item], params)
        }
        linearLayoutList[0]?.setImageDrawable(
            ContextCompat.getDrawable(
                applicationContext,
                R.drawable.checkbox_blank_circle
            )
        )
        pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                var size = linearLayoutList.size
                for (item in 0..dotCount - 1) {
                    linearLayoutList[item]?.setImageDrawable(
                        ContextCompat.getDrawable(
                            applicationContext,
                            R.drawable.checkbox_blank_circle_outline
                        )
                    )
                }
                linearLayoutList[position]?.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.checkbox_blank_circle
                    )
                )
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    fun getList(): ArrayList<Int> {
        return this.myList
    }

    private fun openCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the camera")
        imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)!!
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        startActivityForResult(intent, IMAGE_CAPTURE_CODE)
    }

    fun addView(newPage: View) {
        val pageIndex = imageAdapter.addView(newPage)
//        pager.setCurrentItem(pageIndex, true)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera()
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            val layoutInflater = this.layoutInflater
            val view = layoutInflater.inflate(R.layout.layout_image_click, null)
            val imageView = view.findViewById<ImageView>(R.id.imageMine)
            imageView.setImageURI(null)
            imageView.setImageURI(imageUri)
            imageAdapter.addView(view)
            imageAdapter.notifyDataSetChanged()
            setIndicator()
        }
    }
}