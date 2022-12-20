package watch

import java.util.concurrent.Callable

class WatchCallable(private val folderPath: String) : Callable<Void> {

    override fun call(): Void? {
        /*val wf = WatchFolder()
        wf.watchFolder(folderPath)*/

        return null
    }

}