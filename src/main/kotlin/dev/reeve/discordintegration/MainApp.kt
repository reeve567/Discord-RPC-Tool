package dev.reeve.discordintegration

import tornadofx.*

fun main(args: Array<String>) {
	launch<MainApp>(args)
}

class MainApp : App(MainView::class) {
	val mainView: MainView by inject()
}