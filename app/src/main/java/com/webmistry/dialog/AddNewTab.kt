package com.webmistry.dialog

import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

//private const val TAB_TITLE = "TAB_TITLE"
//private const val TAB_URL = "TAB_URL"

@AndroidEntryPoint
class AddNewTab @Inject constructor() : BottomSheetDialogFragment() {
//    private lateinit var binding: AddNewTabBinding
//    private lateinit var permission: ActivityResultLauncher<String>
//    private lateinit var gallery: ActivityResultLauncher<String>
//    private var image: Uri? = null
//
//    private var tabUrl: String? = null
//    private var tabTitle: String? = null

    /* override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddNewTabBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gallery = registerForActivityResult(ActivityResultContracts.GetContent()) {
            if (it != null) {
                webTabViewModel.setImage(
                    Image(
                        it,
                        View.INVISIBLE,
                        "Tab icon uploaded",
                        android.R.color.black
                    )
                )
            } else {
                if (image == null) {
                    Toast.makeText(requireContext(), "Image Not Selected....", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }


        permission = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                gallery.launch("image/*")
            } else {
                Toast.makeText(requireContext(), "Permission Required*", Toast.LENGTH_SHORT).show()
            }
        }

        webTabViewModel.getImage().observe(requireActivity()) {
            binding.apply {
                image = it.uri
                tabImage.setImageURI(it.uri)
                imageErrorTextView.text = it.imageErrorText
                addIconAndTextViewGroup.visibility = it.visibilityMode
            }
        }

        binding.apply {
            webTabTitleTextInputEditText.doOnTextChanged { text, _, _, _ ->
                tabTitle = validateTabTitle(text?.trim().toString(), webTabTitleTextInputLayout)
            }
            webTabURLTextInputEditText.doOnTextChanged { text, _, _, _ ->
                tabUrl = validateTabURL(text?.trim().toString(), webTabURLTextInputLayout)
            }
        }
        val containTabTitle = savedInstanceState?.getString(TAB_TITLE)
        val containTabUrl = savedInstanceState?.getString(TAB_URL)
        if (containTabTitle != null) {
            tabTitle = savedInstanceState.getString(TAB_TITLE)
        }
        if (containTabUrl != null) {
            tabUrl = savedInstanceState.getString(TAB_URL)
        }

        binding.apply {
            imageCardView.setOnClickListener {
                permission.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }

            saveNewTabButton.setOnClickListener {
                if (image == null) {
                    webTabViewModel.setImage(
                        Image(
                            image,
                            View.VISIBLE,
                            "Tab icon can't be empty",
                            android.R.color.holo_red_dark
                        )
                    )
                } else {
                     if (tabTitle==null) {
                    validateTabTitle(tabTitle,webTabTitleTextInputLayout)
                    } else if (tabUrl==null) {
                    validateTabURL(tabUrl,webTabURLTextInputLayout)
                    }else{
                        saveNewTabInRoomDatabase()
                    }
                }
            }
        }
    }

    private fun validateTabTitle(tabTitle: String?, textInputLayout: TextInputLayout): String? {
        return if (tabTitle==null) {
            textInputLayout.error = "Filed can't be empty"
            null
        } else if (tabTitle.matches(".*[!@#$%^&*()_+,>/?|:;'].*".toRegex())) {
            textInputLayout.error = "Not use special symbol(!@#\$%^&*)"
            null
        } else if (tabTitle.matches(".*[0-9].*".toRegex())) {
            textInputLayout.error = "Not use number in title"
            null
        } else {
            textInputLayout.helperText = " "
            tabTitle
        }
    }

    private fun validateTabURL(tabTitle: String?, textInputLayout: TextInputLayout): String? {
        return if (tabTitle==null) {
            textInputLayout.error = "Filed can't be empty"
            null
        } else if (!tabTitle.matches(".*[ht].*".toRegex())) {
            textInputLayout.error = "Include https:// your web url"
            null
        } else if (!tabTitle.matches(".*[tp].*".toRegex())) {
            textInputLayout.error = "Include https:// your web url"
            null
        } else if (!tabTitle.matches(".*[s:/].*".toRegex())) {
            textInputLayout.error = "Include https:// your web url"
            null
        } else if (!tabTitle.matches(".*[/].*".toRegex())) {
            textInputLayout.error = "Include https:// your web url"
            null
        } else {
            textInputLayout.helperText = " "
            tabTitle
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        Toast.makeText(requireContext(), "On Dismiss Call", Toast.LENGTH_SHORT).show()
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        Toast.makeText(requireContext(), "On Cancel Call", Toast.LENGTH_SHORT).show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (tabTitle != null) {
            outState.putString(TAB_TITLE, tabTitle)
        }
        if (tabUrl != null) {
            outState.putString(TAB_URL, tabUrl)
        }
    }

    private fun saveNewTabInRoomDatabase() {
        CoroutineScope(Dispatchers.IO).async {
            val byteArray= convertUriToBitmap(image!!)?.let {
                TypeConverter.convertBitmapToBitArray(
                    it
                )
            }
            tabDao.addNewTabs(WebTab(byteArray,tabTitle!!,tabUrl!!))
            Log.e("AdminTag", "saveNewTabInRoomDatabase: New Tab Added Successfully..", )
            dialog?.cancel()
        }
//        Toast.makeText(requireContext(), "New Tab added successfully....", Toast.LENGTH_SHORT).show()
    }

   private fun convertUriToBitmap(uri: Uri): Bitmap? {
        return MediaStore.Images.Media.getBitmap(requireActivity().contentResolver,uri)
    }

//    fun convertBitmapToUri(bitmap): Bitmap? {
//        return MediaStore.Images.Media.getBitmap(requireActivity().contentResolver,uri)
//    }*/

}
    */
}



