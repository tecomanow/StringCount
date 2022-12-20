package callback

import java.nio.file.Path

interface OnCompletedCalculatorListener {
    fun onComplete(path: Path)
}