package com.example.lab06.controllers

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.example.lab06.R
import com.example.lab06.models.MyViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [QuizStartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class QuizStartFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var startButton: Button
    lateinit var nameEditText: EditText
    lateinit var contactsButton: Button

    private val myViewModel : MyViewModel by activityViewModels()
    //contact permission code
    private val CONTACT_PERMISSION_CODE = 1;


    @SuppressLint("Range")
    private val getPerson = registerForActivityResult(PickContact()) {
        it?.also{ contactUri ->
            val projection = arrayOf(
                ContactsContract.CommonDataKinds.Phone.LOOKUP_KEY,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NORMALIZED_NUMBER
            )

            requireActivity().contentResolver.query(contactUri, projection, null, null, null)?.apply{
                moveToFirst()
                val chosenName = this.getString(getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                nameEditText.setText(chosenName)
                myViewModel.setPlayerName(chosenName)
                myViewModel.resetHighScore()
                Log.i("xxx", "NAME: $chosenName")
                close()
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(
            true // default to enabled
        ) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.homeFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            this,  // LifecycleOwner
            callback
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_quiz_start, container, false)
        view?.apply {
            initializeView(this)
            registerListeners(this)
            if (myViewModel.getPlayerName() != ""){
                contactsButton.setVisibility(View.INVISIBLE)
                nameEditText.setVisibility(View.INVISIBLE)
            }
        }
        return view
    }

    private fun registerListeners(view: View){
        startButton.setOnClickListener{
            //Log.i(ContentValues.TAG, playerName.text.toString())
            findNavController().navigate(R.id.action_quizStart_to_questionFragment)
        }
    }


    class PickContact : ActivityResultContract<Int, Uri?>(){
        override fun createIntent(context: Context, input: Int?)=
            Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI).also{
                it.type = ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE
            }


        override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
            return if (resultCode == AppCompatActivity.RESULT_OK) intent?.data else null

        }
    }


    private fun initializeView(view: View){
        nameEditText = view.findViewById(R.id.editTextName)
        startButton = view.findViewById(R.id.startButton)
        contactsButton = view.findViewById(R.id.contactsButton)
        contactsButton.setOnClickListener {
            //check permission allowed or not
            if (checkContactPermission()){
                //allowed
                getPerson.launch(0)
            }
            else{
                //not allowed, request
                requestContactPermission()
            }
        }
    }

    private fun checkContactPermission(): Boolean{
        //check if permission was granted/allowed or not, returns true if granted/allowed, false if not
        return  ContextCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.READ_CONTACTS
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestContactPermission(){
        //request the READ_CONTACTS permission
        val permission = arrayOf(android.Manifest.permission.READ_CONTACTS)
        ActivityCompat.requestPermissions(requireActivity(), permission, CONTACT_PERMISSION_CODE)

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment QuizStart.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            QuizStartFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}