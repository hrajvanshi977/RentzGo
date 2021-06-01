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
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.airbnb.lottie.LottieAnimationView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import single.LatitudeLongitude
import java.io.ByteArrayOutputStream


class MainActivityTest : AppCompatActivity() {
    val IMAGE_CAPTURE_CODE = 1001
    val PERMISSION_CODE: Int = 1000
    lateinit var imageUri: Uri
    private var dotCount = 0
    private var myList = arrayListOf<Int>()
    lateinit var imageClickButton: ImageView
    lateinit var imageAdapter: ImageAdapter
    lateinit var pager: ViewPager
    var flag = 0
    var count = 0
    private var linearLayoutList = arrayOfNulls<ImageView>(0)

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private lateinit var lottianimation: LottieAnimationView
    private lateinit var textView: TextView

    private fun initialization() {
        imageAdapter = ImageAdapter
        pager = findViewById(R.id.viewPage)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_test)
        initialization()
        saveCurrentLocation()
        myList.add(R.raw.photo_uploading)
        pager.adapter = imageAdapter
        val layoutInflater = this.layoutInflater
        val view = layoutInflater.inflate(R.layout.layout_image_click, null)
        lottianimation = view.findViewById(R.id.animation)
        textView = view.findViewById(R.id.uploadHomeImageText)
        textView.text = "Uploading more photos increases chances of renting your property!"
        ImageAdapter.thisList = ArrayList()
        imageAdapter.addView(view, 0)
        imageAdapter.notifyDataSetChanged()
        setIndicator()
        imageClickButton = findViewById(R.id.camera)
        val nextButton = findViewById<ImageView>(R.id.submitHomeImages)
        nextButton.setOnClickListener {
            if (flag == 0) {
                val builder = android.app.AlertDialog.Builder(this, R.style.AlertDialogStyle)
                builder.setMessage("Please Upload atleast one photo of your property!")
                builder.setCancelable(true)
                builder.setPositiveButton(
                    "ok"
                ) { dialog, id -> dialog.cancel() }
                val alert = builder.create()
                alert!!.show()
            } else {
                val list = imageAdapter.thisList
                if (list.size != 0) {
                    CurrentPostingProperty.images.clear()
                    for (item in 0 until list.size) {
                        val view = list[item]
                        val imageView = view.findViewById<ImageView>(R.id.imageMine)
                        val baos = ByteArrayOutputStream()
                        imageView.invalidate()
                        val drawable = imageView.drawable as BitmapDrawable
                        val bitmap = drawable.bitmap
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos)
                        val array = baos.toByteArray()
//                        val uploadTask = filePath.child(item.toString()).putBytes(array)
                        CurrentPostingProperty.images.add(array)
                    }
                }
                val postActivityIntent = Intent(this, PostActivity::class.java)
                postActivityIntent.putExtra("IndividualRoom", "1")
                startActivity(postActivityIntent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
        }

        imageClickButton.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED || checkSelfPermission(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
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
        linearLayoutList = arrayOfNulls(dotCount)

        Log.i("size of the array", dotCount.toString())

        for (item in 0 until dotCount) {
            linearLayoutList[item] = ImageView(this)
            linearLayoutList[item]?.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.inactive_image_circle
                )
            )
            var params =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
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
        linearLayoutList[imageAdapter.thisList.size - 1]?.setImageDrawable(
            ContextCompat.getDrawable(
                applicationContext,
                R.drawable.active_image_circle
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
                for (item in 0 until dotCount) {
                    linearLayoutList[item]?.setImageDrawable(
                        ContextCompat.getDrawable(
                            applicationContext,
                            R.drawable.inactive_image_circle
                        )
                    )
                }
                linearLayoutList[position]?.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.active_image_circle
                    )
                )
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })
        pager.setCurrentItem(count - 1)
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

    private fun saveCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationProviderClient.lastLocation.addOnSuccessListener {
            if (it != null) {
                LatitudeLongitude.latitude = it.latitude
                LatitudeLongitude.longitude = it.longitude
            }
        }
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
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
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
            lottianimation = view.findViewById(R.id.animation)
            lottianimation.visibility = View.GONE
            val imageView = view.findViewById<ImageView>(R.id.imageMine)
            imageView.setImageURI(null)
            imageView.setImageURI(imageUri)

            if (flag == 0) {
                imageAdapter.setView(0, view)
                pager.adapter = imageAdapter
                flag++
            } else {
                imageAdapter.addView(view)
            }
            count++
            imageAdapter.notifyDataSetChanged()
            setIndicator()
        }
    }
}