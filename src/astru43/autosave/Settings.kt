package astru43.autosave

import com.fs.starfarer.api.Global
import lunalib.lunaSettings.LunaSettings
import lunalib.lunaSettings.LunaSettingsListener
import org.apache.log4j.Logger

class Settings {
    private val logger: Logger = Global.getLogger(Settings::class.java)

    var autosaveOnJump: Boolean = false
        private set
    var autosaveOnTransaction: Boolean = false
        private set
    var autosaveOnBattle: Boolean = false
        private set
    var useFullSave: Boolean = false
        private set
    var fullSavePeriod: Int = 15
        private set

    init {
        loadSettings()
        val listener = Listener(::loadSettings)
        LunaSettings.addSettingsListener(listener)
    }

    private fun loadSettings() {
        autosaveOnJump = LunaSettings.getBoolean(ModId, "autosave_on_jump") == true
        autosaveOnTransaction = LunaSettings.getBoolean(ModId, "autosave_on_transaction") == true
        autosaveOnBattle = LunaSettings.getBoolean(ModId, "autosave_on_battle") == true
        useFullSave = LunaSettings.getBoolean(ModId, "use_full_save") == true
        fullSavePeriod = LunaSettings.getInt(ModId, "full_save_period") ?: 15
        logger.info("""{
            |  autosave_on_jump: $autosaveOnJump
            |  autosave_on_transaction: $autosaveOnTransaction
            |  autosave_on_battle: $autosaveOnBattle
            |  periodic_full_save: $useFullSave
            |  fullSavePeriod: $fullSavePeriod
            |}""".trimMargin())
    }

    private class Listener(private val update: (() -> Unit)) : LunaSettingsListener {
        override fun settingsChanged(modID: String) {
            if (modID != ModId) return
            update()
        }
    }
}