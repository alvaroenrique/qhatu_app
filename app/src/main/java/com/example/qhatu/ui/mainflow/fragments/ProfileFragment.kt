package com.example.qhatu.ui.mainflow.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.qhatu.R
import com.example.qhatu.domain.ProfileUseCase
import com.example.qhatu.ui.mainflow.activities.MainActivity
import com.example.qhatu.ui.model.UserInfo

import com.example.qhatu.viewmodel.MainActivityViewModel
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_profile.*
import java.lang.Exception


class ProfileFragment : Fragment() {

    private val REQUEST_IMAGE_CAPTURE = 100
    private lateinit var model: MainActivityViewModel
    private lateinit var profileUserCase: ProfileUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model = ViewModelProvider((activity as MainActivity)).get(MainActivityViewModel::class.java)
        profileUserCase = ProfileUseCase(model, (activity as MainActivity))

        profilePurchaseListLink.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_profileFragment_to_purchaseListFragment)
        }

        profileChangePhotoField.setOnClickListener {
            takePictureIntent()
        }

        profileButtonUpdateData.setOnClickListener {
            profileUserCase.updateUserProfileData(
                profileNameInput.text.toString(),
                profileLastNameInput.text.toString(),
                profilePhoneInput.text.toString().toLong(),
                profileEmailInput.text.toString()
            )
        }

        setUserProfileData()

        setUserPicture()

    }

    private fun takePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { pictureIntent ->
            pictureIntent.resolveActivity(activity?.packageManager!!)?.also {
                startActivityForResult(pictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap

            profileUserCase.uploadImageAndSaveUri(imageBitmap)
            profilePictureProgressBar.visibility = View.VISIBLE
        }
    }

    private fun setUserProfileData() {
        val currentUser = model.getUser().value

        profileNameInput.setText(currentUser?.nombre)
        profileLastNameInput.setText(currentUser?.apellidos)
        profilePhoneInput.setText("${currentUser?.celular ?: ""}")
        profileEmailInput.setText(currentUser?.mail)
        profileDirectionField.text = currentUser?.domicilio

        model.getUser().observe(viewLifecycleOwner, Observer<UserInfo> { newName ->
            profileNameInput.setText(newName.nombre)
            profileLastNameInput.setText(newName.apellidos)
            profilePhoneInput.setText("${newName.celular}")
            profileEmailInput.setText(newName.mail)
            profileDirectionField.text = newName.domicilio
        })


    }

    private fun setUserPicture() {
        Picasso.get()
            .load(model.getUserPicture().value)
            .into(circularImageView, object : Callback {
                override fun onSuccess() {
                    profilePictureProgressBar.visibility = View.GONE
                }

                override fun onError(e: Exception?) {
                }
            })

        model.getUserPicture().observe(viewLifecycleOwner, Observer<Uri> { newUserPicture ->
            Picasso.get()
                .load(newUserPicture)
                .into(circularImageView, object : Callback {
                    override fun onSuccess() {
                        profilePictureProgressBar.visibility = View.GONE
                    }

                    override fun onError(e: Exception?) {
                    }
                })
        })
    }


}