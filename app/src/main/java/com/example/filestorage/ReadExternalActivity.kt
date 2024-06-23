package com.example.filestorage

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ReadExternalActivity : AppCompatActivity() {

    private lateinit var binding : ActivityReadExternalBinding;

    val fileName = "ExtFile.txt" // The book hardcode the filename to ExtFile.txt
    // AS homework retrieve the filename from an EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadExternalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fileContent = readFileExternalStorage(fileName)
        binding.externalMessageTextView.text = fileContent

    }

    private fun readFileExternalStorage(fileName: String): String {
        var result = ""
        try {
            // CHeck dahulu phone ini ada atau tidak external storage
            if (CommonUtil.isReadable()) {
                // Dapatkan path untuk sdcard tersebut
                val sdcard = Environment.getExternalStorageDirectory()
                // Dapatkan path untuk filename (ExtFile.txt)
                val file = File(sdcard, fileName)

                // Baca file dari path yang diberi
                val br = BufferedReader(FileReader(file))
                result = br.use(BufferedReader::readText)
            }
        }
        catch ( e: IOException) {
            print(e.message)
        }
        return result
}
    }
}