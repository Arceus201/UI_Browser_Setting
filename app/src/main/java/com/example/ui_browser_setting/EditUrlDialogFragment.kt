import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.ui_browser_setting.databinding.DialogEditUrlBinding

class EditUrlDialogFragment : DialogFragment() {

    interface EditUrlDialogListener {
        fun onDialogPositiveClick(position: Int, newUrl: String)
    }

    private var listener: EditUrlDialogListener? = null
    private var position: Int = 0
    private var url: String = ""

    companion object {
        private const val ARG_POSITION = "position"
        private const val ARG_URL = "url"

        fun newInstance(position: Int, url: String): EditUrlDialogFragment {
            val fragment = EditUrlDialogFragment()
            val args = Bundle()
            args.putInt(ARG_POSITION, position)
            args.putString(ARG_URL, url)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val args = arguments
        if (args != null) {
            position = args.getInt(ARG_POSITION)
            url = args.getString(ARG_URL, "")
        }

        val dialogBinding = DialogEditUrlBinding.inflate(LayoutInflater.from(context))
        val editTextUrl = dialogBinding.editTextUrl
        editTextUrl.setText(url)

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .create()

        dialogBinding.buttonOk.setOnClickListener {
            val newUrl = editTextUrl.text.toString()
            if (newUrl.isNotEmpty()) {
                listener?.onDialogPositiveClick(position, newUrl)
            }
            dialog.dismiss()
        }

        dialogBinding.buttonCancel.setOnClickListener {
            dialog.dismiss()
        }

        return dialog
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? EditUrlDialogListener
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}
