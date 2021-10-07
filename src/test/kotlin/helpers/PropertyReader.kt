package helpers

import java.io.FileInputStream
import java.io.IOException
import java.util.*
import java.util.logging.Logger
import kotlin.system.exitProcess

val log: Logger = Logger.getLogger("logger")

/**
 * Read properties : Read properties from a specific location according to environment
 * Environment Variables: staging or test for now.
 *
 * @return
 */
fun readProperties(): Properties {
    val properties = Properties()
    val environment = getEnvironment()
    try {
        FileInputStream("src/test/resources/environment/$environment/config/configuration.properties").use { properties.load(it) }
    } catch (e: IOException) {
        log.severe("Error thrown: ${e.localizedMessage}")
        exitProcess(1)
    }
    return properties
}

/**
 * Get environment : Getting environment variable from the runner.
 * Use -Denv = <environment Name> to provide the variable.
 * Default return value 'test' (If no value is given)
 *
 * @return
 */
fun getEnvironment(): String = if (System.getProperty("env") == null) {
    "test"
} else {
    System.getProperty("env")
}