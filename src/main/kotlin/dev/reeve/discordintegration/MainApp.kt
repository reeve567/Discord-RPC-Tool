package dev.reeve.discordintegration

import javafx.stage.Stage
import tornadofx.*

class MainApp : App(MainView::class) {
	val mainView: MainView by inject()
}