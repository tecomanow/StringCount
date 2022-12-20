import java.io.BufferedOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.file.*
import java.nio.file.StandardWatchEventKinds.*
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext
import kotlin.io.path.Path
import kotlin.io.path.absolutePathString

class UnzipUtils {

    companion object {
        const val BUFFER_SIZE = 4096
    }

    fun unzip(zipFilePath: String, destPath: String){

        //executeProgramAndListenDirectory(destPath)

        try {
            val destDir = File(destPath)
            if(!destDir.exists()){
                destDir.mkdir()
            }

            val zipIn = ZipInputStream(FileInputStream(zipFilePath))
            var entry: ZipEntry? = zipIn.nextEntry

            while(entry != null){

                val filePath: String = destPath + File.separator + entry.name

                println("-----> UNZIPPING ${entry.name} TO ${File(filePath).absolutePath}")

                if(!entry.isDirectory){
                    extractFile(zipIn, filePath)
                }else{
                    File(filePath).mkdir()
                }

                zipIn.closeEntry()
                entry = zipIn.nextEntry
            }

            println("-----> FILE EXTRACTED")

        }catch (e: Exception){
            e.printStackTrace()
            println("Something went wrong: ${e.message}")
        }
    }

    private fun extractFile(zipIn: ZipInputStream, filePath: String) {

        try {
            val bos = BufferedOutputStream(FileOutputStream(filePath))
            val bytesIn = ByteArray(BUFFER_SIZE)
            var read = 0

            do {
                bos.write(bytesIn, 0, read)
                read = zipIn.read(bytesIn)
            }while (read > 0)

            bos.close()

        }catch (e: Exception){
            e.printStackTrace()
        }


    }

}