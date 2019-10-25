package dev.reeve.discordintegration

import tornadofx.*
import java.util.concurrent.atomic.AtomicBoolean

class MainView : View("My View") {
	val model: DiscordIntegrationModel by inject()
	var thread: Thread = Thread()


	override fun onUndock() {
		super.onUndock()
		running = AtomicBoolean(false)
	}

	override val root = form {
		title = "Discord RPC"
		fieldset("Discord Integration") {
			field("Application Key") {
				textfield(model.applicationKey) {
					if (config.containsKey(APPLICATION_KEY)) {
						text = config.get(APPLICATION_KEY) as String
					}
					required()
				}
			}
			field("Details") {
				textfield(model.details) {
					with(config) {
						if (containsKey(DETAILS)) {
							text = get(DETAILS) as String
						}
					}
				}
			}
			field("State") {
				textfield(model.state) {
					with(config) {
						if (containsKey(STATE)) {
							text = get(STATE) as String
						}
					}
				}
			}
			field("Large Image Key") {
				textfield(model.largeImageKey) {
					with(config) {
						if (containsKey(LARGE_IMAGE_KEY)) {
							text = get(LARGE_IMAGE_KEY) as String
						}
					}
				}
			}
			field("Large Image Text") {
				textfield(model.largeImageText) {
					with(config) {
						if (containsKey(LARGE_IMAGE_TEXT)) {
							text = get(LARGE_IMAGE_TEXT) as String
						}
					}
				}
			}
		}
		button("Save") {
			action {
				RPC(config, thread, model)
			}
			enableWhen(model.valid)
		}
	}

	companion object {
		val APPLICATION_KEY = "APPLICATION_KEY"
		val DETAILS = "DETAILS"
		val STATE = "STATE"
		val LARGE_IMAGE_KEY = "LARGE_IMAGE_KEY"
		val LARGE_IMAGE_TEXT = "LARGE_IMAGE_TEXT"
		var running = AtomicBoolean(true)
	}
}
