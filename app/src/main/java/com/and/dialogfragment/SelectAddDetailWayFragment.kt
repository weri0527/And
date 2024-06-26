package com.and.dialogfragment

import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.and.databinding.FragmentSelectAddDetailWayBinding
import com.and.datamodel.DrugDataModel
import com.and.viewModel.UserDataViewModel

class SelectAddDetailWayFragment : DialogFragment() {
    private var _binding: FragmentSelectAddDetailWayBinding? = null
    private val binding get() = _binding!!
    private val userDataViewModel: UserDataViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectAddDetailWayBinding.inflate(inflater, container, false)
        val selectedCategory = arguments?.getParcelable("selectedList", DrugDataModel::class.java) ?: DrugDataModel()
        binding.apply {
            writeBtn.setOnClickListener {
                val writeDialogFragment = WriteDialogFragment()
                writeDialogFragment.clickYesListener = WriteDialogFragment.OnClickYesListener {
                    try {
                        userDataViewModel.addDetail(selectedCategory, listOf(it))
                    } catch (e: Exception) {
                        return@OnClickYesListener
                    } finally {
                        dismiss()
                    }
                }
                writeDialogFragment.show(requireActivity().supportFragmentManager, "writeDetail")
            }
        }

        isCancelable = true
        this.dialog?.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        this.dialog?.window!!.setGravity(Gravity.BOTTOM)
        this.dialog?.window!!.attributes.y = 40
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        resizeDialog()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun resizeDialog() {
        val params: ViewGroup.LayoutParams? = this.dialog?.window?.attributes
        val deviceWidth = Resources.getSystem().displayMetrics.widthPixels
        params?.width = (deviceWidth * 0.95).toInt()
        this.dialog?.window?.attributes = params as WindowManager.LayoutParams
    }
}