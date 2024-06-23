package com.example.filestorage

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class WriteExternalActivity : AppCompatActivity() {
    private lateinit var binding:ActivityWriteExternalBinding
    val PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = ActivityWriteExternalBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.externalSaveBtn.setOnClickListener {
            // Check WRITE PERMISSION

            val permissionCheck = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                proceedToWrite(this)

            } else {

                ActivityCompat.requestPermissions(this,

                    arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),

                    PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE)

            }
        }

    }

    override fun onRequestPermissionsResult(

        requestCode: Int,

        permissions: Array<out String>,

        grantResults: IntArray

    ) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode){

            PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE -> {

                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    proceedToWrite(this)
                } else {
                    Log.d("debug","Something is wrong, permission not granted")
                }

                return

            }

        }

    }

    private fun proceedToWrite(context: Context) {
        val fileName = binding.externalFileNameEditText.text.toString()
        val content = binding.externalContentEditText.text.toString()

        try {
            // Kalau saya ada readable SD Card
            if (CommonUtil.isReadable()) {
                val fullPath = Environment.getExternalStorageDirectory().absolutePath
                // Dapatkan path filename iaitu path sdcard + fileName
                val myFile = File(fullPath+ File.separator+"/"+fileName)

                val fOut = FileOutputStream(myFile)

                // Write inside the file, "content"
                val myOutWriter = OutputStreamWriter(fOut)
                myOutWriter.append(content)
                myOutWriter.close()
                fOut.close()
                Toast.makeText(
                    context,
                    "File written to External memory",
                    Toast.LENGTH_LONG
                ).show()

            }
        }
        catch (e:Exception){

}
    }
}