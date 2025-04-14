package astru43.autosave

import com.fs.starfarer.api.Global
import lunalib.lunaSettings.LunaSettings
import lunalib.lunaSettings.LunaSettingsListener
import org.apache.log4j.Logger

class Settings {
    var autosaveOnJump: Boolean = false
        private set
    var autosaveOnTransaction: Boolean = false
        private set
    var autosaveOnBattle: Boolean = false
        private set
    private val logger: Logger = Global.getLogger(Settings::class.java)

    init {
        loadSettings()
        val listener = Listener(::loadSettings)
        LunaSettings.addSettingsListener(listener)
    }

    private fun loadSettings() {
        autosaveOnJump = LunaSettings.getBoolean(ModId, "autosave_on_jump") == true
        autosaveOnTransaction = LunaSettings.getBoolean(ModId, "autosave_on_transaction") == true
        autosaveOnBattle = LunaSettings.getBoolean(ModId, "autosave_on_battle") == true
        logger.info("""{
            |  autosave_on_jump: $autosaveOnJump
            |  autosave_on_transaction: $autosaveOnTransaction
            |  autosave_on_battle: $autosaveOnBattle
            |}""".trimMargin())
    }

    private class Listener(private val update: (() -> Unit)) : LunaSettingsListener {
        override fun settingsChanged(modID: String) {
            if (modID != ModId) return
            update()
        }
    }
}