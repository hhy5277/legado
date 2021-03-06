package io.legado.app.utils

// import org.apache.commons.text.StringEscapeUtils

fun String?.safeTrim() = if (this.isNullOrBlank()) null else this.trim()

fun String?.isContentPath(): Boolean = this?.startsWith("content://") == true

fun String?.isAbsUrl() =
    this?.let {
        it.startsWith("http://", true)
                || it.startsWith("https://", true)
    } ?: false

fun String?.isJson(): Boolean =
    this?.run {
        val str = this.trim()
        when {
            str.startsWith("{") && str.endsWith("}") -> true
            str.startsWith("[") && str.endsWith("]") -> true
            else -> false
        }
    } ?: false

fun String?.isJsonObject(): Boolean =
    this?.run {
        val str = this.trim()
        str.startsWith("{") && str.endsWith("}")
    } ?: false

fun String?.isJsonArray(): Boolean =
    this?.run {
        val str = this.trim()
        str.startsWith("[") && str.endsWith("]")
    } ?: false

fun String?.htmlFormat(): String =
    this?.replace("(?i)<(br[\\s/]*|/*p\\b.*?|/*div\\b.*?)>".toRegex(), "\n")
        ?.replace("<[script>]*.*?>|&nbsp;".toRegex(), "")
        ?.replace("\\s*\\n+\\s*".toRegex(), "\n　　")
        ?.replace("^[\\n\\s]+".toRegex(), "　　")
        ?.replace("[\\n\\s]+$".toRegex(), "")
        ?: ""

fun String.splitNotBlank(vararg delimiter: String): Array<String> = run {
    this.split(*delimiter).map { it.trim() }.filterNot { it.isBlank() }.toTypedArray()
}

fun String.splitNotBlank(regex: Regex, limit: Int = 0): Array<String> = run {
    this.split(regex, limit).map { it.trim() }.filterNot { it.isBlank() }.toTypedArray()
}


