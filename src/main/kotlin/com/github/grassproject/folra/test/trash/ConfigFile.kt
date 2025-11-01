//`ackage com.github.grassproject.folra.util.config
//
//import com.github.grassproject.folra.api.FolraPlugin
//import org.bukkit.configuration.file.FileConfiguration
//import org.bukkit.configuration.file.YamlConfiguration
//import java.io.File
//import java.io.InputStreamReader
//
//class ConfigFile(private val plugin: FolraPlugin, private val name: String) {
//
//    private val file = File(plugin.dataFolder, name)
//    private var config: FileConfiguration = YamlConfiguration.loadConfiguration(file)
//
//    fun load(): FileConfiguration {
//        file.parentFile?.mkdirs()
//        if (!file.exists()) {
//            try { plugin.saveResource(name, false) }
//            catch (_: IllegalArgumentException) { file.createNewFile() }
//        }
//
//        plugin.getResource(name)?.let { stream ->
//            val defaults = YamlConfiguration.loadConfiguration(InputStreamReader(stream, Charsets.UTF_8))
//            config.options().copyDefaults(true)
//            config.setDefaults(defaults)
//        }
//
//        save()
//        return config
//    }
//
//    fun get(): FileConfiguration = config
//    fun save() = config.save(file)
//    fun exists(): Boolean = file.exists()
//    fun delete(): Boolean = file.delete()
//    fun getFile(): File = file
//
//}`