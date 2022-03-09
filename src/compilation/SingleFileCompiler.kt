package compilation

import java.io.File
import java.io.InputStream
import java.io.PrintWriter
import java.nio.file.Paths

var root = "./src"
var mainFilePath = "./src/compilation/Main.kt"
var extensionsFilePath = "./src/compilation/Extensions.kt"
var outputCompiledFilePath = "./src/CompiledMain.kt"

fun main() {
    println(Paths.get("").toAbsolutePath().toString())
    val mainFile = File(mainFilePath)
    val extensionFile = File(extensionsFilePath)
    val compiledFile = File(outputCompiledFilePath)
    compiledFile.delete()
    compiledFile.createNewFile()

    val unknonwImports = HashSet<String>()
    val importedFiles = HashMap<String, File>()

    val imports = mutableListOf<String>()
    imports.addAll(detectImports(mainFile))
    imports.addAll(detectImports(extensionFile))

    while(imports.isNotEmpty()) {
        val import = imports.removeFirst()
        val file = getFileFromImport(import)
        if (file != null) {
            imports.addAll(detectImports(file))
            println("Imported $file for $import")
            importedFiles.put(import, file)
        } else {
            unknonwImports.add(import)
        }
    }

    compiledFile.printWriter().use { out ->
        unknonwImports.forEach { import ->
            out.println("import $import")
        }
        printClassWithoutPackageNorImport(mainFile, out)
        printClassWithoutPackageNorImport(extensionFile, out)
        importedFiles.values.forEach { file ->
            printClassWithoutPackageNorImport(file, out)
        }
    }
}

fun printClassWithoutPackageNorImport(classFile: File , out: PrintWriter) {
    val inputStream: InputStream = classFile.inputStream()
    inputStream.bufferedReader().forEachLine { line ->
        if (!line.startsWith("import") && !line.startsWith("package")) {
            out.println(line)
        }
    }
}

fun detectImports(file: File): List<String> {
    var result = mutableListOf<String>()
    val inputStream: InputStream = file.inputStream()
    inputStream.bufferedReader().forEachLine { line ->
        if (line.startsWith("import")) {
            result.add(line.split(" ")[1])
        }
    }
    return result
}

fun getFileFromImport(import: String): File? {
    var keys = import.split(".")
    var currentDir = root
    var lastFile = false
    for(key in keys) {
        if (key[0] in 'A'..'Z') {
            if (File("$currentDir/$key.kt").exists()) {
                return File("$currentDir/$key.kt")
            }
        }

        if (File("$currentDir/$key").exists()) {
            currentDir = "$currentDir/$key"
        } else {
            return null
        }
    }
    return null
}
