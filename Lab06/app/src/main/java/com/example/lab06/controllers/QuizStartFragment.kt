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
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.lab06.R
import com.example.lab06.models.MyViewModel


class QuizStartFragment : Fragment() {

    lateinit var startButton: Button
    lateinit var nameEditText: EditText
    lateinit var contactsButton: Button

    private lateinit var myViewModel : MyViewModel
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

            //getting the reference to the activity's viewModel
            myViewModel = ViewModelProvider(requireActivity()).get(MyViewModel::class.java)
            /*if (myViewModel.getPlayerName() != "") {
                contactsButton.setVisibility(View.INVISIBLE)
                nameEditText.setVisibility(View.INVISIBLE)
            }*/
        }
        return view
    }

    /*private fun initViewModel(view : View) {
        val quizController = QuizController(view)
        myViewModel.setQuestions(quizController.questions)
    }*/

    private fun registerListeners(view: View){
        startButton.setOnClickListener{
            //Log.i(ContentValues.TAG, playerName.text.toString())
            if (nameEditText.text.toString() != ""){
                myViewModel.setPlayerName(nameEditText.text.toString())
                myViewModel.resetHighScore()
            }

            if (myViewModel.getPlayerName() != "") {

                findNavController().navigate(R.id.action_quizStart_to_questionFragment)
            }
            else{
                val toast = Toast.makeText(requireContext(), "type in a name", Toast.LENGTH_LONG)
                toast.show()

            }


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


}