package watch

import callback.OnCompletedCalculatorListener
import java.io.File
import java.net.URI
import java.nio.file.*
import kotlin.io.path.absolutePathString
import kotlin.io.path.name
import java.nio.file.Path.*

class WatchFolder(private val listener: OnCompletedCalculatorListener) {

    var watchService: WatchService = FileSystems.getDefault().newWatchService()
    //var listener: OnCompletedCalculatorListener

    fun watchFolder(folderPath: String) {

        val path = Paths.get(folderPath)
        println(path.absolutePathString())

        try {

            var isResultFound = false;

            val key: WatchKey = path.register(
                watchService,
                StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_MODIFY,
                StandardWatchEventKinds.ENTRY_DELETE
            )

            while (!isResultFound) run {

                //println("RODANDO WHILE")

                key.pollEvents().forEach { event ->

                    //println("RODANDO FOR")


                    val pathEvent: WatchEvent<Path> = event as WatchEvent<Path>

                    val fileName = pathEvent.context()
                    val kind: WatchEvent.Kind<*> = event.kind()

                    if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                        if(fileName.name == "resultado.txt"){
                            //println("PROGRAMA P/ EXECUTAR")
                            isResultFound = true;
                            listener.onComplete(Paths.get(path.absolutePathString() + File.separator + fileName.name))
                            println(Paths.get(path.absolutePathString() + File.separator + fileName.name).toString())
                        }
                        println("A NEW FILE WAS CREATED: $fileName")
                    }

                    if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
                        if(fileName.name == "resultado.txt"){
                            //println("PROGRAMA P/ EXECUTAR")
                            isResultFound = true;
                            listener.onComplete(Paths.get(path.absolutePathString() + File.separator + fileName.name))
                            println(Paths.get(path.absolutePathString() + File.separator + fileName.name).toString())
                        }
                        println("A NEW FILE WAS MODIFIED: $fileName")
                    }

                }

                val valid: Boolean = key.reset()
                if (!valid) {
                    return
                }

            }

            //println("SAI DO WHILE WHILE")

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

}

