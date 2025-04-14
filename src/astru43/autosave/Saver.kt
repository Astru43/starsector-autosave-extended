package astru43.autosave

import com.fs.starfarer.api.EveryFrameScript
import com.fs.starfarer.api.Global
import com.fs.starfarer.api.campaign.*
import com.fs.starfarer.api.combat.EngagementResultAPI
import org.apache.log4j.Logger

class Saver(private val settings: Settings) : BaseCampaignEventListener(false), EveryFrameScript {
    private val logger: Logger = Global.getLogger(Saver::class.java)
    private var shouldSave = false

    override fun isDone(): Boolean = false

    override fun runWhilePaused(): Boolean = true

    override fun advance(amount: Float) {
        val ui = Global.getSector().campaignUI
        if (ui.isShowingMenu || ui.isShowingDialog) return
        if (shouldSave) {
            shouldSave = false
            ui.autosave()
        }
    }

    override fun reportFleetJumped(
        fleet: CampaignFleetAPI?,
        from: SectorEntityToken?,
        to: JumpPointAPI.JumpDestination?
    ) {
        if (!settings.autosaveOnJump) return
        if (fleet == null) return
        if (fleet.isPlayerFleet && from != to?.destination) {
            logger.info("AUTOSAVE: Jump triggered")
            shouldSave = true
        }
    }

    override fun reportPlayerMarketTransaction(transaction: PlayerMarketTransaction?) {
        if (!settings.autosaveOnTransaction) return
        logger.info("AUTOSAVE: Transaction triggered")
        shouldSave = true
    }

    override fun reportPlayerEngagement(result: EngagementResultAPI?) {
        if (!settings.autosaveOnBattle) return
        logger.info("AUTOSAVE: Battle result triggered")
        shouldSave = true
    }
}