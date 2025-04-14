package astru43.autosave

import com.fs.starfarer.api.BaseModPlugin
import com.fs.starfarer.api.Global

const val ModId = "astru43_autosaveextended"

@Suppress("unused") // This is the mods entry point
class AutosavePlugin : BaseModPlugin() {
    private lateinit var settings: Settings
    override fun onApplicationLoad() {
        super.onApplicationLoad()
        settings = Settings()
    }

    override fun onGameLoad(newGame: Boolean) {
        super.onGameLoad(newGame)
        val saver = Saver(settings)
        Global.getSector().addTransientScript(saver)
        Global.getSector().addTransientListener(saver)
    }

}